package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
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
      System.out.println("is this cancelling?");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void create(ActionEvent event) {

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
      
      ArrayList<String> types = new ArrayList<>();
      types.add("No asset type");
      for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
        types.add(type.getName());
      }
      
      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      assetTypes.setPromptText("Select an asset type");
      
      lifeExpectancyBox.setVisible(false);

      ArrayList<String> rooms = new ArrayList<>();
      rooms.add("No room");
      for (int i = 0; i <= 50; i++) {
        rooms.add(i+"");
      }

      ObservableList<String> roomsList = FXCollections.observableArrayList(rooms);
      roomChoice.setItems(roomsList);
      
      roomChoice.setPromptText("Select a room");

      ArrayList<String> floors = new ArrayList<>();
      floors.add("No floor");
      for (int i = 0; i <= 20; i++) {
        floors.add(i+"");
      }

      ObservableList<String> floorsList = FXCollections.observableArrayList(floors);
      floorChoice.setItems(floorsList);

      floorChoice.setPromptText("Select a floor");

    }

    @FXML
    void handleAssetType(ActionEvent event) {

    }

}
