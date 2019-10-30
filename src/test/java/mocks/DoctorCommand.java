package mocks;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;

/**
 * Stub Command for testing new Parser.
 */

public class DoctorCommand extends ArgCommand {

    @Override
    public void execute(DukeCore core) {
        core.ui.print("Argument: " + getArg() + System.lineSeparator() + "Switch: "
                + getSwitchVal("switch"));
    }

    @Override
    protected ArgSpec getSpec() {
        return DoctorSpec.getSpec();
    }
}