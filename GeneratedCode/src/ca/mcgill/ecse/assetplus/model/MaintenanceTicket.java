/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 52 "../../../../../Model.ump"
// line 170 "../../../../../Model.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum State { Open, Closed, Resolved }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextTicketNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceTicket Attributes
  private String description;
  private Date creationDate;
  private State state;
  private boolean isApprovalRequired;

  //Autounique Attributes
  private int ticketNumber;

  //MaintenanceTicket Associations
  private AssetPlus assetPlus;
  private User user;
  private HotelAsset hotelAsset;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(String aDescription, Date aCreationDate, State aState, boolean aIsApprovalRequired, AssetPlus aAssetPlus, User aUser, HotelAsset aHotelAsset)
  {
    description = aDescription;
    creationDate = aCreationDate;
    state = aState;
    isApprovalRequired = aIsApprovalRequired;
    ticketNumber = nextTicketNumber++;
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddHotelAsset = setHotelAsset(aHotelAsset);
    if (!didAddHotelAsset)
    {
      throw new RuntimeException("Unable to create maintenanceHistory due to hotelAsset. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setState(State aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApprovalRequired(boolean aIsApprovalRequired)
  {
    boolean wasSet = false;
    isApprovalRequired = aIsApprovalRequired;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public State getState()
  {
    return state;
  }

  public boolean getIsApprovalRequired()
  {
    return isApprovalRequired;
  }

  public int getTicketNumber()
  {
    return ticketNumber;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_GetOne */
  public HotelAsset getHotelAsset()
  {
    return hotelAsset;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      existingAssetPlus.removeMaintenanceTicket(this);
    }
    assetPlus.addMaintenanceTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    if (aUser == null)
    {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removeMaintenanceTicket(this);
    }
    user.addMaintenanceTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setHotelAsset(HotelAsset aHotelAsset)
  {
    boolean wasSet = false;
    if (aHotelAsset == null)
    {
      return wasSet;
    }

    HotelAsset existingHotelAsset = hotelAsset;
    hotelAsset = aHotelAsset;
    if (existingHotelAsset != null && !existingHotelAsset.equals(aHotelAsset))
    {
      existingHotelAsset.removeMaintenanceHistory(this);
    }
    hotelAsset.addMaintenanceHistory(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeMaintenanceTicket(this);
    }
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removeMaintenanceTicket(this);
    }
    HotelAsset placeholderHotelAsset = hotelAsset;
    this.hotelAsset = null;
    if(placeholderHotelAsset != null)
    {
      placeholderHotelAsset.removeMaintenanceHistory(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "ticketNumber" + ":" + getTicketNumber()+ "," +
            "description" + ":" + getDescription()+ "," +
            "isApprovalRequired" + ":" + getIsApprovalRequired()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hotelAsset = "+(getHotelAsset()!=null?Integer.toHexString(System.identityHashCode(getHotelAsset())):"null");
  }
}