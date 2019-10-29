import CommandTestPackage.CommandTest;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.PatientMap;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit class testing the class GsonStorage.
 */

/* @@author JacobToresson */
public class GsonStorageTest extends CommandTest {

    /**
     * A dummy patient used for testing. In this context a dummy patient refers to a patient object that only has
     * a name, a bed number and one allergy.
     */
    private Patient dummy1 = new Patient("dummy1", "A100", "nuts", 0, 0, 0,
            0, "", "");

    /**
     * A dummy patient used for testing.
     */
    private Patient dummy2 = new Patient("dummy2", "A200", "", 0, 0, 0,
            0, "", "");

    /**
     * A dummy patient used for testing.
     */
    private Patient dummy3 = new Patient("dummy3", "A300", "cats", 0, 0, 0,
            0, "", "");

    /**
     * The expected Json representation of dummy1, dummy2, dummy 3 and the complex patient that is created with
     * the createComplexPatient method.
     */
    private String expected = "[{\"bedNo\":\"A300\",\"allergies\":\"cats\",\"impressions\":{},\"height\":0,"
            + "\"weight\":0,"
            + "\"age\":0,\"number\":0,\"address\":\"\",\"history\":\"\",\"name\":\"dummy3\"},{\"bedNo\":\"A100\","
            + "\"allergies\":\"nuts\",\"impressions\":{},\"height\":0,\"weight\":0,\"age\":0,\"number\":0,"
            + "\"address\":\"\",\"history\":\"\",\"name\":\"dummy1\"},{\"bedNo\":\"C1\",\"allergies\":\"test "
            + "allergies\",\"primaryDiagnosis\":{\"description\":\"test description 2\",\"evidences\":{\"test obs "
            + "2\":{\"type\":\"Observation\",\"properties\":{\"isObjective\":true,\"priority\":1,\"summary\":\"test"
            + " summary 2\",\"name\":\"test obs 2\"}},\"test obs 1\":{\"type\":\"Observation\","
            + "\"properties\":{\"isObjective\":false,\"priority\":0,\"summary\":\"test summary 1\",\"name\":\"test "
            + "obs 1\"}},\"test result 1\":{\"type\":\"Result\",\"properties\":{\"priority\":2,\"summary\":\"test "
            + "summary 1\",\"name\":\"test result 1\"}},\"test result 2\":{\"type\":\"Result\","
            + "\"properties\":{\"priority\":3,\"summary\":\"test summary 2\",\"name\":\"test result 2\"}}},"
            + "\"treatments\":{\"test inv 1\":{\"type\":\"Investigation\",\"properties\":{\"statusIdx\":1,"
            + "\"priority\":0,\"summary\":\"test summary 1\",\"name\":\"test inv 1\"}},\"test plan "
            + "1\":{\"type\":\"Plan\",\"properties\":{\"statusIdx\":1,\"priority\":0,\"summary\":\"test summary "
            + "2\",\"name\":\"test plan 1\"}}},\"name\":\"test imp 1\"},\"impressions\":{\"test imp "
            + "2\":{\"description\":\"test description 2\",\"evidences\":{},\"treatments\":{\"test inv "
            + "2\":{\"type\":\"Investigation\",\"properties\":{\"statusIdx\":3,\"priority\":2,\"summary\":\"test "
            + "summary 2\",\"name\":\"test inv 2\"}},\"test medicine 1\":{\"type\":\"Medicine\","
            + "\"properties\":{\"dose\":\"test dose 2\",\"startDate\":\"test start date\",\"duration\":\"test "
            + "duration\",\"statusIdx\":3,\"priority\":2,\"name\":\"test medicine 1\"}},\"test medicine "
            + "2\":{\"type\":\"Medicine\",\"properties\":{\"dose\":\"test dose 1\",\"startDate\":\"test start "
            + "date\",\"duration\":\"test duration\",\"statusIdx\":1,\"priority\":0,\"name\":\"test medicine 2\"}},"
            + "\"test plan 2\":{\"type\":\"Plan\",\"properties\":{\"statusIdx\":2,\"priority\":1,\"summary\":\"test"
            + " summary 2\",\"name\":\"test plan 2\"}}},\"name\":\"test imp 2\"},\"test imp "
            + "1\":{\"description\":\"test description 2\",\"evidences\":{\"test obs 2\":{\"type\":\"Observation\","
            + "\"properties\":{\"isObjective\":true,\"priority\":1,\"summary\":\"test summary 2\",\"name\":\"test "
            + "obs 2\"}},\"test obs 1\":{\"type\":\"Observation\",\"properties\":{\"isObjective\":false,"
            + "\"priority\":0,\"summary\":\"test summary 1\",\"name\":\"test obs 1\"}},\"test result "
            + "1\":{\"type\":\"Result\",\"properties\":{\"priority\":2,\"summary\":\"test summary 1\","
            + "\"name\":\"test result 1\"}},\"test result 2\":{\"type\":\"Result\",\"properties\":{\"priority\":3,"
            + "\"summary\":\"test summary 2\",\"name\":\"test result 2\"}}},\"treatments\":{\"test inv "
            + "1\":{\"type\":\"Investigation\",\"properties\":{\"statusIdx\":1,\"priority\":0,\"summary\":\"test "
            + "summary 1\",\"name\":\"test inv 1\"}},\"test plan 1\":{\"type\":\"Plan\","
            + "\"properties\":{\"statusIdx\":1,\"priority\":0,\"summary\":\"test summary 2\",\"name\":\"test plan "
            + "1\"}}},\"name\":\"test imp 1\"}},\"height\":123,\"weight\":456,\"age\":100,\"number\":6582447,"
            + "\"address\":\"test address\",\"history\":\"test history\",\"name\":\"testCPatient\"},"
            + "{\"bedNo\":\"A200\",\"allergies\":\"\",\"impressions\":{},\"height\":0,\"weight\":0,\"age\":0,"
            + "\"number\":0,\"address\":\"\",\"history\":\"\",\"name\":\"dummy2\"}]";

