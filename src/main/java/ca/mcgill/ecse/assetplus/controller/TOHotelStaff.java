/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.util.*;

// line 51 "../../../../../../AssetPlusTransferObjects.ump"
public abstract class TOHotelStaff extends TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOHotelStaff Attributes
  private List<Integer> ticketFixed;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOHotelStaff(String aEmail, String aName, String aPassword, String aPhoneNumber, List<Integer> aTicketsRaised, List<Integer> aTicketFixed)
  {
    super(aEmail, aName, aPassword, aPhoneNumber, aTicketsRaised);
    ticketFixed = aTicketFixed;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public List<Integer> getTicketFixed()
  {
    return ticketFixed;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketFixed" + "=" + (getTicketFixed() != null ? !getTicketFixed().equals(this)  ? getTicketFixed().toString().replaceAll("  ","    ") : "this" : "null");
  }
}