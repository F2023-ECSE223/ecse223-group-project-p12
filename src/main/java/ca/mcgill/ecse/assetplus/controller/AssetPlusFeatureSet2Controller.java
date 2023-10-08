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
                 AssetPlusFeatureUtility.isLifeSpanValid(expectedLifeSpanInDays);

    if(!err.isEmpty()){
      return err;
    }

    try {
      AssetPlusApplication.getAssetPlus().addAssetType(name, expectedLifeSpanInDays);
    } 
    catch (Exception e) {
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
                 AssetPlusFeatureUtility.isLifeSpanValid(newExpectedLifeSpanInDays);

    if(!err.isEmpty()){
      return err;
    }

    try {
      for(AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
        if(type.getName()==oldName){
          type.setName(newName);
          type.setExpectedLifeSpan(newExpectedLifeSpanInDays);
          break;
        }
      }
    }
    catch (Exception e) {
      return e.getMessage();
    }

    return "";
  }

  /**
   * <p>Delete the asset type specified by its name.
   * @param name the name of the asset type
   * @return an empty string or an error message
   */
  public static String deleteAssetType(String name) {
    String err = AssetPlusFeatureUtility.isStringValid(name, "name") + 
                 AssetPlusFeatureUtility.isExistingAssetType(name);

     if(!err.isEmpty()){
      return err;
    }

    try {
      for(AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
        if(type.getName() == name){
          AssetPlusApplication.getAssetPlus().removeAssetType(type);
          break;
        }
      }
      
    }
    catch (Exception e) {
      return e.getMessage();
    }
    
    return "";
  }

}