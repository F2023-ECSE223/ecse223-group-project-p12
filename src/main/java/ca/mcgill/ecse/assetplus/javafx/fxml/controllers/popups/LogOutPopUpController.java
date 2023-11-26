package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LogOutPopUpController {

    @FXML
    private Button okButton;

    @FXML
    void OkClicked(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}
