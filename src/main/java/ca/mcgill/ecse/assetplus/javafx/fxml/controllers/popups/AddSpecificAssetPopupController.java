package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import static java.lang.Integer.highestOneBit;
import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> floorChoice;

    @FXML
    private TextField lifeExpectancy;

    @FXML
    private ComboBox<String> roomChoice;

    @FXML
    private Label topPopups;

    @FXML
    private VBox lifeExpectancyBox;

    @FXML 
    private VBox errorBox;

    @FXML
    void cancel(ActionEvent event) {
      System.out.println("is this cancelling?");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void create(ActionEvent event) {
      int room = 0, floor = 0;
      boolean hasErrorDate = false;
      boolean hasErrorType = false;

      if (roomChoice.getValue().contains("No") || roomChoice.getValue().contains("select")){
        room = -1;
      } else {
        room = Integer.parseInt(roomChoice.getValue());
      }

      if (floorChoice.getValue().contains("No") || floorChoice.getValue().contains("select")){
        floor = 0;
      } else {
        floor = Integer.parseInt(floorChoice.getValue());
      }
      
      errorBox.getChildren().clear();

      Label errorType = new Label("Please select an asset type.\n");
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

    
    Label errorDate = new Label("Please select a purchase date.\n");
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

      if(!hasErrorDate && !hasErrorType) {
        int number;
        if (AssetPlusFeatureTOController.getSpecificAssets().size() == 0){
          number = 1;
        } else {
          number = (AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber()+1);   
        }
        AssetPlusFeatureSet3Controller.addSpecificAsset(number, floor, room, java.sql.Date.valueOf(dateChoice.getValue()), assetTypes.getValue());
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      }
    }

    public void initialize() {
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

      ArrayList<String> rooms = new ArrayList<>();
      rooms.add("No room");
      for (int i = 0; i <= 50; i++) {
        rooms.add(i+"");
      }

      ObservableList<String> roomsList = FXCollections.observableArrayList(rooms);
      roomChoice.setItems(roomsList);
      
      roomChoice.setValue("Select a room");

      ArrayList<String> floors = new ArrayList<>();
      floors.add("No floor");
      for (int i = 0; i <= 20; i++) {
        floors.add(i+"");
      }

      ObservableList<String> floorsList = FXCollections.observableArrayList(floors);
      floorChoice.setItems(floorsList);

      floorChoice.setValue("Select a floor");

    }

    @FXML
    private void handleAssetType(ActionEvent event) {
      String selectedValue = assetTypes.getValue();
      if (!selectedValue.equals("No asset type")){
        lifeExpectancyBox.setVisible(true);

        for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
          if(selectedValue.equals(type.getName())){
            lifeExpectancy.setText(type.getExpectedLifeSpan()+"");
          }
        }
        
      System.out.println("Selected: " + selectedValue);
   } else {
      lifeExpectancyBox.setVisible(false);
   }

  }
}


