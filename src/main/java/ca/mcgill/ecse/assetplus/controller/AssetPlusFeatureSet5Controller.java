package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * <p>Feature 5 - Add image URL to maintenance ticket and delete it</p>
 * @author Julia B.Grenier
 */
public class AssetPlusFeatureSet5Controller {
  
  /**
   * <p>Add an image URL to the specified maintenance ticket</p>
   * @param imageURL the URL of the image
   * @param ticketID the ID number of the ticket
   * @return an empty string or an error message 
   */
  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(ticketID, "ticketID") + 
                  AssetPlusFeatureUtility.isStringValid(imageURL, "Image URL", "cannot") + 
                  AssetPlusFeatureUtility.isExistingTicket(ticketID) +
                  isStartingWithHttpOrHttps(imageURL) +
                  isExistingImageURL(imageURL, ticketID);
    
    if (!err.isEmpty()) {
      System.out.println(err);
      return err;
    }

    // Add image
    try {
      // Create a new ticket image
      TicketImage createdImage = MaintenanceTicket.getWithId(ticketID).addTicketImage(imageURL);
      // Add it to the list of the maintenance ticket
      MaintenanceTicket.getWithId(ticketID).addTicketImage(createdImage);
      AssetPlusPersistence.save();
    }
    catch (RuntimeException e) {
      return e.getMessage();
    }
    return ""; // empty string means operation was successful (no error)
  }

  /**
   * <p>Delete an image URL from the specified maintenance ticket</p>
   * @param imageURL the URL of the image
   * @param ticketID the ID number of the ticket
   */
  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(ticketID, "ticketID") + 
                  AssetPlusFeatureUtility.isStringValid(imageURL, "imageURL", "cannot") + 
                  AssetPlusFeatureUtility.isExistingTicket(ticketID);
    if (!err.isEmpty()) {
      System.out.println(err);
      return;
    }

    // Find the image with the URL and delete it
    for (TicketImage image : MaintenanceTicket.getWithId(ticketID).getTicketImages() ) {
      if (imageURL.equals(image.getImageURL())) {
        image.delete();
        AssetPlusPersistence.save();
        return;
      }
    }
    System.out.println("Error: Image not found.");
    AssetPlusPersistence.save();
  }

  /**
   * <p>Check if the input string is a valid URL starting with "http://" or "https://" and returns an empty string if it is.</p>
   * @param imageURL the URL of the image
   * @return an empty string or an error message
   */
  private static String isStartingWithHttpOrHttps(String imageURL) {
    if (imageURL.startsWith("http://") || imageURL.startsWith("https://")){
      return "";
    } else {
      return "Error: Image URL must start with http:// or https://.\n";
    }
  }

  /**
  * <p>Check if the input string is an existing image URL associated with the ticket id and returns an error if it is.</p>
  * @param imageURL the URL of the image
  * @return an empty string or an error message
  */
  private static String isExistingImageURL(String imageURL, int ticketID) {
    if (MaintenanceTicket.hasWithId(ticketID)){
      for (TicketImage image : MaintenanceTicket.getWithId(ticketID).getTicketImages() ) {
        if (imageURL.equals(image.getImageURL())) {
          return "Error: Image already exists for the ticket.\n";
        }
      }
    }
    return "";
  }

}
