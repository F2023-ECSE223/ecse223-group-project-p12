package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import java.util.*;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 * @author Camille Pouliot
 */
public class AssetPlusFeatureSet1Controller {

  /**
   * <p>Update the password of the manager/<p>
   * @param password password of the manager
   * @return an empty string or an error message
   */
  public static String updateManager(String password) {
    //Input validation
    String err = managerPasswordVerification(password);

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
    AssetPlusPersistence.save();
    return "";
  }

  /**
   * <p>Add an employee or a guest based on the parameter isEmployee</p>
   * @param email unique email of a user
   * @param password password of a user
   * @param name name of a user (optional)
   * @param phoneNumber phone number of a user (optional)
   * @param isEmployee True if the user is an employee, false if the user is a guest
   * @return an empty string or an error message
   */
  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    
    //Input validation
    String err;

    if (isEmployee){
      err = employeeEmailVerification(email) + isEmptyPassword(password);
    } else {
      err = guestEmailVerification(email) + isEmptyPassword(password) + isAlreadyExistingEmail(email);
    }

    if(!err.isEmpty()) {
      return err;
    }

    //Creating and adding the employee or guest
    try {
      if (isEmployee) {
        Employee newEmployee = AssetPlusApplication.getAssetPlus().addEmployee(email, name, password, phoneNumber);
        AssetPlusApplication.getAssetPlus().addEmployee(newEmployee); 
      } else {
        Guest aGuest = AssetPlusApplication.getAssetPlus().addGuest(email, name, password, phoneNumber);
        AssetPlusApplication.getAssetPlus().addGuest(aGuest);
      }
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    AssetPlusPersistence.save();
    return "";
  }

