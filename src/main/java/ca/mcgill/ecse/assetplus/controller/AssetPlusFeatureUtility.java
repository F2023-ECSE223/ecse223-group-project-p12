package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureUtility {
  // Input validation static methods:

  public static String isGreaterThanOrEqualToZero(int number, String subject){
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than or equal to 0.\n";
    }
    return "";
  }

  public static String isStringValid(String input, String subject) {
    if (input.isEmpty()) {
      return "Error: the " + subject + " must not be an empty String.\n";
    }
    return "";
  }

  public static String isExistingAssetType(String name){
    for (AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
      if(type.getName() == name)
        return "";
    }
    return "Error: the asset type specified with this name does not exist.\n";
  }

    public static String isExistingAsset(int assetNumber) {
      if (SpecificAsset.getWithAssetNumber(assetNumber) != null){
        return "";
      } else {
        return "Error: there is no specific asset with that assetNumber.\n";
      }
    }


    public static String isExistingUser(String email) {
      if (!User.hasWithEmail(email)) {
        return "Error: user not found.";
      }
      return "";
    }

    public static String isExistingTicket(int id){
      if ( !MaintenanceTicket.hasWithId(id)){
        return "Error: ticket not found";
      }
      return "";
    }

    public static String isValidAssetNumberForTicket(int assetNumber){
      if ((SpecificAsset.getWithAssetNumber(assetNumber) != null) || (assetNumber == -1)){
        return "";
      } else {
        return "Error: not a valid asset number for a ticket.\n";
      }
    }


    // Other utility methods

    public static TOMaintenanceTicket convertFromMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
      List<TOMaintenanceNote> toMaintenanceNotes = convertFromMaintenanceNotes(maintenanceTicket.getTicketNotes());
      TOMaintenanceNote[] allNotes = toMaintenanceNotes.toArray(new TOMaintenanceNote[0]);

      return new TOMaintenanceTicket(
          maintenanceTicket.getId(),
          maintenanceTicket.getRaisedOnDate(),
          maintenanceTicket.getDescription(),
          maintenanceTicket.getTicketRaiser().getEmail(),
          maintenanceTicket.getAsset().toString(),
          maintenanceTicket.getAsset().getAssetType().getExpectedLifeSpan(),
          maintenanceTicket.getAsset().getPurchaseDate(),
          maintenanceTicket.getAsset().getFloorNumber(),
          maintenanceTicket.getAsset().getRoomNumber(),
          convertFromTicketImages(maintenanceTicket.getTicketImages()),
          allNotes
        );
    }

    public static List<TOMaintenanceNote> convertFromMaintenanceNotes(List<MaintenanceNote> maintenanceNotes) {
      List<TOMaintenanceNote> toMaintenanceNotes = new ArrayList<>();

      for (MaintenanceNote maintenanceNote: maintenanceNotes) {
        TOMaintenanceNote toMaintenanceNote = new TOMaintenanceNote(
          maintenanceNote.getDate(), 
          maintenanceNote.getDescription(), 
          maintenanceNote.getNoteTaker().getEmail());
        toMaintenanceNotes.add(toMaintenanceNote);
      }
      return toMaintenanceNotes;
    }

    public static List<String> convertFromTicketImages(List<TicketImage> ticketImages) {
      List<String> imageURLS = new ArrayList<>();

      for (TicketImage ticketImage: ticketImages) {
        imageURLS.add(ticketImage.getImageURL());
      }
      return imageURLS;
    }

}
