package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

  public static String isGreaterThanZero(int number, String subject){
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than  0.\n";
    }
    return "";
  }

  public static String isStringValid(String input, String subject) {
    if (input.isEmpty()) {
      return "Error: " + subject + " cannot be empty.\n";
    }
    return "";
  }

  public static String isExistingAssetType(String name){
    String err = "";
    AssetType type =  AssetType.getWithName(name);
    if(type == null){
      err = "The asset type does not exist";
    }
    return err;
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
      if (!MaintenanceTicket.hasWithId(id)){
        return "Ticket does not exist";
      }
      return "";
    }

    public static boolean isEmployeeEmailValid(String email) {
      return email.endsWith("@ap.com");
    }

    public static String isValidAssetNumberForTicket(int assetNumber){
      if ((SpecificAsset.getWithAssetNumber(assetNumber) != null) || (assetNumber == -1)){
        return "";
      } else {
        return "Error: not a valid asset number for a ticket.\n";
      }
    }

    public static String isStartingWithHttpOrHttps(String imageURL) {
      if (imageURL.startsWith("http://") || imageURL.startsWith("https://")){
        return "";
      } else {
        return "Error: Image URL must start with http:// or https://.\n";
      }
    }

    public static String isExistingImageURL(String imageURL, int ticketID) {
      if (MaintenanceTicket.hasWithId(ticketID)){
        for (TicketImage image : MaintenanceTicket.getWithId(ticketID).getTicketImages() ) {
          if (imageURL.equals(image.getImageURL())) {
            return "Error: Image already exists for the ticket.\n";
          }
        }
      }
      return "";
    }

    public static String guestEmailVerification(String email){
      if (email.isEmpty()) {
        return "Email cannot be empty";
      } else if (email.equalsIgnoreCase("manager@ap.com")){
        return "Email cannot be manager@ap.com";
      }

      String err = isExistingUser(email);
      
      if (err.isEmpty()) {
        return "Email already linked to an guest account";
      }
      if (email.endsWith("@ap.com")) {
        return "Email domain cannot be @ap.com";
      } else if (email.contains(" ")) {
        return "Email must not contain any spaces";
      } else {
        String[] emailParts = email.split("@");
        if (emailParts.length != 2) {
          return "Invalid email";
        } else if (emailParts[0].isEmpty() || emailParts[1].isEmpty()) {
          return "Invalid email";        
        }else {
          String[] domainParts = emailParts[1].split("\\.");
          if (domainParts.length != 2) {
            return "Invalid email";
          } else if (domainParts[0].isEmpty() || domainParts[1].isEmpty()) {
            return "Invalid email";
          }else {
            return "";
          }
        }
      }
    }

    public static String isEmptyPassword(String password){
      if (password.isEmpty()) {
        return "Password cannot be empty";
      } else {
        return "";
      }

    }
    
    public static String employeeEmailVerification(String email){
      if (email.isEmpty()) {
        return "Email cannot be empty";
      } else if (email.equalsIgnoreCase("manager@ap.com")){
        return "Email cannot be manager@ap.com";
      }

      String err = isExistingUser(email);
      
      if (err.isEmpty()) {
        return "Email already linked to an employee account";
      }
      if (email.contains(" ")) {
        return "Email must not contain any spaces";
      } else {
        String[] emailParts = email.split("@");
        if (emailParts.length != 2) {
          return "Invalid email";
        } else if (emailParts[0].isEmpty() || emailParts[1].isEmpty()) {
          return "Invalid email";        
        }else {
          String[] domainParts = emailParts[1].split("\\.");
          if (domainParts.length != 2) {
            return "Invalid email";
          } else if (domainParts[0].isEmpty() || domainParts[1].isEmpty()) {
            return "Invalid email";
          }else if (!email.endsWith("@ap.com")) {
            return "Email domain must be @ap.com";
          }else {
            return "";
          }
        }
      }
    }

    public static String managerPasswordVerification(String password) {

      if (password.isEmpty()) {
        return "Password cannot be empty";
      } else if (password.length() < 4) {
        return "Password must be at least four characters long";
      } else if (!password.contains("!") && !password.contains("#") && !password.contains("$")) {
        return "Password must contain one character out of !#$";
      } else if (password.toUpperCase().equals(password)) {
        return "Password must contain one lower-case character";
      } else if (password.toLowerCase().equals(password)) {
        return "Password must contain one upper-case character";
      } else {
        return "";
      }
    }

    // Other utility methods

    public static List<TOMaintenanceTicket> getAllTickets(List<MaintenanceTicket> maintenanceTickets) {
      List<TOMaintenanceTicket> toMaintenanceTickets = new ArrayList<>();

      for (MaintenanceTicket ticket: maintenanceTickets) {
        toMaintenanceTickets.add(convertFromMaintenanceTicket(ticket));
      }

      return toMaintenanceTickets;
    }

    public static TOMaintenanceTicket convertFromMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
      List<TOMaintenanceNote> toMaintenanceNotes = convertFromMaintenanceNotes(maintenanceTicket.getTicketNotes());
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

      return new TOMaintenanceTicket(
          maintenanceTicket.getId(),
          maintenanceTicket.getRaisedOnDate(),
          maintenanceTicket.getDescription(),
          maintenanceTicket.getTicketRaiser().getEmail(),
          assetName,
          expectedLifeSpanInDays,
          purchaseDate,
          floorNumber,
          roomNumber,
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

  public static String isLessThanLimit(String subject, int number, int limit){
    if (number < limit){
      return "The " + subject + " shall not be less than " + limit;
    } else {
      return "";
    }
  }

  
}

