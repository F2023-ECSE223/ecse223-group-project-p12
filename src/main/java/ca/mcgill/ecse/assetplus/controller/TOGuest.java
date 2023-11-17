/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;

// line 46 "../../../../../../AssetPlusTransferObjects.ump"
public class TOGuest extends TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGuest(String aEmail, String aName, String aPassword, String aPhoneNumber, List<Integer> aTicketsRaised)
  {
    super(aEmail, aName, aPassword, aPhoneNumber, aTicketsRaised);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}