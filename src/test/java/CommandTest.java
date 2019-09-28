import duke.DukeContext;
import duke.command.Ui;
import duke.exception.DukeFatalException;
import duke.task.Storage;
import duke.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class CommandTest {
    protected static DukeContext ctx;
    protected static ByteArrayOutputStream testOut = new ByteArrayOutputStream(); //stores printed output
    protected static PrintStream testPrint = new PrintStream(testOut); //System.out replacement, prints to testOut

    /**
     * Create data directory if necessary and use a test task file to create test DukeContext, with output directed to
     * testOut.
     */
    @BeforeAll
    public static void setupCtx() {
        File dataDir = new File("data");
        if (!dataDir.exists() && !dataDir.mkdir()) {
            fail("Could not create data directory!");
        }

        Ui ui = new Ui(System.in, testPrint);
        try {
            ctx = new DukeContext(new Storage("data" + File.separator + "test.tsv"), ui);
            ctx.storage.writeTaskFile("");
        } catch (DukeFatalException excp) {
            fail("Could not setup storage for testing!");
        }
    }

    /**
     * Reset taskList and testOut, and flush the testPrint stream after each test is done with them.
     */
    @AfterEach
    public void clearTaskList() {
        ctx.taskList = new TaskList();
        testPrint.flush();
        testOut.reset();
    }

    /**
     * Deletes testing data after test is completed.
     */
    @AfterAll
    public static void clearTestData() {
        File testData = new File("data" + File.separator + "test.tsv");
        if (!testData.delete()) {
            fail("Unable to delete test data file!");
        }
    }
}
