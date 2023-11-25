package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ModifySpecificAssetPopupController {

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private TextField assetNumber;

  @FXML
  private ComboBox<String> assetTypes;

  @FXML
  private Button cancelAssetBtn;

  @FXML
  private Button modifyAssetBtn;

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

  public static TOSpecificAsset asset;

  @FXML 
  private VBox errorBox;


  public static void get(int assetNumber){
    for (TOSpecificAsset assets : AssetPlusFeatureTOController.getSpecificAssets()){
      if (assets.getAssetNumber() == assetNumber){
        asset = assets;
      }
    }
  }
  
    @FXML
    void cancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void modify(ActionEvent event) {
      int room = 0;
      int floor = 0;
      String type = "";
    

      if (roomChoice.getValue().contains("No")){
        room = -1;
      } else if (roomChoice.getValue().contains("Current")) {
        room = asset.getRoomNumber();
      } else {
        room = Integer.parseInt(roomChoice.getValue());
      }

      if (floorChoice.getValue().contains("Current")) {
        floor = asset.getFloorNumber();
      } else {
        floor = Integer.parseInt(floorChoice.getValue());
      }

      if (assetTypes.getValue().contains("Current")) {
        type = asset.getAssetType().getName();
      } else {
        type = assetTypes.getValue();
      }

      int number;
      if (AssetPlusFeatureTOController.getSpecificAssets().size() == 0){
        number = 1;
      } else {
        number = asset.getAssetNumber();   
      }
      System.out.println(java.sql.Date.valueOf(dateChoice.getValue()));
      String result = AssetPlusFeatureSet3Controller.updateSpecificAsset(number, floor, room, java.sql.Date.valueOf(dateChoice.getValue()), type);
      System.out.println(result);
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();

      }


    public void initialize() {
      assetNumber.setEditable(false);
      assetNumber.setText(asset.getAssetNumber()+"");
      assetNumber.setFocusTraversable(false);

      dateChoice.setEditable(true);
      LocalDate localDate = asset.getPurchaseDate().toLocalDate();
      dateChoice.setValue(localDate);
      
      assetTypes.setValue("Current type: " + asset.getAssetType().getName());

      ArrayList<String> types = new ArrayList<>();
      types.add("Current type: " + asset.getAssetType().getName());
      for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
        if (!type.getName().equals(asset.getAssetType().getName())){
          types.add(type.getName());
        } 

      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      lifeExpectancyBox.setVisible(true);
      lifeExpectancy.setText(asset.getAssetType().getExpectedLifeSpan()+" years");

      ArrayList<String> rooms = new ArrayList<>();
      rooms.add("Current room: " + asset.getRoomNumber());
      roomChoice.setValue("Current room: " + asset.getRoomNumber());
      rooms.add("No room");
      for (int i = 0; i <= 50; i++) {
        rooms.add(i+"");
      }

      ObservableList<String> roomsList = FXCollections.observableArrayList(rooms);
      roomChoice.setItems(roomsList);
      
      ArrayList<String> floors = new ArrayList<>();
      floors.add("Current floor: " + asset.getFloorNumber());
      floorChoice.setValue("Current floor: " + asset.getFloorNumber());
      for (int i = 0; i <= 20; i++) {
        floors.add(i+"");
      }

      ObservableList<String> floorsList = FXCollections.observableArrayList(floors);
      floorChoice.setItems(floorsList);
    }

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
