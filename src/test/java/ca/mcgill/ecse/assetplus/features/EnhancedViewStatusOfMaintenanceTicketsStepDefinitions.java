package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class defines the Gherkin step defintions for the EnhancedViewStatusOfMaintenanceTickets feature.
 */
public class EnhancedViewStatusOfMaintenanceTicketsStepDefinitions{

  private List<TOMaintenanceTicket> tickets;

  /**
   * Gherkin step definition method to create and add employees to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the employee information.
   *
   * @author Anjali Singhal
   */
  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system (io.cucumber.datatable.DataTable dataTable){
    // Turns the dataTable into a list of lists (each row becomes a list).
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

    // Iterates through each list to create the specified user and add it to the AssetPlus application.
    for (Map<String, Object> row : tableList) {
      String email = (row.get("email")).toString();
      String password = (row.get("password")).toString();
      String name = (row.get("name")).toString();
      String phoneNumber = (row.get("phoneNumber").toString());

      Employee employee = AssetPlusApplication.getAssetPlus().addEmployee(email, name, password,  phoneNumber);
      AssetPlusApplication.getAssetPlus().addEmployee(employee);
    }
  }

  /**
   * Gherkin step definition method to create and add manager to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the manager information.
   *
   * @author Anjali Singhal
   */
  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system (io.cucumber.datatable.DataTable dataTable){
      AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
      // Create new instance of a manager. 
      Manager manager = new Manager(dataTable.row(1).get(0), null, dataTable.row(1).get(1), null, assetPlus);
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
   * @author Anjali Singhal
   */
  @Given("the following asset types exist in the system")
    public void the_following_asset_types_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
      // Turns the dataTable into a list of lists (each row becomes a list).
      List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);
  
      // Iterates through each list to create the specified asset types and add it to the AssetPlus application.
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
   * @author Anjali Singhal
   */
  @Given("the following assets exist in the system")
  public void the_following_assets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
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
   * @author Anjali Singhal
   */
  @Given("the following tickets exist in the system")
  public void the_following_tickets_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

