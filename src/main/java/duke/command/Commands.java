package duke.command;

import duke.command.dukedata.InvestigationEditSpec;
import duke.command.dukedata.MedicineEditSpec;
import duke.command.dukedata.ObservationEditSpec;
import duke.command.dukedata.PlanEditSpec;
import duke.command.dukedata.ResultEditSpec;
import duke.command.home.HomeDischargeSpec;
import duke.command.home.HomeFindSpec;
import duke.command.home.HomeNewSpec;
import duke.command.home.HomeOpenSpec;
import duke.command.home.HomeReportSpec;
import duke.command.impression.ImpressionDeleteSpec;
import duke.command.impression.ImpressionEditSpec;
import duke.command.impression.ImpressionFindSpec;
import duke.command.impression.ImpressionMoveSpec;
import duke.command.impression.ImpressionNewSpec;
import duke.command.impression.ImpressionOpenSpec;
import duke.command.impression.ImpressionPrimarySpec;
import duke.command.impression.ImpressionPrioritySpec;
import duke.command.impression.ImpressionResultSpec;
import duke.command.impression.ImpressionStatusSpec;
import duke.command.patient.PatientDeleteSpec;
import duke.command.patient.PatientDischargeSpec;
import duke.command.patient.PatientEditSpec;
import duke.command.patient.PatientFindSpec;
import duke.command.patient.PatientHistorySpec;
import duke.command.patient.PatientNewSpec;
import duke.command.patient.PatientOpenSpec;
import duke.command.patient.PatientPrimarySpec;
import duke.command.patient.PatientReportSpec;
import duke.ui.context.Context;

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public class Commands {
    /**
     * Constructs and returns the command corresponding to a name provided by the user.
     *
     * @param cmdStr The user-provided name.
     * @return The newly constructed command without any parameters loaded.
     */
    public Command getCommand(String cmdStr, Context context) {
        if (context == Context.SEARCH) {
            return new Command(SearchSpec.getSpec(cmdStr));
        }

        // check context-independent switches first
        switch (cmdStr) {
        case "bye":
            return new Command(ByeSpec.getSpec());
        case "back":
            return new Command(BackSpec.getSpec());
        case "up":
            return new Command(UpSpec.getSpec());
        default:
            break; //not one of these; continue
        }

        switch (context) {
        case HOME:
            switch (cmdStr) {
            case "find":
                return new ObjCommand(HomeFindSpec.getSpec());
            case "new":
                return new ArgCommand(HomeNewSpec.getSpec());
            case "open":
                return new ObjCommand(HomeOpenSpec.getSpec());
            case "report":
                return new ObjCommand(HomeReportSpec.getSpec());
            case "discharge":
                return new ObjCommand(HomeDischargeSpec.getSpec());
            default:
                return null;
            }

        case PATIENT:
            switch (cmdStr) {
            case "new":
                return new ArgCommand(PatientNewSpec.getSpec());
            case "find":
                return new ObjCommand(PatientFindSpec.getSpec());
            case "report":
                return new Command(PatientReportSpec.getSpec());
            case "discharge":
                return new ArgCommand(PatientDischargeSpec.getSpec());
            case "delete":
                return new ObjCommand(PatientDeleteSpec.getSpec());
            case "primary":
                return new ObjCommand(PatientPrimarySpec.getSpec());
            case "edit":
                return new ArgCommand(PatientEditSpec.getSpec());
            case "history":
                return new ArgCommand(PatientHistorySpec.getSpec());
            case "open":
                return new ObjCommand(PatientOpenSpec.getSpec());
            default:
                return null;
            }

        case IMPRESSION:
            switch (cmdStr) {
            case "new":
                return new ArgCommand(ImpressionNewSpec.getSpec());
            case "edit":
                return new ArgCommand(ImpressionEditSpec.getSpec());
            case "find":
                return new ObjCommand(ImpressionFindSpec.getSpec());
            case "move":
                return new ObjCommand(ImpressionMoveSpec.getSpec());
            case "delete":
                return new ObjCommand(ImpressionDeleteSpec.getSpec());
            case "result":
                return new ObjCommand(ImpressionResultSpec.getSpec());
            case "priority":
                return new ObjCommand(ImpressionPrioritySpec.getSpec());
            case "status":
                return new ObjCommand(ImpressionStatusSpec.getSpec());
            case "open":
                return new ObjCommand(ImpressionOpenSpec.getSpec());
            case "primary":
                return new Command(ImpressionPrimarySpec.getSpec());
            default:
                return null;
            }
        case PLAN:
            if ("edit".equals(cmdStr)) {
                return new ArgCommand(PlanEditSpec.getSpec());
            } else {
                return null;
            }
        case INVESTIGATION:
            if ("edit".equals(cmdStr)) {
                return new ArgCommand(InvestigationEditSpec.getSpec());
            } else {
                return null;
            }
        case MEDICINE:
            if ("edit".equals(cmdStr)) {
                return new ArgCommand(MedicineEditSpec.getSpec());
            } else {
                return null;
            }
        case RESULT:
            if ("edit".equals(cmdStr)) {
                return new ArgCommand(ResultEditSpec.getSpec());
            } else {
                return null;
            }
        case OBSERVATION:
            if ("edit".equals(cmdStr)) {
                return new ArgCommand(ObservationEditSpec.getSpec());
            } else {
                return null;
            }
        default:
            break;
        }
        return null;
    }
}
