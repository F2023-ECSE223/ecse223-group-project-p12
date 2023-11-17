package ca.mcgill.ecse.assetplus.features;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureMaintenanceTicketController;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class defines the Gherkin step defintions for the EnhancedViewStatusOfMaintenanceTickets feature and the ProcessMaintenanceTicket feature.
 * 
 * @author Anjali Singhal
 * @author Tayba Jusab
 * @author Julia Grenier
 * @author Sahar Fathi
 * @author Émilia Gagné
 * @author Camille Pouliot
 */
public class MaintenanceTicketsStepDefinitions {

  public String error;

  /**
   * Gherkin step definition method to create and add employees to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the employee information.
   * 
   * @author Anjali Singhal
   */
  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : tableList) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String phoneNumber = row.get("phoneNumber");

      Employee employee = new Employee(email, name, password, phoneNumber, AssetPlusApplication.getAssetPlus());
      AssetPlusApplication.getAssetPlus().addEmployee(employee);
    }
  }

  /**
   * Gherkin step definition method to create and add manager to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the manager information.
   * 
   *  @author Anjali Singhal
   */
  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
      AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
      // Create new instance of a manager.
      Manager manager =
          new Manager(dataTable.row(1).get(0), null, dataTable.row(1).get(1), null, assetPlus);
      // If no manager exists in the application, set manager.
      if (!assetPlus.hasManager())
        assetPlus.setManager(manager);
      // Check if the existing manager matches to the specified manager in the feature file.
      Assertions.assertTrue(assetPlus.hasManager());
      Assertions.assertEquals("manager@ap.com", assetPlus.getManager().getEmail());
      Assertions.assertEquals("manager", assetPlus.getManager().getPassword());
  }

  /**
   * Gherkin step definition method to create and add asset types to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the asset type information.
   * 
   *  @author Anjali Singhal
   */
  @Given("the following asset types exist in the system")
  public void the_following_asset_types_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified asset types and add it to the AssetPlus
    // application.
    for (Map<String, Object> row : tableList) {
      String name = (row.get("name")).toString();
      int expectedLifeSpan = Integer.parseInt(row.get("expectedLifeSpan").toString());
      AssetType type = AssetPlusApplication.getAssetPlus().addAssetType(name, expectedLifeSpan);
      AssetPlusApplication.getAssetPlus().addAssetType(type);
    }
  }

   /**
   * Gherkin step definition method to create and add specific assets to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the asset information.
   * 
   *  @author Anjali Singhal
   */
  @Given("the following assets exist in the system")
  public void the_following_assets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (Map<String, Object> row : tableList) {
      int assetNumber = Integer.parseInt(row.get("assetNumber").toString());
      String assetType = (row.get("type")).toString();
      int floorNumber = Integer.parseInt(row.get("floorNumber").toString());
      int roomNumber = Integer.parseInt(row.get("roomNumber").toString());
      Date purchaseDate = Date.valueOf(row.get("purchaseDate").toString());

      // Adding the specific asset based on the table information.
      SpecificAsset asset = AssetPlusApplication.getAssetPlus().addSpecificAsset(assetNumber,
          floorNumber, roomNumber, purchaseDate, AssetType.getWithName(assetType));
      AssetPlusApplication.getAssetPlus().addSpecificAsset(asset);
    }
  }

  /**
   * Gherkin step definition method to create and add maintenance tickets to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the maintenance ticket information.
   * 
   * @author Tayba Jusab
   * @author Émilia Gagné
   * @author Camille Pouliot
   * @author Julia B.Grenier
   * @author Sahar Fathi
   * @author Anjali Singhal
   */
  @Given("the following tickets exist in the system")
  public void the_following_tickets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (Map<String, Object> row : tableList) {
      int ticketID = Integer.parseInt(row.get("id").toString());
      String ticketRaiser = (row.get("ticketRaiser").toString());
      User aUser = User.getWithEmail(ticketRaiser);
      Date dateRaised = Date.valueOf(row.get("raisedOnDate").toString());
      String description = (row.get("description").toString());

      // Adding the specific maintenance ticket based on the table information.
      MaintenanceTicket ticket = new MaintenanceTicket(ticketID, dateRaised, description, AssetPlusApplication.getAssetPlus(), aUser);
      AssetPlusApplication.getAssetPlus().addMaintenanceTicket(ticket);
      
      if (!row.get("status").equals("Open")) {
        //Setting up the asset
        int assetNumber = Integer.parseInt(row.get("assetNumber").toString());
        if (assetNumber != -1) {
          SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
          ticket.setAsset(asset);
        }
        else{
          ticket.setAsset(null);
        }

        //Setting up the staff
        String assignedStaff = (row.get("fixedByEmail").toString());
        HotelStaff aStaff;
        if (assignedStaff != null){
          aStaff = (HotelStaff) HotelStaff.getWithEmail(assignedStaff);
        }
        else{
          aStaff = null;
        }
        
        //Setting up timeToResolve
        String string_timeToResolve = (row.get("timeToResolve").toString());
        TimeEstimate timeToResolve = TimeEstimate.valueOf(string_timeToResolve);

        //Setting up approvalRequired
        String string_approval = (row.get("approvalRequired").toString());
       
        //Setting up priority
        String priorityString = (row.get("priority").toString());
        PriorityLevel priorityLevel = PriorityLevel.valueOf(priorityString);

        ticket.managerReviews(aStaff, priorityLevel, timeToResolve, Boolean.parseBoolean(string_approval));

        String status = row.get("status").toString();

        //Setting status of tickets
        switch(status){
          case("InProgress"):
            ticket.startWork();
            break;
          case("Resolved"):
            ticket.startWork();
            ticket.completeWork();
            break;
          case("Closed"):
            ticket.startWork();
            ticket.completeWork();
            if(ticket.hasFixApprover()){
              ticket.approveWork();
            }
            break;
          default:
        }
      }
    }
  }

  /**
   * Gherkin step definition method to create and add maintenance notes to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the maintenance notes information.
   * 
   * @author Tayba Jusab
   */
  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (Map<String, Object> row : tableList) {
      String noteTaker = (row.get("noteTaker").toString());
      int ticketID = Integer.parseInt(row.get("ticketId").toString());
      Date addedOnDate = Date.valueOf(row.get("addedOnDate").toString());
      String description = (row.get("description").toString());

      // Add the specific maintenance ticket notes to the appropriate maintenance ticket.
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      HotelStaff staff = (HotelStaff) HotelStaff.getWithEmail(noteTaker); 
      MaintenanceNote note = new MaintenanceNote(addedOnDate, description, ticket, staff);
      ticket.addTicketNote(note);
    }
  }

  /**
   * Gherkin step definition method to create and add maintenance images to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the maintenance images information.
   * 
   * @author Sahar Fathi
   */
  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, String>> rows = dataTable.asMaps();

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (var row : rows) {
      String imageUrl = row.get("imageUrl");
      int ticketId = Integer.parseInt(row.get("ticketId"));
      new TicketImage(imageUrl, MaintenanceTicket.getWithId(ticketId));
    }
  }

  /**
   * Gherkin step definition method to assign the appropiate status and approvalRequired to a specific maintenance ticket.
   * 
   * @param string maintenance ticket id
   * @param string2 maintenance ticket status
   * @param string3 boolean approval required
   * 
   * @author Camille Pouliot
   * @author Julia B. Grenier
   */
  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String string, String string2,
      String string3) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    String status = string2; 
    while (!(ticket.getStatusFullName().equals(status))) {
      if ((ticket.getStatusFullName().equals("Open"))) {
        ticket.managerReviews(ticket.getTicketFixer(), ticket.getPriority(), ticket.getTimeToResolve(), Boolean.parseBoolean(string3));
      } else if ((ticket.getStatusFullName().equals("Assigned"))) {
        ticket.startWork();
      } else if ((ticket.getStatusFullName().equals("InProgress"))) {
        ticket.completeWork();
      } else if ((ticket.getStatusFullName().equals("Resolved"))) {
        ticket.approveWork();
      }
    }
  }

  /**
   * Gherkin step definition method to assign the appropiate status to a specific maintenance ticket.
   * 
   * @param string maintenance ticket id
   * @param string2 maintenance ticket status
   * 
   * @author Émilia Gagné
   * @author Camille Pouliot
   */
  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String string, String string2) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    String status = string2;
    while (!(ticket.getStatusFullName().equals(status))) {
      if ((ticket.getStatusFullName().equals("Open"))) {
        ticket.managerReviews(null, null, null, true);
      } else if ((ticket.getStatusFullName().equals("Assigned"))) {
        ticket.startWork();
      } else if ((ticket.getStatusFullName().equals("InProgress"))) {
        ticket.completeWork();
      } else if ((ticket.getStatusFullName().equals("Resolved"))) {
        ticket.approveWork();
      }
    }
  }

  /**
   * Gherkin step definition method to display all the maintenance tickets in enhanced view from the AssetPlus application.
   * 
   * @author Anjali Singhal
   */
  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    AssetPlusFeatureSet6Controller.getTickets();
  }

  /**
   *  Gherkin step definition method to assign the appropiate staff, priority, timeToResolve  and approvalRequired to a specific maintenance ticket from the AssetPlus application.
   * 
   * @param string maintenance ticket id
   * @param string2 ticket fixer email
   * @param string3 timeToResolve
   * @param string4 priority
   * @param string5 approvalRequired
   * 
   * @author Sahar Fathi
   */
  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String string, String string2, String string3, String string4, String string5) {
    
        int id = Integer.parseInt(string);
        PriorityLevel priority = PriorityLevel.valueOf(string4);
        String stringTimeToResolve = (string3);
        TimeEstimate timeToResolve = TimeEstimate.valueOf(stringTimeToResolve);

        error = AssetPlusFeatureMaintenanceTicketController.assignStaffToMaintenanceTicket(string2, priority, timeToResolve, Boolean.parseBoolean(string5), Integer.parseInt(string));
  }

  /**
   *  Gherkin step definition method to start work on a specific maintenance ticket from the AssetPlus application.
   * 
   * @param string maintenance ticket id
   * 
   * @author Tayba Jusab
   */
  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String string) {
    error = AssetPlusFeatureMaintenanceTicketController.startWorkingOnTicket(Integer.parseInt(string));
    System.err.println(error);
  }

  /**
   *  Gherkin step definition method for manager to approve the work done on a specific maintenance ticket from the AssetPlus application.
   * 
   * @param string maintenance ticket id
   * 
   * @author Tayba Jusab
   */
  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String string) {
    error = AssetPlusFeatureMaintenanceTicketController.approveTicket(Integer.parseInt(string));
  }

  /**
   *  Gherkin step definition method for staff to complete the work on a specific maintenance ticket from the AssetPlus application.
   * 
   * @param string maintenance ticket id
   * 
   * @author Tayba Jusab
   */
  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String string) {
    error = AssetPlusFeatureMaintenanceTicketController.completeTicket(Integer.parseInt(string));
  }

  /**
   *  Gherkin step definition method for manager to disapprove the work done on a specific maintenance ticket from the AssetPlus application.
   * 
   * @param string maintenance ticket id
   * @param string2 date on which ticket is disapproved
   * @param string3 disapproval reason (note)
   * 
   * @author Tayba Jusab
   */
  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String string,
      String string2, String string3) {
        error = AssetPlusFeatureMaintenanceTicketController.disapproveTicket(Integer.parseInt(string), Date.valueOf(string2), string3);
      }

  /**
   * Gherkin step definition method to verify the status of a maintenance ticket in the application.
   * 
   * @param string Existing maintenance ticket id
   * @param string2 maintenance ticket status
   * 
   * @author Émilia Gagné
   */  
  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String string, String string2) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(string2, ticket.getStatusFullName());
  }

  /**
   * Gherkin step definition method to verify the error message displayed in the application.
   * 
   * @param string error message
   * 
   * @author Sahar Fathi
   */  
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertEquals(string, error);
  }

  /**
   * Gherkin step definition method to verify the non-existenance of a maintenance ticket in the application.
   * 
   * @param string maintenance ticket id
   * 
   * @author Sahar Fathi
   */ 
  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String string) {
    assertNull(MaintenanceTicket.getWithId(Integer.parseInt(string)));
  }


  /**
   * Gherkin step definition method to verify the status of a maintenance ticket in the application.
   * 
   * @param string maintenance ticket id
   * @param string2 timeEstimate
   * @param string3 priority
   * @param string4 approvalRequired
   * 
   * @author Anjali Singhal
   * @author Camille Pouliot
   */  
  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String string,
  String string2, String string3, String string4){
    MaintenanceTicket aTicket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    
    assertNotNull(aTicket);
    TimeEstimate expectedTimeEstimate = TimeEstimate.valueOf(string2);

    assertEquals(expectedTimeEstimate, aTicket.getTimeToResolve());
    assertEquals(PriorityLevel.valueOf(string3), aTicket.getPriority());
    
    }

  /**
   * Gherkin step definition method to verify the email of the ticket fixer for a maintenance ticket in the application.
   * 
   * @param string maintenance ticket id
   * @param string2 expected ticket fixer email
   * 
   * @author Anjali Singhal
   * @author Tayba Jusab
   */  
  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String string, String string2) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    HotelStaff staff = ticket.getTicketFixer();
    assertEquals(string2,staff.getEmail());
  }

  /**
   * Gherkin step definition method to verify the email of the ticket fixer for a maintenance ticket in the application.
   * 
   * @param string the expected number of tickets in the application
   * 
   * @author Tayba Jusab
   */  
  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    assertEquals(Integer.parseInt(string), AssetPlusApplication.getAssetPlus().getMaintenanceTickets().size());
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance tickets in the application for viewing.
   * 
   * @param dataTable Cucumber DataTable containing all the tickets which should be existing in the AssetPlus application.
   * 
   * @author Anjali Singhal
   */
  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);

        for (int i = 0; i < AssetPlusApplication.getAssetPlus().getMaintenanceTickets().size(); i++) {
          MaintenanceTicket ticket = MaintenanceTicket.getWithId(i+1);//ID starts at 1 but table row starts at 0
          assertEquals(tableList.get(i).get("id"), String.valueOf(ticket.getId()));
          assertEquals(tableList.get(i).get("ticketRaiser"), ticket.getTicketRaiser().getEmail());
          assertEquals(tableList.get(i).get("raisedOnDate"), ticket.getRaisedOnDate().toString());
          assertEquals(tableList.get(i).get("description"), ticket.getDescription());
          assertEquals(tableList.get(i).get("status"), ticket.getStatusFullName());

          //Attributes that are fixed once the ticket is Reviewed
          if (tableList.get(i).get("status") != "Open"){
            if(tableList.get(i).get("fixedByEmail")!= null) assertEquals(tableList.get(i).get("fixedByEmail"), ticket.getTicketFixer().getEmail());
            if (tableList.get(i).get("timeToResolve") != null) assertEquals(tableList.get(i).get("timeToResolve"), ticket.getTimeToResolve().toString());
            if (tableList.get(i).get("priority") != null) assertEquals(tableList.get(i).get("priority"), ticket.getPriority().toString());
            if (tableList.get(i).get("approvalRequired") != null) assertEquals(tableList.get(i).get("approvalRequired"), Boolean.toString(ticket.hasFixApprover()));
          }
          if(ticket.hasAsset()){
            assertEquals(tableList.get(i).get("assetName"), ticket.getAsset().getAssetType().getName());//An asset does not have a name, but its asset type does
            assertEquals(tableList.get(i).get("expectLifeSpan"), Integer.toString(ticket.getAsset().getAssetType().getExpectedLifeSpan()));
            assertEquals(tableList.get(i).get("purchaseDate"), ticket.getAsset().getPurchaseDate().toString());
            assertEquals(tableList.get(i).get("floorNumber"), Integer.toString(ticket.getAsset().getFloorNumber()));
            assertEquals(tableList.get(i).get("roomNumber"), Integer.toString(ticket.getAsset().getRoomNumber()));
          }
      }
  }

  /**
   * Gherkin step definition method to verify the maintenance notes associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the notes associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Camille Pouliot
   */
  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String string,
      io.cucumber.datatable.DataTable dataTable) {
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
        List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);
    
        for (int i = 0; i < ticket.getTicketNotes().size(); i++) {
          assertEquals(tableList.get(i).get("noteTaker"), ticket.getTicketNote(i).getNoteTaker().getEmail());
          assertEquals(tableList.get(i).get("addedOnDate"), ticket.getTicketNote(i).getDate().toString());
          assertEquals(tableList.get(i).get("description"), ticket.getTicketNote(i).getDescription());
        }
      }

  /**
   * Gherkin step definition method to verify the amount of maintenance notes associated to a specific ticket id in the application.
   * 
   * @param string Existing maintenance ticket id
   * 
   * @author Camille Pouliot
   */
  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(0, ticket.getTicketNotes().size());
  }

  /**
   * Gherkin step definition method to verify the URLs of maintenance images associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the images associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Camille Pouliot
   */
  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string,
      io.cucumber.datatable.DataTable dataTable) {

        MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
        List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);

        for (int i = 0; i < ticket.getTicketImages().size(); i++) {
          assertEquals(tableList.get(i).get("imageUrl"), ticket.getTicketImage(i).getImageURL());
        }
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance images associated to a specific ticket id in the application.
   * 
   * @param string Existing maintenance ticket id
   * 
   * @author Camille Pouliot
   */
  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(0, ticket.getTicketImages().size());
  }
}
