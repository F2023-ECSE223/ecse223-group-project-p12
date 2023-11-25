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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;

public class ModifyTicketPopUpController {

    private int ticketId;

    public static TOMaintenanceTicket ticket;

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
    private ComboBox<String> typeField;
    
    @FXML
    private Label updateTicketError;

    @FXML
    void initialize(){
        ticketNumberField.setEditable(false);
        ticketNumberField.setText(((Integer)ticketId).toString());
        ticketNumberField.setFocusTraversable(false);
        ticketStatusField.setEditable(false);
        //ticketStatusField.setText(.toString());
        ticketStatusField.setFocusTraversable(false);

        Button pencilBtn = new Button();
        pencilBtn.getStyleClass().add("icon-pencil");
        pencilBtn.setPickOnBounds(true);
        //pencilBtn.setOnAction(e -> pencilBtnClicked());

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
        updateTicketError.setText(null);
        
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void changeAssetNumber(MouseEvent event) {
        ObservableList<TOAssetType> list = ViewUtils.getAssetTypes();
        for (TOAssetType type : list){
            typeField.getItems().add(type.getName());
        }

        //get assets only for the selected type
        ObservableList<TOSpecificAsset> list2 = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list2){
            assetNumberField.getItems().add(Integer.toString(asset.getAssetNumber()));
        }

    }

    @FXML
    void updateTicketClicked(ActionEvent event) {
       
    }
    public void setTicketId(int id) {
        ticketId = id;
    // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        ticketStatusField.setEditable(false);
        ticketNumberField.setEditable(false);
        
        ticket = AssetPlusFeatureSet6Controller.getTicket(ticketId);
        
        ticketNumberField.setText(Integer.toString(ticketId));
        ticketStatusField.setText(ticket.getStatus().toString());
        descriptionField.setText(ticket.getDescription());
        raiserField.setText(ticket.getRaisedByEmail());
        //raisedDateField.setValue(ticket.getRaisedOnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        typeField.setValue(ticket.getAssetName());
        raisedDateField.setValue(ticket.getRaisedOnDate().toLocalDate());
        if (ticket.getAssetName() == null){
            assetNumberField.setValue(null);
        }
        else{
            TOAssetType type = ViewUtils.getWithAssetName(ticket.getAssetName());
            List<TOSpecificAsset>  assets = type.getTOSpecificAssets();
            //complete this
        }

        updateTicketError.setText(null);

  }

}
