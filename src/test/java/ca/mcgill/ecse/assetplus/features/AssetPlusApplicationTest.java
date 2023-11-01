package ca.mcgill.ecse.assetplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import io.cucumber.java.After;

public class AssetPlusApplicationTest {

  private static AssetPlus ap = AssetPlusApplication.getAssetPlus();

  private static String filename = "testdata.assetplus.json";

  File file;
  @BeforeAll
  public static void setUpOnce() {
    AssetPlusPersistence.setFilename(filename);
  }

  @BeforeEach
  public void setUp() {
    // remove test file
    file = new File(filename);
    file.delete();
    // clear all data
    ap.delete();
  }

  @Test
  public void testPersistence() {
    AssetPlusFeatureSet2Controller.addAssetType("couch", 100);
    AssetPlusFeatureSet2Controller.addAssetType("next couch", 200);
    AssetPlusFeatureSet2Controller.addAssetType("next next couch", 300);

    AssetPlusFeatureSet3Controller.addSpecificAsset(1, 0, -1, null, "couch3000");
    AssetPlusFeatureSet3Controller.addSpecificAsset(1, 0, -1, null, "couch2000");

    // load model again and check it
    AssetPlus ap2 = AssetPlusPersistence.load();

    assertEquals(ap2.getAssetType(0).getName(), "couch");
    assertEquals(ap2.getAssetType(1).getName(), "next couch");

  }

  @Test
  public void testPersistenceReinitialization() {
    AssetPlusFeatureSet2Controller.addAssetType("couch", 100);
    AssetPlusFeatureSet2Controller.addAssetType("nextCouch", 200);
    AssetPlusFeatureSet2Controller.addAssetType("nextNextCouch", 300);

    AssetPlusFeatureSet3Controller.addSpecificAsset(1, 0, -1, null, "couch");
    AssetPlusFeatureSet3Controller.addSpecificAsset(2, 0, -1, null, "nextCouch");


    // simulate shutting down the application
    ap.delete();
    ap.reinitialize();
    // Make sure there is nothing in the ap
    assertEquals(0, ap.getAssetTypes().size());
    assertEquals(0, ap.getSpecificAssets().size());

    // load model again and add further model elements
    ap = AssetPlusPersistence.load();
    // Make sure the application is loaded properly
    assertEquals(3, ap.getAssetTypes().size());
    assertEquals(2, ap.getSpecificAssets().size());
    assertTrue(ap.getAssetType(0).getName().equals("couch"));
    assertEquals(ap.getSpecificAsset(0).getAssetNumber(), 1);
    assertTrue(SpecificAsset.getWithAssetNumber(2).getAssetType().getName().equals("nextCouch"));

    String err1 = AssetPlusFeatureSet3Controller.addSpecificAsset(3, 0, -1, null, "type doesnt exist");
    System.out.println(err1);
    assertTrue(!err1.isEmpty());
    String err2 = AssetPlusFeatureSet2Controller.addAssetType("nextNextCouch", 300);
    assertTrue(!err2.isBlank());


    AssetPlusFeatureSet2Controller.addAssetType("nextNextNextCouch", 300);
    AssetPlusFeatureSet3Controller.addSpecificAsset(3, 0, -1, null, "nextNextNextCouch");

    ap = AssetPlusPersistence.load();

    assertTrue(!(SpecificAsset.getWithAssetNumber(3).getAssetType().equals("nextNextNextCouch")));
    
    AssetPlusFeatureSet2Controller.deleteAssetType("nextNextNextCouch");

  }

  @After
  public void tearDown() {
    file.delete();
    // clear all data
    ap.delete();
  }


  /* 
  private void checkResultSchedule(String driverName, boolean driverOnSickLeave, int driverId, Date assignmentDate,
      int assignmentRouteNumber, String assignmentBusLicencePlate, boolean assignmentBusInRepairShop, Shift shift,
      BTMS btms, int numberSchedules, int numberAssignments, int numberRoutes, int busStopNumbers, int numberBuses,
      int numberDrivers) {
    assertEquals(numberSchedules, btms.getSchedules().size());
    if (numberSchedules > 0) {
      assertEquals(shift, btms.getSchedule(0).getShift());
      assertNotNull(btms.getSchedule(0).getAssignment());
      assertNotNull(btms.getSchedule(0).getDriver());
      assertEquals(btms, btms.getSchedule(0).getBTMS());
    }
    assertEquals(numberAssignments, btms.getAssignments().size());
    if (numberAssignments > 0) {
      assertEquals(assignmentDate, btms.getAssignment(0).getDate());
      assertNotNull(btms.getAssignment(0).getBus());
      assertNotNull(btms.getAssignment(0).getRoute());
      assertEquals(numberSchedules, btms.getAssignment(0).getDriverSchedules().size());
      assertEquals(btms, btms.getAssignment(0).getBTMS());
    }
    assertEquals(numberRoutes, btms.getRoutes().size());
    if (numberRoutes > 0) {
      assertEquals(assignmentRouteNumber, btms.getRoute(0).getNumber());
      assertEquals(numberAssignments, btms.getRoute(0).getRouteAssignments().size());
      assertEquals(btms, btms.getRoute(0).getBTMS());
    }
    assertEquals(numberDrivers, btms.getDrivers().size());
    if (numberDrivers > 0) {
      assertEquals(driverName, btms.getDriver(0).getName());
      assertEquals(driverOnSickLeave, btms.getDriver(0).isOnSickLeave());
      assertEquals(driverId, btms.getDriver(0).getId());
      assertEquals(numberSchedules, btms.getDriver(0).getDriverSchedules().size());
      assertEquals(btms, btms.getDriver(0).getBTMS());
    }
    assertEquals(numberBuses, btms.getVehicles().size());
    if (numberBuses > 0) {
      assertEquals(assignmentBusLicencePlate, btms.getVehicle(0).getLicencePlate());
      assertEquals(assignmentBusInRepairShop, btms.getVehicle(0).isInRepairShop());
      assertEquals(numberAssignments, btms.getVehicle(0).getRouteAssignments().size());
      assertEquals(btms, btms.getVehicle(0).getBTMS());
    }
  }
  */

}