package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addImageButton;

    @FXML
    private Label replaceMe;

    @FXML
    private TextField ticketNumberField;

    @FXML
    void AddImage(ActionEvent event) {

    }

}


    
/* 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    // the driver and assignment choice boxes are refreshable
    replaceMe.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
      replaceMe.setText("I changed you");
    });

    // register the refreshable nodes
    AssetPlusFXMLView.getInstance().registerRefreshEvent(replaceMe);
  }
}*/