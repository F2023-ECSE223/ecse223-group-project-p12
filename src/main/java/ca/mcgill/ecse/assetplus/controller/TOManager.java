/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.util.*;

// line 71 "../../../../../../AssetPlusTransferObjects.ump"
public class TOManager extends TOHotelStaff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOManager Attributes
  private List<Integer> ticketApproved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOManager(String aEmail, String aName, String aPassword, String aPhoneNumber, List<Integer> aTicketsRaised, List<Integer> aTicketFixed, List<Integer> aTicketApproved)
  {
    super(aEmail, aName, aPassword, aPhoneNumber, aTicketsRaised, aTicketFixed);
    ticketApproved = aTicketApproved;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public List<Integer> getTicketApproved()
  {
    return ticketApproved;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketApproved" + "=" + (getTicketApproved() != null ? !getTicketApproved().equals(this)  ? getTicketApproved().toString().replaceAll("  ","    ") : "this" : "null");
  }
}