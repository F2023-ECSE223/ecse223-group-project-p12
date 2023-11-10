package ca.mcgill.ecse.assetplus.controller;

import javax.naming.ldap.ManageReferralControl;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 */
public class AssetPlusFeatureMaintenanceTicketController {

  /**
   * <p>Assign an hotel staff to a maintenance ticket</p>
   * @param staff an employee that will be assigned to the specified ticket
   * @param priority the priority level of the ticket
   * @param timeToResolve the time estimated by the manager that it will take to resolve the ticket
   * @param approvalRequired true if the ticket needs to be approved before it is closed when it is completed
   * @param ticketID the id of a maintenance ticket that is not assigned yet
   * @return an empty string or an error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
   */
  public static String assignStaffToMaintenanceTicket(String staffEmail, PriorityLevel priority, TimeEstimate timeToResolve, boolean approvalRequired, int ticketID) {
    // Input validations
    if(!AssetPlusFeatureUtility.isExistingTicket(ticketID).isEmpty()){
      return "Maintenance ticket does not exist.";
    }

    String err = isExistingStaff(staffEmail) + isActionAdequateForCurrentState(ticketID, "assign");

    if (!err.isEmpty()) {
      return err;
    }
    
<<<<<<< Updated upstream
    //Modify the approveRequired boolean in this function call (last argument)
    ticket.managerReviews(staff, priority, timeToResolve, ticket.hasFixApprover());

=======
    MaintenanceTicket.getWithId(ticketID).managerReviews((HotelStaff) HotelStaff.getWithEmail(staffEmail), priority, timeToResolve, approvalRequired);
    AssetPlusPersistence.save();
    
>>>>>>> Stashed changes
    return "";
  }
  
  /**
   * <p>Mark a ticket as InProgress when an hotel staff start to work on their assigned maintenance ticket</p>
   * @param ticketID the id of a maintenance ticket that has been assigned
   * @return an empty string or an error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
   */
  public static String startWorkingOnTicket(int ticketID) {
    // Input validations
    if(!AssetPlusFeatureUtility.isExistingTicket(ticketID).isEmpty()){
      return "Maintenance ticket does not exist.";
    }

    String err = isActionAdequateForCurrentState(ticketID, "start");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.startWork();

    return "";
  }

  /**
   * <p>Mark a ticket as Resolved</p>
   * @param ticketID the id a maintenance ticket that is InProgress
   * @return an empty string or an error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
   */
  public static String completeTicket(int ticketID) {
    // Input validations
    if(!AssetPlusFeatureUtility.isExistingTicket(ticketID).isEmpty()){
      return "Maintenance ticket does not exist.";
    }

    String err = isActionAdequateForCurrentState(ticketID, "complete");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.completeWork();
    
    return "";

  }
  
  /**
   * <p>Mark a ticket as Closed</p>
   * @param ticketID the id of a maintenance ticket that is Resolved
   * @return an empty string or an error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
   */
  public static String approveTicket(int ticketID) {
    // Input validations
    if(!AssetPlusFeatureUtility.isExistingTicket(ticketID).isEmpty()){
      return "Maintenance ticket does not exist.";
    }

    String err = isActionAdequateForCurrentState(ticketID, "approve");

    if (!err.isEmpty()) {
      return err;
    }

    ticket.approveWork();

    return "";

  }

  /**
   * <p>Mark a ticket as InProgress after it was disapproved by the manager</p>
   * @param ticketID the id a maintenance ticket that is Resolved
   * @param date the date that the note was left
   * @param reason the reason why the ticket was disapproved that will be in the description of the note
   * @return an empty string or an error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
   */
  public static String disapproveTicket(int ticketID, Date date, String reason) {
    // Input validations
    if(!AssetPlusFeatureUtility.isExistingTicket(ticketID).isEmpty()){
      return "Maintenance ticket does not exist.";
    }
    
    String err = isActionAdequateForCurrentState(ticketID, "disapprove");
    
    if (!err.isEmpty()) {
      return err;
    }

    ticket.disapproveWork(ticket.getRaisedOnDate(), ticket.getDescription(), ticket.getTicketFixer());

    return "";
  }


  // Private helper methods

  /**
   * <p> Verify that the the current state is the expected state for the action that is getting done</p>
   * @param ticket the ticket which state will be verified
   * @param action the action that is going to be performed with the ticket
   * @return a string that will be empty if the current state is adequate for the action, else it will contain the error message
   * @author Émilia Gagné, Julia B.Grenier, Camille Pouliot
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
        isValidAction = !ticket.getStatusFullName().equals("InProgress");
        isValidCurrentState = ticket.getStatusFullName().equals("Assigned");
        break;
      case "complete":
        isValidAction = !ticket.getStatusFullName().equals("Resolved") &&
                        !ticket.getStatusFullName().equals("Closed");
        isValidCurrentState = ticket.getStatusFullName().equals("InProgress");
        break;
      case "approve":
        isValidAction = !ticket.getStatusFullName().equals("Closed");
        isValidCurrentState = ticket.getStatusFullName().equals("Resolved");
        break;
      case "disapprove":
        // Weirdly the error messages doesnt follow the same logic as the one before
        isValidCurrentState = ticket.getStatusFullName().equals("Resolved") && 
                              !ticket.getStatusFullName().equals("Closed") ;
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
   
    // Look for invalid action or current state and return the corresponding error
    if (!isValidAction) {
        return "The maintenance ticket is already " + currentState + ".";
    }
    if (!isValidCurrentState) {
        return "Cannot " + action + " a maintenance ticket which is " + currentState + ".";
    }

    return "";
  }

  
  /**
   * <p> Verify that the email correspond to an existing hotel staff</p>
   * @param staffEmail the email of the hotel staff who is supposed to exist
   * @return a string that will be empty if the hotel staff exist, else it will contain the error message
   * @author Émilia Gagné, Julia B.Grenier
   */
  private static String isExistingStaff(String staffEmail){
    if(HotelStaff.getWithEmail(staffEmail) == null){
      return "Staff to assign does not exist.";
    }
    return "";
  }


} 