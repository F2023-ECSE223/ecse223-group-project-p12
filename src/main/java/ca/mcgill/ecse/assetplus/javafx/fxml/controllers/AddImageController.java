package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;
import java.net.URL;

public class AddImageController implements Initializable{

    private ResourceBundle bundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
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
