package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.AssetTypesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModifyAssetTypePopUpController {

  private String name;

  @FXML
  private Button ModifyAssetTypeButton;

  @FXML
  private Button CancelAssetTypeButton;

  @FXML
  private TextField AssetTypeModifyName;

  @FXML
  private TextField AssetTypeModifyLifespan;


  @FXML
  void initialize(){
    name = "";
  }

  @FXML
  void ModifyClicked(ActionEvent event) {
    String newName = AssetTypeModifyName.getText();
    int newLifespan = Integer.valueOf(AssetTypeModifyLifespan.getText());
    AssetPlusFeatureSet2Controller.updateAssetType(name, newName, newLifespan);
    AssetPlusFXMLView.getInstance().refresh();
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  @FXML
  void CancelClicked(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setName(String nameToSet) {
    name = nameToSet;
  }
  
}
