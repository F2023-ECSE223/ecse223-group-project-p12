package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import javafx.application.Application;
import javafx.scene.text.Font;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    // TODO Start the application user interface here
    Application.launch(AssetPlusFXMLView.class, args);
  }

  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      // these attributes are default, you should set them later with the setters
      //assetPlus = new AssetPlus();
      assetPlus = AssetPlusPersistence.load();
    }
    return assetPlus;
  }

}
