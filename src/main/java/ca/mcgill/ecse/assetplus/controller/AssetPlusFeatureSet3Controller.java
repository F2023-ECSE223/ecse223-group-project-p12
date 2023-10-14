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
   * <p> Add a specific asset with a number, floor and room number, purchase date and asset type. <p>
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
        AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(floorNumber, "floorNumber") +
        AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(roomNumber, "roomNumber")  +
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
   * <p> Update a specific asset with a new floor number, new room number, new purchase date and a new asset type. <p>
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
        String err = AssetPlusFeatureUtility.isExistingAsset(assetNumber) +
        AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(newFloorNumber, "newFloorNumber")+
        AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(newRoomNumber, "newRoomNumber") +
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
   * <p> Delete a specific asset from the AssetPlus application instance.
   * @param assetNumber the specific asset which needs to be deleted
   */
  public static void deleteSpecificAsset(int assetNumber) {
    //Verify that the specific asset exists.
    String err = AssetPlusFeatureUtility.isExistingAsset(assetNumber);
        if (!err.isEmpty()){
          System.out.println(err);
          return;
        }

        //Delete the specific asset from the AssetPlus application instance. 
        (SpecificAsset.getWithAssetNumber(assetNumber)).delete();
  }

}
