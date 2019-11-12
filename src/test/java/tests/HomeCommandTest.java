package tests;

import duke.command.ArgCommand;
import duke.command.ObjCommand;
import duke.command.home.HomeNewSpec;
import duke.command.home.HomeReportSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HomeCommandTest extends CommandTest {

    /**
     * Tests HomeNewCommand with all switches present.
     */
    @Test
    public void homeNewCommand_allSwitches_correctPatientCreated() {
        String[] switchNames = {"name", "bed", "allergies", "height", "weight", "age", "number", "address", "history"};
        String[] switchVals = {"testCPatient", "C1", "test allergies", "123", "456", "100", "6582447", "test address",
            "test history"};
        Patient patient = new Patient("testCPatient", "C1", "test allergies", 123,
                456, 100, 6582447, "test address", "test history");
        try {
            ArgCommand newPatientCmd = new ArgCommand(HomeNewSpec.getSpec(), null, switchNames, switchVals);
            newPatientCmd.execute(core);
            assertTrue(patient.equals(core.patientData.getPatientByBed("C1")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command: " + excp.getMessage());
        }
    }

    /**
     * Tests HomeReport Command with the summary switch present.
     */
    /*
    @Test
    public void homeReportCommandTest() {
        String[] switchNames = {"bed", "summary"};
        String[] switchVals = {"testC1", "This as a test report that is used to test the report command from the"
                + " home context"};
        try {
            core.patientData.addPatient(new Patient("testCPatient", "testC1", "test allergies",
                    123, 456, 100, 6582447, "test address", "test history"));
            ObjCommand newReportCmd = new ObjCommand(HomeReportSpec.getSpec(), null, switchNames, switchVals);
            newReportCmd.execute(core);
            String expected = "PATIENT REPORT\n\nThis report shows all the data that was stored about a patient at "
                    + "the time the report was created.\n\nReport Summary: This as a test report that is used to test "
                    + "the report command from the home context\n\nPatient Data;\n\tName of patient: "
                    + "testCPatient\n\tBed Number: testC1\n\tHeight: 123\n\tWeight: 456\n\tAllergies: test "
                    + "allergies\n\tAge: 100\n\tNumber: 6582447\n\tAddress: test address\n\tHistory: "
                    + "test history\n\n";
            // String actual = Files.readString(Paths.get("data/Reports/testCPatient-testC1.txt"),
            //        StandardCharsets.US_ASCII);
            String actual = new Scanner(new File("data" + File.separator + "Reports"
                    + File.separator + "testCPatient-testC1.txt"))
                    .useDelimiter("\\Z").next().replaceAll(System.lineSeparator(), "\n");
            printDifferences(expected, actual);
            assertEquals(expected, actual);
        } catch (DukeException | IOException excp) {
            fail("Exception thrown when validly creating report from command in home context: " + excp.getMessage());
        }
    }
     */

    /**
     * Function to print out the characters that differ between two strings that should be identical.
     */
    private void printDifferences(String str1, String str2) {
        for (int i = 0; i < str1.length() && i < str2.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                System.out.println("index " + i);
                System.out.println(str1.charAt(i) + "  " + str2.charAt(i));
            }
        }
        if (str1.length() != str2.length()) {
            System.out.println("str1 " + str1.length() + " str2 " + str2.length());
            System.out.println(str1);
        }
    }
}
