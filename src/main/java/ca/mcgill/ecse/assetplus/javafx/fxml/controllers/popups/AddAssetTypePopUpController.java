package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
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
  private ResourceBundle resources;

  @FXML
  void initialize(){
    name = "";
    errorMessage.setText("");
  }

  @FXML
  void CreateClicked(ActionEvent event) {
    
    boolean errorLife = false;
    
    String name = AssetTypeAddName.getText();
    String imageURL = AssetTypeAddImageURL.getText();

    if(AssetTypeAddLifespan.getText().isEmpty()){
      errorMessage.setText(translateErrorMessage("Lifespan field cannot be empty"));
      return;
    }

    int lifespan = 0;
    String regex = "\\d+";

    if (AssetTypeAddLifespan.getText().matches(regex)){
      lifespan = Integer.valueOf(AssetTypeAddLifespan.getText());
    } else {
      errorLife = true;
      errorMessage.setText(resources.getString("key.AssetTypeAddPopUpLifespanError"));
    }

    String err = AssetPlusFeatureSet2Controller.addAssetType(name, lifespan, imageURL);

    if(!err.isEmpty()){
      if (!errorLife){
        errorMessage.setText(translateErrorMessage(err));
      }
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

  private String translateErrorMessage(String err) {
    ResourceBundle resources = AssetPlusFXMLView.getInstance().getBundle();
    switch (err) {
        case "The name must not be empty":
            return resources.getString("key.NameMustNotBeEmpty");
        case "Lifespan field cannot be empty":
            return resources.getString("key.LifespanMustNotBeEmpty");
        case "The expected life span must be greater than 0 days":
            return resources.getString("key.LifeSpanMustBeGreaterThan0");
        case "Error: Image URL must start with http:// or https://.\n":
            return resources.getString("key.InvalidImageURL");
        default:
            return "Error";
    }
  }
  
}
