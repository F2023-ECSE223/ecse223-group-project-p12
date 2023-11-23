package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;

public class AddTicketPopUpController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Label replaceMe;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private ChoiceBox<String> assetNumberField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField raiserField;

    @FXML
    private DatePicker raisedDateField;

    @FXML
    private Label addTicketError;

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
    private ChoiceBox<String> typeField;

    @FXML
    void initialize(){

        ObservableList<TOAssetType> list = ViewUtils.getAssetTypes();
        for (TOAssetType type : list){
            typeField.getItems().add(type.getName());
        }
    }
    
    
    @FXML
    void addTicketClicked(ActionEvent event) {
        Integer ticketNumber = Integer.parseInt(ticketNumberField.getText());
        String description = descriptionField.getText();
        String raiser = raiserField.getText();
        //figure out date picker
        LocalDate date = raisedDateField.getValue();
        Date raisedDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int assetNumber = -1;
        if (assetNumberField.getValue() != null){
            assetNumber = Integer.parseInt(assetNumberField.getValue());
        }

        if (ticketNumber == null || description == null || raiser == null || raisedDate == null){
                addTicketError.setText("All required fields are not entered. Try again!");
        }
        else{
            String err = AssetPlusFeatureSet4Controller.addMaintenanceTicket(assetNumber, (java.sql.Date)raisedDate, description, raiser, assetNumber);
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");
                raiserField.setText("");
                assetNumberField.setValue(null);
                typeField.setValue(null);
                raisedDateField.setValue(null);
            }
            else{
                addTicketError.setText(err);
            }      
        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUp();
    }


     /** Calls the controller and returns true on success. This method is included for readability. */
    public static boolean successful(String controllerResult) {
        return callController(controllerResult);
    }

    /** Calls the controller and shows an error, if applicable. */
    public static boolean callController(String result) {
        if (result.isEmpty()) {
            //BtmsFxmlView.getInstance().refresh();
        return true;
         }
        //showError(result);
        return false;
  }
}


