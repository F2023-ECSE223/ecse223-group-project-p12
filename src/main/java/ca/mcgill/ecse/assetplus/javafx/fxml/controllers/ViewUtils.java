package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import static java.lang.Integer.SIZE;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
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
    showError(result);
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void loadPopupWindow(String fxml, String title) { 
    AssetPlusFXMLView.getInstance().loadPopupWindow(fxml, title);
  }

  public static void closePopupWindow(Stage popUp) {
    if (popUp != null) {
      popUp.close();
    }
  }

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  public static ObservableList<TOMaintenanceTicket> getMaintenanceTickets() {
    List<TOMaintenanceTicket> ticket = AssetPlusFeatureSet6Controller.getTickets();
    return FXCollections.observableList(ticket);
  }

  public static ObservableList<TOAssetType> getAssetTypes() {
    List<TOAssetType> ticket = AssetPlusFeatureTOController.getAssetTypes();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(ticket);
  }

  public static ObservableList<String> getTicketImages(int id) {
    TOMaintenanceTicket ticket = AssetPlusFeatureSet6Controller.getTicket(id);

    if (ticket==null) {
      System.out.println("The ticket does not exist");
      return null;
    } 
    System.out.println("Exist!!");

    return FXCollections.observableList(ticket.getImageURLs());
  }

  public static String getUsername(String email) {
    return AssetPlusFeatureSet1Controller.getUsername(email);
  }

  public static List<TOHotelStaff> getHotelStaffs() {
    return AssetPlusFeatureSet1Controller.getHotelStaffs();
  }
}
