package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.User;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 * @author Camille Pouliot
 */
public class AssetPlusFeatureSet1Controller {

  /**
   * <p>Update the password of the manager<p>
   * @param password
   */
  public static String updateManager(String password) {
    //Input validation
    AssetPlusFeatureUtility.isStringValid(password, "password");

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
   * @param email 
   * @param password
   * @param name
   * @param phoneNumber
   * @param isEmployee
   */
  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingUser(email);

    if (err.isEmpty()) {
      return "Error: a user with this email already exist";
    }
    
    if (!AssetPlusFeatureUtility.isEmployeeEmailValid(email) && isEmployee) {
      return "Error: the email for an employee needs to end with \"@ap.com\"";
    }

    //Creating and adding the employee or guest
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

  /**
   * <p>Update an employee or a guest with their email<p>
   * @param email
   * @param newPassword
   * @param newName
   * @param newPhoneNumber
   */
  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingUser(email) + AssetPlusFeatureUtility.isStringValid("password", newPassword);

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