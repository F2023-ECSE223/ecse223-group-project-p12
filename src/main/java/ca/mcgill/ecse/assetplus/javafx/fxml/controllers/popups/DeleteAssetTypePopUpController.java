package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.javafx.fxml.events.AssetTypeDeletedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    name = "";
  }

  @FXML
  void DeleteClicked(ActionEvent event) {
    AssetTypeDeletedEvent assetTypeDeletedEvent = new AssetTypeDeletedEvent(ViewUtils.getTicketsFromAssetType(name));
    AssetPlusFeatureSet2Controller.deleteAssetType(name);
    AssetPlusFXMLView.getInstance().refresh();
    AssetPlusFXMLView.getInstance().fireEvent(assetTypeDeletedEvent);
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  @FXML
  void CancelClicked(ActionEvent event) {
    AssetPlusFXMLView.getInstance().closePopUpWindow();
  }

  public void setAssetTypeInfo(String name, int lifeExp, String url) {
    this.name = name;
    AssetTypeDeleteName.setText(name);
    AssetTypeDeleteLifespan.setText(Integer.toString(lifeExp));
    /* if (url != null)
      AssetTypeDeleteImageURL.setText(url); */
  }
}
