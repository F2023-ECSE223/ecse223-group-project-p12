/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 38 "../../../../../Model.ump"
// line 158 "../../../../../Model.ump"
public class HotelAsset
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HotelAsset Attributes
  private Date purchaseDate;

  //Autounique Attributes
  private int number;

  //HotelAsset Associations
  private List<MaintenanceTicket> maintenanceHistory;
  private AssetType assetType;
  private Location location;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HotelAsset(Date aPurchaseDate, AssetType aAssetType, Location aLocation)
  {
    purchaseDate = aPurchaseDate;
    number = nextNumber++;
    maintenanceHistory = new ArrayList<MaintenanceTicket>();
    boolean didAddAssetType = setAssetType(aAssetType);
    if (!didAddAssetType)
    {
      throw new RuntimeException("Unable to create hotelAsset due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddLocation = setLocation(aLocation);
    if (!didAddLocation)
    {
      throw new RuntimeException("Unable to create hotelAsset due to location. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public int getNumber()
  {
    return number;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getMaintenanceHistory(int index)
  {
    MaintenanceTicket aMaintenanceHistory = maintenanceHistory.get(index);
    return aMaintenanceHistory;
  }

  public List<MaintenanceTicket> getMaintenanceHistory()
  {
    List<MaintenanceTicket> newMaintenanceHistory = Collections.unmodifiableList(maintenanceHistory);
    return newMaintenanceHistory;
  }

  public int numberOfMaintenanceHistory()
  {
    int number = maintenanceHistory.size();
    return number;
  }

  public boolean hasMaintenanceHistory()
  {
    boolean has = maintenanceHistory.size() > 0;
    return has;
  }

  public int indexOfMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    int index = maintenanceHistory.indexOf(aMaintenanceHistory);
    return index;
  }
  /* Code from template association_GetOne */
  public AssetType getAssetType()
  {
    return assetType;
  }
  /* Code from template association_GetOne */
  public Location getLocation()
  {
    return location;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceHistory()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addMaintenanceHistory(String aDescription, Date aCreationDate, MaintenanceTicket.State aState, boolean aIsApprovalRequired, AssetPlus aAssetPlus, User aUser)
  {
    return new MaintenanceTicket(aDescription, aCreationDate, aState, aIsApprovalRequired, aAssetPlus, aUser, this);
  }

  public boolean addMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    boolean wasAdded = false;
    if (maintenanceHistory.contains(aMaintenanceHistory)) { return false; }
    HotelAsset existingHotelAsset = aMaintenanceHistory.getHotelAsset();
    boolean isNewHotelAsset = existingHotelAsset != null && !this.equals(existingHotelAsset);
    if (isNewHotelAsset)
    {
      aMaintenanceHistory.setHotelAsset(this);
    }
    else
    {
      maintenanceHistory.add(aMaintenanceHistory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    boolean wasRemoved = false;
    //Unable to remove aMaintenanceHistory, as it must always have a hotelAsset
    if (!this.equals(aMaintenanceHistory.getHotelAsset()))
    {
      maintenanceHistory.remove(aMaintenanceHistory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceHistoryAt(MaintenanceTicket aMaintenanceHistory, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceHistory(aMaintenanceHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceHistory()) { index = numberOfMaintenanceHistory() - 1; }
      maintenanceHistory.remove(aMaintenanceHistory);
      maintenanceHistory.add(index, aMaintenanceHistory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceHistoryAt(MaintenanceTicket aMaintenanceHistory, int index)
  {
    boolean wasAdded = false;
    if(maintenanceHistory.contains(aMaintenanceHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceHistory()) { index = numberOfMaintenanceHistory() - 1; }
      maintenanceHistory.remove(aMaintenanceHistory);
      maintenanceHistory.add(index, aMaintenanceHistory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceHistoryAt(aMaintenanceHistory, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetType(AssetType aAssetType)
  {
    boolean wasSet = false;
    if (aAssetType == null)
    {
      return wasSet;
    }

    AssetType existingAssetType = assetType;
    assetType = aAssetType;
    if (existingAssetType != null && !existingAssetType.equals(aAssetType))
    {
      existingAssetType.removeHotelAsset(this);
    }
    assetType.addHotelAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLocation(Location aLocation)
  {
    boolean wasSet = false;
    if (aLocation == null)
    {
      return wasSet;
    }

    Location existingLocation = location;
    location = aLocation;
    if (existingLocation != null && !existingLocation.equals(aLocation))
    {
      existingLocation.removeHotelAsset(this);
    }
    location.addHotelAsset(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=maintenanceHistory.size(); i > 0; i--)
    {
      MaintenanceTicket aMaintenanceHistory = maintenanceHistory.get(i - 1);
      aMaintenanceHistory.delete();
    }
    AssetType placeholderAssetType = assetType;
    this.assetType = null;
    if(placeholderAssetType != null)
    {
      placeholderAssetType.removeHotelAsset(this);
    }
    Location placeholderLocation = location;
    this.location = null;
    if(placeholderLocation != null)
    {
      placeholderLocation.removeHotelAsset(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "location = "+(getLocation()!=null?Integer.toHexString(System.identityHashCode(getLocation())):"null");
  }
}