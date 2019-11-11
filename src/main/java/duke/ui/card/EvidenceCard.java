package duke.ui.card;

import duke.data.Evidence;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

//@@author JeremyKwok
/**
 * A UI card that displays the basic information of an {@code Evidence}.
 */
public abstract class EvidenceCard extends UiCard {
    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;

    private final Evidence evidence;

    /**
     * Constructs a EvidenceCard object with the specified {@code Evidence}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     * @param evidence     Evidence object.
     */
    public EvidenceCard(String fxmlFileName, Evidence evidence) throws DukeFatalException {
        super(fxmlFileName);

        this.evidence = evidence;
        fillEvidenceCard();
    }

    /**
     * Fills up the UI card with the {@code Evidence}'s details.
     */
    private void fillEvidenceCard() {
        nameLabel.setText(evidence.getName());
        criticalLabel.setText(evidence.getPriorityStr());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof EvidenceCard)) {
            return false;
        }

        EvidenceCard card = (EvidenceCard) object;
        return evidence == card.getEvidence();
    }

    public abstract Evidence getEvidence();
}
