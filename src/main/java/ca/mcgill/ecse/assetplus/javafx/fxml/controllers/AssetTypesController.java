package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AssetTypesController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addAssetTypeButton;

    @FXML
    private Button AddAssetTypeCreateButton;

    @FXML
    private Button AddAssetTypeCancelButton;

    @FXML
    private Label replaceMe;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TextField AddAssetTypeName;

    @FXML
    private TextField AddAssetTypeLifespan;

    @FXML 
    private AnchorPane AddAssetTypePane;

    @FXML
    private FlowPane grid;


    @FXML
    void initialize() {
    
    }

    @FXML
    void AddImage(ActionEvent event) {

    }

    @FXML
    void addAssetTypeClicked(ActionEvent event) {
      showAssetTypes();
    }

    private void showAssetTypes(){

      grid.getChildren().clear();
      int width = 200;
      
      VBox vbox = new VBox(8); // spacing = 8
      vbox.setPrefSize(width, 280);
      vbox.setBackground(new Background(new BackgroundFill(Color.rgb(247, 246, 254), CornerRadii.EMPTY, Insets.EMPTY)));
      

      DropShadow ds = new DropShadow();
      ds.setOffsetX(3.0);
      ds.setOffsetY(3.0);
      ds.setColor(Color.GRAY);

      ImageView imageView;
      String imageUrl = "https://www.ikea.com/ca/en/images/products/blidvaeder-table-lamp-off-white-ceramic-beige__1059591_pe849714_s5.jpg";      
      Image image = new Image(imageUrl, 200, 200, true, true);
      // If the image was loaded without exceptions, consider it valid
      if (image.isError() == false) {
          System.out.println("Image valid");
          imageView = new ImageView(image);          
      }
      else {
          imageView = new ImageView(new Image("ca/mcgill/ecse/assetplus/javafx/resources/Images/warning.png",width, width, true, true));
      }

      HBox hbox = new HBox();
      hbox.setPrefSize(width, 15);
      hbox.setSpacing(10);

      Rectangle rOffset = new Rectangle(115, 15);
      rOffset.setFill(Color.rgb(247, 246, 254));

      Button pencilBtn = new Button();
      pencilBtn.getStyleClass().add("icon-pencil");
      pencilBtn.setPickOnBounds(true);

      Button assetBtn = new Button();
      assetBtn.getStyleClass().add("icon-asset");
      assetBtn.setPickOnBounds(true);

      Button trashBtn = new Button();
      trashBtn.getStyleClass().add("icon-trash");
      trashBtn.setPickOnBounds(true);

      hbox.getChildren().add(rOffset);
      hbox.getChildren().add(assetBtn);
      hbox.getChildren().add(pencilBtn);
      hbox.getChildren().add(trashBtn);
     

      vbox.getChildren().add(imageView);
      Label name = new Label(" Lamp");
      Label lifeExp = new Label(" Life expenctancy: 5 years");  
      vbox.getChildren().add(name);
      vbox.getChildren().add(lifeExp);
      vbox.getChildren().add(hbox);
      vbox.setEffect(ds);
      grid.getChildren().add(vbox);
    }
}


