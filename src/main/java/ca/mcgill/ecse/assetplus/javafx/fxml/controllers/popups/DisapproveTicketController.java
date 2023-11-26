package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Date;

public class DisapproveTicketController {

  @FXML
  private DatePicker datePicker;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField reasonTextField;

    private int ticketId;

    @FXML
    void initialize() {
      errorMessage.setVisible(false);
    }

    @FXML
    void onCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void onDisapprove(ActionEvent event) {
      if (fieldsAreEmpty() || datePicker.getValue() == null) {
        errorMessage.setText("Please fill in all fields, including the date.");
        errorMessage.setVisible(true);
    } else {
      ViewUtils.disapproveTicket(ticketId, Date.valueOf(datePicker.getValue()), reasonTextField.getText());
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
  }

  private boolean fieldsAreEmpty() {
      return descriptionField.getText().isEmpty() || reasonTextField.getText().isEmpty();
  }

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

}
