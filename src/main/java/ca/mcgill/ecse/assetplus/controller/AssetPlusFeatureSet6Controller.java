package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

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
    String err = AssetPlusFeatureUtility.isStringValid(email, "email", "cannot")
        + AssetPlusFeatureUtility.isExistingUser(email, "");
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
    AssetPlusPersistence.save();

  }

  /**
   * <p>Get a list of all maintenance tickets as transfer objects</p>
   * @return the list of tickets
   */
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = 
      AssetPlusApplication.getAssetPlus().getMaintenanceTickets();
    List<TOMaintenanceTicket> tickets = new ArrayList<>();

    for (MaintenanceTicket ticket: maintenanceTickets) {
      tickets.add(convertFromMaintenanceTicket(ticket));
    }
    AssetPlusPersistence.save();
    return tickets;
  }

  /**
   * <p>Converts a maintenance ticket object into a TOMaintenanceTicket</p>
   * @param maintenanceTicket the object to convert
   * @return the converted TOMaintenanceTicket object
   */
  private static TOMaintenanceTicket convertFromMaintenanceTicket(
      MaintenanceTicket maintenanceTicket) {
    List<TOMaintenanceNote> toMaintenanceNotes =
        convertFromMaintenanceNotes(maintenanceTicket.getTicketNotes());
    TOMaintenanceNote[] allNotes = toMaintenanceNotes.toArray(new TOMaintenanceNote[0]);

    String assetName;
    Integer expectedLifeSpanInDays;
    Date purchaseDate;
    Integer floorNumber;
    Integer roomNumber;

    if (maintenanceTicket.getAsset() == null) {
      assetName = null;
      expectedLifeSpanInDays = -1;
      purchaseDate = null;
      floorNumber = -1;
      roomNumber = -1;
    } else {
      assetName = maintenanceTicket.getAsset().getAssetType().getName();
      expectedLifeSpanInDays = maintenanceTicket.getAsset().getAssetType().getExpectedLifeSpan();
      purchaseDate = maintenanceTicket.getAsset().getPurchaseDate();
      floorNumber = maintenanceTicket.getAsset().getFloorNumber();
      roomNumber = maintenanceTicket.getAsset().getRoomNumber();
    }

    AssetPlusPersistence.save();
    return new TOMaintenanceTicket(maintenanceTicket.getId(), maintenanceTicket.getRaisedOnDate(),
        maintenanceTicket.getDescription(), maintenanceTicket.getTicketRaiser().getEmail(), "status", 
        maintenanceTicket.getTicketFixer().getEmail(), maintenanceTicket.getTimeToResolve().toString(), 
        maintenanceTicket.getPriority().toString(), maintenanceTicket.hasFixApprover(), assetName, expectedLifeSpanInDays, 
        purchaseDate, floorNumber, roomNumber, convertFromTicketImages(maintenanceTicket.getTicketImages()), allNotes);

  }
  

  /**
   * <p>Converts a list of maintenance notes into a list of TOMaintenanceNote</p>
   * @param maintenanceNotes the list of notes to convert
   * @return the converted list of TOMaintenanceNote
   */
  private static List<TOMaintenanceNote> convertFromMaintenanceNotes(
      List<MaintenanceNote> maintenanceNotes) {
    List<TOMaintenanceNote> toMaintenanceNotes = new ArrayList<>();

    for (MaintenanceNote maintenanceNote : maintenanceNotes) {
      TOMaintenanceNote toMaintenanceNote = new TOMaintenanceNote(maintenanceNote.getDate(),
          maintenanceNote.getDescription(), maintenanceNote.getNoteTaker().getEmail());
      toMaintenanceNotes.add(toMaintenanceNote);
    }
    return toMaintenanceNotes;
  }

  /**
   * <p>Converts a list of TicketImage object into a list of image url strings</p>
   * @param ticketImages the list of TicketImage to convert
   * @return the converted list of image url strings
   */
  private static List<String> convertFromTicketImages(List<TicketImage> ticketImages) {
    List<String> imageURLS = new ArrayList<>();

    for (TicketImage ticketImage : ticketImages) {
      imageURLS.add(ticketImage.getImageURL());
    }
    return imageURLS;
  }

}
