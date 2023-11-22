package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ToolbarController {

    @FXML
    private Button closeButton;

    @FXML
    private Pane draggingZone;

    @FXML
    private Button hideButton;

    @FXML
    private Button maximizeButton;

    @FXML
    void CloseWindow(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closeWindow();
    }

    @FXML
    void HideWindow(ActionEvent event) {
      AssetPlusFXMLView.getInstance().hideWindow();
    }

    @FXML
    void MaximizeWindow(ActionEvent event) {
      AssetPlusFXMLView.getInstance().maximizeWindow();
    }

    @FXML
    void DragZonePressed(MouseEvent event) {
      AssetPlusFXMLView.getInstance().onToolbarPressed(event.getScreenX(), event.getScreenY());
    }

    @FXML
    void DragZoneDragged(MouseEvent event) {
      AssetPlusFXMLView.getInstance().onToolbarDragged(event.getScreenX(), event.getScreenY());
    }
}
