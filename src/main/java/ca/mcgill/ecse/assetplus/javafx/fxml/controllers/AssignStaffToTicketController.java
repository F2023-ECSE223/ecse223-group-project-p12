package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class AssignStaffToTicketController {

    @FXML
    private ChoiceBox<String> staffChoiceBox;

    @FXML
    void initialize() {

    }

    @FXML
    void handleCancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleSave(ActionEvent event) {
      // Saving Logic
      //AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}
