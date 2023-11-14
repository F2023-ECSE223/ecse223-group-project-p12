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
 * <p> Class for the specific asset transfer object.
 */

public class TOSpecificAsset {

  private int assetNumber;
  private int floorNumber;
  private int roomNumber;
  private Date purchaseDate;
  private AssetPlus assetPlus;
  private AssetType assetType;

  /**
   * Constructor to create a specific asset transfer object according to the specific asset which needs to be transferred. 
   */
  public TOSpecificAsset(SpecificAsset asset){
    floorNumber = asset.getFloorNumber();
    assetNumber = asset.getAssetNumber();
    roomNumber = asset.getRoomNumber();
    purchaseDate = asset.getPurchaseDate();
    assetType = asset.getAssetType();
    assetPlus = asset.getAssetPlus();
  }
}




