package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.User;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 * @author Camille Pouliot
 */
public class AssetPlusFeatureSet1Controller {

  /**
   * <p>Update the password of the manager<p>
   * @param password password of the manager
   */
  public static String updateManager(String password) {
    //Input validation
    String err = AssetPlusFeatureUtility.managerPasswordVerification(password);

    if (!err.isEmpty()) {
      return err;
    }
    
    //Updating the manager password with the new password
    try {
      AssetPlusApplication.getAssetPlus().getManager().setPassword(password);
    }
    catch (RuntimeException e){
      return e.getMessage();
    }
    return "";
  }

  /**
   * <p>Add an employee or a guest based on the parameter isEmployee<p>
   * @param email unique email of a user
   * @param password password of a user
   * @param name name of a user (optional)
   * @param phoneNumber phone number of a user (optional)
   * @param isEmployee True if the user is an employee, false if the user is a guest
   */
  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    
    //Input validation
    String err;

    if (isEmployee){
      err = AssetPlusFeatureUtility.employeeEmailVerification(email) + AssetPlusFeatureUtility.isStringValid(password, "Password");
    } else {
      err = AssetPlusFeatureUtility.guestEmailVerification(email) + AssetPlusFeatureUtility.isStringValid(password, "Password"); 
    }

    if(!err.isEmpty()) {
      return err;
    }

    //Creating and adding the employee or guest
    try {
      if (isEmployee) {
       Employee new_employee = AssetPlusApplication.getAssetPlus().addEmployee(email, name, password, phoneNumber);
       AssetPlusApplication.getAssetPlus().addEmployee(new_employee); 
      } else {
        Guest aGuest = AssetPlusApplication.getAssetPlus().addGuest(email, name, password, phoneNumber);
        AssetPlusApplication.getAssetPlus().addGuest(aGuest);
      }
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    
    return "";
  }

  /**
   * <p>Update an employee or a guest with their email<p>
   * @param email unique email of a user
   * @param newPassword New password of the user
   * @param newName New name of the user (optional)
   * @param newPhoneNumber New phone number of a user (optional)
   */
  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingUser(email) + AssetPlusFeatureUtility.isStringValid(newPassword, "Password");

    if (!err.isEmpty()) {
      return err;
    }

    //Updating the fields with the information
    try {
      User userToUpdate = User.getWithEmail(email);
      userToUpdate.setName(newName);
      userToUpdate.setPassword(newPassword);
      userToUpdate.setPhoneNumber(newPhoneNumber);
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

}