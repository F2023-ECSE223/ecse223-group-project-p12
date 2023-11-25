package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
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
    private TextField ticketStatusField;

    @FXML
    private ChoiceBox<String> typeField;

    @FXML
    void initialize(){

        ticketNumberField.setEditable(false);
        ticketNumberField.setText((AssetPlusFeatureSet6Controller.getTickets().get(AssetPlusFeatureSet6Controller.getTickets().size()-1).getId()+1)+"");
        ticketNumberField.setFocusTraversable(false);

        ObservableList<TOAssetType> list = ViewUtils.getAssetTypes();
        for (TOAssetType type : list){
            typeField.getItems().add(type.getName());
        }

        //get assets only for the selected type
        ObservableList<TOSpecificAsset> list2 = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list2){
            assetNumberField.getItems().add(Integer.toString(asset.getAssetNumber()));
        }
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        // set default value to today
        raisedDateField.setValue(LocalDate.now());
        addTicketError.setText(null);

    }
    
    
    @FXML
    void addTicketClicked(ActionEvent event) {
        Integer ticketNumber = Integer.parseInt(ticketNumberField.getText());
        String description = descriptionField.getText();
        String raiser = raiserField.getText();
        //figure out date picker
        LocalDate date = raisedDateField.getValue();
        Date raisedDate = Date.valueOf(date);
        int assetNumber = -1;
        if (assetNumberField.getValue() != null){
            assetNumber = Integer.parseInt(assetNumberField.getValue());
        }

        if (ticketNumber == null || description == null || description.trim().isEmpty() || raiser == null || raiser.trim().isEmpty()|| raisedDate == null){
                addTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage"));
                System.out.println("Ticket not added");
        }
        else{
            System.out.println(ticketNumber);
            System.out.println(AssetPlusFeatureSet4Controller.isNotExistingTicket(ticketNumber));


           // int number = (AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber()+1);
            String err = AssetPlusFeatureSet4Controller.addMaintenanceTicket(ticketNumber, (java.sql.Date)raisedDate, description, raiser, assetNumber);
            ViewUtils.callController("");
       // AssetPlusFeatureSet3Controller.addSpecificAsset(number, Integer.parseInt(floorChoice.getValue()), Integer.parseInt(roomChoice.getValue()), java.sql.Date.valueOf(dateChoice.getValue()), assetTypes.getValue());
            
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");
                raiserField.setText("");
                assetNumberField.setValue(null);
                typeField.setValue(null);
                raisedDateField.setValue(null);
                addTicketError.setText(null);
                //addTicketError.setText("Your ticket has been created!");
                System.out.println("Ticket added");
                AssetPlusFXMLView.getInstance().closePopUpWindow();    
            }
            else{
                addTicketError.setText(err);
            }  

        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

}


