package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import static java.lang.Integer.highestOneBit;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class AddSpecificAssetPopupController {

    @FXML
    private TextField assetNumber;

    @FXML
    private ComboBox<String> assetTypes;

    @FXML
    private Button cancelAssetBtn;

    @FXML
    private Button createAssetBtn;

    @FXML
    private DatePicker dateChoice;

    @FXML
    private Rectangle fieldBg;

    @FXML
    private TextField floorChoice;

    @FXML
    private TextField lifeExpectancy;

    @FXML
    private TextField roomChoice;

    @FXML
    private Label topPopups;

    @FXML
    private VBox lifeExpectancyBox;

    @FXML 
    private VBox errorBox;

    @FXML
    private ResourceBundle resources;

    @FXML
    void cancel(ActionEvent event) {
      System.out.println("is this cancelling?");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void create(ActionEvent event) {
      int room = 0;
      int floor = 0;
      boolean hasErrorDate = false;
      boolean hasErrorType = false;
      boolean hasErrorFloor = false;
      boolean hasErrorRoom = false;

      errorBox.getChildren().clear();

      String regex = "\\d+";

      Label errorRoom= new Label(resources.getString("key.AssetMenu_ErrorRoom"));
      errorRoom.setStyle("-fx-text-fill: red;");
      if (roomChoice.getText().matches(regex)){
        room = Integer.parseInt(roomChoice.getText().trim());
        if (room < 0){
          errorBox.getChildren().add(errorRoom);
          errorBox.setVisible(true);
        }
      } else if (roomChoice.getText().equals("") || roomChoice.getText().equals("-1")) {
        room = -1;
        hasErrorRoom = false;
      } else {
        if (!hasErrorRoom) {
              errorBox.getChildren().add(errorRoom);
              errorBox.setVisible(true);
              hasErrorRoom = true;
          }
      }

      Label errorFloor = new Label(resources.getString("key.AssetMenu_ErrorFloor"));
      errorFloor.setStyle("-fx-text-fill: red;");
      if (floorChoice.getText().matches(regex)){
        floor = Integer.parseInt(floorChoice.getText().trim());
        if (floor < 0){
          errorBox.getChildren().add(errorFloor);
          errorBox.setVisible(true);
        }
      } else {
        if (!hasErrorFloor) {
              errorBox.getChildren().add(errorFloor);
              errorBox.setVisible(true);
              hasErrorFloor = true;
          }
      }

      Label errorType = new Label(resources.getString("key.AssetMenu_ErrorType"));
      if (assetTypes.getValue().contains("Select")) {
          if (!hasErrorType) {
              errorType.setStyle("-fx-text-fill: red;");
              errorBox.getChildren().add(errorType);
              errorBox.setVisible(true);
              hasErrorType = true;
          }
      } else {
          hasErrorType = false;
      }
    
    Label errorDate = new Label(resources.getString("key.AssetMenu_ErrorDate"));
    if (dateChoice.getValue() == null) {
        if (!hasErrorDate) {
            errorDate.setStyle("-fx-text-fill: red;");
            errorBox.getChildren().add(errorDate);
            errorBox.setVisible(true);
            hasErrorDate = true;
        }
    } else {
      hasErrorDate = false;
    }

      if(!hasErrorDate && !hasErrorType && !hasErrorFloor && !hasErrorRoom) {
        int number;
        if (AssetPlusFeatureTOController.getSpecificAssets().size() == 0){
          
          number = 1;
        } else {
          number = (AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber()+1);   
        }

        LocalDate date = dateChoice.getValue();
        AssetPlusFeatureSet3Controller.addSpecificAsset(number, floor, room, (java.sql.Date.valueOf(date.toString())), assetTypes.getValue());
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      } 
    }

    public void initialize() {
      floorChoice.setPromptText("Enter floor");
      roomChoice.setPromptText(("Enter room"));
      errorBox.setVisible(false);
      assetNumber.setEditable(false);
      if (AssetPlusFeatureTOController.getSpecificAssets().size() == 0){
        assetNumber.setText(1+"");
      } else {
        assetNumber.setText((AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber() + 1) +"");
      }
      
      assetNumber.setFocusTraversable(false);

      dateChoice.setEditable(false);
      
      ArrayList<String> types = new ArrayList<>();
      for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
        types.add(type.getName());
      }
      
      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      assetTypes.setValue("Select an asset type");
      
      lifeExpectancyBox.setVisible(false);


    }

    @FXML
    private void handleAssetType(ActionEvent event) {
      String selectedValue = assetTypes.getValue();
      if (!selectedValue.equals("No asset type")){
        lifeExpectancyBox.setVisible(true);

        for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
          if(selectedValue.equals(type.getName())){
            lifeExpectancy.setText(type.getExpectedLifeSpan()+" years");
          }
        }
        
      System.out.println("Selected: " + selectedValue);
   } else {
      lifeExpectancyBox.setVisible(false);
   }

  }

  
}


