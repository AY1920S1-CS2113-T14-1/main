package duke.data;

/*
 * Highest level of abstraction for all DukeObjects storing Patient related Data
 * A Duke object has a relevant name specifying what it is.
 *
 * Attributes:
 * - name: what the object is
 */
public abstract class DukeObject {

    private String name;
    private transient DukeObject parent;

    public DukeObject(String name, DukeObject parent) {
        this.name = name;
        this.parent = parent;
    }

    /*
     * The toString function returns a String representing the information stored in
     * the `DukeObject`
     *
     * @return the String representation of the object
     */
    public String toString() {
        return "Name: " + this.name + "\n";
    }

    /*
     * The toReportString function returns a String formatted
     * to be used in a Report about the relevant DukeObject.
     *
     * @return the String in Report format
     */
    public abstract String toReportString();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(DukeObject parent) {
        this.parent = parent;
    }

    public DukeObject getParent() {
        return parent;
    }
}
