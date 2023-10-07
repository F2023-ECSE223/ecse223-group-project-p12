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
   * @return
   */

  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
        //Verify that the inputs are valid.
        String err = AssetPlusFeatureUtility.isAssetNumberValid(assetNumber) + "/n"  +
        AssetPlusFeatureUtility.isFloorNumberValid(floorNumber) + "/n"  +
        AssetPlusFeatureUtility.isRoomNumberValid(roomNumber) + "/n"  +
        AssetPlusFeatureUtility.isExistingAssetType(assetTypeName);
        if (!err.isEmpty()){
          return err;
        }

        //Add the specific asset to the AssetPlus application instance.
        try {
          for (AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
            if(type.getName() == assetTypeName)
              AssetPlusApplication.getAssetPlus().addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, type);
          }
          
        } catch (RuntimeException e){
          return e.getMessage();
        }
        return "";
  }

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        //Verify that the inputs are valid.
        String err = AssetPlusFeatureUtility.isExistingAsset(assetNumber) + "/n"  +
        AssetPlusFeatureUtility.isFloorNumberValid(newFloorNumber) + "/n"  +
        AssetPlusFeatureUtility.isRoomNumberValid(newRoomNumber) + "/n"  +
        AssetPlusFeatureUtility.isExistingAssetType(newAssetTypeName);
        if (!err.isEmpty()){
          return err;
        }

        //Update the specific asset (found based on its assetNumber) and change the fields. 
        try {
          for (SpecificAsset asset : AssetPlusApplication.getAssetPlus().getSpecificAssets()) {
            if (asset.getAssetNumber() == assetNumber) {
              asset.setFloorNumber(newFloorNumber);
              asset.setRoomNumber(newRoomNumber);
              asset.setPurchaseDate(newPurchaseDate);
              for (AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
                if(type.getName() == newAssetTypeName)
                  asset.setAssetType(type);
              }
            }
          }
       } catch (RuntimeException e){
          return e.getMessage();
        }
        return "";
  }

  public static void deleteSpecificAsset(int assetNumber) {
    //Verify that the specific asset exists.
    String err = AssetPlusFeatureUtility.isExistingAsset(assetNumber);
        if (!err.isEmpty()){
          System.out.println(err);
          return;
        }
        
        //Delete the specific asset from the AssetPlus application instance. 
        for (SpecificAsset asset : AssetPlusApplication.getAssetPlus().getSpecificAssets()) {
          if (asset.getAssetNumber() == assetNumber) {
            AssetPlusApplication.getAssetPlus().removeSpecificAsset(asset);
          }
      }
      
        
  }

}
