package duke.command.impression;

import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class DukeDataSpec extends ArgSpec {

    private static final Map<String, Class> typeMap = Map.ofEntries(
            Map.entry("medicine", Medicine.class),
            Map.entry("plan", Plan.class),
            Map.entry("investigation", Investigation.class),
            Map.entry("result", Result.class),
            Map.entry("observation", Observation.class));

    // TODO: change map to allowed switches, and code accordingly
    private static final Map<String, List<String>> forbiddenSwitchesMap = Map.ofEntries(
            Map.entry("medicine", Arrays.asList("summary", "subjective", "objective")),
            Map.entry("plan", Arrays.asList("dose", "duration", "date", "subjective", "objective")),
            Map.entry("investigation", Arrays.asList("dose", "duration", "date", "subjective", "objective")),
            Map.entry("result", Arrays.asList("dose", "duration", "date", "status", "subjective", "objective")),
            Map.entry("observation", Arrays.asList("dose", "duration", "date", "status")));

    /**
     * Checks if the type of data to add was uniquely specified.
     * @return The type of data specified amongst the switches for this Command, or null if none of them were given.
     * @throws DukeException If multiple data type switches were specified.
     */
    protected String uniqueDataType(ArgCommand cmd) throws DukeException {
        String addType = null;
        for (String type : typeMap.keySet()) {
            if (cmd.isSwitchSet(type)) {
                if (addType != null) {
                    throw new DukeHelpException("Multiple types of data specified: '" + addType
                            + "' and '" + type + "'!", cmd);
                }
                addType = type;
            }
        }
        return addType;
    }

    protected void checkTypeSwitches(String addType, ArgCommand cmd) throws DukeException {
        // check if required switches are in place
        if ("medicine".equals(addType)) {
            if (cmd.getSwitchVal("dose") == null) {
                throw new DukeException("I need to know the dose of this course of medicine!");
            }
            if (cmd.getSwitchVal("duration") == null) {
                throw new DukeException("I need to know how long this medicine will be given for!");
            }
        }

        List<String> forbiddenSwitches = forbiddenSwitchesMap.get(addType);
        for (String switchName : forbiddenSwitches) {
            if (cmd.isSwitchSet(switchName)) {
                throw new DukeException("Illegal switch '" + switchName + "' for data type '" + addType + "'!");
            }
        }
    }
}
