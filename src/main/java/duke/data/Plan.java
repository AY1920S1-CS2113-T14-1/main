package duke.data;

import duke.exception.DukeFatalException;
import duke.ui.card.PlanCard;
import duke.ui.context.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Plan extends Treatment {

    private static final List<String> statusArr = Arrays.asList("Not done/ordered", "In progress", "Completed");

    /**
     * Represents the Plan devised by the doctor to treat a symptom or impression of a patient.
     * A Plan object corresponds to the plan devised by a doctor to treat the signs and symptoms of a Patient,
     * the impression that led to that particular plan being necessary, the status of the treatment,
     * a description of the status, the summary of the plan as well as an integer between 1-4
     * representing the priority or significance of the plan.
     * Attributes:
     * @param name the generic plan name
     * @param impression the impression object the plan is tagged to
     * @param priority the priority level of the plan
     * @param status the current status of the plan
     * @param summary the summary of what the plan entails
     */
    public Plan(String name, Impression impression, int priority, int status, String summary) {
        super(name, impression, priority, status);
        this.summary = summary;
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Summary: " + this.summary + "\n";
        return super.toString() + informationString;
    }

    @Override
    public String toReportString() {
        return null;
    }

    public String getStatusStr() {
        return statusArr.get(getStatusIdx());
    }

    public static List<String> getStatusArr() {
        return Collections.unmodifiableList(statusArr);
    }

    @Override
    public PlanCard toCard() throws DukeFatalException {
        return new PlanCard(this);
    }

    @Override
    public Context toContext() {
        return Context.TREATMENT;
    }
}
