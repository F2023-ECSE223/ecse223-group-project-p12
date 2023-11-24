package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

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
    void cancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void modify(ActionEvent event) {
      StringBuilder error = new StringBuilder();
      boolean hasError = false;
      int room, floor;
      String assetType;

      if (roomChoice.getValue().contains("no") || roomChoice.getValue().contains("select")){
        room = -1;
      }

      if (roomChoice.getValue().contains("Current")){
        room = asset.getRoomNumber();
      } else {
        room = Integer.parseInt(roomChoice.getValue());
      }

      if (floorChoice.getValue().contains("no") || floorChoice.getValue().contains("select")){
        floor = -1;
      }
  
      if (floorChoice.getValue().contains("Current")){
        floor = asset.getFloorNumber();
      } else {
        floor = Integer.parseInt(floorChoice.getValue());
      }

      if (assetTypes.getValue().contains("Select")){
        hasError = true;
        error.append("\n Please select an asset type. \n\n");
      }

      if (assetTypes.getValue().contains("Current")){
        assetType = asset.getAssetType().getName();
      } else {
        assetType = assetTypes.getValue();
      }

      if (dateChoice.getValue() == null){
        hasError = true;
        error.append("\n Please select a purchase date. \n\n");
      }

      if (hasError){
        ViewUtils.showError(error.toString());
      } else {
        int number = asset.getAssetNumber();
        AssetPlusFeatureSet3Controller.updateSpecificAsset(number, number, number, asset.getPurchaseDate(), assetType);
        //AssetMenuController.refreshTables();
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      }
    }


    public static void get(int assetNumber){
      for (TOSpecificAsset assets : AssetPlusFeatureTOController.getSpecificAssets()){
        if(assetNumber == assets.getAssetNumber()){
          asset = assets;
        }
      }
    }

    public void initialize() {

      
      assetNumber.setEditable(false);
      assetNumber.setText(asset.getAssetNumber()+"");
      assetNumber.setFocusTraversable(false);

      dateChoice.setEditable(false);
      
      assetTypes.setValue("Current type: " + asset.getAssetType().getName());

      ArrayList<String> types = new ArrayList<>();
      types.add("Current type: " + asset.getAssetType().getName());
      for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
        if (!type.equals(asset.getAssetType().getName())){
          types.add(type.getName());
        } 

      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      lifeExpectancyBox.setVisible(true);
      lifeExpectancy.setText(asset.getAssetType().getExpectedLifeSpan()+"");

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
      floors.add("No floor");
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
