package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
      AssetPlusFXMLView.getInstance().closePopUp();
    }
    
  }

  @FXML
  void Cancel(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUp();
  }

  public void setTicketId(int id) {
    ticketId = id;
  }

}
