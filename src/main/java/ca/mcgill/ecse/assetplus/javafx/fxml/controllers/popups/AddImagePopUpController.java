package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddImagePopUpController {

  private int ticketId;

  @FXML
  private Button addImageButton;

  @FXML
  private Button cancelButton;

  @FXML
  private TextField urlField;

  @FXML
  private Label errorMessage;

  @FXML
  public void initialize() {
    ticketId = -1;
    errorMessage.setText("");
  }

  @FXML
  void AddImage(ActionEvent event) {
    String url = urlField.getText();

    if (AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(url, ticketId).isEmpty()) {
      errorMessage.setText("");
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
    else {
      errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddImage_NeedToStartWithHttp"));
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
