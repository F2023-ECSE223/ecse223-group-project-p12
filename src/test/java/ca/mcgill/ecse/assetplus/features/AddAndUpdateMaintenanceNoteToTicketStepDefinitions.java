package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateMaintenanceNoteToTicketStepDefinitions {

  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  private String error;

  /**
   */
  @Given("the following employees exist in the system \\(p3)")
  public void the_following_employees_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
        // Retrieving the data from the feature file is a usable format
        List<Map<String, String>> rows = dataTable.asMaps(); 

        // iterating through the rows of the data from the feature file
      for (Map<String, String> row : rows) { 
        // adding employees with the given information from the feature file to the assetPlus
        assetPlus.addEmployee(row.get("email"), row.get("name"), row.get("password"), row.get("phoneNumber")); 
      }
  }

  /**
   */
  @Given("the following manager exists in the system \\(p3)")
  public void the_following_manager_exists_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
      // Retrieving the data from the feature file is a usable format
      List<Map<String, String>> rows = dataTable.asMaps(); 
      // iterating through the rows of the data from the feature file
      for (Map<String, String> row : rows) { 
        // Creating the corresponding manager
        Manager manager = new Manager(row.get("email"), "", row.get("password"), "", assetPlus); 
        assetPlus.setManager(manager); // setting the manager
      }
  }

  /**
   */
  @Given("the following asset types exist in the system \\(p3)")
  public void the_following_asset_types_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
        // Retrieving the data from the feature file in a usable format
        List<Map<String, String>> rows = dataTable.asMaps(); 
        // iterating through the rows of the data from the  feature file
    for (Map<String, String> row : rows) { 
      // adding assetTypes with the given information from the feature file
      assetPlus.addAssetType(row.get("name"), Integer.parseInt(row.get("expectedLifeSpan"))); 
    }
  }

  /**
   */
  @Given("the following assets exist in the system \\(p3)")
  public void the_following_assets_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
        // Retrieving the data from the feature file in a usable format
        List<Map<String, String>> rows = dataTable.asMaps(); 
        // iterating through the rows of the data from the feature file
        for (Map<String, String> row : rows) { 
          // adding specificAssets with the given information from the feature file
          assetPlus.addSpecificAsset(Integer.parseInt(row.get("assetNumber")),
          Integer.parseInt(row.get("floorNumber")), Integer.parseInt(row.get("roomNumber")),
          Date.valueOf(row.get("purchaseDate")), AssetType.getWithName(row.get("type"))); 
        }
  }

  /**
   */
  @Given("the following tickets exist in the system \\(p3)")
  public void the_following_tickets_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
        // Retrieving the data from the feature file in a usable format
        List<Map<String, String>> rows = dataTable.asMaps(); 
        // iterating through the rows of the data from the feature file
        for (Map<String, String> row : rows) { 
          // adding maintenanceTicket with the given information from the feature file
          assetPlus.addMaintenanceTicket(Integer.parseInt(row.get("id")),
              Date.valueOf(row.get("raisedOnDate")), row.get("description"),
              User.getWithEmail(row.get("ticketRaiser")));
        }
  }

  /**
   */
  @Given("the following notes exist in the system \\(p3)")
  public void the_following_notes_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
         // Retrieving the data from the feature file in a usable format
        List<Map<String, String>> rows = dataTable.asMaps();

        // iterating through the rows of the data from the feature file
        for (Map<String, String> row : rows) { 
          // finding the correct maintenanceTicket
          MaintenanceTicket maintenanceTicket =
              MaintenanceTicket.getWithId(Integer.parseInt(row.get("ticketId"))); 
              // adding maintenanceNotes with the given information from the feature file
          maintenanceTicket.addTicketNote(Date.valueOf(row.get("addedOnDate")), row.get("description"),
              (HotelStaff) HotelStaff.getWithEmail(row.get("noteTaker"))); 
        }
  }

  /**
   */
  @When("hotel staff with email {string} attempts to add a new note with date {string} and description {string} to an existing maintenance ticket {string} \\(p3)")
  public void hotel_staff_with_email_attempts_to_add_a_new_note_with_date_and_description_to_an_existing_maintenance_ticket_p3(
      String userEmail, String addedOnDate, String noteDescription, String ticketId) {
    error = AssetPlusFeatureSet7Controller.addMaintenanceNote(Date.valueOf(addedOnDate), noteDescription, Integer.parseInt(ticketId), userEmail); //calling the controller method
  }

  /**
   */
  @When("the manger attempts to update note number {string} for maintenance ticket {string} with note taker {string}, date {string}, and description {string} \\(p3)")
  public void the_manger_attempts_to_update_note_number_for_maintenance_ticket_with_note_taker_date_and_description_p3(
      String noteIndex, String ticketId, String noteTaker, String dateAdded, String noteDescription) {
    error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(Integer.parseInt(ticketId), Integer.parseInt(noteIndex), Date.valueOf(dateAdded.trim()), noteDescription, noteTaker); //calling the controller method
  }

  /**
   */
  @Then("the number of notes in the system shall be {string} \\(p3)")
  public void the_number_of_notes_in_the_system_shall_be_p3(String expectedAmountOfNotes) {
    int amountOfNotes = 0;
    //iterating through all the tickets in the system
    for (MaintenanceTicket maintenanceTicket : assetPlus.getMaintenanceTickets()){ 
      //getting the number of notes per ticket
      amountOfNotes += maintenanceTicket.numberOfTicketNotes(); 
    }
    assertEquals(Integer.parseInt(expectedAmountOfNotes), amountOfNotes);
  }

  /**
   */
  @Then("the number of notes for ticket {string} in the system shall be {string} \\(p3)")
  public void the_number_of_notes_for_ticket_in_the_system_shall_be_p3(String ticketId,
      String expectedAmountOfNotes) {
        //getting a specific maintenance ticket
        MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(ticketId)); 
        //getting the number of notes for that ticket and comparing to the expcted amount of tickets
        assertEquals(Integer.parseInt(expectedAmountOfNotes), maintenanceTicket.numberOfTicketNotes()); 
  }

  /**
   */
  @Then("the note number {string} for ticket {int} with noteTaker {string}, date {string}, and description {string} shall exist in the system \\(p3)")
  public void the_note_number_for_ticket_with_note_taker_date_and_description_shall_exist_in_the_system_p3(
      String noteIndex, Integer ticketId, String noteTaker, String dateAdded,
      String noteDescription) {
        // getting the correct ticket
        MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketId); 
        // getting the correct ticket
        MaintenanceNote maintenanceNote = maintenanceTicket.getTicketNote(Integer.parseInt(noteIndex));
        assertNotNull(maintenanceNote); // making sure it exists
        assertEquals(noteTaker, maintenanceNote.getNoteTaker().getEmail()); // asserting the noteTakers are the same
        assertEquals(dateAdded, maintenanceNote.getDate().toString()); // asserting the dateAddeds are are the same
        assertEquals(noteDescription, maintenanceNote.getDescription()); // asserting the descriptions are the same
  }

  /**
   */
  @Then("the following notes shall exist in the system \\(p3)")
  public void the_following_notes_shall_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
        // Retrieving the data from the feature file in a usable format
        List<List<String>> rows = dataTable.asLists(); 

        List<MaintenanceTicket> tickets = assetPlus.getMaintenanceTickets(); // Gettting all the tickets
        List<MaintenanceNote> notes = new ArrayList<MaintenanceNote>(); // Creating a list to contain the notes
        for (MaintenanceTicket ticket : tickets) {
          notes.addAll(ticket.getTicketNotes()); // iterating through the tickets to get all the notes in the system
        }

        for (MaintenanceNote note : notes) { // iterating through the notes
          List<String> noteAsList = new ArrayList<String>(); // creating a list to store the note information
          noteAsList.add(note.getNoteTaker().getEmail()); // adding the email to the list version of the note
          noteAsList.add("" + note.getTicket().getId()); // adding the ticketId the note is associated to to the list version of the note
          noteAsList.add(note.getDate().toString()); // adding the dateAddedOn to the list version of the note
          noteAsList.add(note.getDescription()); // adding the description to the list version of the note
          assertTrue(rows.contains(noteAsList)); // asserting that the list version of the note exactly is in the example of the feature file
        }
  }

  /**
   */
  @Then("the system shall raise the error {string} \\(p3)")
  public void the_system_shall_raise_the_error_p3(String errorMessage) {
    assertEquals(errorMessage, error); // asserting the errorMessages are identical
  }
}
