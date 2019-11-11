/* @@author JeremyKwok */

package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.SearchResults;
import duke.exception.DukeFatalException;
import duke.ui.card.UiCard;
import duke.ui.commons.UiStrings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * UI window for the Patient context.
 */
class SearchContextWindow extends ContextWindow {
    private static final String FXML = "SearchContextWindow.fxml";

    @FXML
    private Label parentTypeLabel;
    @FXML
    private Label parentNameLabel;
    @FXML
    private Label searchTermLabel;
    @FXML
    private Label searchDetailsLabel;
    @FXML
    private JFXListView<UiCard> searchListPanel;
    @FXML
    private ImageView parentTypeImage2;
    @FXML
    private ImageView parentTypeImage;

    private DukeObject parent;
    private SearchResults searchResults;

    /**
     * Constructs the search UI window.
     */
    SearchContextWindow(SearchResults searchResults) throws DukeFatalException {
        super(FXML);

        if (searchResults != null) {
            this.parent = searchResults.getParent();
            this.searchResults = searchResults;
            setSearchWindow();
        }
    }

    private void setSearchWindow() throws DukeFatalException {
        setParent();
        searchTermLabel.setText(searchResults.getName());
        searchDetailsLabel.setText(searchResults.toString());
        for (DukeObject obj : searchResults.getSearchList()) {
            UiCard card = obj.toCard();
            card.setIndex(searchResults.getSearchList().indexOf(obj) + 1);
            searchListPanel.getItems().add(card);
        }
    }

    private void setParent() {
        if (parent != null) {
            parentTypeLabel.setText(parent.getClass().getSimpleName().toUpperCase() + " SEARCH CONTEXT");
            parentNameLabel.setText(String.valueOf(parent.getName()));
            Image image = new Image(DukeCore.class.getResourceAsStream(
                    UiStrings.SEARCH_HELPER.get(parent.getClass().getSimpleName())));
            parentTypeImage.setImage(image);
            parentTypeImage2.setImage(image);
        }
    }

    @Override
    public void updateUi() throws DukeFatalException {
        setSearchWindow();
    }
}
