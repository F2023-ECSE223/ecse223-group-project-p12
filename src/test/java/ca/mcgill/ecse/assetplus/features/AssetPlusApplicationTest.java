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

  private static String filename = "ap.data";

  File file;
  @BeforeAll
  public static void setUpOnce() {
    AssetPlusPersistence.setFilename(filename);
  }


  @Test
  public void testPutting() {

    //Adding these different objects to ap.data and making sure they've appeared
    AssetPlusFeatureSet2Controller.addAssetType("Asset Type: 1", 100);
    AssetPlusFeatureSet2Controller.addAssetType("Asset Type: 2", 200);
    AssetPlusFeatureSet3Controller.addSpecificAsset(1, 1, 1, null, "Asset Type: 1");
    AssetPlusFeatureSet3Controller.addSpecificAsset(2, 1, 1, null, "Asset Type: 2");
  
  }

   
  @Test
  public void testGetting() {
    
    //Verifying the reloading of the json file into an ap object
    AssetPlus ap2 = AssetPlusPersistence.load();
    assertEquals(ap2.getAssetType(0).getName(), "Asset Type: 1");
    assertEquals(ap2.getAssetType(1).getName(), "Asset Type: 2");
    assertEquals(ap2.getSpecificAsset(0).getAssetNumber(), 1);
    assertEquals(ap2.getSpecificAsset(0).getAssetType().getName(), "Asset Type: 1");
    assertEquals(ap2.getSpecificAsset(1).getAssetNumber(), 2);
    assertEquals(ap2.getSpecificAsset(1).getAssetType().getName(), "Asset Type: 2");    

  }
  

  @Test
  public void testModifying() {

    //Adding these different objects to ap.data and making sure they've appeared
    AssetPlusFeatureSet2Controller.updateAssetType("Asset Type: 1", "Asset Type: 1.1", 1100);
    AssetPlusFeatureSet2Controller.updateAssetType("Asset Type: 2", "Asset Type: 2.2", 2200);
    AssetPlusFeatureSet3Controller.updateSpecificAsset(1, 2, 2, null, "Asset Type: 2.2");
    
  }
  
  @Test
  public void deleting() { 

    //Deleting the different data from the json file
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(1);
    AssetPlusFeatureSet3Controller.deleteSpecificAsset(2);
    AssetPlusFeatureSet2Controller.deleteAssetType("Asset Type: 1.1");
    AssetPlusFeatureSet2Controller.deleteAssetType("Asset Type: 2.2");
    
  }


  @After
  public void tearDown() {
    //file.delete();
    // clear all data
    ap.delete();
  }


  

}