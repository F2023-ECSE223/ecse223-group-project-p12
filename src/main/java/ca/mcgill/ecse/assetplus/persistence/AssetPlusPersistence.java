package ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

public class AssetPlusPersistence {

  private static String filename = "data.json";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

  public static void setFilename(String filename) {
    AssetPlusPersistence.filename = filename;
  }

  public static void save() {
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus ap) {
    serializer.serialize(ap, filename);
  }

  public static AssetPlus load() {
    var ap = (AssetPlus) serializer.deserialize(filename);
    // model cannot be loaded - create empty BTMS
    if (ap == null) {
      ap = new AssetPlus();
    } else {
      ap.reinitialize();
    }
    return ap;
  }

}
