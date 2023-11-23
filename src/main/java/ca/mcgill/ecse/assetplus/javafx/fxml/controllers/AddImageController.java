package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.util.ResourceBundle;
import javax.swing.text.View;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddImagePopUpController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
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
    private Label errorMessage;

    @FXML
    private FlowPane grid;

    @FXML
    public void initialize() {
        currentTicketNumber = -1;
        // the grid listen to the refresh event
        grid.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            refreshView();
        });

        // let the application be aware of the refreshable node
        AssetPlusFXMLView.getInstance().registerRefreshEvent(grid);
    }

    @FXML
    void AddImage(ActionEvent event) {

        if (currentTicketNumber!=-1) {
            AddImagePopUpController controller = (AddImagePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddImagePopUp.fxml");
            if (controller!=null)
                controller.setTicketId(currentTicketNumber);
            else System.out.println("controller null");
        }
        else {
            errorMessage.setVisible(true);
            setErrorMessage("key.AddImage_EnterValidTicket");
        }

    }

    @FXML
    void TicketNumberEntered(KeyEvent event) {
        if( event.getCode() == KeyCode.ENTER ) {
            refreshView();
        }
    }

    private void refreshView() {
        try {
            int input = Integer.parseInt(ticketNumberField.getText());
            ObservableList<String> imageList = ViewUtils.getTicketImages(input);

            if (imageList != null) {
                currentTicketNumber=input;
                errorMessage.setVisible(false);
                showImages(imageList);
            }
            else {
                currentTicketNumber=-1;
                errorMessage.setVisible(true);
                setErrorMessage("key.AddImage_InvalidTicketNumber");
                grid.getChildren().clear();
            }
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    private void showImages(ObservableList<String> list) {
        if (list.size()==0) {
            errorMessage.setVisible(true);
            setErrorMessage("key.AddImage_NoImagesWithTicket");
        }
        grid.getChildren().clear();
        for (String imageURL : list) {
            Rectangle rectangle = new Rectangle(0, 0, 200, 200);
            rectangle.setArcWidth(30.0);
            rectangle.setArcHeight(30.0);
            
            Image image = new Image(imageURL, 200, 200, true, true);
            // If the image was loaded without exceptions, consider it valid
            ImagePattern pattern;
            if (image.isError() == false) {
                pattern = new ImagePattern(image);
            }
            else {
                pattern = new ImagePattern(new Image("ca/mcgill/ecse/assetplus/javafx/resources/Images/warning.png", 200, 200, true, true));
                
            }
            rectangle.setFill(pattern);
            
            // Create the delete button
            Button trashBtn = new Button();
            trashBtn.getStyleClass().add("icon-trash");
            trashBtn.setStyle("-icon-paint:  white");
            trashBtn.setPickOnBounds(true);
            trashBtn.setOnAction(e -> trashBtnClicked(imageURL));

            // Create an AnchorPane to hold the image and button
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().addAll(rectangle, trashBtn);

            // Set the button's position to the top right corner
            AnchorPane.setTopAnchor(trashBtn, 10.0);
            AnchorPane.setRightAnchor(trashBtn, 10.0); 

            grid.getChildren().add(anchorPane);
        }
    }

    private void trashBtnClicked(String url) {
        AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(url, currentTicketNumber);
        ViewUtils.callController("");
    }

    private void setErrorMessage(String message) {
        errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString(message));
    }
}