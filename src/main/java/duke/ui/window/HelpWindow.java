package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.GsonStorage;
import duke.data.Help;
import duke.exception.DukeException;
import duke.ui.UiElement;
import duke.ui.card.HelpCard;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * UI element for the help window.
 */
public class HelpWindow extends UiElement<Region> {
    private static final String FXML = "HelpWindow.fxml";
    private static final String HELP_FILE = "data" + File.separator + "helpDetails.json";

    @FXML
    private JFXListView<HelpCard> helpListView;

    private List<Help> helpList;
    private List<Help> contextedHelpList;

    /**
     * Constructs a help window.
     *
     * @param storage        GSON storage object.
     * @param inputTextField TextArea for user input.
     * @param uiContext      UiContext object.
     * @throws DukeException If the data file cannot be loaded by the GSON storage object.
     */
    public HelpWindow(GsonStorage storage, TextArea inputTextField, UiContext uiContext) throws DukeException {
        super(FXML, null);

        initialise(storage);
        attachListenerToInput(inputTextField);
        attachListenerToContext(uiContext);
    }

    /**
     * Initialises the {@link #helpList} and {@link #helpListView} for the Home context.
     *
     * @param storage GSON storage object.
     * @throws DukeException If the data file cannot be loaded.
     */
    private void initialise(GsonStorage storage) throws DukeException {
        helpList = storage.loadHelpList(HELP_FILE);
        update(Context.HOME);
    }

    /**
     * Updates the {@link #contextedHelpList} and {@link #helpListView} for a given context.
     *
     * @param context The current context.
     */
    private void update(Context context) {
        contextedHelpList = new ArrayList<>();
        helpListView.getItems().clear();

        helpList.forEach(help -> {
            if (help.getContext() == context) {
                contextedHelpList.add(help);
                helpListView.getItems().add(new HelpCard(help, false));
            }
        });
    }

    /**
     * Attaches a listener to the input text field of the {@code CommandWindow}.
     *
     * @param inputTextField TextArea for user input.
     */
    private void attachListenerToInput(TextArea inputTextField) {
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Help> filteredHelpList = new ArrayList<>();
            helpListView.getItems().clear();

            for (Help help : contextedHelpList) {
                String command = help.getCommand();

                if (newValue.contains(command)) {
                    filteredHelpList.add(help);
                    break;
                } else if (command.contains(newValue)) {
                    filteredHelpList.add(help);
                }
            }

            boolean isDetailsShown = (filteredHelpList.size() == 1);
            filteredHelpList.forEach(help -> helpListView.getItems().add(new HelpCard(help, isDetailsShown)));
        });
    }

    /**
     * Attaches a listener to the {@code UiContext}.
     *
     * @param uiContext UiContext object.
     */
    private void attachListenerToContext(UiContext uiContext) {
        uiContext.addListener(event -> {
            update((Context) event.getNewValue());
        });
    }
}
