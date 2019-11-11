package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.ObservationCard;
import duke.ui.context.Context;

import java.util.Map;

public class Observation extends Evidence {

    private boolean isObjective;

    /**
     * Represents observations of a doctor about a patient.
     * An Observation object corresponds to the observations of the doctor about the symptoms of a Patient,
     * the information that led to that particular observation, as well as an integer between 1-4
     * representing the priority or significance of the evidence.
     * Attributes:
     * @param name information on the observation / symptom
     * @param impression the impression object the evidence is tagged to
     * @param summary a summary of what led to the observation
     * @param isObjective whether the observation has physical evidence or is a symptom reported by the patient
     * @param priority the priority level of the evidence
     */
    public Observation(String name, Impression impression, int priority, String summary, boolean isObjective)
            throws DukeException {
        super(name, impression, priority, summary);
        setObjective(isObjective);
    }

    @Override
    public void edit(String newName, int newPriority, Map<String, String> editVals,
                     boolean isAppending) throws DukeException {
        super.edit(newName, newPriority, editVals, isAppending);
        boolean obj = editVals.containsKey("objective");
        boolean subj = editVals.containsKey("subjective");
        if (obj && subj) {
            throw new DukeException("I don't know if you want the observation to be objective"
                    + "or subjective!");
        } else if (obj) {
            setObjective(true);
        } else if (subj) {
            setObjective(false);
        }
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Objective: " + this.isObjective + "\n";
        return super.toString() + informationString;
    }

    //TODO return string in proper format
    @Override
    public String toReportString() {
        return toString();
    }

    public boolean isObjective() {
        return isObjective;
    }

    public void setObjective(boolean objective) {
        isObjective = objective;
    }

    @Override
    public ObservationCard toCard() throws DukeFatalException {
        return new ObservationCard(this);
    }

    @Override
    public Context toContext() {
        return Context.OBSERVATION;
    }
}
