package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
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
    if (!err.isEmpty()) {
      return;
    }

    User userToDelete = User.getWithEmail(email);

    if (userToDelete instanceof Employee) {
      Employee employee = (Employee) userToDelete;
      employee.delete();
    } else if (userToDelete instanceof Guest) {
      Guest guest = (Guest) userToDelete;
      guest.delete();
    }

  }

  /**
   * <p>Get a list of all maintenance tickets as transfer objects
   * @return the list of tickets
   */
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = AssetPlusApplication.getAssetPlus().getMaintenanceTickets();
    List<TOMaintenanceTicket> tickets = AssetPlusFeatureUtility.getAllTickets(maintenanceTickets);
    return tickets;
  }

}