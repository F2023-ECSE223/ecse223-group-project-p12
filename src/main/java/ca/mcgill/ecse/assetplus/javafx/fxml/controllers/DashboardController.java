package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DashboardController {
    @FXML
    void ChangeTabAddImage(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/AddImage.fxml");
    }

    @FXML
    void ChangeTabToSettings(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/Settings.fxml");
    }

    @FXML
    void ChangeTabToTicketMenu(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml");

    }

    @FXML
    void ChangeTabToViewTicketStatus(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/ViewTicketStatus.fxml");
    }

    @FXML
    void ChangeToLogOutTab(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have been logged out.");
        alert.showAndWait();
    }

    @FXML
    void ChangeViewToAssetTypes(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/AssetTypes.fxml");
    }

    @FXML
    void ChangeViewToEmployees(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/Employees.fxml");
    } 
}
