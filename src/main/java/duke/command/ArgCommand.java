package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    private String arg; //argument supplied to the command
    private HashMap<String, String> switchVals; //hashmap of switch parameters
    private final ArgSpec spec;

    public ArgCommand(ArgSpec spec) {
         arg = null;
         switchVals = new HashMap<String, String>();
         this.spec = spec;
    }

    public ArgCommand(ArgSpec spec, String arg, String[] switchNames, String[] switchVals) throws DukeException {
        this(spec);
        initArg(arg);
        if (switchNames.length != switchVals.length) {
            throw new DukeException("You gave me " + switchNames.length + " switch names, but "
                    + switchVals.length + " switch values!");
        }
        for (int i = 0; i < switchNames.length; ++i) {
            initSwitchVal(switchNames[i], switchVals[i]);
        }
    }

    /**
     * Executes this command, with the behaviour being defined by its ArgSpec object.
     * @param core The DukeCore object for this command to operate on.
     * @throws DukeException If an exceptional condition is encountered during execution of the command.
     */
    @Override
    public void execute(DukeCore core) throws DukeException {
        spec.execute(core);
    }

    /**
     * Initialises a particular switch to a particular value, if allowed.
     * @param switchName The name of the switch to be initialised.
     * @param value The value to initialise it to.
     * @throws DukeHelpException If the switch is already initialised, if {@code switchName} is null, if the switch
     * should not have an argument but {@code value} is not null, or if the switch should have an argument but
     * {@code value} is null.
     */
    protected void initSwitchVal(String switchName, String value) throws DukeHelpException {
        Switch newSwitch = getSwitchMap().get(switchName);
        if (getSwitchVals().containsKey(switchName)) {
            throw new DukeHelpException("Multiple values supplied for switch: " + switchName, this);
        }

        if (newSwitch == null) {
            throw new DukeHelpException("I don't know what the '" + switchName + "' switch is!", this);
        }

        if (newSwitch.argLevel == ArgLevel.NONE && value != null) {
            throw new DukeHelpException("The '" + switchName + "' switch should not have an argument!", this);
        }

        if (newSwitch.argLevel == ArgLevel.REQUIRED && value == null) {
            throw new DukeHelpException("The '" + switchName + "' switch should have an argument!", this);
        }
        switchVals.put(switchName, value);
    }

    protected String getSwitchVal(String switchName) {
        return switchVals.get(switchName);
    }

    protected boolean isSwitchSet(String switchName) {
        return switchVals.containsKey(switchName);
    }

    /**
     * Initialises the command's argument to a particular value, if allowed.
     * @param arg The value to initialise the command's argument to.
     * @throws DukeHelpException If the argument is already initialised, if the command should not have an argument but
     * {@code value} is not null, or if the command should have an argument but {@code value} is null.
     */
    protected void initArg(String arg) throws DukeHelpException {
        ArgLevel cmdArgLevel = getCmdArgLevel();
        if (cmdArgLevel == ArgLevel.REQUIRED && arg == null) {
            throw new DukeHelpException("This command requires an argument!", this);
        } else if (cmdArgLevel == ArgLevel.NONE && arg != null) {
            throw new DukeHelpException("This command should not have an argument!", this);
        }

        if (this.arg != null) {
            throw new DukeHelpException("Multiple command arguments supplied! You already gave: " + this.arg, this);
        }
        this.arg = arg;
    }

    protected String getArg() {
        return arg;
    }

    protected ArgLevel getCmdArgLevel() {
        return spec.getCmdArgLevel();
    }

    protected Map<String, Switch> getSwitchMap() {
        return spec.getSwitchMap();
    }

    protected Map<String, String> getSwitchAliases() {
        return spec.getSwitchAliases();
    }

    protected Map<String, String> getSwitchVals() {
        return Collections.unmodifiableMap(switchVals);
    }

    /**
     * Checks if a particular switch, and if not, attempts to parse it as an Integer.
     * @param switchName The name of the switch being extracted.
     * @return The Integer that the string represents, or -1 if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    protected Integer switchToInt(String switchName) throws DukeHelpException {
        String str = this.getSwitchVal(switchName);
        if (str == null) {
            return -1;
        } else {
            try {
                Integer parseInt = Integer.parseInt(str);
                // TODO document this
                if (parseInt < 0) {
                    throw new DukeHelpException("The value of '" + switchName + "' cannot be negative!", this);
                }
                return parseInt;
            } catch (NumberFormatException excp) {
                throw new DukeHelpException("The switch '" + switchName + "' must be an integer!", this);
            }
        }
    }

    /**
     * Sets the arguments for optional switches that require String-type arguments to the empty String.
     * NOTE: Switches with ArgLevel.OPTIONAL are ignored by this method.
     */
    protected void nullToEmptyString() throws DukeException {
        for (Map.Entry<String, Switch> entry : getSwitchMap().entrySet()) {
            String switchName = entry.getKey();
            Switch switchSpec = entry.getValue();
            if (getSwitchVal(switchName) == null && switchSpec.type == String.class && switchSpec.argLevel
                    == ArgLevel.REQUIRED) {
                initSwitchVal(switchName, "");
            }
        }
    }

    /**
     * Checks for equality with another ArgCommand object, inspecting arguments and switches.
     *
     * @param other The ArgCommand object to check against.
     * @return True if all switches that are set in one ArgCommand are set in the other, and if all switches that are
     *     set are set to the same value.
     */
    public boolean equals(ArgCommand other) {
        if (getClass() != other.getClass()) {
            return false;
        }

        if (!CommandUtils.equalsIfNotBothNull(arg, other.arg)) {
            return false;
        }

        Map<String, String> thisSwitchVals = getSwitchVals();
        Map<String, String> otherSwitchVals = other.getSwitchVals();
        for (String switchName : getSwitchMap().keySet()) {
            if ((otherSwitchVals.containsKey(switchName) && !thisSwitchVals.containsKey(switchName))
                    || (!otherSwitchVals.containsKey(switchName) && thisSwitchVals.containsKey(switchName))
                    || !CommandUtils.equalsIfNotBothNull(thisSwitchVals.get(switchName),
                    otherSwitchVals.get(switchName))) {
                return false;
            }
        }
        return true;
    }

    public void checkCommandValid() throws DukeHelpException {
        if (getCmdArgLevel() == ArgLevel.REQUIRED && getArg() == null) {
            throw new DukeHelpException("You need to give an argument for the command!", this);
        }
        for (HashMap.Entry<String, Switch> switchEntry : getSwitchMap().entrySet()) {
            Switch checkSwitch = switchEntry.getValue();
            if (!checkSwitch.isOptional && !switchVals.containsKey(checkSwitch.name)) {
                throw new DukeHelpException("You need to give me this switch: "
                        + switchEntry.getKey(), this);
            }
        }
    }

    public boolean isArgForbidden(String switchName) {
        return getSwitchMap().get(switchName).argLevel == ArgLevel.NONE;
    }

    public boolean hasSwitch(String switchName) {
        return getSwitchMap().containsKey(switchName);
    }
}
