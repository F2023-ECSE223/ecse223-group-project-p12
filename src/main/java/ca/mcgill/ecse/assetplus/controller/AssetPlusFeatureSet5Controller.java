package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet5Controller {

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isTicketIDValid(ticketID) + 
                  AssetPlusFeatureUtility.isStringNotEmpty(imageURL);
    if (!err.isEmpty()) {
      return err;
    }

    // Add image
    try {
      AssetPlusApplication.getAssetPlus().getMaintenanceTicket(ticketID).addTicketImage(imageURL);
    }
    catch (RuntimeException e) {
      return e.getMessage();
    }
    return ""; // empty string means operation was successful (no error)
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isTicketIDValid(ticketID) + 
                  AssetPlusFeatureUtility.isStringNotEmpty(imageURL);
    if (!err.isEmpty()) {
      return;
    }

    // Find the image with the URL and delete it
    for (TicketImage image : AssetPlusApplication.getAssetPlus().getMaintenanceTicket(ticketID).getTicketImages() ) {
      if (image.getImageURL() == imageURL) {
        image.getTicket().removeTicketImage(image);
      }
    }
  }

}
