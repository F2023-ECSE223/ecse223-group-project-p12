package ca.mcgill.ecse.assetplus.controller;

import javax.naming.ldap.ManageReferralControl;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import java.sql.Date;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 */
public class AssetPlusFeatureMaintenanceTicketController {

  /**
   * <p>Assign an hotel staff to a maintenance ticket</p>
   * @param staff an employee that will be assigned to the specified ticket
   * @param ticket a maintenance ticket that is not assigned yet
   * @return an empty string or an error message
   * @author Émilia Gagné and Julia B.Grenier
   */
  public static String assignStaffToMaintenanceTicket(HotelStaff staff, PriorityLevel priority, TimeEstimate timeToResolve, boolean approvalRequired, int ticketID) {
    //Input validation

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }
    //I tested this, and it seems to return an empry string, which is good
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket) + isExistingStaff(staff) + isActionAdequateForCurrentState(ticketID, "assign");
   

    if (!err.isEmpty()) {
      return err;
    }
    //Weirdly, we need to manually put this here
    ticket.setTimeToResolve(timeToResolve);
    ticket.setPriority(priority);
    ticket.setTicketFixer(staff);
    ticket.setFixApprover(approvalRequired ? AssetPlusApplication.getAssetPlus().getManager() : null);
    //Modify the approveRequired boolean in this function call (last argument)
    ticket.managerReviews(staff, priority, timeToResolve, ticket.hasFixApprover());

    return "";
  }
  
  /**
   * <p>Mark a ticket as InProgress when an hotel staff start to work on their assigned maintenance ticket</p>
   * @param ticket a maintenance ticket that has been assigned
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String startWorkingOnTicket(int ticketID) {

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }
    // Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket) + 
                  isActionAdequateForCurrentState(ticketID, "start");
    // To start working on a ticket

    if (!err.isEmpty()) {
      return err;
    }

    ticket.startWork();

    return "";
  }

  /**
   * <p>Mark a ticket as Resolved</p>
   * @param ticket a maintenance ticket that is InProgress
   * @return an empty string or an error message
   * @author Julia B.Grenier 
   */
  public static String completeTicket(int ticketID) {
     //Input validation
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }

    String err = AssetPlusFeatureUtility.isExistingTicket(ticket) + 
                  isActionAdequateForCurrentState(ticketID, "complete");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.completeWork();
    
    return "";

  }
  
  /**
   * <p>Mark a ticket as Closed</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String approveTicket(int ticketID) {

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }

    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket) + 
                  isActionAdequateForCurrentState(ticketID, "approve");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.approveWork();

    return "";

  }

  /**
   * <p>Mark a ticket as InProgress after it was disapproved by the manager</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String disapproveTicket(int ticketID, Date date, String reason) {

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }

    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket) + 
                  isActionAdequateForCurrentState(ticketID, "disapprove");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.disapproveWork(date, reason, (HotelStaff) AssetPlusApplication.getAssetPlus().getManager());

    return "";

  }


  // Private helper methods

  /**
   * <p> Verify that the the current state is the expected state for the action that is getting done</p>
   * @param ticket the ticket which state will be verified
   * @param action the action that is going to be performed with the ticket
   * @return a string that will be empty if the current state is adequate for the action, else it will contain the error message
   * @author Julia B.Grenier
   */
  private static String isActionAdequateForCurrentState(int ticketID, String action) {

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    if(ticket == null){
      return "Maintenance ticket does not exist.";
    }

    boolean isValidCurrentState = true;
    boolean isValidAction = true;
    
    switch(action) {
      case "assign":
        isValidAction = !(ticket.getStatusFullName().equals("Assigned"));
        isValidCurrentState = ticket.getStatusFullName().equals("Open");
        break;
      case "start":
        isValidAction = !ticket.getStatusFullName().equalsIgnoreCase("InProgress");
        isValidCurrentState = ticket.getStatusFullName().equalsIgnoreCase("Assigned");
        break;
      case "complete":
        isValidAction = !ticket.getStatusFullName().equalsIgnoreCase("Resolved") &&
                        !ticket.getStatusFullName().equalsIgnoreCase("Closed");
        isValidCurrentState = ticket.getStatusFullName().equalsIgnoreCase("InProgress");
        break;
      case "approve":
        isValidAction = !ticket.getStatusFullName().equalsIgnoreCase("Closed");
        isValidCurrentState = ticket.getStatusFullName().equalsIgnoreCase("Resolved");
        break;
      case "disapprove":
        // Weirdly the error messages doesnt follow the same logic as the one before
        isValidCurrentState = ticket.getStatusFullName().equalsIgnoreCase("Resolved") && 
                              !ticket.getStatusFullName().equalsIgnoreCase("Closed") ;
        break;
      default:
        return "Error invalid input for action";
    }

    // Display the correct error message depending of the action and current state
    String currentState = ticket.getStatusFullName().toLowerCase();
    // Deal with the InProgress state as it needs a space
    if (currentState.equals("inprogress")) {
      currentState = "in progress";
    }
    
    if (!isValidAction) {
        return "The maintenance ticket is already " + currentState + ".";
    }
    if (!isValidCurrentState) {
        return "Cannot " + action + " a maintenance ticket which is " + currentState + ".";
    }

    return "";
  }

  public static String isExistingStaff(HotelStaff staff){
    if(staff == null || HotelStaff.getWithEmail(staff.getEmail()) == null){
      return "Staff to assign does not exist.";
    }
    return "";
  }


} 