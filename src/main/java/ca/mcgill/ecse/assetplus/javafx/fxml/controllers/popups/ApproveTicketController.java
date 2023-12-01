package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ApproveTicketController {

    @FXML
    private RadioButton approveRadioButton;

    @FXML
    private RadioButton disapproveRadioButton;

    private int ticketId;

    private ToggleGroup group;

  
    @FXML
    void initialize() {
      group = new ToggleGroup();
      approveRadioButton.setToggleGroup(group);
      disapproveRadioButton.setToggleGroup(group);
      disapproveRadioButton.setSelected(true);
    }

    @FXML
    void handleCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleSave(ActionEvent event) {
      if (disapproveRadioButton.isSelected()) {
        AddNotePopUpController controller = (AddNotePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotesAddNotePopUp.fxml", "Reason for Disapproval");
        controller.setTicketId(ticketId);
        controller.setDisapproveNote(true);
        //AssetPlusFXMLView.getInstance().closePopUpWindow();
      } else {
        ViewUtils.approveTicket(ticketId);
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      }
    }

    public void setTicketId(int ticketId) {
      this.ticketId = ticketId;
    }

}