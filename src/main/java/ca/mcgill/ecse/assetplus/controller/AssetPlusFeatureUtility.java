package ca.mcgill.ecse.assetplus.controller;

public class AssetPlusFeatureUtility {
  // Input validation static methods:

  public static String isTicketIDValid(int ticketID) {
    if (ticketID < 0) {
      return "Error: the ticketID must be greater than 0";
    }

    return "";
  }

  public static String isStringNotEmpty(String input) {
    if (input.isEmpty()) {
      return "Error: the String input must not be empty";
    }  

    return "";
  }
}
