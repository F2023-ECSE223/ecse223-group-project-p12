package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
  private TextField floorChoice;

  @FXML
  private TextField lifeExpectancy;

  @FXML
  private TextField roomChoice;

  @FXML
  private Label topPopups;

  @FXML
  private VBox lifeExpectancyBox;

  public static TOSpecificAsset asset;

  @FXML 
  private VBox errorBox;

      @FXML
    private ResourceBundle resources;


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
      boolean hasErrorFloor = false;
      boolean hasErrorRoom = false;

      if (assetTypes.getValue().contains("Current")){
        type = asset.getAssetType().getName();
      } else {
        type = assetTypes.getValue();
      }

      String regex = "\\d+";

      Label errorRoom= new Label(resources.getString("key.AssetMenu_ErrorRoom"));
      errorRoom.setStyle("-fx-text-fill: red;");
      
      if (roomChoice.getText().equals("")) {
        room = asset.getRoomNumber();
      } else if (roomChoice.getText().equals("-1")){
        room = -1;
      } else if (roomChoice.getText().matches(regex)){
        room = Integer.parseInt(roomChoice.getText().trim());
        if (room < -1){
          errorBox.getChildren().add(errorRoom);
          errorBox.setVisible(true);
          hasErrorRoom=true;
        }
        hasErrorRoom=false;
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
          hasErrorRoom=true;
        }
        hasErrorRoom=false;
      } else if (floorChoice.getText().equals("")) {
        floor = asset.getFloorNumber();
      } else {
        if (!hasErrorFloor) {
              errorBox.getChildren().add(errorFloor);
              errorBox.setVisible(true);
              hasErrorFloor = true;
          }
      }

      int number;
      if (AssetPlusFeatureTOController.getSpecificAssets().size() == 0){
        number = 1;
      } else {
        number = asset.getAssetNumber();   
      }

      System.out.println(hasErrorFloor + " and " + hasErrorRoom);
      if (!hasErrorFloor && !hasErrorRoom){
        System.out.println("is this reached");
        String result = AssetPlusFeatureSet3Controller.updateSpecificAsset(number, floor, room, java.sql.Date.valueOf(dateChoice.getValue()), type);
        System.out.println(result);
        ViewUtils.callController("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();
      }
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
      }
      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      lifeExpectancyBox.setVisible(true);
      lifeExpectancy.setText(asset.getAssetType().getExpectedLifeSpan()+" years");

      roomChoice.setPromptText("Current room: " + asset.getRoomNumber());
      floorChoice.setPromptText("Current floor: " + asset.getFloorNumber());
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
