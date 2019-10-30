package duke.ui;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * TextField with an autocomplete feature.
 */
public class AutoCompleteTextField extends TextArea {
    private final SortedSet<String> entries;
    private ContextMenu menu;

    /**
     * Constructs a new textfield with autocomplete.
     * TODO: Populate entries in constructor for the full menu.
     */
    public AutoCompleteTextField() {
        entries = new TreeSet<>();
        menu = new ContextMenu();

        // Listen to changes in textfield
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (getText().length() == 0) {
                menu.hide();
            } else {
                List<String> menuEntries = new ArrayList<>(entries.subSet(getText(), getText() + Character.MAX_VALUE));

                if (entries.size() > 0) {
                    updateMenu(menuEntries);

                    // temporary workaround for focus
                    menu.hide();
                    menu.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                } else {
                    menu.hide();
                }
            }
        });

        // Listen to changes in focus
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            menu.hide();
        });
    }

    /**
     * Update the entries of the context menu.
     *
     * @param menuEntries Entries for the context menu.
     */
    private void updateMenu(List<String> menuEntries) {
        int maxMenuSize = Math.min(menuEntries.size(), 15);

        menu.getItems().clear();

        for (int i = 0; i < maxMenuSize; ++i) {
            final String entry = menuEntries.get(i);
            CustomMenuItem item = new CustomMenuItem(new Label(entry), true);
            item.setOnAction(actionEvent -> {
                setText(entry);
                positionCaret(AutoCompleteTextField.this.getText().length());
                menu.hide();
            });

            menu.getItems().add(item);
        }
    }
}
