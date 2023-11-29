package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FrontPageController {

  @FXML
  private VBox assetBox;

  @FXML
  private Label assetCount;

  @FXML
  private VBox employeeBox;

  @FXML
  private Label employeeCount;

  @FXML
  private StackPane frontPage;

  @FXML
  private Rectangle gradient;

  @FXML
  private VBox guestBox;

  @FXML
  private Label guestCount;

  @FXML
  private ImageView image;

  @FXML
  private VBox infoPane;

  @FXML
  private Button loginButton;

  @FXML
  private VBox ticketBox;

  @FXML
  private Label ticketCount;

  @FXML
  private Button closeButton;

 @FXML
    void initialize() {
      gradient.widthProperty().bind(frontPage.widthProperty());
      gradient.heightProperty().bind(frontPage.heightProperty());

      image.setOpacity(0);
      guestBox.setOpacity(0);
      employeeBox.setOpacity(0);
      ticketBox.setOpacity(0);
      assetBox.setOpacity(0);
      loginButton.setOpacity(0);

      employeeCount.setText(ViewUtils.getHotelStaffs().size()+"");
      guestCount.setText(AssetPlusFeatureTOController.getAllGuests().size()+"");
      ticketCount.setText(AssetPlusFeatureSet6Controller.getTickets().size()+"");
      assetCount.setText(ViewUtils.getSpecificAsset().size()+"");

      // Animate
      FadeTransition fadeInImage = new FadeTransition(Duration.seconds(2), image);
      fadeInImage.setFromValue(0.0);
      fadeInImage.setToValue(1.0);
      
      FadeTransition fadeGuestBox = new FadeTransition(Duration.seconds(0.5), guestBox);
      fadeGuestBox.setFromValue(0.0);
      fadeGuestBox.setToValue(1.0);

      FadeTransition fadeEmployeeBox = new FadeTransition(Duration.seconds(0.5), employeeBox);
      fadeEmployeeBox.setFromValue(0.0);
      fadeEmployeeBox.setToValue(1.0);

      FadeTransition fadeTicketBox = new FadeTransition(Duration.seconds(0.5), ticketBox);
      fadeTicketBox.setFromValue(0.0);
      fadeTicketBox.setToValue(1.0);

      FadeTransition fadeAssetBox = new FadeTransition(Duration.seconds(0.5), assetBox);
      fadeAssetBox.setFromValue(0.0);
      fadeAssetBox.setToValue(1.0);

      FadeTransition fadeButton = new FadeTransition(Duration.seconds(0.5), loginButton);
      fadeButton.setFromValue(0.0);
      fadeButton.setToValue(1.0);
      

      // Start the fade-in animation
      fadeInImage.play();
      fadeInImage.setOnFinished(event -> fadeGuestBox.play());
      fadeGuestBox.setOnFinished(event -> fadeEmployeeBox.play());
      fadeEmployeeBox.setOnFinished(event -> fadeTicketBox.play());
      fadeTicketBox.setOnFinished(event -> fadeAssetBox.play());
      fadeAssetBox.setOnFinished(event -> fadeButton.play());
    }

      @FXML
    void logIn(ActionEvent event) {
      System.out.println("does this happen?");
      AssetPlusFXMLView.getInstance().changeTab("pages/TicketStatus.fxml");
    }


    @FXML
    void CloseWindow(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closeWindow();
    }
}