  /**
   * <p>Update an employee or a guest with their email</p>
   * @param email unique email of a user
   * @param newPassword New password of the user
   * @param newName New name of the user (optional)
   * @param newPhoneNumber New phone number of a user (optional)
   * @return an empty string or an error message
   */
  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingUser(email, "ticket raiser") + 
                 isEmptyPassword(newPassword);

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
    AssetPlusPersistence.save();
    return "";
  }

  /**
   * <p>Checks whether a guest's email is valid</p>
   * @param email the guest's email
   * @return an error message or an empty string
   */
  private static String guestEmailVerification(String email) {
    if (email.isEmpty()) {
      return "Email cannot be empty";
    } else if (email.equalsIgnoreCase("manager@ap.com")) {
      return "Email cannot be manager@ap.com";
    }

    String err = AssetPlusFeatureUtility.isExistingUser(email, "");

    if (err.isEmpty()) {
      return "Email already linked to an guest account";
    }
    if (email.endsWith("@ap.com")) {
      return "Email domain cannot be @ap.com";
    } else if (email.contains(" ")) {
      return "Email must not contain any spaces";
    } else {
      String[] emailParts = email.split("@");
      if (emailParts.length != 2) {
        return "Invalid email";
      } else if (emailParts[0].isEmpty() || emailParts[1].isEmpty()) {
        return "Invalid email";
      } else {
        String[] domainParts = emailParts[1].split("\\.");
        if (domainParts.length != 2) {
          return "Invalid email";
        } else if (domainParts[0].isEmpty() || domainParts[1].isEmpty()) {
          return "Invalid email";
        } else {
          return "";
        }
      }
    }
  }

  /**
   * <p>Checks whether a given password is empty</p>
   * @param password the password
   * @return an error message or an empty string
   */
  private static String isEmptyPassword(String password) {
    if (password.isEmpty()) {
      return "Password cannot be empty";
    } else {
      return "";
    }

  }

  /**
   * <p>Checks whether an employee's email is valid</p>
   * @param email the employee's email
   * @return an error message or an empty string
   */
  private static String employeeEmailVerification(String email) {
    if (email.isEmpty()) {
      return "Email cannot be empty";
    } else if (email.equalsIgnoreCase("manager@ap.com")) {
      return "Email cannot be manager@ap.com";
    }

    String err = AssetPlusFeatureUtility.isExistingUser(email, "");

    if (err.isEmpty()) {
      return "Email already linked to an employee account";
    }
    if (email.contains(" ")) {
      return "Email must not contain any spaces";
    } else {
      String[] emailParts = email.split("@");
      if (emailParts.length != 2) {
        return "Invalid email";
      } else if (emailParts[0].isEmpty() || emailParts[1].isEmpty()) {
        return "Invalid email";
      } else {
        String[] domainParts = emailParts[1].split("\\.");
        if (domainParts.length != 2) {
          return "Invalid email";
        } else if (domainParts[0].isEmpty() || domainParts[1].isEmpty()) {
          return "Invalid email";
        } else if (!email.endsWith("@ap.com")) {
          return "Email domain must be @ap.com";
        } else {
          return "";
        }
      }
    }
  }

  /**
   * <p>Checks whether a given password is valid</p>
   * @param password the password
   * @return an error message or an empty string
   */
  private static String managerPasswordVerification(String password) {

    if (password.isEmpty()) {
      return "Password cannot be empty";
    } else if (password.length() < 4) {
      return "Password must be at least four characters long";
    } else if (!password.contains("!") && !password.contains("#") && !password.contains("$")) {
      return "Password must contain one character out of !#$";
    } else if (password.toUpperCase().equals(password)) {
      return "Password must contain one lower-case character";
    } else if (password.toLowerCase().equals(password)) {
      return "Password must contain one upper-case character";
    } else {
      return "";
    }
  }

  /**
   * <p> Checks whether the email is already linked to a user</p>
   * @param email the email to verify the user existence with
   * @return an error message or an empty string
   */
  private static String isAlreadyExistingEmail(String email) {

    String error = "";

    if (User.hasWithEmail(email)) {
        error = "Email already linked to a guest account";
      } else {
        error = "";
    }
    return error;
  }

  public static Employee getWithName(String name) {
    List<Employee> employees = AssetPlusApplication.getAssetPlus().getEmployees();
    for (Employee employee : employees) {
      if (employee.getName().equals(name)) {
        return employee;
      }
    }
    return null;
  }
    public static List<TOHotelStaff> getHotelStaffs() {
      List<HotelStaff> hotelStaffs = convertEmployeesIntoStaff(AssetPlusApplication.getAssetPlus().getEmployees());

      List<TOHotelStaff> staffs = new ArrayList<>();
      for (HotelStaff hotelStaff: hotelStaffs) {
        staffs.add(convertFromHotelStaff(hotelStaff));
      }

      staffs.add(convertFromHotelStaff(AssetPlusApplication.getAssetPlus().getManager()));
      return staffs;
    }

    private static List<HotelStaff> convertEmployeesIntoStaff(List<Employee> employees) {
      List<HotelStaff> staff = new ArrayList<>();
      for (Employee employee: employees) {
        staff.add((HotelStaff) employee);
      }

      return staff;
    }

    private static TOHotelStaff convertFromHotelStaff(HotelStaff hotelStaff) {
      User user = (User) hotelStaff;

      return new TOEmployee(
        user.getEmail(),
        user.getName(),
        user.getPassword(),
        user.getPhoneNumber(),
        convertFromMaintenanceTicket(user.getRaisedTickets()),
        convertFromMaintenanceTicket(hotelStaff.getMaintenanceTasks())
      );
    }

    private static List<Integer> convertFromMaintenanceTicket(List<MaintenanceTicket> maintenanceTickets) {
      List<Integer> tickets = new ArrayList<>();
      for (MaintenanceTicket ticket: maintenanceTickets) {
        tickets.add(ticket.getId());
      }

      return tickets;
    }

    public static String getStaffEmailFromName(String name) {
      List<HotelStaff> hotelStaffs = convertEmployeesIntoStaff(AssetPlusApplication.getAssetPlus().getEmployees());
      hotelStaffs.add(AssetPlusApplication.getAssetPlus().getManager());

      for (HotelStaff staff: hotelStaffs) {
        if (staff.getName().equals(name)) {
          return staff.getEmail();
        }
      }
      
      return "Email not found";
    }

}