    /**
     * Creates a patient object and assign values to all of its attributes - used to test if the nesting works.
     *
     * @return the created patient object.
     */
    private Patient createComplexPatient() throws DukeException {
        Patient complexPatient = new Patient("testCPatient", "C1", "test allergies", 123,
                456, 100, 6582447, "test address", "test history");
        Impression impression1 = new Impression("test imp 1", "test description 2", complexPatient);
        Impression impression2 = new Impression("test imp 2", "test description 2", complexPatient);
        Observation observation1 = new Observation("test obs 1", impression1, 0,
                "test summary 1", false);
        Observation observation2 = new Observation("test obs 2", impression2, 1,
                "test summary 2", true);
        Result result1 = new Result("test result 1", impression2, 2,
                "test summary 1");
        Result result2 = new Result("test result 2", impression2, 3,
                "test summary 2");
        impression1.addNewEvidence(observation1);
        impression1.addNewEvidence(observation2);
        impression1.addNewEvidence(result1);
        impression1.addNewEvidence(result2);
        complexPatient.addNewImpression(impression1);
        complexPatient.addNewImpression(impression2);
        Plan plan1 = new Plan("test plan 1", impression1, 0, 1,
                "test summary 2");
        Plan plan2 = new Plan("test plan 2", impression2, 1, 2,
                "test summary 2");
        impression1.addNewTreatment(plan1);
        impression2.addNewTreatment(plan2);
        Investigation investigation1 = new Investigation("test inv 1", impression1,
                0, 1, "test summary 1");
        Investigation investigation2 = new Investigation("test inv 2", impression2,
                2, 3, "test summary 2");
        impression1.addNewTreatment(investigation1);
        impression2.addNewTreatment(investigation2);
        Medicine medicine1 = new Medicine("test medicine 2", impression1, 0, 1,
                "test dose 1", "test start date", "test duration");
        Medicine medicine2 = new Medicine("test medicine 1", impression2, 2, 3,
                "test dose 2", "test start date", "test duration");
        impression2.addNewTreatment(medicine1);
        impression2.addNewTreatment(medicine2);
        complexPatient.setPrimaryDiagnosis(impression1.getName());
        return complexPatient;
    }

