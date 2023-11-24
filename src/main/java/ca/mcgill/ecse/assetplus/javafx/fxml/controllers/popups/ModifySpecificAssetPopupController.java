package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
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

public class ModifySpecificAssetPopupController {

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

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void create(ActionEvent event) {

    }

    public void initialize() {

      assetNumber.setEditable(false);
      assetNumber.setText((AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber()+1)+"");
      assetNumber.setFocusTraversable(false);

      dateChoice.setEditable(false);
      
      ArrayList<String> types = new ArrayList<>();
      types.add("No asset type");
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
    void handleAssetType(ActionEvent event) {

    }

}
