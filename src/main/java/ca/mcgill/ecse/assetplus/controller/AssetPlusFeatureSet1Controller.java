package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet1Controller {

  public static String updateManager(String password) {
    try {
      AssetPlusApplication.getAssetPlus().getManager().setPassword(password);
    }
    catch (RuntimeException e){
      return e.getMessage();
    }
    return "";
  }

  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    String err;
    if (isEmployee) {
      err = AssetPlusFeatureUtility.isEmployeeEmailUnique(email);
      
    } else {
      err = AssetPlusFeatureUtility.isGuestEmailUnique(email);
    }
    if (!err.isEmpty()) {
      return err;
    }
    try {
      if (isEmployee) {
       AssetPlusApplication.getAssetPlus().addEmployee(email, name, password, phoneNumber); 
      } else {
        AssetPlusApplication.getAssetPlus().addGuest(email, name, password, phoneNumber);
      }
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    
    return "";
  }

  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    int err;
    boolean isEmployee = AssetPlusFeatureUtility.isEmployee(email);
    if (isEmployee) {
      err = AssetPlusFeatureUtility.isEmployeeEmailValid(email);
      if (err == -1) {
        return "Error: No employee uses this email";
      }
    } else {
      err = AssetPlusFeatureUtility.isGuestEmailValid(email);
      if (err == -1) {
        return "Error: No guest uses this email";
      }
    }
    
    try {
      if (isEmployee) {
        Employee e = AssetPlusApplication.getAssetPlus().getEmployee(err);
        e.setName(newName);
        e.setPassword(newPassword);
        e.setPhoneNumber(newPhoneNumber);
      } else {
        Guest g = AssetPlusApplication.getAssetPlus().getGuest(err);
        g.setName(newName);
        g.setPassword(newPassword);
        g.setPhoneNumber(newPhoneNumber);
      }
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

}