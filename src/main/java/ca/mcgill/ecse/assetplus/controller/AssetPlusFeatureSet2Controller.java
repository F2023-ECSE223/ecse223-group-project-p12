package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;

/**
 * <p>Feature 2 - Add an asset type, update it, and delete it.</p>
 * @author Émilia Gagné
 */
public class AssetPlusFeatureSet2Controller {

  /**
   * <p>Add an asset type with the specified name and expected life span in days.</p>
   * @param name the name of the asset type
   * @param expectedLifeSpanInDays the expected life span of the asset type
   * @return an empty string or an error message
   */
  public static String addAssetType(String name, int expectedLifeSpanInDays) {
    //Input validation
    String err = AssetPlusFeatureUtility.isStringValid(name, "name") + 
                 AssetPlusFeatureUtility.isGreaterThanZero(expectedLifeSpanInDays, "expectedLifeSpanInDays");

    if(!err.isEmpty()){
      return err;
    }

    try {
      AssetPlusApplication.getAssetPlus().addAssetType(name, expectedLifeSpanInDays);
    } 
    catch (RuntimeException e) {
      return e.getMessage();
    }

    return "";
  }

  /**
   * <p>Update the name and expected life span of the asset type specified by its old name</p>
   * @param oldName the name of the asset type to modify
   * @param newName the new name of this asset type
   * @param newExpectedLifeSpanInDays the new expected life span of this asset type
   * @return an empty string or an error message
   */
  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
    //Input validation
    String err = AssetPlusFeatureUtility.isStringValid(newName, "newName") +
                 AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(newExpectedLifeSpanInDays, "newExpectedLifeSpanInDays");

    if(!err.isEmpty()){
      return err;
    }

    try {
      AssetType type = AssetType.getWithName(oldName);
      type.setName(newName);
      type.setExpectedLifeSpan(newExpectedLifeSpanInDays);
    }
    catch (RuntimeException e) {
      return e.getMessage();
    }

    return "";
  }

  /**
   * <p>Delete the asset type specified by its name.
   * @param name the name of the asset type
   * @return an empty string or an error message
   */
  public static void deleteAssetType(String name) {
    String err = AssetPlusFeatureUtility.isStringValid(name, "name") + AssetPlusFeatureUtility.isExistingAssetType(name);
    if(!err.isEmpty()){
      System.out.println(err);
      return;
    }

    try {
      AssetType type = AssetType.getWithName(name);
      type.delete();
    }
    catch (RuntimeException e) {
      System.out.println(err);
    }
    
    return;
  }

}