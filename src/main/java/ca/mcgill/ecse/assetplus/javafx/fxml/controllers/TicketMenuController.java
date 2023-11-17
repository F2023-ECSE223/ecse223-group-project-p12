package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicketMenuController {

    @FXML
    private VBox Dashboard;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox MainWindow;

    @FXML
    private VBox TopContent;


    @FXML
    private Button tabAddImage;

    @FXML
    private Button tabAssetTypes;

    @FXML
    private Button tabAssetTypes1;

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
        // To implement Dialog box
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
