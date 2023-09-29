/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 47 "../../../../../../Model.ump"
// line 164 "../../../../../../Model.ump"
public class AssetType
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, AssetType> assettypesByName = new HashMap<String, AssetType>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssetType Attributes
  private String name;
  private int lifespan;

  //AssetType Associations
  private List<HotelAsset> hotelAssets;
  private AssetPlus assetPlus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssetType(String aName, int aLifespan, AssetPlus aAssetPlus)
  {
    lifespan = aLifespan;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    hotelAssets = new ArrayList<HotelAsset>();
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create assetType due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      assettypesByName.remove(anOldName);
    }
    assettypesByName.put(aName, this);
    return wasSet;
  }

  public boolean setLifespan(int aLifespan)
  {
    boolean wasSet = false;
    lifespan = aLifespan;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static AssetType getWithName(String aName)
  {
    return assettypesByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getLifespan()
  {
    return lifespan;
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
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHotelAssets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public HotelAsset addHotelAsset(Date aPurchaseDate, Location aLocation)
  {
    return new HotelAsset(aPurchaseDate, this, aLocation);
  }

  public boolean addHotelAsset(HotelAsset aHotelAsset)
  {
    boolean wasAdded = false;
    if (hotelAssets.contains(aHotelAsset)) { return false; }
    AssetType existingAssetType = aHotelAsset.getAssetType();
    boolean isNewAssetType = existingAssetType != null && !this.equals(existingAssetType);
    if (isNewAssetType)
    {
      aHotelAsset.setAssetType(this);
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
    //Unable to remove aHotelAsset, as it must always have a assetType
    if (!this.equals(aHotelAsset.getAssetType()))
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
      existingAssetPlus.removeAssetType(this);
    }
    assetPlus.addAssetType(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    assettypesByName.remove(getName());
    while (hotelAssets.size() > 0)
    {
      HotelAsset aHotelAsset = hotelAssets.get(hotelAssets.size() - 1);
      aHotelAsset.delete();
      hotelAssets.remove(aHotelAsset);
    }
    
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeAssetType(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "lifespan" + ":" + getLifespan()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}