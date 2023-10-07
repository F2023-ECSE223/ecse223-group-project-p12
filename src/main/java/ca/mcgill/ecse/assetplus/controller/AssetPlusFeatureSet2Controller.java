package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;

/**
 * <p>Feature 2 - Add an asset type, update it, and delete it.</p>
 * @author Émilia Gagné
 */
public class AssetPlusFeatureSet2Controller {

  /**
   * <p>Add an asset type with the specified name and expected life span in days.
   * @param name the name of the asset type
   * @param expectedLifeSpanInDays the expected life span of the asset type
   * @return an empty string or an error message
   */
  public static String addAssetType(String name, int expectedLifeSpanInDays) {
    //Input validation
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  public static void deleteAssetType(String name) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

}