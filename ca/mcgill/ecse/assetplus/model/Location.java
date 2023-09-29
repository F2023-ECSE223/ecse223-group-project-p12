/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 107 "../../../../../../Model.ump"
// line 185 "../../../../../../Model.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private int floor;
  private int room;

  //Location Associations
  private List<HotelAsset> hotelAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(int aFloor, int aRoom)
  {
    floor = aFloor;
    room = aRoom;
    hotelAssets = new ArrayList<HotelAsset>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFloor(int aFloor)
  {
    boolean wasSet = false;
    floor = aFloor;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoom(int aRoom)
  {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public int getFloor()
  {
    return floor;
  }

  public int getRoom()
  {
    return room;
  }
  /* Code from template association_GetMany */
  public HotelAsset getHotelAsset(int index)
  {
    HotelAsset aHotelAsset = hotelAssets.get(index);
    return aHotelAsset;
  }

  public List<HotelAsset> getHotelAssets()
  {
    List<HotelAsset> newHotelAssets = Collections.unmodifiableList(hotelAssets);
    return newHotelAssets;
  }

  public int numberOfHotelAssets()
  {
    int number = hotelAssets.size();
    return number;
  }

  public boolean hasHotelAssets()
  {
    boolean has = hotelAssets.size() > 0;
    return has;
  }

  public int indexOfHotelAsset(HotelAsset aHotelAsset)
  {
    int index = hotelAssets.indexOf(aHotelAsset);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHotelAssets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public HotelAsset addHotelAsset(Date aPurchaseDate, AssetType aAssetType)
  {
    return new HotelAsset(aPurchaseDate, aAssetType, this);
  }

  public boolean addHotelAsset(HotelAsset aHotelAsset)
  {
    boolean wasAdded = false;
    if (hotelAssets.contains(aHotelAsset)) { return false; }
    Location existingLocation = aHotelAsset.getLocation();
    boolean isNewLocation = existingLocation != null && !this.equals(existingLocation);
    if (isNewLocation)
    {
      aHotelAsset.setLocation(this);
    }
    else
    {
      hotelAssets.add(aHotelAsset);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHotelAsset(HotelAsset aHotelAsset)
  {
    boolean wasRemoved = false;
    //Unable to remove aHotelAsset, as it must always have a location
    if (!this.equals(aHotelAsset.getLocation()))
    {
      hotelAssets.remove(aHotelAsset);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHotelAssetAt(HotelAsset aHotelAsset, int index)
  {  
    boolean wasAdded = false;
    if(addHotelAsset(aHotelAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotelAssets()) { index = numberOfHotelAssets() - 1; }
      hotelAssets.remove(aHotelAsset);
      hotelAssets.add(index, aHotelAsset);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHotelAssetAt(HotelAsset aHotelAsset, int index)
  {
    boolean wasAdded = false;
    if(hotelAssets.contains(aHotelAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotelAssets()) { index = numberOfHotelAssets() - 1; }
      hotelAssets.remove(aHotelAsset);
      hotelAssets.add(index, aHotelAsset);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHotelAssetAt(aHotelAsset, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=hotelAssets.size(); i > 0; i--)
    {
      HotelAsset aHotelAsset = hotelAssets.get(i - 1);
      aHotelAsset.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "floor" + ":" + getFloor()+ "," +
            "room" + ":" + getRoom()+ "]";
  }
}