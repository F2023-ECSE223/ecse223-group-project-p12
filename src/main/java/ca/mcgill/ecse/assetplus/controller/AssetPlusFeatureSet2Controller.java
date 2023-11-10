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
    String err = AssetPlusFeatureUtility.isStringValid(name, "name", "must not") + 
                 isGreaterThanZero(expectedLifeSpanInDays, "expected life span") +
                 isNotExistingAssetType(name);

    if(!err.isEmpty()){
      return err;
    }

    try {
      AssetType type = AssetPlusApplication.getAssetPlus().addAssetType(name, expectedLifeSpanInDays);
      AssetPlusApplication.getAssetPlus().addAssetType(type);
    } 
    catch (RuntimeException e) {
      return e.getMessage();
    }
    AssetPlusPersistence.save();
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
    String err = AssetPlusFeatureUtility.isStringValid(newName, "name", "must not") +
                 isGreaterThanZero(newExpectedLifeSpanInDays, "expected life span") +
                 AssetPlusFeatureUtility.isExistingAssetType(oldName);

    if(!err.isEmpty()){
      return err;
    }

    if (!isSameType(oldName, newName)){
      err = isNotExistingAssetType(newName);
    }

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
    AssetPlusPersistence.save();
    return "";
  }

  /**
   * <p>Delete the asset type specified by its name.</p>
   * @param name the name of the asset type
   * @return an empty string or an error message
   */
  public static void deleteAssetType(String name) {
    String err = AssetPlusFeatureUtility.isStringValid(name, "name", "must not") + AssetPlusFeatureUtility.isExistingAssetType(name);
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
    AssetPlusPersistence.save();
    return;
  }

  /**
   * <p>Check if the input integer is greater than zero, and returns an empty string if it is.</p>
   * @param number the integer to check for input validation
   * @param subject the name of the type of number 
   * @return an empty string or an error message
   */
  private static String isGreaterThanZero(int number, String subject){
    if (number <= 0) {
      return "The "+ subject + " must be greater than 0 days";
    }
    return "";
  }

  /**
   * <p>Check if an asset with a certain name doesn't exist</p>
   * @param name the asset type name
   * @return an error message or an empty string
   */
  private static String isNotExistingAssetType(String name){
    String err = "";
    AssetType type =  AssetType.getWithName(name);
    if(type != null){
      err = "The asset type already exists";
    }
    return err;
  }

  /**
   * <p> Checks whether or not the asset type name remains the same in the update
   * @param oldName the old asset type which should be updated
   * @param newName the new name to be given to the asset type
   * @return a boolean to determine if the asset type names are the same
   */
  private static boolean isSameType(String oldName, String newName){
      if (oldName.equals(newName)){
        return true;
      } else {
        return false;
      }
  }

}