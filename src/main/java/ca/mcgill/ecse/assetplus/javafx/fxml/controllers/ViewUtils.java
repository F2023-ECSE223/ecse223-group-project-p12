package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import static java.lang.Integer.SIZE;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddImagePopUpController;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {
  
  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(String result) {
    if (result.isEmpty()) {
      AssetPlusFXMLView.getInstance().refresh();
      return true;
    }
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  public static ObservableList<TOMaintenanceTicket> getMaintenanceTickets() {
    List<TOMaintenanceTicket> ticket = AssetPlusFeatureSet6Controller.getTickets();
    return FXCollections.observableList(ticket);
  }

  public static ObservableList<String> getTicketImages(int id) {
    TOMaintenanceTicket ticket = AssetPlusFeatureSet6Controller.getTicket(id);

    if (ticket==null) {
      return null;
    }

    return FXCollections.observableList(ticket.getImageURLs());
  }

  public static String getUsername(String email) {
    return AssetPlusFeatureSet1Controller.getUsername(email);
  }


}
