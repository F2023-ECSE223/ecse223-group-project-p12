package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteViewNotesPopUpController {

    private int ticketId;
    private int index;

    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        ticketId = -1;
        index = -1;
    }

    @FXML
    void Cancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
    
    @FXML
    void DeleteNote(ActionEvent event) {
      if (ticketId != -1 && index != -1) {
        AssetPlusFeatureSet7Controller.deleteMaintenanceNote(ticketId, index);
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      }
    }

    public void setTicketIdAndIndex(int id, int index) {
      this.ticketId = id;
      this.index = index;
    }
}
