/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 70 "../../../../../Model.ump"
// line 119 "../../../../../Model.ump"
public class AssignedTicket extends MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PriorityLevel { URGENT, NORMAL, LOW }
  public enum TimeEstimate { LESS_THAN_A_DAY, ONE_TO_THREE_DAYS, THREE_TO_SEVEN_DAYS, ONE_TO_THREE_WEEKS, THREE_PLUS_WEEKS }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssignedTicket Attributes
  private Date startDate;
  private TimeEstimate timeEstimate;
  private PriorityLevel priority;

  //AssignedTicket Associations
  private List<Note> notes;
  private List<Image> images;
  private Manager assignsTicket;
  private Manager manager;
  private Employee employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssignedTicket(String aDescription, Date aCreationDate, State aState, boolean aIsApprovalRequired, AssetPlus aAssetPlus, User aUser, HotelAsset aHotelAsset, Date aStartDate, TimeEstimate aTimeEstimate, PriorityLevel aPriority, Manager aAssignsTicket)
  {
    super(aDescription, aCreationDate, aState, aIsApprovalRequired, aAssetPlus, aUser, aHotelAsset);
    startDate = aStartDate;
    timeEstimate = aTimeEstimate;
    priority = aPriority;
    notes = new ArrayList<Note>();
    images = new ArrayList<Image>();
    boolean didAddAssignsTicket = setAssignsTicket(aAssignsTicket);
    if (!didAddAssignsTicket)
    {
      throw new RuntimeException("Unable to create assignedTicket due to assignsTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeEstimate(TimeEstimate aTimeEstimate)
  {
    boolean wasSet = false;
    timeEstimate = aTimeEstimate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriority(PriorityLevel aPriority)
  {
    boolean wasSet = false;
    priority = aPriority;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public TimeEstimate getTimeEstimate()
  {
    return timeEstimate;
  }

  public PriorityLevel getPriority()
  {
    return priority;
  }
  /* Code from template association_GetMany */
  public Note getNote(int index)
  {
    Note aNote = notes.get(index);
    return aNote;
  }

  public List<Note> getNotes()
  {
    List<Note> newNotes = Collections.unmodifiableList(notes);
    return newNotes;
  }

  public int numberOfNotes()
  {
    int number = notes.size();
    return number;
  }

  public boolean hasNotes()
  {
    boolean has = notes.size() > 0;
    return has;
  }

  public int indexOfNote(Note aNote)
  {
    int index = notes.indexOf(aNote);
    return index;
  }
  /* Code from template association_GetMany */
  public Image getImage(int index)
  {
    Image aImage = images.get(index);
    return aImage;
  }

  public List<Image> getImages()
  {
    List<Image> newImages = Collections.unmodifiableList(images);
    return newImages;
  }

  public int numberOfImages()
  {
    int number = images.size();
    return number;
  }

  public boolean hasImages()
  {
    boolean has = images.size() > 0;
    return has;
  }

  public int indexOfImage(Image aImage)
  {
    int index = images.indexOf(aImage);
    return index;
  }
  /* Code from template association_GetOne */
  public Manager getAssignsTicket()
  {
    return assignsTicket;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }

  public boolean hasManager()
  {
    boolean has = manager != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
  }

  public boolean hasEmployee()
  {
    boolean has = employee != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Note addNote(User aAuthor, String aText, Date aDateCreated)
  {
    return new Note(aAuthor, aText, aDateCreated, this);
  }

  public boolean addNote(Note aNote)
  {
    boolean wasAdded = false;
    if (notes.contains(aNote)) { return false; }
    AssignedTicket existingAssignedTicket = aNote.getAssignedTicket();
    boolean isNewAssignedTicket = existingAssignedTicket != null && !this.equals(existingAssignedTicket);
    if (isNewAssignedTicket)
    {
      aNote.setAssignedTicket(this);
    }
    else
    {
      notes.add(aNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeNote(Note aNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aNote, as it must always have a assignedTicket
    if (!this.equals(aNote.getAssignedTicket()))
    {
      notes.remove(aNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addNoteAt(Note aNote, int index)
  {  
    boolean wasAdded = false;
    if(addNote(aNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNotes()) { index = numberOfNotes() - 1; }
      notes.remove(aNote);
      notes.add(index, aNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveNoteAt(Note aNote, int index)
  {
    boolean wasAdded = false;
    if(notes.contains(aNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNotes()) { index = numberOfNotes() - 1; }
      notes.remove(aNote);
      notes.add(index, aNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addNoteAt(aNote, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfImages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Image addImage(String aUrl)
  {
    return new Image(aUrl, this);
  }

  public boolean addImage(Image aImage)
  {
    boolean wasAdded = false;
    if (images.contains(aImage)) { return false; }
    AssignedTicket existingAssignedTicket = aImage.getAssignedTicket();
    boolean isNewAssignedTicket = existingAssignedTicket != null && !this.equals(existingAssignedTicket);
    if (isNewAssignedTicket)
    {
      aImage.setAssignedTicket(this);
    }
    else
    {
      images.add(aImage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeImage(Image aImage)
  {
    boolean wasRemoved = false;
    //Unable to remove aImage, as it must always have a assignedTicket
    if (!this.equals(aImage.getAssignedTicket()))
    {
      images.remove(aImage);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addImageAt(Image aImage, int index)
  {  
    boolean wasAdded = false;
    if(addImage(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveImageAt(Image aImage, int index)
  {
    boolean wasAdded = false;
    if(images.contains(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addImageAt(aImage, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssignsTicket(Manager aAssignsTicket)
  {
    boolean wasSet = false;
    if (aAssignsTicket == null)
    {
      return wasSet;
    }

    Manager existingAssignsTicket = assignsTicket;
    assignsTicket = aAssignsTicket;
    if (existingAssignsTicket != null && !existingAssignsTicket.equals(aAssignsTicket))
    {
      existingAssignsTicket.removeAssignedTicket(this);
    }
    assignsTicket.addAssignedTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (manager != null && !manager.equals(aNewManager) && equals(manager.getIsAssigned()))
    {
      //Unable to setManager, as existing manager would become an orphan
      return wasSet;
    }

    manager = aNewManager;
    AssignedTicket anOldIsAssigned = aNewManager != null ? aNewManager.getIsAssigned() : null;

    if (!this.equals(anOldIsAssigned))
    {
      if (anOldIsAssigned != null)
      {
        anOldIsAssigned.manager = null;
      }
      if (manager != null)
      {
        manager.setIsAssigned(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setEmployee(Employee aNewEmployee)
  {
    boolean wasSet = false;
    if (employee != null && !employee.equals(aNewEmployee) && equals(employee.getIsAssigned()))
    {
      //Unable to setEmployee, as existing employee would become an orphan
      return wasSet;
    }

    employee = aNewEmployee;
    AssignedTicket anOldIsAssigned = aNewEmployee != null ? aNewEmployee.getIsAssigned() : null;

    if (!this.equals(anOldIsAssigned))
    {
      if (anOldIsAssigned != null)
      {
        anOldIsAssigned.employee = null;
      }
      if (employee != null)
      {
        employee.setIsAssigned(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (notes.size() > 0)
    {
      Note aNote = notes.get(notes.size() - 1);
      aNote.delete();
      notes.remove(aNote);
    }
    
    while (images.size() > 0)
    {
      Image aImage = images.get(images.size() - 1);
      aImage.delete();
      images.remove(aImage);
    }
    
    Manager placeholderAssignsTicket = assignsTicket;
    this.assignsTicket = null;
    if(placeholderAssignsTicket != null)
    {
      placeholderAssignsTicket.removeAssignedTicket(this);
    }
    Manager existingManager = manager;
    manager = null;
    if (existingManager != null)
    {
      existingManager.delete();
    }
    Employee existingEmployee = employee;
    employee = null;
    if (existingEmployee != null)
    {
      existingEmployee.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeEstimate" + "=" + (getTimeEstimate() != null ? !getTimeEstimate().equals(this)  ? getTimeEstimate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "priority" + "=" + (getPriority() != null ? !getPriority().equals(this)  ? getPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignsTicket = "+(getAssignsTicket()!=null?Integer.toHexString(System.identityHashCode(getAssignsTicket())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null");
  }
}