package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * <p> Controller file to deal with all of the transfer objects which need to be created for the view. 
 */
public class AssetPlusFeatureTOController {

  /**
   * <p>Generate a list of specific asset transfer objects. </p>
   * @return a hashmap with the keys being an asset number and the value being the associated specific asset transfer object.
   */
  public static HashMap<Integer, TOSpecificAsset> getSpecificAssets() {
  List<SpecificAsset> assets = AssetPlusApplication.getAssetPlus().getSpecificAssets();
  HashMap<Integer, TOSpecificAsset> TOassets = new HashMap<>();

  for (SpecificAsset asset : assets){
    TOassets.put(asset.getAssetNumber(), new TOSpecificAsset(asset.getAssetNumber(), asset.getFloorNumber(), asset.getRoomNumber(), asset.getPurchaseDate()));
  }
  return TOassets;
  }


  /**
   * <p>Get a list of all maintenance tickets as transfer objects</p>
   * @return the list of tickets
   */
  public static List<TOAssetType> getAssetTypes() {
    List<AssetType> assetTypes = AssetPlusApplication.getAssetPlus().getAssetTypes();
    List<TOAssetType> assetTypesTO = new ArrayList<>();

    for (AssetType assetType: assetTypes) {
      assetTypesTO.add(convertFromAssetType(assetType));
    }

    return assetTypesTO;
  }

  private static TOAssetType convertFromAssetType(AssetType assetType){

    List<SpecificAsset> assets = assetType.getSpecificAssets();
    
    //After the TOSpecificAsset methods are implemented, then we can covert SpecifAssets to SpecificAssetsTO
    List<TOSpecificAsset> TOassets= new ArrayList<>();

    TOAssetType assetTypeTO = new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan());

    for(TOSpecificAsset asset : TOassets){
      assetTypeTO.addTOSpecificAsset(asset);
    }

    return assetTypeTO;
  }

}
