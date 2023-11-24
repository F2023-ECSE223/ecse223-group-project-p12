package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML
    private VBox Dashboard;

    @FXML
    private Button tabAssetTypes;

    @FXML
    private Button tabAssetMenu;

    @FXML
    private Button tabEmployees;

    @FXML
    private Button tabLogOut;

    @FXML
    private Button tabSettings;

    @FXML
    private Button tabTicketMenu;

    @FXML
    private Button tabTicketStatus;

    private void ClearClassTag() {
        tabAssetTypes.getStyleClass().remove("currentMenuButton");
        tabAssetMenu.getStyleClass().remove("currentMenuButton");
        tabEmployees.getStyleClass().remove("currentMenuButton");
        tabSettings.getStyleClass().remove("currentMenuButton");
        tabTicketMenu.getStyleClass().remove("currentMenuButton");
        tabTicketStatus.getStyleClass().remove("currentMenuButton");
    }

    @FXML
    void initialize() {
        String tab = AssetPlusFXMLView.getInstance().getCurrentPage();
        ClearClassTag();
        switch (tab) {
            case "pages/AssetTypes.fxml":
                tabAssetTypes.getStyleClass().add("currentMenuButton");
                break;
            case "pages/AssetMenu.fxml":
                tabAssetMenu.getStyleClass().add("currentMenuButton");
                break;
            case "pages/Employees.fxml":
                tabEmployees.getStyleClass().add("currentMenuButton");
                break;
            case "pages/Settings.fxml":
                tabSettings.getStyleClass().add("currentMenuButton");
                break;
            case "pages/TicketMenu.fxml":
                tabTicketMenu.getStyleClass().add("currentMenuButton");
                break;
            case "pages/TicketStatus.fxml":
                tabTicketStatus.getStyleClass().add("currentMenuButton");
                break;
            default:
                break;
        }
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
    void ChangeTabToTicketStatus(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketStatus.fxml");
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
    void ChangeViewToAssetMenu(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/AssetMenu.fxml");
    }

    @FXML
    void ChangeViewToEmployees(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/Employees.fxml");
    } 
}
