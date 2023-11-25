package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddImagePopUpController {

  private int ticketId;

  @FXML
  void initialize() {
      ticketId = -1;
  }

  @FXML
  private Button addImageButton;

  @FXML
  private Button cancelButton;

  @FXML
  private TextField urlField;

  @FXML
  void AddImage(ActionEvent event) {
    String url = urlField.getText();
    if ( ViewUtils.callController((AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(url, ticketId))) ) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
  }

  @FXML
  void Cancel(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setTicketId(int id) {
    ticketId = id;
  }

}
