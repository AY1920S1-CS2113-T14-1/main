package duke.ui;

import duke.ui.card.UiCard;
import javafx.stage.Stage;

import java.util.List;

/**
 * Application Programming Interface (API) of the UI component of the application.
 */
public interface Ui {
    /**
     * Starts the UI (and the JavaFX application).
     *
     * @param primaryStage Stage created by the JavaFX system when the application first starts up.
     */
    void start(Stage primaryStage);

    /**
     * Prints message on the command window.
     *
     * @param message Output message.
     */
    void print(String message);

    /**
     * Retrieves list of UI cards in current {@code UiContext}.
     *
     * @return List of UI cards.
     */
    List<UiCard> getCardList();
}
