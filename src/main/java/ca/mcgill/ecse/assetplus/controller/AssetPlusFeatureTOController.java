package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * <p> Controller file to deal with all of the transfer objects which need to be created for the view. 
 */
public class AssetPlusFeatureTOController {

  /**
   * <p>Generate a list of specific asset transfer objects. </p>
   * @return a hashmap with the keys being an asset number and the value being the associated specific asset transfer object.
   */
  public static HashMap<Integer, TOSpecificAsset> getSpecificAssets() {
  List<SpecificAsset> assets = AssetPlusApplication.getAssetPlus().getSpecificAssets();
  HashMap<Integer, TOSpecificAsset> TOassets = new HashMap<>();

  for (SpecificAsset asset : assets){
    TOassets.put(asset.getAssetNumber(), new TOSpecificAsset(asset.getAssetNumber(), asset.getFloorNumber(), asset.getRoomNumber(), asset.getPurchaseDate()));
  }
  return TOassets;
  }

  private static TOEmployee convertFromEmployee(
      Employee employee) {
    List<MaintenanceTicket> ticketsFixedList = employee.getMaintenanceTasks();
    List<MaintenanceTicket> ticketsRaisedList = employee.getRaisedTickets();


    String email = employee.getEmail();
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
    
    return new TOEmployee(email, name, password, phoneNumber, ticketsRaised, ticketFixed);

  }

  public static List<TOEmployee> getAllEmployees() {
    List<Employee> employees = AssetPlusApplication.getAssetPlus().getEmployees();
    List<TOEmployee> toEmployees = new ArrayList<>();

    for (Employee employee: employees) {
      toEmployees.add(convertFromEmployee(employee));
    }
    return toEmployees;
  }

}
