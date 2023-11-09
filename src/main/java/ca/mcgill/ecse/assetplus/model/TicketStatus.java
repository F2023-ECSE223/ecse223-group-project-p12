/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import java.sql.Date;

/**
 * Authors: Émilia Gagné, Julia B.Grenier
 */
// line 4 "../../../../../../TicketStatus.ump"
public class TicketStatus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TicketStatus State Machines
  public enum Status { Open, Assigned, InProgress, Resolved, Closed }
  private Status status;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketStatus()
  {
    setStatus(Status.Open);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean managerReviews(HotelStaff staff,PriorityLevel priority,TimeEstimate timeToResolve,boolean approvalRequired)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 14 "../../../../../../TicketStatus.ump"
        doReview(staff, priority, timeToResolve, approvalRequired);
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startWork()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Assigned:
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean completeWork()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case InProgress:
        if (getMaintenanceTicket().hasFixApprover())
        {
          setStatus(Status.Resolved);
          wasEventProcessed = true;
          break;
        }
        if (!(getMaintenanceTicket().hasFixApprover()))
        {
          setStatus(Status.Closed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean approveWork()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Resolved:
        setStatus(Status.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean disapproveWork(Date date,String desc,HotelStaff noteTaker)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Resolved:
        // line 27 "../../../../../../TicketStatus.ump"
        doDisapproveWork(date, desc, noteTaker);
        setStatus(Status.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;
  }

  public void delete()
  {}

  // line 36 "../../../../../../TicketStatus.ump"
   private void doReview(HotelStaff staff, PriorityLevel priority, TimeEstimate timeToResolve, boolean approvalRequired){
    MaintenanceTicket ticket = getMaintenanceTicket();
    
    if (ticket != null && ticket.hasTicketFixer()) {
      ticket.setTicketFixer(staff);
      ticket.setPriority(priority);
      ticket.setTimeToResolve(timeToResolve);
      ticket.setFixApprover(approvalRequired ? AssetPlusApplication.getAssetPlus().getManager() : null);
    }
  }

  // line 47 "../../../../../../TicketStatus.ump"
   private void doDisapproveWork(Date date, String desc, HotelStaff noteTaker){
    MaintenanceTicket ticket = getMaintenanceTicket();
    
    if (ticket != null) {
      MaintenanceNote newNote = ticket.addTicketNote(date, desc, noteTaker);
      ticket.addTicketNote(newNote);
    }
  }

}