package duke.data;

import duke.exception.DukeException;

import java.util.Map;

public abstract class DukeData extends DukeObject {

    // TODO change priority to primitive int

    public static final int PRIORITY_MAX = 4;
    public static final int PRIORITY_CRITICAL = 1;
    public static final int PRIORITY_NONE = 0;
    private Integer priority;
    private transient Impression parent;

    /**
     * Abstraction of the evidence or treatment data of a patient.
     * A DukeData object corresponds to the evidence or treatment a doctor has,
     * the impression that led to that data as well as an integer
     * between 1-4 representing the priority or significance of the investigation.
     * Attributes:
     * @param name the evidence or treatment needed
     * @param impression the impression object the data is tagged to
     * @param priority the priority level of the investigation
     */
    public DukeData(String name, Impression impression, Integer priority) throws DukeException {
        super(name, impression);
        parent = impression;
        setPriority(priority);
    }

    @Override
    public void setParent(DukeObject obj) {
        this.parent = (Impression) obj;
    }

    @Override
    public Impression getParent() {
        return parent;
    }

    public void setName(String name) {
        super.setName(name);
    }

    public Integer getPriority() {
        return priority;
    }

    /**
     * Updates priority of treatment.
     * @param priority The integer value of the priority between 1 to 4
     * @return the integer of the updated priority
     */
    public Integer setPriority(Integer priority) throws DukeException {
        if (priority < 0 || priority > DukeData.PRIORITY_MAX) {
            throw new DukeException("Priority must be between 0 and " + DukeData.PRIORITY_MAX + "!");
        }
        this.priority = priority;
        return getPriority();
    }

    @Override
    public String toString() {
        String informationString;
        informationString = "Impression: " + getParent().getName() + "\n";
        informationString += "Priority: " + this.priority + "\n";
        return super.toString() + informationString;
    }


    /**
     * Checks for equality with another DukeData object - all fields have the same value and all references point to
     * the same objects. Primarily for testing.
     * @param other The DukeData to compare against.
     * @return True if all fields and references are the same, false otherwise.
     */
    public boolean equals(DukeData other) {
        return getName().equals(other.getName())
                && priority.equals(other.priority)
                && getParent() == other.getParent();
        // null check required because medicine summary is null
    }

    /**
     * This function updates DukeData attributes if there is changes.
     * Utilises isAppending to determine whether to append or replace the attribute.
     * @param newName the name entered by the user, null if not applicable
     * @param newPriority the priority specified by user, -1 if not applicable
     * @param editVals unused in this method, used in overridden method
     * @param isAppending true if not a replacement
     * @throws DukeException if priority specified by user is invalid
     */
    public void edit(String newName, int newPriority, Map<String, String> editVals,
                     boolean isAppending)
            throws DukeException {
        if (newName != null) {
            setName((isAppending) ? getName() + " " + newName : newName);
        }
        if (newPriority != -1) {
            setPriority(newPriority);
        }
    }
}
