package ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

 /**
   * Implementation of the Persistence Layer to the AssetPlus application.
   * 
   * @author Tayba Jusab
   * @author Émilia Gagné
   * @author Camille Pouliot
   * @author Julia B.Grenier
   * @author Sahar Fathi
   * @author Anjali Singhal
   */

public class AssetPlusPersistence {

  //The following fields and setFilename method deal with the json file location. 
  private static String filename = "ap.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

  public static void setFilename(String filename) {
    AssetPlusPersistence.filename = filename;
  }

  //This method is utilized in the controller methods in order to save the modified objects to the json file. 
  public static void save() {
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus ap) {
    serializer.serialize(ap, filename);
  }

  //This method is utilized to load the AssetPlus data back in upon restarting the application.
  public static AssetPlus load() {
    var ap = (AssetPlus) serializer.deserialize(filename);
    
    if (ap == null) {
      ap = new AssetPlus();
    } else {
      //ap.reinitialize();
    }
    return ap;
  }

}
