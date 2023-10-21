package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
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

    // Search employees
    for (Employee employee: AssetPlusApplication.getAssetPlus().getEmployees()) {
      if (employee.getEmail().equals(email)) {
        employee.delete();
      }
    }

    // Search guests
    for (Guest guest: AssetPlusApplication.getAssetPlus().getGuests()) {
      if (guest.getEmail().equals(email)) {
        guest.delete();
      }
    }
  }

  /**
   * <p>Get a list of all maintenance tickets as transfer objects
   * @return the list of tickets
   */
  public static List<TOMaintenanceTicket> getTickets() {
    List<TOMaintenanceTicket> tickets = AssetPlusFeatureUtility.getAllTickets(AssetPlusApplication.getAssetPlus().getMaintenanceTickets());
    return tickets;
  }

}