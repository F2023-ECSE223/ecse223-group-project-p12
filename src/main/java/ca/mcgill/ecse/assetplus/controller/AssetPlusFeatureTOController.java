package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;


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
      convertedAssets.add(new TOSpecificAsset(asset.getAssetNumber(), asset.getFloorNumber(), asset.getRoomNumber(), asset.getPurchaseDate(), new TOAssetType(asset.getAssetType().getName(), asset.getAssetType().getExpectedLifeSpan())));
    }
    return convertedAssets;
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

    List<SpecificAsset> assets = assetType.getSpecificAssets();
    
    //After the TOSpecificAsset methods are implemented, then we can covert SpecifAssets to SpecificAssetsTO
    List<TOSpecificAsset> TOassets= new ArrayList<>();

    TOAssetType assetTypeTO = new TOAssetType(assetType.getName(), assetType.getExpectedLifeSpan());

    for(TOSpecificAsset asset : TOassets){
      assetTypeTO.addTOSpecificAsset(asset);
    }

    return assetTypeTO;
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
