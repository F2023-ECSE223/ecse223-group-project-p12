/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 72 "../../../../../../AssetPlus.ump"
public class AssetTypeImage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssetTypeImage Attributes
  private String imageURL;

  //AssetTypeImage Associations
  private AssetType assetType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssetTypeImage(String aImageURL, AssetType aAssetType)
  {
    imageURL = aImageURL;
    boolean didAddAssetType = setAssetType(aAssetType);
    if (!didAddAssetType)
    {
      throw new RuntimeException("Unable to create assetTypeImage due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setImageURL(String aImageURL)
  {
    boolean wasSet = false;
    imageURL = aImageURL;
    wasSet = true;
    return wasSet;
  }

  public String getImageURL()
  {
    return imageURL;
  }
  /* Code from template association_GetOne */
  public AssetType getAssetType()
  {
    return assetType;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAssetType(AssetType aNewAssetType)
  {
    boolean wasSet = false;
    if (aNewAssetType == null)
    {
      //Unable to setAssetType to null, as assetTypeImage must always be associated to a assetType
      return wasSet;
    }
    
    AssetTypeImage existingAssetTypeImage = aNewAssetType.getAssetTypeImage();
    if (existingAssetTypeImage != null && !equals(existingAssetTypeImage))
    {
      //Unable to setAssetType, the current assetType already has a assetTypeImage, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AssetType anOldAssetType = assetType;
    assetType = aNewAssetType;
    assetType.setAssetTypeImage(this);

    if (anOldAssetType != null)
    {
      anOldAssetType.setAssetTypeImage(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AssetType existingAssetType = assetType;
    assetType = null;
    if (existingAssetType != null)
    {
      existingAssetType.setAssetTypeImage(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "imageURL" + ":" + getImageURL()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null");
  }
}