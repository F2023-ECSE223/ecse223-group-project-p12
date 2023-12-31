package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
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
import java.sql.Date;

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

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  public static ObservableList<TOMaintenanceTicket> getMaintenanceTickets() {
    List<TOMaintenanceTicket> ticket = AssetPlusFeatureSet6Controller.getTickets();
    return FXCollections.observableList(ticket);
  }

  public static ObservableList<TOAssetType> getAssetTypes() {
    List<TOAssetType> assetType = AssetPlusFeatureTOController.getAssetTypes();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(assetType);
  }

   public static ObservableList<TOSpecificAsset> getSpecificAsset() {
    List<TOSpecificAsset> asset = AssetPlusFeatureTOController.getSpecificAssets();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(asset);
  }

  public static ObservableList<String> getTicketImages(int id) {
    TOMaintenanceTicket ticket = AssetPlusFeatureSet6Controller.getTicket(id);

    if (ticket==null) {
      return null;
    } 
    return FXCollections.observableList(ticket.getImageURLs());
  }

  public static ObservableList<TOMaintenanceNote> getTicketNotes(int id) {
    TOMaintenanceTicket ticket = AssetPlusFeatureSet6Controller.getTicket(id);

    if (ticket==null) {
      return null;
    }
    return FXCollections.observableList(ticket.getNotes());
  }

  public static List<TOHotelStaff> getHotelStaffs() {
    return AssetPlusFeatureSet1Controller.getHotelStaffs();
  }

  public static void startWork(int ticketId) {
    AssetPlusFeatureMaintenanceTicketController.startWorkingOnTicket(ticketId);
  }

  public static void completeWork(int ticketId) {
    AssetPlusFeatureMaintenanceTicketController.completeTicket(ticketId);
  }

  public static void approveTicket(int ticketId) {
    AssetPlusFeatureMaintenanceTicketController.approveTicket(ticketId);
  }

  public static void disapproveTicket(int ticketId, Date date, String reason) {
    AssetPlusFeatureMaintenanceTicketController.disapproveTicket(ticketId, date, reason);
  }

  public static TOMaintenanceTicket getTicket(int ticketId) {
    return AssetPlusFeatureSet6Controller.getTicket(ticketId);
  }

  public static TOAssetType getWithAssetName(String name){
  List<TOAssetType> list = getAssetTypes();
        for (TOAssetType type : list){
            if (type.getName().equals(name)){
              return type;
            }
        }
        return null;
  }

  public static int getSpecificAssetFromTicket(TOMaintenanceTicket ticket) {
    TOSpecificAsset asset = AssetPlusFeatureSet6Controller.getSpecificAssetFromTicket(ticket);
    if (asset != null) {
      return asset.getAssetNumber();
    }
    return -1;
  }

  public static List<Integer> getTicketsFromAssetType(String assetType) {
    List<Integer> ticketIds = new ArrayList<>();
    for (TOMaintenanceTicket ticket: AssetPlusFeatureSet6Controller.getTickets()) {
      String assetName = ticket.getAssetName();
      if (assetName != null && assetName.equals(assetType)) {
        ticketIds.add(ticket.getId());
      }
    }
    return ticketIds;
  }

  public static List<Integer> getTicketsFromEmployee(String email) {
    List<Integer> ticketIds = new ArrayList<>();
    for (TOMaintenanceTicket ticket: AssetPlusFeatureSet6Controller.getTickets()) {
      String employeeEmail = ticket.getRaisedByEmail();
      if (employeeEmail != null && employeeEmail.equals(email)) {
        ticketIds.add(ticket.getId());
      }
    }
    return ticketIds;
  }

  public static void deleteTicketsWithIds(List<Integer> ticketIds) {
    for (int id: ticketIds) {
      AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(id);
    }
  }
  
  public static int getNumberOfTickets(String statusOrPriority) {
    int count = 0;
    List<TOMaintenanceTicket> tickets = AssetPlusFeatureSet6Controller.getTickets();

    for (TOMaintenanceTicket ticket: tickets) {
      if (ticket.getStatus().toString().equals(statusOrPriority) || ticket.getPriority().equals(statusOrPriority)) {
        count++;
      }
    }
    
    return count;
  }
  
}
