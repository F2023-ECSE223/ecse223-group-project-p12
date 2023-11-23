package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddImageController {
    private int currentTicketNumber;

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addImageButton;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private FlowPane grid;


    @FXML
    void initialize() {
        currentTicketNumber = -1;
    }

    @FXML
    void AddImage(ActionEvent event) {
        ViewUtils.loadPopupWindow("popUp/AddImage.fxml", "Add Image");
    }

    @FXML
    void TicketNumberEntered(KeyEvent event) {
        if( event.getCode() == KeyCode.ENTER ) {
            try {
                int input = Integer.parseInt(ticketNumberField.getText());
                ObservableList<String> imageList = ViewUtils.getTicketImages(input);

                if (imageList != null) {
                    // Do not reload the images if it is already the correct ones
                    if (currentTicketNumber != input) {
                        currentTicketNumber = input;
                        showImages(imageList);
                    }
                }
                else {
                    grid.getChildren().clear();
                }
            }
            catch(NumberFormatException e) {
                System.out.println(e);
            }
        }
    }

    
    private void showImages(ObservableList<String> list) {
        grid.getChildren().clear();
        for (String imageURL : list) {
            Rectangle rectangle = new Rectangle(0, 0, 200, 200);
            rectangle.setArcWidth(30.0);
            rectangle.setArcHeight(30.0);
            
            Image image = new Image(imageURL, 200, 200, true, true);
            // If the image was loaded without exceptions, consider it valid
            if (image.isError() == false) {
                System.out.println("Image valid");
                ImagePattern pattern = new ImagePattern(image);

                rectangle.setFill(pattern);
                grid.getChildren().add(rectangle);
            }
            else {
                ImagePattern pattern = new ImagePattern(new Image("ca/mcgill/ecse/assetplus/javafx/resources/Images/warning.png", 200, 200, true, true));
                rectangle.setFill(pattern);
                grid.getChildren().add(rectangle);
            }
        }
    }
}