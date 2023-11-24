/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.util.*;

// line 89 "../../../../../../AssetPlusTransferObjects.ump"
public class TOAssetType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAssetType Attributes
  private String name;
  private int expectedLifeSpan;

  //TOAssetType Associations
  private List<TOSpecificAsset> tOSpecificAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAssetType(String aName, int aExpectedLifeSpan)
  {
    name = aName;
    expectedLifeSpan = aExpectedLifeSpan;
    tOSpecificAssets = new ArrayList<TOSpecificAsset>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpectedLifeSpan(int aExpectedLifeSpan)
  {
    boolean wasSet = false;
    expectedLifeSpan = aExpectedLifeSpan;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getExpectedLifeSpan()
  {
    return expectedLifeSpan;
  }
  /* Code from template association_GetMany */
  public TOSpecificAsset getTOSpecificAsset(int index)
  {
    TOSpecificAsset aTOSpecificAsset = tOSpecificAssets.get(index);
    return aTOSpecificAsset;
  }

  public List<TOSpecificAsset> getTOSpecificAssets()
  {
    List<TOSpecificAsset> newTOSpecificAssets = Collections.unmodifiableList(tOSpecificAssets);
    return newTOSpecificAssets;
  }

  public int numberOfTOSpecificAssets()
  {
    int number = tOSpecificAssets.size();
    return number;
  }

  public boolean hasTOSpecificAssets()
  {
    boolean has = tOSpecificAssets.size() > 0;
    return has;
  }

  public int indexOfTOSpecificAsset(TOSpecificAsset aTOSpecificAsset)
  {
    int index = tOSpecificAssets.indexOf(aTOSpecificAsset);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOSpecificAssets()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTOSpecificAsset(TOSpecificAsset aTOSpecificAsset)
  {
    boolean wasAdded = false;
    if (tOSpecificAssets.contains(aTOSpecificAsset)) { return false; }
    tOSpecificAssets.add(aTOSpecificAsset);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTOSpecificAsset(TOSpecificAsset aTOSpecificAsset)
  {
    boolean wasRemoved = false;
    if (tOSpecificAssets.contains(aTOSpecificAsset))
    {
      tOSpecificAssets.remove(aTOSpecificAsset);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOSpecificAssetAt(TOSpecificAsset aTOSpecificAsset, int index)
  {  
    boolean wasAdded = false;
    if(addTOSpecificAsset(aTOSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOSpecificAssets()) { index = numberOfTOSpecificAssets() - 1; }
      tOSpecificAssets.remove(aTOSpecificAsset);
      tOSpecificAssets.add(index, aTOSpecificAsset);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOSpecificAssetAt(TOSpecificAsset aTOSpecificAsset, int index)
  {
    boolean wasAdded = false;
    if(tOSpecificAssets.contains(aTOSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOSpecificAssets()) { index = numberOfTOSpecificAssets() - 1; }
      tOSpecificAssets.remove(aTOSpecificAsset);
      tOSpecificAssets.add(index, aTOSpecificAsset);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOSpecificAssetAt(aTOSpecificAsset, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    tOSpecificAssets.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "]";
  }
}