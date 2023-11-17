/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.util.*;

// line 58 "../../../../../../AssetPlusTransferObjects.ump"
public class TOEmployee extends TOHotelStaff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEmployee(String aEmail, String aName, String aPassword, String aPhoneNumber, List<String> aTicketsRaised, List<Integer> aTicketFixed)
  {
    super(aEmail, aName, aPassword, aPhoneNumber, aTicketsRaised, aTicketFixed);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}