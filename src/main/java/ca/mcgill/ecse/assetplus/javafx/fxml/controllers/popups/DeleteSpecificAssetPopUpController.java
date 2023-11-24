package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeleteSpecificAssetPopUpController {

    @FXML
    private TextField assetNumber;

    @FXML
    private Button cancelAssetBtn;

    @FXML
    private Button deleteAssetBtn;

    @FXML
    private Label topPopups;

    public static TOSpecificAsset asset;

    @FXML
    void cancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void delete(ActionEvent event) {
        AssetPlusFeatureSet3Controller.deleteSpecificAsset(asset.getAssetNumber());
        //AssetMenuController.refreshTables();
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    public void initialize() {
      assetNumber.setEditable(false);
      assetNumber.setFocusTraversable(false);

      assetNumber.setText("#"+asset.getAssetNumber()+"");
      assetNumber.setStyle("-fx-text-fill: #333333;");

    }

    
    public static void get(int assetNumber){
      for (TOSpecificAsset assets : AssetPlusFeatureTOController.getSpecificAssets()){
        if(assetNumber == assets.getAssetNumber()){
          asset = assets;
        }
      }
    }

}
