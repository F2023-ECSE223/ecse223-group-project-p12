package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.FileInputStream;
import java.io.IOException;

public class FrontPageController {
  @FXML
  private StackPane frontPage;

  @FXML
  private VBox infoPane;

  @FXML
  private Label assetCount;

  @FXML
  private Label ticketCount;

  @FXML
  private Label employeeCount;


  @FXML
  private Label guestCount;



 @FXML
    void initialize() {
      try (FileInputStream input = new FileInputStream("src/main/java/ca/mcgill/ecse/assetplus/javafx/resources/Images/frontPage.png")){
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.preserveRatioProperty().set(false);
        imageView.fitWidthProperty().bind(frontPage.widthProperty());
        imageView.fitHeightProperty().bind(frontPage.heightProperty());
        frontPage.getChildren().remove(infoPane);
        frontPage.getChildren().add(imageView);
        frontPage.getChildren().add(infoPane);
      } catch (IOException e) {
        e.printStackTrace();
      }

      employeeCount.setText(ViewUtils.getHotelStaffs().size()+"");
      guestCount.setText(AssetPlusFeatureTOController.getAllGuests().size()+"");
      ticketCount.setText(AssetPlusFeatureSet6Controller.getTickets().size()+"");
      assetCount.setText(ViewUtils.getSpecificAsset().size()+"");
    }

      @FXML
    void logIn(ActionEvent event) {
      System.out.println("does this happen?");
      AssetPlusFXMLView.getInstance().changeTab("pages/TicketStatus.fxml");
    }

}


