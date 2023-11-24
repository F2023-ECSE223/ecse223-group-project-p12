package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOHotelStaff;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AssignStaffToTicketController {

    @FXML
    private ChoiceBox<String> staffChoiceBox;

    @FXML
    void initialize() {
      for (TOHotelStaff staff: ViewUtils.getHotelStaffs()) {
        this.staffChoiceBox.getItems().add(staff.getName());
      }
    }

    @FXML
    void handleCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleSave(ActionEvent event) {
      // Saving Logic
      //AssetPlusFXMLView.getInstance().closePopUpWindow();
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}
