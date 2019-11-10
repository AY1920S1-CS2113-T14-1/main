package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.exception.DukeFatalException;

public class PatientOpenSpec extends PatientObjSpec {
    private static final PatientOpenSpec spec = new PatientOpenSpec();

    public static PatientOpenSpec getSpec() {
        return spec;
    }

    private PatientOpenSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "im"),
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("investigation", String.class, true, ArgLevel.NONE, "inv")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeFatalException {
        core.uiContext.open(obj);
        core.updateUi("Accessing " + obj.getParent().getName() + "'s " + obj.getClass().getSimpleName() + " '"
                + obj.getName() + "'");
    }
}
