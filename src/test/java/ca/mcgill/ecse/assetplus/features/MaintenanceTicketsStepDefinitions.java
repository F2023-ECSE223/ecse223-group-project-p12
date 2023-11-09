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
import javafx.scene.layout.Priority;

public class MaintenanceTicketsStepDefinitions {

  public String error;

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
        SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
        ticket.setAsset(asset);

        
        //Setting up the staff
        String assignedStaff = (row.get("fixedByEmail").toString());
        User aStaff = HotelStaff.getWithEmail(assignedStaff);

        //Setting up timeToResolve
        String string_timeToResolve = (row.get("timeToResolve").toString());

        //Setting up approvalRequired
        String string_approval = (row.get("approvalRequired").toString());
        boolean approvalRequired;
        if (string_approval.equals("true")){
          approvalRequired = true;
        } else {
          approvalRequired = false;
        }
        
        //Setting up timeToResolve
        TimeEstimate timeToResolve = TimeEstimate.valueOf(string_timeToResolve);
    
        String priorityString = (row.get("priority").toString());
        PriorityLevel priorityLevel = PriorityLevel.valueOf(priorityString);
        
        //I should not need to do these 4 function calls, because managerReviews is supposed to set it by itself
        ticket.setTimeToResolve(timeToResolve);
        ticket.setTicketFixer((HotelStaff)aStaff);
        ticket.setTimeToResolve(timeToResolve);
        ticket.setFixApprover(approvalRequired ? AssetPlusApplication.getAssetPlus().getManager() : null);
        ticket.managerReviews((HotelStaff)aStaff, priorityLevel, timeToResolve, approvalRequired);

        String status = row.get("status").toString();

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

  //MODIFY THIS FUNCTION - given the umple code with status inside Maintenance Ticket
  @Given("ticket {string} is marked as {string} with requires approval {string}")
  public void ticket_is_marked_as_with_requires_approval(String string, String string2,
      String string3) {
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    String status = string2;
    

    //With the new implementation of umple, we should be able to do 
    //ticket.setStatus
   while (!(ticket.getStatusFullName().equals(status))) {
      if ((ticket.getStatusFullName().equals("Open"))) {
        break;
      } else if ((ticket.getStatusFullName().equals("Assigned"))) {
        ticket.startWork();
      } else if ((ticket.getStatusFullName().equals("InProgress"))) {
        ticket.completeWork();
      } else if ((ticket.getStatusFullName().equals("Resolved"))) {
        ticket.approveWork();
      }
    }
    if (Boolean.parseBoolean(string3)) {
      ticket.setFixApprover(AssetPlusApplication.getAssetPlus().getManager());
    }
  }

  //TO MODIFY
  @Given("ticket {string} is marked as {string}")
  public void ticket_is_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    String status = string2;

