/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.util.*;

// line 36 "../../../../../../AssetPlusTransferObjects.ump"
public abstract class TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOUser Attributes
  private String email;
  private String name;
  private String password;
  private String phoneNumber;
  private List<Integer> ticketsRaised;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOUser(String aEmail, String aName, String aPassword, String aPhoneNumber, List<Integer> aTicketsRaised)
  {
    email = aEmail;
    name = aName;
    password = aPassword;
    phoneNumber = aPhoneNumber;
    ticketsRaised = aTicketsRaised;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getEmail()
  {
    return email;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public List<Integer> getTicketsRaised()
  {
    return ticketsRaised;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketsRaised" + "=" + (getTicketsRaised() != null ? !getTicketsRaised().equals(this)  ? getTicketsRaised().toString().replaceAll("  ","    ") : "this" : "null");
  }
}