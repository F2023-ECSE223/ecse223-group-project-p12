package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import org.checkerframework.checker.units.qual.radians;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;

public class AssetPlusFeatureSet7Controller {

  public static String addMaintenanceNote(Date date, String description, int ticketID,
      String email) {
        String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
        AssetPlusFeatureUtility.isExistingHotelStaff(email) +
        AssetPlusFeatureUtility.isDescriptionValid(description);

        if (!err.isEmpty()){
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

  // index starts at 0
  public static String updateMaintenanceNote(int ticketID, int index, Date newDate,
      String newDescription, String newEmail) {
        String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
        AssetPlusFeatureUtility.isDescriptionValid(newDescription);
        //missing check for existing note

        if (!err.isEmpty()){
          return err;
        } 
        
        try {
          MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);

          ticket.getTicketNote(index).setDate(newDate);
          HotelStaff hotelStaff =  (HotelStaff) User.getWithEmail(newEmail);
          ticket.getTicketNote(index).setNoteTaker(hotelStaff);
          ticket.getTicketNote(index).setDescription(newDescription);
        } catch (RuntimeException e){
          return e.getMessage();
        }
        return "";
    
  }

  // index starts at 0
  public static void deleteMaintenanceNote(int ticketID, int index) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

}
