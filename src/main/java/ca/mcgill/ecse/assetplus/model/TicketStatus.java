/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import java.sql.Date;
import java.util.*;

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

  //TicketStatus Associations
  private MaintenanceTicket MaintenanceTicket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketStatus(MaintenanceTicket aMaintenanceTicket)
  {
    if (aMaintenanceTicket == null || aMaintenanceTicket.getTicketStatus() != null)
    {
      throw new RuntimeException("Unable to create TicketStatus due to aMaintenanceTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    MaintenanceTicket = aMaintenanceTicket;
    setStatus(Status.Open);
  }

  public TicketStatus(int aIdForMaintenanceTicket, Date aRaisedOnDateForMaintenanceTicket, String aDescriptionForMaintenanceTicket, AssetPlus aAssetPlusForMaintenanceTicket, User aTicketRaiserForMaintenanceTicket)
  {
    MaintenanceTicket = new MaintenanceTicket(aIdForMaintenanceTicket, aRaisedOnDateForMaintenanceTicket, aDescriptionForMaintenanceTicket, this, aAssetPlusForMaintenanceTicket, aTicketRaiserForMaintenanceTicket);
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

  public boolean managerReviews(HotelStaff staff,PriorityLevel priority,TimeEstimate timeToResolve,Manager fixApprover)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Open:
        // line 13 "../../../../../../TicketStatus.ump"
        doReview(staff, priority, timeToResolve, fixApprover);
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
        // line 26 "../../../../../../TicketStatus.ump"
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
  /* Code from template association_GetOne */
  public MaintenanceTicket getMaintenanceTicket()
  {
    return MaintenanceTicket;
  }

  public void delete()
  {
    MaintenanceTicket existingMaintenanceTicket = MaintenanceTicket;
    MaintenanceTicket = null;
    if (existingMaintenanceTicket != null)
    {
      existingMaintenanceTicket.delete();
    }
  }

  // line 35 "../../../../../../TicketStatus.ump"
   private void doReview(HotelStaff staff, PriorityLevel priority, TimeEstimate timeToResolve, Manager fixApprover){
    MaintenanceTicket ticket = getMaintenanceTicket();
    
    if (ticket != null && ticket.hasTicketFixer()) {
      ticket.setTicketFixer(staff);
      ticket.setPriority(priority);
      ticket.setTimeToResolve(timeToResolve);
      ticket.setFixApprover(fixApprover);
    }
  }

  // line 46 "../../../../../../TicketStatus.ump"
   private void doDisapproveWork(Date date, String desc, HotelStaff noteTaker){
    MaintenanceTicket ticket = getMaintenanceTicket();
    
    if (ticket != null) {
      MaintenanceNote newNote = ticket.addTicketNote(date, desc, noteTaker);
      ticket.addTicketNote(newNote);
    }
  }

}