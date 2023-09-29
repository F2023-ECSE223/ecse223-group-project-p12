/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.State;
import java.util.*;
import java.sql.Date;

// line 20 "../../../../../../Model.ump"
// line 140 "../../../../../../Model.ump"
public class Manager extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private List<AssignedTicket> assignedTickets;
  private AssignedTicket isAssigned;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aEmail, String aPassword, AssetPlus aAssetPlus, AssignedTicket aIsAssigned)
  {
    super(aEmail, aPassword, aAssetPlus);
    assignedTickets = new ArrayList<AssignedTicket>();
    boolean didAddIsAssigned = setIsAssigned(aIsAssigned);
    if (!didAddIsAssigned)
    {
      throw new RuntimeException("Unable to create manager due to isAssigned. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public AssignedTicket getAssignedTicket(int index)
  {
    AssignedTicket aAssignedTicket = assignedTickets.get(index);
    return aAssignedTicket;
  }

  public List<AssignedTicket> getAssignedTickets()
  {
    List<AssignedTicket> newAssignedTickets = Collections.unmodifiableList(assignedTickets);
    return newAssignedTickets;
  }

  public int numberOfAssignedTickets()
  {
    int number = assignedTickets.size();
    return number;
  }

  public boolean hasAssignedTickets()
  {
    boolean has = assignedTickets.size() > 0;
    return has;
  }

  public int indexOfAssignedTicket(AssignedTicket aAssignedTicket)
  {
    int index = assignedTickets.indexOf(aAssignedTicket);
    return index;
  }
  /* Code from template association_GetOne */
  public AssignedTicket getIsAssigned()
  {
    return isAssigned;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignedTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AssignedTicket addAssignedTicket(String aDescription, Date aCreationDate, State aState, boolean aIsApprovalRequired, AssetPlus aAssetPlus, User aUser, HotelAsset aHotelAsset, Date aStartDate, AssignedTicket.TimeEstimate aTimeEstimate, AssignedTicket.PriorityLevel aPriority)
  {
    return new AssignedTicket(aDescription, aCreationDate, aState, aIsApprovalRequired, aAssetPlus, aUser, aHotelAsset, aStartDate, aTimeEstimate, aPriority, this);
  }

  public boolean addAssignedTicket(AssignedTicket aAssignedTicket)
  {
    boolean wasAdded = false;
    if (assignedTickets.contains(aAssignedTicket)) { return false; }
    Manager existingAssignsTicket = aAssignedTicket.getAssignsTicket();
    boolean isNewAssignsTicket = existingAssignsTicket != null && !this.equals(existingAssignsTicket);
    if (isNewAssignsTicket)
    {
      aAssignedTicket.setAssignsTicket(this);
    }
    else
    {
      assignedTickets.add(aAssignedTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignedTicket(AssignedTicket aAssignedTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssignedTicket, as it must always have a assignsTicket
    if (!this.equals(aAssignedTicket.getAssignsTicket()))
    {
      assignedTickets.remove(aAssignedTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignedTicketAt(AssignedTicket aAssignedTicket, int index)
  {  
    boolean wasAdded = false;
    if(addAssignedTicket(aAssignedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedTickets()) { index = numberOfAssignedTickets() - 1; }
      assignedTickets.remove(aAssignedTicket);
      assignedTickets.add(index, aAssignedTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignedTicketAt(AssignedTicket aAssignedTicket, int index)
  {
    boolean wasAdded = false;
    if(assignedTickets.contains(aAssignedTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignedTickets()) { index = numberOfAssignedTickets() - 1; }
      assignedTickets.remove(aAssignedTicket);
      assignedTickets.add(index, aAssignedTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignedTicketAt(aAssignedTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setIsAssigned(AssignedTicket aNewIsAssigned)
  {
    boolean wasSet = false;
    if (aNewIsAssigned == null)
    {
      //Unable to setIsAssigned to null, as manager must always be associated to a isAssigned
      return wasSet;
    }
    
    Manager existingManager = aNewIsAssigned.getManager();
    if (existingManager != null && !equals(existingManager))
    {
      //Unable to setIsAssigned, the current isAssigned already has a manager, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AssignedTicket anOldIsAssigned = isAssigned;
    isAssigned = aNewIsAssigned;
    isAssigned.setManager(this);

    if (anOldIsAssigned != null)
    {
      anOldIsAssigned.setManager(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=assignedTickets.size(); i > 0; i--)
    {
      AssignedTicket aAssignedTicket = assignedTickets.get(i - 1);
      aAssignedTicket.delete();
    }
    AssignedTicket existingIsAssigned = isAssigned;
    isAssigned = null;
    if (existingIsAssigned != null)
    {
      existingIsAssigned.setManager(null);
    }
    super.delete();
  }

}