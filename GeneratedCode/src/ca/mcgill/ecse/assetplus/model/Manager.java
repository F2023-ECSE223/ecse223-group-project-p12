/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 20 "../../../../../Model.ump"
// line 140 "../../../../../Model.ump"
public class Manager extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private AssignedTicket isAssigned;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aEmail, String aPassword, AssetPlus aAssetPlus, AssignedTicket aIsAssigned)
  {
    super(aEmail, aPassword, aAssetPlus);
    boolean didAddIsAssigned = setIsAssigned(aIsAssigned);
    if (!didAddIsAssigned)
    {
      throw new RuntimeException("Unable to create manager due to isAssigned. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AssignedTicket getIsAssigned()
  {
    return isAssigned;
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
    AssignedTicket existingIsAssigned = isAssigned;
    isAssigned = null;
    if (existingIsAssigned != null)
    {
      existingIsAssigned.setManager(null);
    }
    super.delete();
  }

}