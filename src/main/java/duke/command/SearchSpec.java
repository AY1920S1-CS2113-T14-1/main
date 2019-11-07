package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.SearchResults;
import duke.exception.DukeException;

public class SearchSpec extends ArgSpec {

    private static final SearchSpec spec = new SearchSpec();
    private static String input;

    public static SearchSpec getSpec() {
        return getSpec(null);
    }

    public static SearchSpec getSpec(String input) {
        SearchSpec.input = input;
        return spec;
    }

    private SearchSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        assert (core.queuedCommand != null);
        SearchResults results;
        int idx;
        try {
            idx = Integer.parseInt(input);
        } catch (NumberFormatException excp) {
            throw new DukeException("Please enter the index of a search result!");
        }
        try {
            results = (SearchResults) core.uiContext.getObject();
        } catch (ClassCastException excp) {
            throw new DukeException("Search command called from non-search context!");
        }

        DukeObject result = results.getResult(idx);
        core.queuedCommand.execute(core, result);
        // TODO: send the DukeObject back to the caller

    }
}
