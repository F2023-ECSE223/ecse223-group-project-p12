package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.FlowPane;

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
    private FlowPane grid;

    @FXML
    void AddImage(ActionEvent event) {

    }


    @FXML
    void initialize() {
        replaceMe.setText("I replaced you");

        Rectangle rectangle1 = new Rectangle(0, 0, 200, 200);
        rectangle1.setArcWidth(30.0);
        rectangle1.setArcHeight(30.0);

        ImagePattern pattern1 = new ImagePattern(
            new Image("https://www.ikea.com/ca/en/images/products/kivik-chaise-grann-bomstad-black__0115141_pe268333_s5.jpg", 200, 200, true, true) // Resizing
        );

        rectangle1.setFill(pattern1);

        grid.getChildren().add(rectangle1);

        //System.out.println(ViewUtils.getMaintenanceTickets().size());

        /* ObservableList<String> images = ViewUtils.getTicketImages(1);
        
        for (String imageURL : images) {
            Rectangle rectangle = new Rectangle(0, 0, 200, 200);
            rectangle.setArcWidth(30.0);
            rectangle.setArcHeight(30.0);

            ImagePattern pattern = new ImagePattern(
                new Image(imageURL, 200, 200, true, true) // Resizing
            );

            rectangle.setFill(pattern);

            grid.getChildren().add(rectangle);
        } */
    }
}