    while (!(ticket.getStatusFullName().equals(status))) {
      if ((ticket.getStatusFullName().equals("Open"))) {
        ticket.managerReviews(ticket.getTicketFixer(), ticket.getPriority(), ticket.getTimeToResolve(), ticket.hasFixApprover());
        break;
      } else if ((ticket.getStatusFullName().equals("Assigned"))) {
        ticket.startWork();
      } else if ((ticket.getStatusFullName().equals("InProgress"))) {
        ticket.completeWork();
      } else if ((ticket.getStatusFullName().equals("Resolved"))) {
        ticket.approveWork();
      }
    }
    System.out.println(status.toString());
    System.out.println(ticket.getStatusFullName());
  }

  //UNSURE IF THIS IS CORRECT
  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system() {
    AssetPlusFeatureSet6Controller.getTickets();
  }

  //How we pass ticket needs to be changed
  @When("the manager attempts to assign the ticket {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_ticket_to_with_estimated_time_priority_and_requires_approval(
      String string, String string2, String string3, String string4, String string5) {
    
        int id = Integer.parseInt(string);
        HotelStaff staff = (HotelStaff) HotelStaff.getWithEmail(string2);
        PriorityLevel priority = PriorityLevel.valueOf(string4);

        String stringTimeToResolve = (string3);
        TimeEstimate timeToResolve = TimeEstimate.valueOf(stringTimeToResolve);

        error = AssetPlusFeatureMaintenanceTicketController.assignStaffToMaintenanceTicket(staff, priority, timeToResolve, Boolean.parseBoolean(string5), Integer.parseInt(string));
  }

  @When("the hotel staff attempts to start the ticket {string}")
  public void the_hotel_staff_attempts_to_start_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    error = AssetPlusFeatureMaintenanceTicketController.startWorkingOnTicket(Integer.parseInt(string));
  }

  @When("the manager attempts to approve the ticket {string}")
  public void the_manager_attempts_to_approve_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    error = AssetPlusFeatureMaintenanceTicketController.approveTicket(Integer.parseInt(string));
  }

  @When("the hotel staff attempts to complete the ticket {string}")
  public void the_hotel_staff_attempts_to_complete_the_ticket(String string) {
    // Write code here that turns the phrase above into concrete actions
    error = AssetPlusFeatureMaintenanceTicketController.completeTicket(Integer.parseInt(string));
  }

  @When("the manager attempts to disapprove the ticket {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_ticket_on_date_and_with_reason(String string,
      String string2, String string3) {
        error = AssetPlusFeatureMaintenanceTicketController.disapproveTicket(Integer.parseInt(string));
      }

  @Then("the ticket {string} shall be marked as {string}")
  public void the_ticket_shall_be_marked_as(String string, String string2) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(string2, ticket.getStatusFullName());
  }

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertEquals(string, error);
  }

  @Then("the ticket {string} shall not exist in the system")
  public void the_ticket_shall_not_exist_in_the_system(String string) {
    assertNull(MaintenanceTicket.getWithId(Integer.parseInt(string)));
  }


  @Then("the ticket {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_ticket_shall_have_estimated_time_priority_and_requires_approval(String expectedTicketID,String expectedEstimatedTime, String expectedPriority, String expectedApproval){
    MaintenanceTicket aTicket = MaintenanceTicket.getWithId(Integer.parseInt(expectedTicketID));
    
    assertNotNull(aTicket);
    TimeEstimate expectedTimeEstimate = TimeEstimate.valueOf(expectedEstimatedTime);

    assertEquals(expectedTimeEstimate, aTicket.getTimeToResolve());
    assertEquals(PriorityLevel.valueOf(expectedPriority), aTicket.getPriority());
    
    }

  
  //TO COMPLETE
  @Then("the ticket {string} shall be assigned to {string}")
  public void the_ticket_shall_be_assigned_to(String string, String string2) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
  }

  @Then("the number of tickets in the system shall be {string}")
  public void the_number_of_tickets_in_the_system_shall_be(String string) {
    assertEquals(Integer.parseInt(string), AssetPlusApplication.getAssetPlus().getMaintenanceTickets().size());
  }

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

          if(!ticket.getStatusFullName().equals("Open")){
            //The way the feature file is done, when you open a ticket, you don't have to specify the name of the asset
            //Without the name of the asset, we can't know his asset type and related attributes
            assertEquals(tableList.get(i).get("assetName"), ticket.getAsset().getAssetType().getName());//An asset does not have a name, but its asset type does
            assertEquals(tableList.get(i).get("expectLifeSpan"), Integer.toString(ticket.getAsset().getAssetType().getExpectedLifeSpan()));
            assertEquals(tableList.get(i).get("purchaseDate"), ticket.getAsset().getPurchaseDate().toString());
            assertEquals(tableList.get(i).get("floorNumber"), Integer.toString(ticket.getAsset().getFloorNumber()));
            assertEquals(tableList.get(i).get("roomNumber"), Integer.toString(ticket.getAsset().getRoomNumber()));
            //Attributes that are fixed once the ticket state is Reviewed
            assertEquals(tableList.get(i).get("fixedByEmail"), ticket.getTicketFixer().getEmail());
            assertEquals(tableList.get(i).get("timeToResolve"), ticket.getTimeToResolve().toString());
            assertEquals(tableList.get(i).get("priority"), ticket.getPriority().toString());
            assertEquals(tableList.get(i).get("approvalRequired"), Boolean.toString(ticket.hasFixApprover()));
          }
          
          
        }
  }

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

  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(0, ticket.getTicketNotes().size());
  }

  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string,
      io.cucumber.datatable.DataTable dataTable) {

        MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
        List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);

        for (int i = 0; i < ticket.getTicketImages().size(); i++) {
          assertEquals(tableList.get(i).get("imageUrl"), ticket.getTicketImage(i).toString());
        }
  }

  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(Integer.parseInt(string));
    assertEquals(0, ticket.getTicketImages().size());
  }
}
