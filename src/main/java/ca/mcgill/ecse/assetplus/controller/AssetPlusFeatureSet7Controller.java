package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.User;

/**
 * <p>Feature 7 - Adding, updating and deleting maintenance notes</p>
 * @author Tayba Jusab
 * @author Sahar Fathi
 */
public class AssetPlusFeatureSet7Controller {

  /**
   * <p>Add a maintenance note to a specific ticket</p>
   * @param date the date of the note
   * @param description the description of the note
   * @param ticketID the id of the ticket to which a note will be added
   * @param email the email of the note taker
   * @return an empty string or an error message
   */
  public static String addMaintenanceNote(Date date, String description, int ticketID,
      String email) {

        String err = AssetPlusFeatureUtility.isDescriptionEmpty(description) + 
                     AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                     AssetPlusFeatureUtility.isExistingUser(email, "hotel staff");

        if (!err.isEmpty()) {
          return err;
        }

        try {
          MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
          HotelStaff hotelStaff =  (HotelStaff) User.getWithEmail(email);
          MaintenanceNote note = ticket.addTicketNote(date, description, hotelStaff);
          ticket.addTicketNote(note);

        } catch (RuntimeException e){
          return e.getMessage();
        }

        return "";
  }

  /**
   * <p>Update the maintenance note of a specific ticket</p>
   * @param ticketID the id of the ticket whose note we will update
   * @param index the index of the note in the list of notes
   * @param newDate the note's new date
   * @param newDescription the note's new description
   * @param newEmail the new user who updated the note
   * @return an empty string or an error message
   */
  public static String updateMaintenanceNote(int ticketID, int index, Date newDate,
      String newDescription, String newEmail) {

        String err = AssetPlusFeatureUtility.isDescriptionEmpty(newDescription) +
                     AssetPlusFeatureUtility.isExistingTicket(ticketID) +
                     AssetPlusFeatureUtility.isExistingUser(newEmail, "hotel staff") + 
                     isExistingNote(ticketID, index);

        if (!err.isEmpty()) {
          return err;
        }

        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

        if (ticket != null && index <= ticket.getTicketNotes().size() - 1) {
          MaintenanceNote note = ticket.getTicketNote(index);
          note.setDate(newDate);
          note.setDescription(newDescription);

          HotelStaff staff = (HotelStaff) User.getWithEmail(newEmail);
          note.setNoteTaker(staff);
        }

        return "";
  }

  /**
   * <p>Delete a note of a specific ticket</p>
   * @param ticketID the ticket whose note to delete
   * @param index the index of the note to delete
   */
  public static void deleteMaintenanceNote(int ticketID, int index) {

    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID);
    
    if (!err.isEmpty()) {
      return;
    }

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

    try {
      MaintenanceNote note = ticket.getTicketNote(index);
      note.delete();
    } catch (RuntimeException e) {
      return;
    }
  }

  private static String isExistingNote(int ticketID, int index) {
    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID);
    if (err.isEmpty()) {
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

      try {
        ticket.getTicketNote(index);
      } catch (Exception e) {
        return "Note does not exist";
      }
    }
    return "";
  }

}
