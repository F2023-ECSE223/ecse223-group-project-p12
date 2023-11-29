package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;


/**
 * <p> Controller file to deal with all of the transfer objects which need to be created for the view. 
 */
public class AssetPlusFeatureTOController {

  /**
   * <p>Generate a list of specific asset transfer objects. </p>
   * @return a list with the indexes holding the associated specific asset transfer object.
   */
  public static List<TOSpecificAsset> getSpecificAssets() {
    List<SpecificAsset> assets = 
      AssetPlusApplication.getAssetPlus().getSpecificAssets();
    List<TOSpecificAsset> convertedAssets = new ArrayList<>();

    for (SpecificAsset asset: assets) {

      TOAssetType assetType = new TOAssetType(asset.getAssetType().getName(), asset.getAssetType().getExpectedLifeSpan());
      if(asset.getAssetType().getImage() != null && !asset.getAssetType().getImage().isEmpty()){
        assetType.setImageURL(asset.getAssetType().getImage());
      }
      convertedAssets.add(new TOSpecificAsset(asset.getAssetNumber(), asset.getFloorNumber(), asset.getRoomNumber(), asset.getPurchaseDate(), assetType));
    }
    return convertedAssets;
  }

  public static HashMap<String, List<TOSpecificAsset>> getAssetToType() {
    HashMap<String, List<TOSpecificAsset>> list = new HashMap<>();

    for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()) {
        list.put(type.getName(), new ArrayList<>());
    }

    for (TOSpecificAsset asset : AssetPlusFeatureTOController.getSpecificAssets()) {
        String assetType = asset.getAssetType().getName();
        
        if (list.containsKey(assetType)) {
            list.get(assetType).add(asset);
        }
    }

    return list;
}

public static ArrayList<Integer> getAssetNumberFromType(String typeName){
  HashMap<String, List<TOSpecificAsset>> list = AssetPlusFeatureTOController.getAssetToType();
  List<TOSpecificAsset> assets = list.get(typeName);
  ArrayList<Integer> assetNumbers = new ArrayList<Integer>();
  for (TOSpecificAsset asset: assets){
    assetNumbers.add((Integer)asset.getAssetNumber());
  }
  return assetNumbers;
} 

  /**
   * <p>Get a list of all maintenance tickets as transfer objects</p>
   * @return the list of tickets
   */
  public static List<TOAssetType> getAssetTypes() {
    List<AssetType> assetTypes = AssetPlusApplication.getAssetPlus().getAssetTypes();
    List<TOAssetType> assetTypesTO = new ArrayList<>();

    for (AssetType assetType: assetTypes) {
      assetTypesTO.add(convertFromAssetType(assetType));
    }

    return assetTypesTO;
  }


  private static TOAssetType convertFromAssetType(AssetType assetType){
    
    //After the TOSpecificAsset methods are implemented, then we can covert SpecifAssets to SpecificAssetsTO
    List<TOSpecificAsset> TOassets= new ArrayList<>();
  
    TOAssetType assetTypeTO = new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan());
    if(assetType.getImage() != null && !assetType.getImage().isEmpty()){
      assetTypeTO.setImageURL(assetType.getImage());
    }

    for(TOSpecificAsset asset : TOassets){
      assetTypeTO.addTOSpecificAsset(asset);
    }

    return assetTypeTO;
  }

  public static TOEmployee convertFromEmployee(String email) {
    Employee employee = (Employee) User.getWithEmail(email);
    List<MaintenanceTicket> ticketsFixedList = employee.getMaintenanceTasks();
    List<MaintenanceTicket> ticketsRaisedList = employee.getRaisedTickets();


    String aEmail = employee.getEmail();
    String name = employee.getName();
    String password = employee.getPassword();
    String phoneNumber = employee.getPhoneNumber();
    List<Integer> ticketsRaised = new ArrayList<>();
    List<Integer> ticketFixed = new ArrayList<>();
    
    for (int i = 0; i < ticketsFixedList.size(); i++) {
      ticketFixed.add(ticketsFixedList.get(i).getId());
    }

    for (int i = 0; i < ticketsRaisedList.size(); i++) {
      ticketsRaised.add(ticketsRaisedList.get(i).getId());
    }
    
    return new TOEmployee(aEmail, name, password, phoneNumber, ticketsRaised, ticketFixed);

  }

  public static List<TOEmployee> getAllEmployees() {
    List<Employee> employees = AssetPlusApplication.getAssetPlus().getEmployees();
    List<TOEmployee> toEmployees = new ArrayList<>();

    for (Employee employee: employees) {
      String email = employee.getEmail();
      toEmployees.add(convertFromEmployee(email));
    }
    return toEmployees;
  }

  public static TOGuest convertFromGuest(String email) {
    Guest guest = (Guest) User.getWithEmail(email);
    List<MaintenanceTicket> ticketsRaisedList = guest.getRaisedTickets();


    String aEmail = guest.getEmail();
    String name = guest.getName();
    String password = guest.getPassword();
    String phoneNumber = guest.getPhoneNumber();
    List<Integer> ticketsRaised = new ArrayList<>();
    
    for (int i = 0; i < ticketsRaisedList.size(); i++) {
      ticketsRaised.add(ticketsRaisedList.get(i).getId());
    }
    
    return new TOGuest(aEmail, name, password, phoneNumber, ticketsRaised);

  }

  public static List<TOGuest> getAllGuests() {
    List<Guest> guests = AssetPlusApplication.getAssetPlus().getGuests();
    List<TOGuest> toGuests = new ArrayList<>();

    for (Guest guest: guests) {
      String email = guest.getEmail();
      toGuests.add(convertFromGuest(email));
    }
    return toGuests;
  }

  public static void assignTicketTo(String staffEmail, int ticketId, String priority, String timeEstimate, boolean approvalRequired) {
    PriorityLevel priorityLevel = PriorityLevel.valueOf(priority);
    TimeEstimate estimate = null;
    switch (timeEstimate) {
      case "Less than a day":
        estimate = TimeEstimate.LessThanADay;
        break;
      case "1-3 days":
        estimate = TimeEstimate.OneToThreeDays;
        break;
      case "3-7 days":
        estimate = TimeEstimate.ThreeToSevenDays;
        break;
      case "1-3 weeks":
        estimate = TimeEstimate.OneToThreeWeeks;
        break;
      case "3 or more weeks":
        estimate = TimeEstimate.ThreeOrMoreWeeks;
        break;
    }

    AssetPlusFeatureMaintenanceTicketController.assignStaffToMaintenanceTicket(staffEmail, priorityLevel, estimate, approvalRequired, ticketId);
  }
}
