package duke.command.patient;

import duke.DukeCore;
import duke.command.ObjSpec;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

public abstract class PatientObjSpec extends ObjSpec {

    protected String type;

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Map<String, Boolean> conditions = new HashMap<>();
        conditions.put("impression", cmd.isSwitchSet("impression"));
        conditions.put("critical", cmd.isSwitchSet("critical"));
        conditions.put("followup", cmd.isSwitchSet("followup"));

        String type = null;
        for (Map.Entry<String, Boolean> condition : conditions.entrySet()) {
            if (condition.getValue()) {
                if (type == null) {
                    type = condition.getKey();
                } else {
                    throw new DukeException("Please provide at most 1 unique type (impression, critical, or "
                            + "followup) for the command!");
                }
            }
        }

        Patient patient = PatientUtils.getPatient(core);
        DukeObject object = PatientUtils.findFromPatient(patient, type, cmd.getArg());
        if (object == null) {
            SearchResults results = PatientUtils.searchFromPatient(patient, type, cmd.getArg());
            processResults(core, results);
        } else {
            executeWithObj(core, object);
        }
    }
}
