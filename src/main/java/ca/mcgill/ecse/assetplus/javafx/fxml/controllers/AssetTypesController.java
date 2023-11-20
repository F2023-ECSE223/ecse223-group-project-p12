package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AssetTypesController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addAssetTypeButton;

    @FXML
    private Button AddAssetTypeCreateButton;

    @FXML
    private Button AddAssetTypeCancelButton;

    @FXML
    private Label replaceMe;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TextField AddAssetTypeName;

    @FXML
    private TextField AddAssetTypeLifespan;

    @FXML 
    private AnchorPane AddAssetTypePane;

    @FXML
    void initialize() {
    
    }

    @FXML
    void AddImage(ActionEvent event) {

    }

    @FXML
    void addAssetTypeClicked(ActionEvent event) {
      AddAssetTypePane.setVisible(true); 
    }

    @FXML
    void AddAssetTypeCreateButtonClicked(ActionEvent event) {
      AddAssetTypePane.setVisible(false); 
    }

    @FXML
    void AddAssetTypeCancelButtonClicked(ActionEvent event) {
      AddAssetTypePane.setVisible(false); 
    }
}


