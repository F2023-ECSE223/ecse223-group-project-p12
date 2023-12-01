package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
  private TextField AssetTypeModifyImageURL;

  @FXML 
  private Label errorMessage;


  @FXML
  void initialize(){
    name = "";
    errorMessage.setText("");
  }

  @FXML
  void ModifyClicked(ActionEvent event) {
    String newName = AssetTypeModifyName.getText();
    String newImageURL = AssetTypeModifyImageURL.getText().isEmpty() ? null : AssetTypeModifyImageURL.getText();

    if(AssetTypeModifyLifespan.getText().isEmpty()){
      errorMessage.setText(translateErrorMessage("Lifespan field cannot be empty"));
      return;
    }

    int newLifespan = Integer.valueOf(AssetTypeModifyLifespan.getText());
    String err = AssetPlusFeatureSet2Controller.updateAssetType(name, newName, newLifespan, newImageURL);

    if(!err.isEmpty()){
      errorMessage.setText(translateErrorMessage(err));
      return;
    }

    AssetPlusFXMLView.getInstance().refresh();
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  @FXML
  void CancelClicked(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setAssetTypeInfo(String name, int lifeExp, String url) {
    this.name = name;
    AssetTypeModifyName.setText(name);
    AssetTypeModifyLifespan.setText(Integer.toString(lifeExp));
    if (url != null)
      AssetTypeModifyImageURL.setText(url);
  }  private String translateErrorMessage(String err) {
    ResourceBundle resources = AssetPlusFXMLView.getInstance().getBundle();
    switch (err) {
        case "The name must not be empty":
            return resources.getString("key.NameMustNotBeEmpty");
        case "Lifespan field cannot be empty":
            return resources.getString("key.LifespanMustNotBeEmpty");
        case "The lifespan must be greater than 0.":
            return resources.getString("key.LifeSpanMustBeGreaterThan0");
        case "Error: Image URL must start with http:// or https://.\n":
            return resources.getString("key.InvalidImageURL");
        default:
            return "Error";
    }
  }


  
}
