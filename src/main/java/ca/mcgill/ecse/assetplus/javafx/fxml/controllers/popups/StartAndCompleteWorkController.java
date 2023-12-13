package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartAndCompleteWorkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label title;

    private int ticketId;

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleYes(ActionEvent event) {
        if (this.title.getText().equals(resources.getString("key.TicketStatus_StartWork"))) {
            ViewUtils.startWork(ticketId);
        } else if (this.title.getText().equals(resources.getString("key.TicketStatus_CompleteWork"))) {
            ViewUtils.completeWork(ticketId);
        }
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

}
