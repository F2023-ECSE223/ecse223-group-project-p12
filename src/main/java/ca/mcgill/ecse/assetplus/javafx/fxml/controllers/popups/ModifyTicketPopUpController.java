package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
import java.sql.Date;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;

public class ModifyTicketPopUpController {

    private int ticketId;

    private static TOMaintenanceTicket ticket;

    @FXML
    private ComboBox<String> assetNumberField;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private TextArea descriptionField;
    
    @FXML
    private Rectangle fieldBg;
    
    @FXML
    private Button modifyTicketButton;
    
    @FXML
    private SVGPath pencilAsset;
    
    @FXML
    private SVGPath pencilDate;
    
    @FXML
    private SVGPath pencilDescription;
    
    @FXML
    private SVGPath pencilEmail;
    
    @FXML
    private SVGPath pencilType;
    
    @FXML
    private DatePicker raisedDateField;
    
    @FXML
    private TextField raiserField;
    
    @FXML
    private TextField ticketNumberField;
    
    @FXML
    private TextField ticketStatusField;
    
    @FXML
    private TextField typeField;
    
    @FXML
    private Label updateTicketError;

    @FXML
    void initialize(){
        ticketNumberField.setFocusTraversable(false);
        ticketStatusField.setFocusTraversable(false);   
        ObservableList<TOSpecificAsset> list2 = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list2){
            assetNumberField.getItems().add(Integer.toString(asset.getAssetNumber()));
        }
        assetNumberField.getItems().add("No asset");
        updateTicketError.setText(null);
        
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void updateTicketClicked(ActionEvent event) {
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
                updateTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage1"));
        }
        else{
            int ticketNumber = Integer.parseInt(ticketNumberString);
            String err = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(ticketNumber, (java.sql.Date)raisedDate, description, raiser, assetNumber);
            ViewUtils.callController("");   
            if (err == ""){
                ticketNumberField.setText("");
                descriptionField.setText("");
                raiserField.setText("");
                assetNumberField.setValue(null);
                typeField.setText(null);
                raisedDateField.setValue(null);
                updateTicketError.setText(null);
                AssetPlusFXMLView.getInstance().closePopUpWindow();    
            }
            else{
                updateTicketError.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketMenu_ErrorMessage2"));
            }  

        }
    }

    public void setTicketId(int id) {
        ticketId = id;
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        ticketStatusField.setEditable(false);
        ticketNumberField.setEditable(false);
        typeField.setEditable(false);
        typeField.setFocusTraversable(false);
        
        ticket = AssetPlusFeatureSet6Controller.getTicket(ticketId);
        
        ticketNumberField.setText(Integer.toString(ticketId));
        ticketStatusField.setText(ticket.getStatus().toString());
        descriptionField.setText(ticket.getDescription());
        raiserField.setText(ticket.getRaisedByEmail());
        typeField.setText(ticket.getAssetName());
        raisedDateField.setValue(ticket.getRaisedOnDate().toLocalDate());
        
        TOAssetType type = ViewUtils.getWithAssetName(ticket.getAssetName());
        List<TOSpecificAsset>  assets = type.getTOSpecificAssets();
        assetNumberField.setValue(((Integer) ViewUtils.getSpecificAssetFromTicket(ticket)).toString());

        updateTicketError.setText(null);

  }

@FXML
void handleSelection(ActionEvent event) {
        if (assetNumberField.getValue() != null && !assetNumberField.getValue().equals("No asset")){
        int assetNumberSelected = Integer.parseInt(assetNumberField.getValue());
        ObservableList<TOSpecificAsset> list = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list){
           if(asset.getAssetNumber() == assetNumberSelected){
                typeField.setText(asset.getAssetType().getName());
           }
        }
    
        }
}

}
