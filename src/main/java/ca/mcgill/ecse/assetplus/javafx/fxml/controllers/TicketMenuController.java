package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;

public class TicketMenuController {


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
    private Button cancelButton;

    @FXML
    private Button addTickeButton;

    @FXML
    private Button updateTicketButton;

    @FXML
    private Button deleteTicketButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private ChoiceBox<?> assetNumberField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField raiserField;

    @FXML
    private TextField raisedDateField;

    @FXML
    private TextField ticketNumberField1;

    @FXML
    private TextField ticketNumberField11;

    @FXML
    private TextField ticketNumberField12;

    @FXML
    private TextField ticketNumberField121;

    @FXML
    private TextField ticketNumberField13;

    @FXML
    private TextField ticketStatusField;

    @FXML
    private ChoiceBox<?> typeField;

    @FXML
    private Hyperlink urlField;

    @FXML
    void AddImage(ActionEvent event) {

    }

    @FXML
    void addTicketClicked(ActionEvent event) {
        int ticketNumebr = Integer.parseInt(ticketNumberField.getText());
        String description = descriptionField.getText();
        String raiser = raiserField.getText();
        //figure out date picker
        String raisedDate = raisedDateField.getText();




    }

    @FXML
    void cancelClicked(ActionEvent event) {

    }

    @FXML
    void deleteTicketClicked(ActionEvent event) {

    }

    @FXML
    void updateTicketClicked(ActionEvent event) {

    }

}


