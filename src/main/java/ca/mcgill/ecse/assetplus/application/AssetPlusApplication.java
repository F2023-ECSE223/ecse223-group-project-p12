package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import javafx.application.Application;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    getAssetPlus();
    Application.launch(AssetPlusFXMLView.class, args);
  }
  
  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      assetPlus = AssetPlusPersistence.load();
    }
    return assetPlus;
  }

}
