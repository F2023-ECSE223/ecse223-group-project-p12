package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * <p>Feature 4 - Add, update, and delete Maintenance ticket</p>
 * @author Anjali Singhal
 */

public class AssetPlusFeatureSet4Controller {


  /**
   * <p>Add a maintenance ticket with an id, raised date, description, email and asset number.</p>
   * @param id the unique number associated to the maintenance ticket 
   * @param raisedDate the date on which the maintenance ticket was raised
   * @param description the details/desciption of the maintenance ticket
   * @param email the email of the user who raised the ticket
   * @param newAssetNumber the asset number of the asset associated with the ticket
   * @return an empty string or an error message
   */
  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
     //Input Validation 
    String err = AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(id, "Ticket id") + 
    isNotExistingTicket(id) + AssetPlusFeatureUtility.isDescriptionEmpty(description) + 
    AssetPlusFeatureUtility.isStringValid(email, "Email", "cannot") + 
    AssetPlusFeatureUtility.isExistingUser(email, "ticket raiser") + 
    isValidAssetNumberForTicket(assetNumber);
  
    if (!err.isEmpty()){
        return err;
    }

    //Add the specific ticket to the AssetPlus application instance.
    try {
        MaintenanceTicket newTicket = AssetPlusApplication.getAssetPlus().addMaintenanceTicket(id, raisedOnDate, description, User.getWithEmail(email));
        if (assetNumber != -1) {
          SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
          newTicket.setAsset(asset);
        }
        //if no asset associated to the ticket
        else {
          newTicket.setAsset(null);
        }
        AssetPlusApplication.getAssetPlus().addMaintenanceTicket(newTicket);
      }
    catch (RuntimeException e){
        return e.getMessage();
    }
    AssetPlusPersistence.save();
      return "";  
  }

  /**
   * <p>Update a maintenance ticket of the specified ID with a new raised date, new description, new email and new asset number.</p>
   * @param id the unique number associated to the maintenance ticket 
   * @param newRaisedDate the date on which the maintenance ticket was raised
   * @param newDescription the details/desciption of the maintenance ticket
   * @param newEmail the email of the user who raised the ticket
   * @param newAssetNumber the asset number of the asset associated with the ticket
   * @return an empty string or an error message
   */
  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    //Input Validation

    String err = AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(id, "Ticket id") + 
    AssetPlusFeatureUtility.isDescriptionEmpty(newDescription) + 
    AssetPlusFeatureUtility.isExistingTicket(id) +
    AssetPlusFeatureUtility.isStringValid(newEmail, "Email", "cannot") + 
    AssetPlusFeatureUtility.isExistingUser(newEmail, "ticket raiser") + 
    isValidAssetNumberForTicket(newAssetNumber);
  
    
    if (!err.isEmpty()){
        return err;
    }

    //Update the specific ticket (found based on its id) and change the corresponding fields in the AssetPlus application instance.
    try {
        MaintenanceTicket currentTicket = MaintenanceTicket.getWithId(id);
        currentTicket.setRaisedOnDate(newRaisedOnDate);
        currentTicket.setDescription(newDescription);
        currentTicket.setTicketRaiser(User.getWithEmail(newEmail));
        if (newAssetNumber != -1){
          SpecificAsset asset = SpecificAsset.getWithAssetNumber(newAssetNumber);
          currentTicket.setAsset(asset);
        }
        else {
          currentTicket.setAsset(null);
        }
    } catch (RuntimeException e){
        return e.getMessage();
    }
    AssetPlusPersistence.save();
      return "";
  }

  /**
   * <p>Delete a maintenance ticket with the specified ID from the AssetPlus application instance.</p>
   * @param id the unique number associated to the maintenance ticket
   */
  public static void deleteMaintenanceTicket(int id) {
    //Input Validation
    String err = AssetPlusFeatureUtility.isExistingTicket(id);
    
    //Verify that specific ticket exists
    if (!err.isEmpty()){
      System.out.println(err);
      return;
    }

    //Delete the specific ticket from the AssetPlus application instance. 
    MaintenanceTicket.getWithId(id).delete();
  }


  /**
   * <p>Check if the input number is a valid asset number to input in a ticket and returns an empty string if it is.</p>
   * @param assetNumber the number associated to an asset and can be -1 if there is not asset associated to the ticket
   * @return an empty string or an error message
   */
  private static String isValidAssetNumberForTicket(int assetNumber){
    if ((SpecificAsset.getWithAssetNumber(assetNumber) != null) || (assetNumber == -1)){
      return "";
    } else {
      return "The asset does not exist";
    }
  }

  /**
   * <p>Check if the input id is an  not an existing maintenance ticket and returns an error if it is.</p>
   * @param id the ticket id associated to a maintenance ticket
   * @return an empty string or an error message
   */
  private static String isNotExistingTicket(int id){
    if (MaintenanceTicket.hasWithId(id)){
      return "Ticket id already exists";
    }
    return "";
  }

}
