package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.AssetTypesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddAssetTypePopUpController {

  private String name;

  @FXML
  private Button CreateAssetTypeButton;

  @FXML
  private Button CancelAssetTypeButton;

  @FXML
  private TextField AssetTypeAddName;

  @FXML
  private TextField AssetTypeAddLifespan;

  @FXML
  private TextField AssetTypeAddImageURL;

  @FXML
  private Label errorMessage;


  @FXML
  void initialize(){
    name = "";
    errorMessage.setText("");
  }

  @FXML
  void CreateClicked(ActionEvent event) {

    String name = AssetTypeAddName.getText();

    if(AssetTypeAddLifespan.getText().isEmpty()){
      errorMessage.setText("Lifespan field cannot be empty");
      return;
    }

    int lifespan = Integer.valueOf(AssetTypeAddLifespan.getText());

    String err = AssetPlusFeatureSet2Controller.addAssetType(name, lifespan);

    if(!err.isEmpty()){
      errorMessage.setText(err);
      return;
    }

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
