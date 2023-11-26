package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOHotelStaff;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import static java.lang.Integer.valueOf;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifyNotePopUpController {

  private int ticketId;
  private int index;

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

    for (TOHotelStaff staff: ViewUtils.getHotelStaffs()) {
      this.authorEmail.getItems().add(staff.getEmail());
    }
  }

  @FXML
  void UpdateNote(ActionEvent event) {
    String desc = descriptionField.getText();
    String email = authorEmail.getValue();
    Date date = Date.valueOf(datePicker.getValue());

    if (email == null) {
      errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_ErrorAuthor"));
    }
    else if (AssetPlusFeatureSet7Controller.updateMaintenanceNote(ticketId,index,date,desc,email).isEmpty()) {
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

  public void setTicketIdAndIndex(int id, int index) {
    this.ticketId = id;
    this.index = index;

    TOMaintenanceNote note = ViewUtils.getTicketNotes(id).get(index);
    datePicker.setValue(note.getDate().toLocalDate());
    authorEmail.setValue(note.getNoteTakerEmail());
    descriptionField.setText(note.getDescription());
  }

}
