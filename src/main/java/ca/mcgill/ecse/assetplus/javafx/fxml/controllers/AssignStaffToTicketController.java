package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.TOHotelStaff;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AssignStaffToTicketController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ChoiceBox<String> staffChoiceBox;

    @FXML
    void initialize() {
      resources = AssetPlusFXMLView.getInstance().getBundle();

      for (TOHotelStaff staff: ViewUtils.getHotelStaffs()) {
        this.staffChoiceBox.getItems().add(staff.getName());
      }

      this.staffChoiceBox.setValue(resources.getString("key.TicketStatus_SelectStaff"));
    }

    @FXML
    void handleCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleSave(ActionEvent event) {
      String staffSelected = this.staffChoiceBox.getValue();
      //ViewUtils.assignTicketTo(staffSelected);
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}
