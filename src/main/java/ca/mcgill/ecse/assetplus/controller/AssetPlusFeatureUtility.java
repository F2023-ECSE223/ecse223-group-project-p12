package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

/**
 * <p>This class contains input validation methods that are used in multiple controller classes</p>
 * 
 * @author Sahar Fathi
 * @author Anjali Singhal
 * @author Julia B. Grenier
 * @author Tayba Jusab
 * @author Camille Pouliot
 * @author Émilia Gagné
 */
public class AssetPlusFeatureUtility {
  // Input validation static methods:

  /**
   * <p>Check if the input integer is greater than or equal zero, and returns an empty string if it is.</p>
   * 
   * @param number the integer to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isGreaterThanOrEqualToZero(int number, String subject) {
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than or equal to 0.\n";
    }
    return "";
  }

  /**
   * <p>Check if the input string is valid, and returns an empty string if it is.</p>
   * 
   * @param input the string to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isStringValid(String input, String subject, String cannotOrMustNot) {
    if (input.isEmpty()) {
      return "The " + subject + " " + cannotOrMustNot + " be empty";
    }
    return "";
  }

  /**
   * <p>Check if the input string is an existing asset type and returns an empty string if it is.</p>
   * 
   * @param name the asset type name to check if it is an existing asset type
   * @return an empty string or an error message
   */
  public static String isExistingAssetType(String name) {
    String err = "";
    AssetType type = AssetType.getWithName(name);
    if (type == null) {
      err = "The asset type does not exist";
    }
    return err;
  }

  /**
   * <p>Check if the input number is an existing asset number and returns an empty string if it is.</p>
   * 
   * @param assetNumber the asset number to check if it is an existing asset
   * @return an empty string or an error message
   */
  public static String isExistingAsset(int assetNumber) {
    if (SpecificAsset.getWithAssetNumber(assetNumber) != null) {
      return "";
    } else {
      return "Error: there is no specific asset with that assetNumber.\n";
    }
  }

  /**
   * <p>Check if the input email is an existing user and returns an empty string if it is.</p>
   * 
   * @param email the email is associated to a user
   * @param subject a parameter to specify the error message
   * @return an empty string or an error message
   */
  public static String isExistingUser(String email, String subject) {

    String error = "";

    if (email.equals("manager@ap.com")){
         return "";
    }
    if (!User.hasWithEmail(email)) {
      switch (subject) {
        case "hotel staff":
          error = "Hotel staff does not exist";
          break;
        case "ticket raiser":
          error = "The ticket raiser does not exist";
          break;
        default:
          error = "Error: user not found";
      }
    }
    
    return error;
  }

  /**
   * <p>Check if the input id is an existing maintenance ticket and returns an empty string if it is.</p>
   * 
   * @param id the ticket id associated to a maintenance ticket
   * @return an empty string or an error message
   */
  public static String isExistingTicket(MaintenanceTicket ticket) {
    if (ticket == null || !MaintenanceTicket.hasWithId(ticket.getId())) {
      return "Maintenance ticket does not exist.";
    }
    return "";
  }

  public static String isExistingTicket(int id) {
    if (!MaintenanceTicket.hasWithId(id)) {
      return "Ticket does not exist";
    }
    return "";
  }

  /**
   * <p>Check if the input string is empty, and return an error if it is.</p>
   * 
   * @param input the string to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isDescriptionEmpty(String input) {
    if (input.isEmpty()) {
      return "Ticket description cannot be empty";
    }
    return "";
  }
}

