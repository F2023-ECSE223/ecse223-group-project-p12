package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MaintenanceHistoryPopUpController {

  public static TOSpecificAsset asset;

    @FXML
    private VBox ticketList;


    @FXML
    void initialize() {
      

    }

    public static void get(TOSpecificAsset aAsset){
      asset = aAsset;
    }

}
