package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
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

public class UpdateTicketPopUpController {

    @FXML
    private Label updateTicketError;

    @FXML
    private ChoiceBox<String> assetNumberChoice;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField ticketDescriptionField;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TextField ticketRaiserField;

    @FXML
    private TextField ticketStatusField;

    @FXML
    private DatePicker raisedDateField;

    @FXML
    private ChoiceBox<String> typeChoice;

    @FXML
    private Button updateTicketButton;

    @FXML
    void initialize(){
        // set editable to false so that the user cannot choose from the calendar
        raisedDateField.setEditable(false);
        ticketStatusField.setEditable(false);
        ticketNumberField.setEditable(false);
        
        ticketNumberField.setText("");
        ticketStatusField.setText("");
        ticketDescriptionField.setText("");
        ticketRaiserField.setText("");
        raisedDateField.setValue(null);
        assetNumberChoice.setValue("");
        typeChoice.setValue("");

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
            typeChoice.getItems().add(type.getName());
        }

        //get assets only for the selected type
        ObservableList<TOSpecificAsset> list2 = ViewUtils.getSpecificAsset();
        for (TOSpecificAsset asset : list2){
            assetNumberChoice.getItems().add(Integer.toString(asset.getAssetNumber()));
        }

    }

    @FXML
    void changeDescription(ActionEvent event) {

    }

    @FXML
    void changeRaisedDate(ActionEvent event) {

    }

    @FXML
    void changeTicketRaiser(ActionEvent event) {

    }

    @FXML
    void updateTicketClicked(ActionEvent event) {
       
    }

}
