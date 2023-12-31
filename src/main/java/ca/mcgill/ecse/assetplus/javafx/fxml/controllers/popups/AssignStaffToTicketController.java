package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOHotelStaff;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class AssignStaffToTicketController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private RadioButton approveRadioButton;

    @FXML
    private RadioButton disapproveRadioButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<String> staffComboBox;

    @FXML
    private ComboBox<String> timeEstimateComboBox;

    private int ticketId;

    private ToggleGroup group;

    @FXML
    void initialize() {
      resources = AssetPlusFXMLView.getInstance().getBundle();

      for (TOHotelStaff staff: ViewUtils.getHotelStaffs()) {
        this.staffComboBox.getItems().add(staff.getEmail());
      }

      this.staffComboBox.setValue(resources.getString("key.TicketStatus_SelectStaff"));

      this.priorityComboBox.getItems().addAll(
        "Low",
        "Normal",
        "Urgent"
      );

      this.timeEstimateComboBox.getItems().addAll(
        "Less than a day",
        "1-3 days",
        "3-7 days",
        "1-3 weeks",
        "3 or more weeks"
      );

      errorLabel.setVisible(false);

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
      if (isValidSelection()) {
        String staffSelected = this.staffComboBox.getValue();
        AssetPlusFeatureTOController.assignTicketTo(staffSelected, ticketId, getPriority(), getTimeEstimate(), approveRadioButton.isSelected());
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      } else {
        errorLabel.setText(resources.getString("key.TicketStatus_Error_FillAllPrompts"));
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: red");
      }
    }

    private boolean isValidSelection() {
      return isValidComboBoxSelection(staffComboBox) && 
             isValidComboBoxSelection(priorityComboBox) &&
             isValidComboBoxSelection(timeEstimateComboBox);
    }

    private boolean isValidComboBoxSelection(ComboBox<String> comboBox) {
      return comboBox.getValue() != null && !comboBox.getValue().isEmpty() && !comboBox.getValue().equals(resources.getString("key.TicketStatus_SelectStaff"));
    }

    public void setTicketId(int ticketId) {
      this.ticketId = ticketId;
    }

    private String getPriority() {
      return this.priorityComboBox.getValue();
    }

    private String getTimeEstimate() {
      return this.timeEstimateComboBox.getValue();
    }

}
