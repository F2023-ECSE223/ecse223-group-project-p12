package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class ApproveTicketController {

    @FXML
    private RadioButton approveRadioButton;

    @FXML
    private RadioButton disapproveRadioButton;

    private int ticketId;

    @FXML
    void handleCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleSave(ActionEvent event) {
      if (disapproveRadioButton.isSelected()) {
        // Get controller and set ticket id for the next controller
        DisapproveTicketController controller = (DisapproveTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DisapproveTicket.fxml", "Reason for Disapproval");
        controller.setTicketId(ticketId);

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