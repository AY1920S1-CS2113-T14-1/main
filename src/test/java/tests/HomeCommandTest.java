package tests;

import duke.command.ArgCommand;
import duke.command.home.HomeNewCommand;
import duke.command.home.HomeReportCommand;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeCommandTest extends CommandTest {

    /**
     * Tests HomeNewCommand with all switches present.
     */
    @Test
    public void homeNewCommand_allSwitches_correctPatientCreated() {
        ArgCommand newPatientCmd = new HomeNewCommand();
        String[] switchNames = {"bed", "allergies", "height", "weight", "age", "number", "address", "history"};
        String[] switchVals = {"C1", "test allergies", "123", "456", "100", "6582447", "test address", "test history"};
        setupCommand(newPatientCmd, "testCPatient", switchNames, switchVals);
        Patient patient = new Patient("testCPatient", "C1", "test allergies", 123,
                456, 100, 6582447, "test address", "test history");
        try {
            newPatientCmd.execute(core);
            assertTrue(patient.equals(core.patientMap.getPatient("C1")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }

    /**
     * Tests HomeReport Command with the summary switch present.
     */
    @Test
    public void homeReportCommandTest() {
        ArgCommand newReportCmd = new HomeReportCommand();
        String[] switchNames = {"bed", "summary"};
        String[] switchVals = {"testC1", "This as a test report that is used to test the report command from the"
                + " home context"};
        core.patientMap.addPatient(new Patient("testCPatient", "testC1", "test allergies",
                123, 456, 100, 6582447, "test address", "test history"));
        setupCommand(newReportCmd,"", switchNames, switchVals); //TODO change the way it is implemented
        try {
            newReportCmd.execute(core);
            String expected = "PATIENT REPORT\n\nThis report shows all the data that was stored about a patient at "
                    + "the time the report was created.\n\nReport Summary: This as a test report that is used to test "
                    + "the report command from the home context\n\nPatient Data;\n\tName of patient: "
                    + "testCPatient\n\tBed Number: testC1\n\tHeight: 123\n\tWeight: 456\n\tAllergies: test "
                    + "allergies\n\tAge: 100\n\tNumber: 6582447\n\tAddress: test address\n\tHistory: "
                    + "test history\n\n\n";
            String actual = Files.readString(Paths.get("data/Reports/testCPatient-testC1.txt"),
                    StandardCharsets.US_ASCII);
            assertEquals(actual, expected);
        } catch (DukeException | IOException excp) {
            fail("Exception thrown when validly creating report from command in home context!");
        }
    }
}
