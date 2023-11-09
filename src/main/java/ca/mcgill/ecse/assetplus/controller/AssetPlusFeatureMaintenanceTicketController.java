package ca.mcgill.ecse.assetplus.controller;

import javax.naming.ldap.ManageReferralControl;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import ca.mcgill.ecse.assetplus.model.TicketStatus.Status;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 */
public class AssetPlusFeatureMaintenanceTicketController {

  /**
   * <p>Assign an hotel staff to a maintenance ticket</p>
   * @param staff an employee or manager that will be assigned to the specified ticket
   * @param priority a priority for the ticket importance
   * @param timeToResolve time required to resolve the ticket according to manager
   * @param manager the manager who is reviewing the ticket
   * @param ticket a maintenance ticket that is not assigned yet
   * @return an empty string or an error message
   * @author Émilia Gagné and Julia B.Grenier
   */

  public static String assignStaffToMaintenanceTicket(HotelStaff staff, PriorityLevel priority, TimeEstimate timeToResolve, boolean approvalRequired, int ticketID) {

    //Input validation

    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                  isActionAdequateForCurrentState(ticketID, "assign") + isExistingStaff(staff);
    if(timeToResolve == null){
      err = err + "Error: TimeEstimateIsNull";
    }

    if(priority == null){
      err = err + "Error: Priority sould not be null";
    }

    if (!err.isEmpty()) {
      return err;
    }
    
    MaintenanceTicket.getWithId(ticketID).getTicketStatus().managerReviews(staff, priority, timeToResolve, approvalRequired);

    return "";
  }
  
  /**
   * <p>Mark a ticket as InProgress when an hotel staff start to work on their assigned maintenance ticket</p>
   * @param ticket a maintenance ticket that has been assigned
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String startWorkingOnTicket(int ticketID) {
    // Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                  isActionAdequateForCurrentState(ticketID, "assign");
    // To start working on a ticket

    if (!err.isEmpty()) {
      return err;
    }

    MaintenanceTicket.getWithId(ticketID).getTicketStatus().startWork();

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
    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                  isActionAdequateForCurrentState(ticketID, "complete");

    if (!err.isEmpty()) {
      return err;
    }

    MaintenanceTicket.getWithId(ticketID).getTicketStatus().completeWork();
    
    return "";

  }
  
  /**
   * <p>Mark a ticket as Closed</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String approveTicket(int ticketID) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                  isActionAdequateForCurrentState(ticketID, "approve");

    if (!err.isEmpty()) {
      return err;
    }

    MaintenanceTicket.getWithId(ticketID).getTicketStatus().approveWork();

    return "";

  }

  /**
   * <p>Mark a ticket as InProgress after it was disapproved by the manager</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   * @author Julia B.Grenier
   */
  public static String disapproveTicket(int ticketID) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticketID) + 
                  isActionAdequateForCurrentState(ticketID, "disapprove");

    if (!err.isEmpty()) {
      return err;
    }

    MaintenanceTicket.getWithId(ticketID).getTicketStatus().disapproveWork(MaintenanceTicket.getWithId(ticketID).getRaisedOnDate(), MaintenanceTicket.getWithId(ticketID).getDescription(), MaintenanceTicket.getWithId(ticketID).getTicketFixer());

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
    boolean isValidCurrentState = true;
    boolean isValidAction = true;
    if (MaintenanceTicket.hasWithId(ticketID)) {
      return "";
    }
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    switch (action) {
      case "assign":
        isValidAction = !(ticket.getTicketStatus().getStatus().equals(Status.Assigned));
        isValidCurrentState = ticket.getTicketStatus().getStatus().equals(Status.Open);
        break;
      case "start":
        isValidAction = !ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("InProgress");
        isValidCurrentState = ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("Assigned");
        break;
      case "complete":
        isValidAction = !ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("Completed");
        isValidCurrentState = ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("InProgress");
        break;
      case "approve":
      case "disapprove":
        isValidAction = !ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("Closed");
        isValidCurrentState = ticket.getTicketStatus().getStatusFullName().equalsIgnoreCase("Completed");
        break;
      default:
        return "Error invalid input for action";
    }

    // Display the correct error message depending of the action and current state
    if (!isValidCurrentState || !isValidAction) {
      String currentState = ticket.getTicketStatus().getStatusFullName().toLowerCase();
      // Deal with the InProgress state as it needs a space
      if (currentState=="inprogress") {
        currentState = "in progress";
      }
      
      // The error message about the validity of the action has more priority than the other
      if (isValidAction) {
        return "Cannot " + action + " a maintenance ticket which is " + currentState + ".";
      } else {
        return "The maintenance ticket is already " + currentState;
      }
      
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