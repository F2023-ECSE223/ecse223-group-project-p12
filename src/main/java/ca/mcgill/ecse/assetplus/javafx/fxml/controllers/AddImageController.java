package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

public class AddImageController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addImageButton;

    @FXML
    private Label replaceMe;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private GridPane grid;

    @FXML
    void AddImage(ActionEvent event) {

    }


    @FXML
    void initialize() {
        replaceMe.setText("I replaced you");

        Image image = new Image("https://www.ikea.com/ca/en/images/products/kivik-chaise-grann-bomstad-black__0115141_pe268333_s5.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setCache(true);
        imageView.setPreserveRatio(true);

        grid.add(imageView, 0, 0);


    }
}