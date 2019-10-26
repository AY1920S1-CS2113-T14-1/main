package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.exception.DukeException;
import duke.ui.Context;

import java.util.Arrays;

public class ImpressionNewCommand extends DukeDataCommand {

    private static final String[] medicineStatusArr = {"Not ordered", "In progress", "Completed"};
    private static final String[] invxStatusArr = {"Not ordered", "In progress"};
    private static final String[] planStatusArr = {"Not ordered", "In progress", "Reported"};

    @Override
    protected ArgSpec getSpec() {
        return ImpressionNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType();
        checkTypeSwitches(addType);
        DukeData newData;

        //extract data and add type
        switch(addType) {
        case "medicine":

            break;
        case "plan": //fallthrough
        case "investigation":
            break;
        case "result":
            break;
        case "observation":
            break;
        default:
            throw new DukeException("Invalid data type!");
        }

        if (isSwitchSet("go")) {
            switch(addType) {
            case "plan": //fallthrough
            case "medicine":
                core.uiContext.setContext(Context.TREATMENT, newData);
                break;
            case "investigation":
                core.uiContext.setContext(Context.INVESTIGATION, newData);
                break;
            case "result": //fallthrough
            case "observation":
                core.uiContext.setContext(Context.EVIDENCE, newData);
            default:
                throw new DukeException("Invalid data type!");   case:
            }
        }
    }
}
