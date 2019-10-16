package duke.command;

import duke.DukeCore;

import java.util.Map;
import java.util.TreeSet;

/**
 * Stub Command for testing new Parser.
 */
public class DoctorCommand extends ArgCommand {
    static {
        String[] switchNameArr = new String[]{"switch"};
        ArgLevel[] argLevelArr = new ArgLevel[]{ArgLevel.OPTIONAL};
        String[] switchRootArr = new String[]{"s"};
        switchInit(switchNameArr, argLevelArr, switchRootArr);
    }

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + super.getArg() + System.lineSeparator() + "Switch: " +
                super.getSwitchVal("switch"));
    }

    ArgLevel getCmdArgLevel() {
        return ArgLevel.OPTIONAL;
    }

    String getEmptyArgMsg() {
        return "You didn't tell me what to do!";
    }
}