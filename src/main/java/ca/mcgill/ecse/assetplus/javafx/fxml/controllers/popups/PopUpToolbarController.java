package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PopUpToolbarController {

    @FXML
    private Button closeButton;

    @FXML
    private Pane draggingZone;

    @FXML
    void CloseWindow(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void DragZonePressed(MouseEvent event) {
      AssetPlusFXMLView.getInstance().onPopUpToolbarPressed(event.getScreenX(), event.getScreenY());
    }

    @FXML
    void DragZoneDragged(MouseEvent event) {
      AssetPlusFXMLView.getInstance().onPopUpToolbarDragged(event.getScreenX(), event.getScreenY());
    }
}
