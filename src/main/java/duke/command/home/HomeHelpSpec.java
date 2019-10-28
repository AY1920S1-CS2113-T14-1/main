package duke.command.home;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class HomeHelpSpec extends ArgSpec {

    private static final HomeHelpSpec spec = new HomeHelpSpec();

    public static HomeHelpSpec getSpec() {
        return spec;
    }

    private HomeHelpSpec() {
        emptyArgMsg = "You did not tell me anything about the help function!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("discharge", String.class, true, ArgLevel.NONE, "d"),
                new Switch("archive", String.class, true, ArgLevel.NONE, "a"),
                new Switch("open", String.class, true, ArgLevel.NONE, "o"),
                new Switch("new", String.class, true, ArgLevel.NONE, "n"),
                new Switch("history", String.class, true, ArgLevel.NONE, "h"),
                new Switch("undo", String.class, true, ArgLevel.NONE, "u"),
                new Switch("redo", String.class, true, ArgLevel.NONE, "r")
        );
    }
}
