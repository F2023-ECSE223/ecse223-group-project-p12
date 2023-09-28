/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 85 "../../../../../Model.ump"
// line 180 "../../../../../Model.ump"
public class Image
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Image Attributes
  private String url;

  //Image Associations
  private AssignedTicket assignedTicket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Image(String aUrl, AssignedTicket aAssignedTicket)
  {
    url = aUrl;
    boolean didAddAssignedTicket = setAssignedTicket(aAssignedTicket);
    if (!didAddAssignedTicket)
    {
      throw new RuntimeException("Unable to create image due to assignedTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public String getUrl()
  {
    return url;
  }
  /* Code from template association_GetOne */
  public AssignedTicket getAssignedTicket()
  {
    return assignedTicket;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssignedTicket(AssignedTicket aAssignedTicket)
  {
    boolean wasSet = false;
    if (aAssignedTicket == null)
    {
      return wasSet;
    }

    AssignedTicket existingAssignedTicket = assignedTicket;
    assignedTicket = aAssignedTicket;
    if (existingAssignedTicket != null && !existingAssignedTicket.equals(aAssignedTicket))
    {
      existingAssignedTicket.removeImage(this);
    }
    assignedTicket.addImage(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AssignedTicket placeholderAssignedTicket = assignedTicket;
    this.assignedTicket = null;
    if(placeholderAssignedTicket != null)
    {
      placeholderAssignedTicket.removeImage(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "url" + ":" + getUrl()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assignedTicket = "+(getAssignedTicket()!=null?Integer.toHexString(System.identityHashCode(getAssignedTicket())):"null");
  }
}