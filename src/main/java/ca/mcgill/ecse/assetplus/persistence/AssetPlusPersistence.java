package ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

/**
 * This class defines the persistence layer for the AssetPlusApplcation. 
 *
 * @author Sahar Fathi
 */

public class AssetPlusPersistence {

  //This sets up the json file which in which objects will be serialized, and the file used to deserialize back into objects.
  private static String filename = "ap.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

  public static void setFilename(String filename) {
    AssetPlusPersistence.filename = filename;
  }

  //This method is called in the different controller actions in order to update the json file. 
  public static void save() {
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus ap) {
    serializer.serialize(ap, filename);
  }

  //This method is used to load the data back in from the json file after the application has been terminated. 
  public static AssetPlus load() {
    var ap = (AssetPlus) serializer.deserialize(filename);
    
    if (ap == null) {
      ap = new AssetPlus();
    } else {
      ap.reinitialize();
    }
    return ap;
  }

}
