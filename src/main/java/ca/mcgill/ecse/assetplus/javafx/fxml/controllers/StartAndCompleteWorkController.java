package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartAndCompleteWorkController {

    @FXML
    void handleCancel(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleYes(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}