    /**
     * Compares all the attributes of two patients and returns true if they all are the same, otherwise it returns
     * false.
     *
     * @return A boolean stating if the storage function is working properly or not.
     */
    private boolean identical(Patient patient1, Patient patient2) {
        if (!(patient1.getBedNo().equals(patient2.getBedNo()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAllergies(), patient2.getAllergies()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getHeight(), patient2.getHeight()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getWeight(), patient2.getWeight()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAge(), patient2.getAge()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getNumber(), patient2.getNumber()))) {
            return false;
        } else if (!(java.util.Objects.equals(patient1.getAddress(), patient2.getAddress()))) {
            return false;
        } else {
            return java.util.Objects.equals(patient1.getHistory(), patient2.getHistory());
        }
    }

    /**
     * Tests if patients are transformed from the json file to the hash map properly.
     */
    @Test
    public void loadPatientHashMapTest() throws DukeException, IOException {
        core.patientMap = core.storage.resetAllData();
        FileWriter fileWriter = new FileWriter(testFilePath);
        fileWriter.write(expected);
        fileWriter.close();
        core.patientMap = new PatientMap(core.storage);
        assertTrue(identical(core.patientMap.getPatient("A100"), dummy1));
        assertTrue(identical(core.patientMap.getPatient("A200"), dummy2));
        assertTrue(identical(core.patientMap.getPatient("A300"), dummy3));
        assertTrue(identical(core.patientMap.getPatient("C1"), createComplexPatient()));
    }

    /**
     * Tests if patients are transformed from the hash map to the json file properly.
     */
    @Test
    public void writeJsonFileTest() throws IOException, DukeException {
        core.patientMap = core.storage.resetAllData();
        core.patientMap = core.storage.resetAllData();
        core.patientMap.addPatient(dummy1);
        core.patientMap.addPatient(dummy2);
        core.patientMap.addPatient(dummy3);
        core.patientMap.addPatient(createComplexPatient());
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        String json = Files.readString(Paths.get(testFilePath), StandardCharsets.US_ASCII);
        assertEquals(expected, json);
    }

    /**
     * Creates the Json representation of a dummy patient. Then recreates the patient objects based on what
     * is in the json file. When the recreation is done it checks if the first patients are identical to the new one.
     */
    @Test
    public void identicalDummyPatient() throws IOException, DukeException {
        core.patientMap = core.storage.resetAllData();
        core.patientMap.addPatient(dummy1);
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.patientMap = new PatientMap(core.storage);
        Patient dummyPatientRecreated = core.patientMap.getPatient(dummy1.getBedNo());
        boolean equals = identical(dummy1, dummyPatientRecreated);
        assertTrue(equals);
        core.patientMap = core.storage.resetAllData();
    }

    /**
     * Creates the Json representation of a complex patient and then recreates the patient objects based on what
     * is in the json file. When the recreation is done it checks if the first patients are identical to the new one.
     */
    @Test
    public void identicalComplexPatient() throws IOException, DukeException {
        core.patientMap = core.storage.resetAllData();
        Patient complexPatient = createComplexPatient();
        core.patientMap.addPatient(complexPatient);
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.storage.loadPatientHashMap();
        Patient complexPatientRecreated = core.patientMap.getPatient(complexPatient.getBedNo());
        boolean equals = identical(complexPatient, complexPatientRecreated);
        assertTrue(equals);
    }
}