    // Iterate through each map representing a row and cast it to the appropriate type.
    for (Map<String, Object> row : tableList) {
      int ticketID = Integer.parseInt(row.get("id").toString());
      String ticketRaiser = (row.get("ticketRaiser").toString());
      Date dateRaised = Date.valueOf(row.get("raisedOnDate").toString());
      String description = (row.get("description").toString());
      int assetNumber = Integer.parseInt(row.get("assetNumber").toString());

      // Adding the specific maintenance ticket based on the table information.
      User aUser = User.getWithEmail(ticketRaiser);
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
      MaintenanceTicket ticket =
          new MaintenanceTicket(ticketID, dateRaised, description, assetPlus, aUser);
      ticket.setAsset(asset);
    }
  }

  /**
   * Gherkin step definition method to create and add maintenance notes to the AssetPlus application.
   * 
   * @param dataTable Cucumber DataTable containing the maintenance notes information.
   *
   * @author Anjali Singhal
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
   * @author Anjali Singhal
   */
  @Given("the following ticket images exist in the system")
  public void the_following_ticket_images_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
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
   * Gherkin step definition method to display all the maintenance tickets in enhanced view from the AssetPlus application.
   * 
   * @author Anjali Singhal
   */
  @When("the manager attempts to view all maintenance tickets in the system")
  public void the_manager_attempts_to_view_all_maintenance_tickets_in_the_system(){
    tickets = AssetPlusFeatureSet6Controller.getTickets();     //no get tickets method in this controller
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance tickets in the application for viewing.
   * 
   * @param dataTable Cucumber DataTable containing all the tickets which should be existing in the AssetPlus application.
   * 
   * @author Anjali Singhal
   */
  @Then("the following maintenance tickets shall be presented")
  public void the_following_maintenance_tickets_shall_be_presented(io.cucumber.datatable.DataTable dataTable){
     // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, String>> rows = dataTable.asMaps();
    // i used to keep track of the index of in the list of existing tickets in the application.
    int i = 0;
    // Iterate through each map representing a row, cast it to the appropriate type and compare with the expected output.
    for (var row : rows) {
      TOMaintenanceTicket currTicket = tickets.get(i);
      int id = Integer.parseInt(row.get("id"));
      assertEquals(id, currTicket.getId());
      String ticketRaiserEmail = row.get("ticketRaiser");
      assertEquals(ticketRaiserEmail, currTicket.getRaisedByEmail());
      Date raisedOnDate = Date.valueOf(row.get("raisedOnDate"));
      assertEquals(raisedOnDate, currTicket.getRaisedOnDate());
      String description = row.get("description");
      assertEquals(description, currTicket.getDescription());
      String assetName = row.get("assetName");
      assertEquals(assetName, currTicket.getAssetName());
      String expectLifeSpanStr = row.get("expectLifeSpan");
      int expectLifeSpan = -1;
      if (expectLifeSpanStr != null) {
        expectLifeSpan = Integer.parseInt(expectLifeSpanStr);
      }
      assertEquals(expectLifeSpan, currTicket.getExpectLifeSpanInDays());
      String purchaseDateStr = row.get("purchaseDate");
      Date purchaseDate = null;
      if (purchaseDateStr != null) {
        purchaseDate = Date.valueOf(purchaseDateStr);
      }
      assertEquals(purchaseDate, currTicket.getPurchaseDate());
      String floorNumberStr = row.get("floorNumber");
      int floorNumber = -1;
      if (floorNumberStr != null) {
        floorNumber = Integer.parseInt(floorNumberStr);
      }
      assertEquals(floorNumber, currTicket.getFloorNumber());
      String roomNumberStr = row.get("roomNumber");
      int roomNumber = -1;
      if (roomNumberStr != null) {
        roomNumber = Integer.parseInt(roomNumberStr);
      }
      assertEquals(roomNumber, currTicket.getRoomNumber());
      String status = row.get("status");
      assertEquals(status, currTicket.getStatus());
      String fixerEmail = row.get("fixedByEmail");      //not sure if there should be a check for null
      assertEquals(fixerEmail, currTicket.getFixedByEmail());
      String timeToResolve = row .get("timeToResolve");
      assertEquals(timeToResolve, currTicket.getTimeToResolve());
      String priority = row.get("priority");
      assertEquals(priority, currTicket.getPriority());
      String approvalRequiredStr = row.get("approvalRequired");
      boolean approvalRequired = Boolean.parseBoolean(approvalRequiredStr);      //not sure if if (approvalRequiredStr != null){ } needed
      assertEquals(approvalRequired, currTicket.getApprovalRequired());
      i++;     
    }
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance notes associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the notes associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Anjali Singhal
   */
  @Then("the ticket with id {string} shall have the following notes")
  public void the_ticket_with_id_shall_have_the_following_notes(String string, io.cucumber.datatable.DataTable dataTable) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    // Iterate through each ticket existing in the application to find the ticket with the specified id.
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    } 
    assertNotNull(currTicket);
    List<TOMaintenanceNote> currTicketNotes = currTicket.getNotes();
     // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, String>> rows = dataTable.asMaps();
    // i used to keep track of the index in the list of existing notes in the application.
    int i = 0;
    // Iterate through each map representing a row, cast it to the appropriate type and compare with the expected output.
    for (var row : rows) {
      TOMaintenanceNote currNote = currTicketNotes.get(i);
      String noteTaker = row.get("noteTaker");
      assertEquals(noteTaker, currNote.getNoteTakerEmail());
      Date addedOnDate = Date.valueOf(row.get("addedOnDate"));
      assertEquals(addedOnDate, currNote.getDate());
      String description = row.get("description");
      assertEquals(description, currNote.getDescription());
      i++;
    }
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance notes associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the notes associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Anjali Singhal
   */
  @Then("the ticket with id {string} shall have no notes")
  public void the_ticket_with_id_shall_have_no_notes(String string) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    // Iterate through each ticket existing in the application to find the ticket with the specified id.
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }
    assertNotNull(currTicket);
    assertEquals(currTicket.hasNotes(), false);
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance images associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the images associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Anjali Singhal
   */
  @Then("the ticket with id {string} shall have the following images")
  public void the_ticket_with_id_shall_have_the_following_images(String string, io.cucumber.datatable.DataTable dataTable) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    // Iterate through each ticket existing in the application to find the ticket with the specified id.
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }
    assertNotNull(currTicket);
    
    List<String> currTicketImageURLs = currTicket.getImageURLs();
    // Turns the data table into a list of HashMaps for which the column name is the key.
    List<Map<String, String>> rows = dataTable.asMaps();
    // i used to keep track of the index in the list of existing image URL in the application.
    int i = 0;
    // Iterate through each map representing a row, cast it to the appropriate type and compare with the expected output.
    for (var row : rows) {
      assertEquals(currTicketImageURLs.get(i), row.get("imageUrl"));
      i++;
    }
  }

  /**
   * Gherkin step definition method to verify the amount of maintenance images associated to a specific ticket id in the application.
   * 
   * @param dataTable Cucumber DataTable containing all the images associated to a specific ticket id, which should be existing in the AssetPlus application.
   * @param string Existing maintenance ticket id
   * 
   * @author Anjali Singhal
   */
  @Then("the ticket with id {string} shall have no images")
  public void the_ticket_with_id_shall_have_no_images(String string) {
    int ticketID = Integer.parseInt(string);
    TOMaintenanceTicket currTicket = null;
    // Iterate through each ticket existing in the application to find the ticket with the specified id.
    for (var ticket : tickets) {
      if (ticket.getId() == ticketID) {
        currTicket = ticket;
      }
    }
    assertNotNull(currTicket);
    assertEquals(currTicket.getImageURLs().size(), 0);
  }
  
}

