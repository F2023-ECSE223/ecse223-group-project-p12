/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 78 "../../../../../Model.ump"
// line 175 "../../../../../Model.ump"
public class Note
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Note Attributes
  private User author;
  private String text;
  private Date dateCreated;

  //Note Associations
  private AssignedTicket assignedTicket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Note(User aAuthor, String aText, Date aDateCreated, AssignedTicket aAssignedTicket)
  {
    author = aAuthor;
    text = aText;
    dateCreated = aDateCreated;
    boolean didAddAssignedTicket = setAssignedTicket(aAssignedTicket);
    if (!didAddAssignedTicket)
    {
      throw new RuntimeException("Unable to create note due to assignedTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAuthor(User aAuthor)
  {
    boolean wasSet = false;
    author = aAuthor;
    wasSet = true;
    return wasSet;
  }

  public boolean setText(String aText)
  {
    boolean wasSet = false;
    text = aText;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateCreated(Date aDateCreated)
  {
    boolean wasSet = false;
    dateCreated = aDateCreated;
    wasSet = true;
    return wasSet;
  }

  public User getAuthor()
  {
    return author;
  }

  public String getText()
  {
    return text;
  }

  public Date getDateCreated()
  {
    return dateCreated;
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
      existingAssignedTicket.removeNote(this);
    }
    assignedTicket.addNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AssignedTicket placeholderAssignedTicket = assignedTicket;
    this.assignedTicket = null;
    if(placeholderAssignedTicket != null)
    {
      placeholderAssignedTicket.removeNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "text" + ":" + getText()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "author" + "=" + (getAuthor() != null ? !getAuthor().equals(this)  ? getAuthor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateCreated" + "=" + (getDateCreated() != null ? !getDateCreated().equals(this)  ? getDateCreated().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignedTicket = "+(getAssignedTicket()!=null?Integer.toHexString(System.identityHashCode(getAssignedTicket())):"null");
  }
}