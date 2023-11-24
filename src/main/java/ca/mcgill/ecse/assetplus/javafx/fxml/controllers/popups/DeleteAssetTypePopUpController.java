package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.AssetType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeleteAssetTypePopUpController {

  private String name;

  @FXML
  private Button DeleteAssetTypeButton;

  @FXML
  private Button CancelAssetTypeButton;

  @FXML
  private Label AssetTypeDeleteName;

  @FXML
  private Label AssetTypeDeleteLifespan;

  @FXML
  void initialize(){
    name ="";
  }

  @FXML
  void DeleteClicked(ActionEvent event) {
    AssetPlusFeatureSet2Controller.deleteAssetType(name);
    AssetPlusFXMLView.getInstance().refresh();
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  @FXML
  void CancelClicked(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setName(String nameToSet) {
    name = nameToSet;
    AssetTypeDeleteName.setText(name);
    String end = AssetPlusFXMLView.getInstance().getBundle().getString("key.AssetType_Years");
    AssetTypeDeleteLifespan.setText(Integer.toString(AssetType.getWithName(name).getExpectedLifeSpan())+ " " + end);
  }
  
}
