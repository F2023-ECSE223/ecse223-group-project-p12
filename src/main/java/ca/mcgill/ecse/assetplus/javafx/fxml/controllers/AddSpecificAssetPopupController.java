package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.nio.file.AtomicMoveNotSupportedException;
import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class AddSpecificAssetPopupController {

    @FXML
    private TextField assetNumber;

    @FXML
    private ChoiceBox<String> assetTypes;

    @FXML
    private Button cancelAssetBtn;

    @FXML
    private Button createAssetBtn;

    @FXML
    private DatePicker dateChoice;

    @FXML
    private Rectangle fieldBg;

    @FXML
    private ChoiceBox<String> floorChoice;

    @FXML
    private TextField lifeExpectancy;

    @FXML
    private ChoiceBox<String> roomChoice;

    @FXML
    private Label topPopups;

    @FXML
    private VBox lifeExpectancyBox;

    @FXML
    void cancel(ActionEvent event) {
      System.out.println("is this cancelling?");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void create(ActionEvent event) {

    }
    public void initialize() {

      assetNumber.setEditable(false);
      assetNumber.setText((AssetPlusFeatureTOController.getSpecificAssets().get(AssetPlusFeatureTOController.getSpecificAssets().size()-1).getAssetNumber()+1)+"");
      assetNumber.setFocusTraversable(false);
      
      ArrayList<String> types = new ArrayList<>();
      for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
        types.add(type.getName());
      }
      types.add("No asset type");

      ObservableList<String> typesList = FXCollections.observableArrayList(types);
      assetTypes.setItems(typesList);

      assetTypes.setValue("Select an asset type");
      
      lifeExpectancyBox.setVisible(false);

      ArrayList<String> rooms = new ArrayList<>();
      for (int i = 0; i <= 50; i++) {
        rooms.add(i+"");
      }
      rooms.add("No room");

      ObservableList<String> roomsList = FXCollections.observableArrayList(rooms);
      roomChoice.setItems(roomsList);
      
      roomChoice.setValue("Select a room");

      ArrayList<String> floors = new ArrayList<>();
      for (int i = 0; i <= 20; i++) {
        floors.add(i+"");
      }
      floors.add("No floor");
      
      ObservableList<String> floorsList = FXCollections.observableArrayList(floors);
      floorChoice.setItems(floorsList);

      floorChoice.setValue("Select a floor");

    }

    @FXML
    private void handleAssetType(ActionEvent event) {
      String selectedValue = assetTypes.getValue();
      System.out.println("Selected: " + selectedValue);
      lifeExpectancyBox.setVisible(true);

   }

}
