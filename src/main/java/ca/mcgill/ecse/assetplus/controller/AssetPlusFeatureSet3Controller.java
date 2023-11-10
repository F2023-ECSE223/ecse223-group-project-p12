package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;

/**
 * <p>Feature 3 - Add, update, and delete asset</p>
 * @author Sahar Fathi
 */

public class AssetPlusFeatureSet3Controller {

  /**
   * <p>Add a specific asset with a number, floor and room number, purchase date and asset type.</p>
   * @param assetNumber the number associated to the specific asset
   * @param floorNumber the floor number on which the specific asset is located 
   * @param roomNumber the room number in which the specific asset is located 
   * @param purchaseDate the date on which the specific asset was purchased
   * @param assetTypeName the type of asset which the specific asset belongs to
   * @return an empty string or an error message
   */

  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
        //Verify that the inputs are valid.
        String err = AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(assetNumber, "assetNumber") +
        isLessThanLimit("asset number", assetNumber, 1) + 
        isLessThanLimit("floor number", floorNumber, 0) +
        isLessThanLimit("room number", roomNumber, -1)  +
        AssetPlusFeatureUtility.isExistingAssetType(assetTypeName);
        if (!err.isEmpty()){
          return err;
        }

        //Add the specific asset to the AssetPlus application instance.
        try {
          SpecificAsset asset = AssetPlusApplication.getAssetPlus().addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, AssetType.getWithName(assetTypeName));
          AssetPlusApplication.getAssetPlus().addSpecificAsset(asset);
        } catch (RuntimeException e){
          return e.getMessage();
        }
        return "";
  }

  /**
   * <p>Update a specific asset with a new floor number, new room number, new purchase date and a new asset type.</p>
   * @param assetNumber the number associated to the specific asset
   * @param newFloorNumber the new floor number on which the specific asset is located  
   * @param newRoomNumber the new room number in which the specific asset is located 
   * @param newPurchaseDate the new date on which the specific asset was purchased
   * @param newAssetTypeName the new type of asset which the specific asset belongs to
   * @return an empty string or an error message
   */
  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        //Verify that the inputs are valid.
        String err = isExistingAsset(assetNumber) +
        isLessThanLimit("floor number", newFloorNumber, 0) +
        isLessThanLimit("room number", newRoomNumber, -1)  +
        AssetPlusFeatureUtility.isExistingAssetType(newAssetTypeName);
        if (!err.isEmpty()){
          return err;
        }

        //Update the specific asset (found based on its assetNumber) and change the fields.
        try {
          SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
          asset.setFloorNumber(newFloorNumber);
          asset.setRoomNumber(newRoomNumber);
          asset.setPurchaseDate(newPurchaseDate);
          asset.setAssetType(AssetType.getWithName(newAssetTypeName));
       } catch (RuntimeException e){
          return e.getMessage();
        }
        return "";
  }

  /**
   * <p>Delete a specific asset from the AssetPlus application instance.</p>
   * @param assetNumber the specific asset which needs to be deleted
   */
  public static void deleteSpecificAsset(int assetNumber) {
    //Verify that the specific asset exists.
    String err = isExistingAsset(assetNumber);
        if (!err.isEmpty()){
          System.out.println(err);
          return;
        }

        //Delete the specific asset from the AssetPlus application instance. 
        (SpecificAsset.getWithAssetNumber(assetNumber)).delete();
  }

  /**
   * <p>Check if the input number is an existing asset number and returns an empty string if it is.</p>
   * @param assetNumber the asset number to check if it is an existing asset
   * @return an empty string or an error message
   */
  private static String isExistingAsset(int assetNumber) {
    if (SpecificAsset.getWithAssetNumber(assetNumber) != null){
      return "";
    } else {
      return "Error: there is no specific asset with that assetNumber.\n";
    }
  }

  /**
   * <p>Check if the input number is less than the specified limit, and returns an error if it is.</p>
   * @param subject the type of number
   * @param number the integer to check for input validation
   * @param limit the minimum value the number should have
   * @return an empty string or an error message
   */
  private static String isLessThanLimit(String subject, int number, int limit){
    if (number < limit){
      return "The " + subject + " shall not be less than " + limit;
    } else {
      return "";
    }
  }

}
