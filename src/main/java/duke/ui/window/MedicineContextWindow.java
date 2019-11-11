package duke.ui.window;

import duke.data.Medicine;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * UI window for the Medicine context.
 */
public class MedicineContextWindow extends DukeDataContextWindow {
    private static final String FXML = "MedicineContextWindow.fxml";

    @FXML
    private Label statusLabel;
    @FXML
    private Label doseLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label durationLabel;

    private Medicine medicine;

    /**
     * Constructs a UI context window for a Medicine object.
     *
     * @param medicine Medicine object.
     */
    public MedicineContextWindow(Medicine medicine) throws DukeFatalException {
        super(FXML, medicine);

        this.medicine = medicine;
        updateUi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        super.updateUi();
        statusLabel.setText(medicine.getStatusStr());
        doseLabel.setText(medicine.getDose());
        startDateLabel.setText(medicine.getStartDate());
        durationLabel.setText(medicine.getDuration());
    }
}
