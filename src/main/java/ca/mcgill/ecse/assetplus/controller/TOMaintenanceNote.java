/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

// line 29 "../../../../../../AssetPlusTransferObjects.ump"
public class TOMaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMaintenanceNote Attributes
  private Date date;
  private String description;
  private String noteTakerEmail;

  //TOMaintenanceNote Associations
  private TOHotelStaff noteTaker;

  //Helper Variables
  private boolean canSetNoteTaker;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMaintenanceNote(Date aDate, String aDescription, String aNoteTakerEmail, TOHotelStaff aNoteTaker)
  {
    date = aDate;
    description = aDescription;
    noteTakerEmail = aNoteTakerEmail;
    canSetNoteTaker = true;
    if (!setNoteTaker(aNoteTaker))
    {
      throw new RuntimeException("Unable to create TOMaintenanceNote due to aNoteTaker. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }

  public String getNoteTakerEmail()
  {
    return noteTakerEmail;
  }
  /* Code from template association_GetOne */
  public TOHotelStaff getNoteTaker()
  {
    return noteTaker;
  }
  /* Code from template association_SetUnidirectionalOne */
  private boolean setNoteTaker(TOHotelStaff aNewNoteTaker)
  {
    boolean wasSet = false;
    if (!canSetNoteTaker) { return false; }
    canSetNoteTaker = false;
    if (aNewNoteTaker != null)
    {
      noteTaker = aNewNoteTaker;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "noteTakerEmail" + ":" + getNoteTakerEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "noteTaker = "+(getNoteTaker()!=null?Integer.toHexString(System.identityHashCode(getNoteTaker())):"null");
  }
}