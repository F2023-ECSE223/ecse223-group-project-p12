package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;

/**
 * <p>Feature 6 - View status of all maintenance tickets / Delete employee / guest</p>
 * @author Tayba Jusab
 */
public class AssetPlusFeatureSet6Controller {

  /**
   * <p>Delete an employee or guest based on their email</p>
   * @param email the email of the user
   */
  public static void deleteEmployeeOrGuest(String email) {
    // Input validations
    String err = AssetPlusFeatureUtility.isStringValid(email, "email") + 
                 AssetPlusFeatureUtility.isExistingUser(email);
    if (err.isEmpty()) {
      return;
    }

    User userToDelete = User.getWithEmail(email);
    userToDelete.delete();
  }

  /**
   * <p>Get a list of all maintenance tickets as transfer objects
   * @return the list of tickets
   */
  public static List<TOMaintenanceTicket> getTickets() {
    List<TOMaintenanceTicket> tickets = new ArrayList<>();

    List<SpecificAsset> assets = AssetPlusApplication.getAssetPlus().getSpecificAssets();

    for (SpecificAsset asset : assets) {
      for (MaintenanceTicket maintenanceTicket : asset.getMaintenanceTickets()) {
        tickets.add(AssetPlusFeatureUtility.convertFromMaintenanceTicket(maintenanceTicket));
      }
    }

    return tickets;
  }

}