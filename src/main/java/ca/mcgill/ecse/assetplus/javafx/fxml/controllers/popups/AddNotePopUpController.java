package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddNotePopUpController {

  private int ticketId;

  @FXML
  private Button addNoteButton;

  @FXML
  private Label authorEmail;

  @FXML
  private Button cancelButton;

  @FXML
  private Label currDate;

  @FXML
  private TextArea descriptionField;

  @FXML
  private Label errorMessage;

  
  @FXML
  public void initialize() {
    ticketId = -1;
    errorMessage.setText("");
  }

  @FXML
  void AddNote(ActionEvent event) {
    String desc = descriptionField.getText();

    if (AssetPlusFeatureSet7Controller.addMaintenanceNote(null, desc, ticketId, desc)) {
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
