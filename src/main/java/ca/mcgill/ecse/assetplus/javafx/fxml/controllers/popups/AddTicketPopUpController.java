package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class AddTicketPopUpController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private ComboBox<String> assetNumberField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField raiserField;

    @FXML
    private DatePicker raisedDateField;

    @FXML
    private Label addTicketError;

    @FXML
    private TextField ticketNumberField1;

    @FXML
    private TextField ticketStatusField;

    @FXML
    private TextField typeField;


    @FXML
    void initialize(){

        ticketNumberField.setEditable(false);
        ticketNumberField.setText((AssetPlusFeatureSet6Controller.getTickets().get(AssetPlusFeatureSet6Controller.getTickets().size()-1).getId()+1)+"");
        ticketNumberField.setFocusTraversable(false);
        ticketStatusField.setEditable(false);
        ticketStatusField.setFocusTraversable(false);
        typeField.setEditable(false);
        typeField.setFocusTraversable(false);
        //get assets only for the selected type
        ObservableList<TOSpecificAsset> list2 = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list2){
            assetNumberField.getItems().add(Integer.toString(asset.getAssetNumber()));
        }
        assetNumberField.getItems().add("No asset");
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        // set default value to today
        raisedDateField.setValue(LocalDate.now());
        addTicketError.setText(null);

    }
    
    @FXML
    void addTicketClicked(ActionEvent event) {
        String ticketNumberString = ticketNumberField.getText();
        String description = descriptionField.getText();
        String raiser = raiserField.getText();
        LocalDate date = raisedDateField.getValue();
        Date raisedDate = Date.valueOf(date);
        int assetNumber = -1;
        if (assetNumberField.getValue() != null && !assetNumberField.getValue().equals("No asset")){
            assetNumber = Integer.parseInt(assetNumberField.getValue());
        }
        if (description == null || description.trim().isEmpty() || raiser == null || raiser.trim().isEmpty()|| raisedDate == null || raisedDate == null){
                addTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage1"));
                System.out.println("Ticket not added");
        }
        else{
            int ticketNumber = 0;
            if (ticketNumberString != null) {
                ticketNumber = Integer.parseInt(ticketNumberString);
            }
            String err = AssetPlusFeatureSet4Controller.addMaintenanceTicket(ticketNumber, (java.sql.Date)raisedDate, description, raiser, assetNumber);
            ViewUtils.callController("");
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");
                raiserField.setText("");
                assetNumberField.setValue(null);
                typeField.setText(null);
                raisedDateField.setValue(null);
                addTicketError.setText(null);
                System.out.println("Ticket added");
                AssetPlusFXMLView.getInstance().closePopUpWindow();    
            }
            else{
                addTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage2"));
                System.out.println("Ticket not added");
            }  

        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void handleAssetNumberSelection(ActionEvent event) {
        if (assetNumberField.getValue() != null && !assetNumberField.getValue().equals("No asset")){
            //if (!assetNumberField.getValue().equals("No asset")){
        int assetNumberSelected = Integer.parseInt(assetNumberField.getValue());
        ObservableList<TOSpecificAsset> list = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list){
           if(asset.getAssetNumber() == assetNumberSelected){
                typeField.setText(asset.getAssetType().getName());
           }
        //}
        }
        }
    }
}


