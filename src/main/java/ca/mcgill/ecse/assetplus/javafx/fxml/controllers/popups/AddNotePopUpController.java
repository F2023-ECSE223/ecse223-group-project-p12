package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOHotelStaff;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.time.LocalDate;

public class AddNotePopUpController {

  private int ticketId;

  @FXML
  private Label instructionLabel;

  @FXML
  private Button addNoteButton;

  @FXML
  private ComboBox<String> authorEmail;

  @FXML
  private Button cancelButton;

  @FXML
  private DatePicker datePicker;

  @FXML
  private TextArea descriptionField;

  @FXML
  private Label errorMessage;

  
  @FXML
  public void initialize() {
    ticketId = -1;
    errorMessage.setText("");

    LocalDate localDate = LocalDate.now();
    datePicker.setValue(localDate);

    for (TOHotelStaff staff: ViewUtils.getHotelStaffs()) {
      this.authorEmail.getItems().add(staff.getEmail());
    }
  }

  @FXML
  void AddNote(ActionEvent event) {
    String desc = descriptionField.getText();
    String email = authorEmail.getValue();
    Date date = Date.valueOf(datePicker.getValue());

    if (email == null) {
      errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_ErrorAuthor"));
    }
    else if (isDisapproveNote) {
      ViewUtils.disapproveTicket(ticketId, date, desc);
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
    else if (AssetPlusFeatureSet7Controller.addMaintenanceNote(date, desc, ticketId, email).isEmpty()) {
      errorMessage.setText("");
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
    else {
      errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_ErrorDescription"));
    }
  }

  @FXML
  void Cancel(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setTicketId(int id) {
    ticketId = id;
  }

  public void setDisapproveReason() {
    instructionLabel.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_WhyDisapprove"));
  }
}
