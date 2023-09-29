/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 28 "../../../../../../Model.ump"
// line 147 "../../../../../../Model.ump"
public class Employee extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private AssignedTicket isAssigned;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aPassword, AssetPlus aAssetPlus, AssignedTicket aIsAssigned)
  {
    super(aEmail, aPassword, aAssetPlus);
    boolean didAddIsAssigned = setIsAssigned(aIsAssigned);
    if (!didAddIsAssigned)
    {
      throw new RuntimeException("Unable to create employee due to isAssigned. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      //Unable to setIsAssigned to null, as employee must always be associated to a isAssigned
      return wasSet;
    }
    
    Employee existingEmployee = aNewIsAssigned.getEmployee();
    if (existingEmployee != null && !equals(existingEmployee))
    {
      //Unable to setIsAssigned, the current isAssigned already has a employee, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AssignedTicket anOldIsAssigned = isAssigned;
    isAssigned = aNewIsAssigned;
    isAssigned.setEmployee(this);

    if (anOldIsAssigned != null)
    {
      anOldIsAssigned.setEmployee(null);
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
      existingIsAssigned.setEmployee(null);
    }
    super.delete();
  }

}