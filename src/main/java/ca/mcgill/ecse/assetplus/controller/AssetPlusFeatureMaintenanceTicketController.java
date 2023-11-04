package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

/**
 * <p>Feature 1 - Update manager password / add employee or guest / update employee or guest</p>
 * @author Julia B.Grenier
 */
public class AssetPlusFeatureMaintenanceTicketController {

  /**
   * <p>Assign an hotel staff to a maintenance ticket</p>
   * @param staff an employee that will be assigned to the specified ticket
   * @param ticket a maintenance ticket that is not assigned yet
   * @return an empty string or an error message
   */
  public static String assignStaffToMaintenanceTicket(Employee staff, MaintenanceTicket ticket) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket.getId());

    if (!err.isEmpty()) {
      return err;
    }

    return "";
  }
  
  /**
   * <p>Mark a ticket as InProgress when an hotel staff start to work on their assigned maintenance ticket</p>
   * @param ticket a maintenance ticket that has been assigned
   * @return an empty string or an error message
   */
  public static String startWorkingOnTicket(MaintenanceTicket ticket) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket.getId());

    if (!err.isEmpty()) {
      return err;
    }

    ticket.getTicketStatus().startWork();

    return "";
  }

  /**
   * <p>Mark a ticket as Resolved</p>
   * @param ticket a maintenance ticket that is InProgress
   * @return an empty string or an error message
   */
  public static String completeTicket(MaintenanceTicket ticket) {
     //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket.getId());

    if (!err.isEmpty()) {
      return err;
    }
    ticket.getTicketStatus().completeWork();
    
    return "";

  }
  
  /**
   * <p>Mark a ticket as Closed</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   */
  public static String approveTicket(MaintenanceTicket ticket) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket.getId());

    if (!err.isEmpty()) {
      return err;
    }

    ticket.getTicketStatus().approveWork();

    return "";

  }

  /**
   * <p>Mark a ticket as InProgress after it was disapproved by the manager</p>
   * @param ticket a maintenance ticket that is Resolved
   * @return an empty string or an error message
   */
  public static String disapproveTicket(MaintenanceTicket ticket) {
    //Input validation
    String err = AssetPlusFeatureUtility.isExistingTicket(ticket.getId());

    if (!err.isEmpty()) {
      return err;
    }

    ticket.getTicketStatus().disapproveWork(ticket.getRaisedOnDate(), ticket.getDescription(), ticket.getTicketFixer());
    

    return "";

  }

} 