package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

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
                  AssetPlusFeatureUtility.isStartingWithHttpOrHttps(imageURL) +
                  AssetPlusFeatureUtility.isExistingImageURL(imageURL, ticketID);
    
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
        return;
      }
    }
    System.out.println("Error: Image not found.");
  }

}
