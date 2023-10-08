package ca.mcgill.ecse.assetplus.features;

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.ParseException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeleteAssetStepDefinitions {
  private AssetPlus application = new AssetPlus();

  @Given("the following asset types exist in the system \\(p12)")
  public void the_following_asset_types_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
        //Turns the dataTable into a list of lists (each row becomes a list).
        List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

        //Iterates through each list to create the specified asset types and add it to the AssetPlus application.
        for (Map<String, Object> row : tableList) {
          String name = (String) row.get("name");
          int expectedLifeSpan = (int) row.get("expectedLifeSpan");
          application.addAssetType(name, expectedLifeSpan);
      }
        
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    //throw new io.cucumber.java.PendingException();
  }

  @Given("the following assets exist in the system \\(p12)")
  public void the_following_assets_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
        //Turns the data table into a list of HashMaps for which the column name is the key. 
        List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);

        // Iterate through each map representing a row and cast it to the appropriate type.
        for (Map<String, Object> row : tableList) {
            int assetNumber = (int) row.get("assetNumber");
            String assetType = (String) row.get("assetType");
            int floorNumber = (int) row.get("floorNumber");
            int roomNumber = (int) row.get("roomNumber");

            //Very annoying parsing to transform a string date into an SQL date. Please let me know if there's any other better way. 
            String purchaseDate = (String) row.get("purchaseDate");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            java.util.Date utilDate;
            try {
                utilDate = dateFormat.parse(purchaseDate);
            } catch (java.text.ParseException e) {
                // Handle the parse exception
                throw new RuntimeException("Invalid date format: " + purchaseDate, e);
            }
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            //Adding the specific asset based on the table information. 
            application.addSpecificAsset(assetNumber, floorNumber, roomNumber, sqlDate, AssetType.getWithName(assetType));
          }  
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    //throw new io.cucumber.java.PendingException();
  }

  @When("the manager attempts to delete the asset with number {string} \\(p12)")
  public void the_manager_attempts_to_delete_the_asset_with_number_p12(String string) {
    //Removes the specific asset based on the asset number given.
    application.removeSpecificAsset(SpecificAsset.getWithAssetNumber(Integer.parseInt(string)));
    // Write code here that turns the phrase above into concrete actions
    //throw new io.cucumber.java.PendingException();
  }

  @Then("the number of assets in the system shall be {string} \\(p12)")
  public void the_number_of_assets_in_the_system_shall_be_p12(String string) {
    //Confirms that the amount of assets has gone down after remvoving an asset.
    assertEquals(Integer.parseInt(string), application.getSpecificAssets().size());
    // Write code here that turns the phrase above into concrete actions
    //throw new io.cucumber.java.PendingException();
  }

  @Then("the following assets shall exist in the system \\(p12)")
  public void the_following_assets_shall_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
        //Turns the data table into a list of HashMaps with the column name as the key. 
        List<Map<String, Object>> tableList = dataTable.asMaps(String.class, Object.class);
        //This loop is used to iterate through the table at the same time as the assets in the application, in order
        //to assert that they are equals with respect to their fields. Application counter augments the index of the application asset list. 
        int applicationCounter = 0;
        for (Map<String, Object> row : tableList) {
          SpecificAsset asset = application.getSpecificAssets().get(applicationCounter);
          assertEquals((int)row.get("assetNumber"), asset.getAssetNumber());
          assertEquals((String)row.get("type"), asset.getAssetType());
          assertEquals((int) row.get("floorNumber"), asset.getFloorNumber());
          assertEquals((int) row.get("roomNumber"), asset.getRoomNumber());
          applicationCounter++;
        }
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    // For other transformations you can register a DataTableType.
    //throw new io.cucumber.java.PendingException();
  }
}
