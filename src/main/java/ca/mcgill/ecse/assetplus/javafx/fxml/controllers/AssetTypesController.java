package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddAssetTypePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddImagePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteAssetTypePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifyAssetTypePopUpController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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
    private TextField AssetTypeAddName;

    @FXML
    private TextField AssetTypeAddLifespan;

    @FXML 
    private AnchorPane AddAssetTypePane;

    @FXML
    private FlowPane grid;

    @FXML
    void AddImage(ActionEvent event) {

    }


    @FXML
    void initialize() {
      showAssetTypes();
      grid.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
        showAssetTypes();
      });
      AssetPlusFXMLView.getInstance().registerRefreshEvent(grid);
    }

    @FXML
    void addAssetTypeClicked(ActionEvent event) {

      AddAssetTypePopUpController controller = (AddAssetTypePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssetTypeAddPopUp.fxml", "Add Asset Type");
    }

    public void showAssetTypes(){

      grid.getChildren().clear();
      List<TOAssetType> assetTypes = AssetPlusFeatureTOController.getAssetTypes();
      int width = 200;
      
      for(TOAssetType assetType:assetTypes){

        VBox vbox = new VBox(8); // spacing = 8
        vbox.setPrefSize(width, 280);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(247, 246, 254), CornerRadii.EMPTY, Insets.EMPTY)));
        String nameAssetType = assetType.getName();
        

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
        pencilBtn.setOnAction(e -> pencilBtnClicked(nameAssetType));

        Button assetBtn = new Button();
        assetBtn.getStyleClass().add("icon-asset");
        assetBtn.setPickOnBounds(true);
        assetBtn.setOnAction(e -> assetBtnClicked(nameAssetType));
        
        Button trashBtn = new Button();
        trashBtn.getStyleClass().add("icon-trash");
        trashBtn.setPickOnBounds(true);
        trashBtn.setOnAction(e -> trashBtnClicked(nameAssetType));


        hbox.getChildren().add(rOffset);
        hbox.getChildren().add(assetBtn);
        hbox.getChildren().add(pencilBtn);
        hbox.getChildren().add(trashBtn);
      

        vbox.getChildren().add(imageView);
        Label name = new Label(assetType.getName());
        name.setFont(new Font(15));
        String nbYears = Integer.toString(assetType.getExpectedLifeSpan());
        String intro = AssetPlusFXMLView.getInstance().getBundle().getString("key.AssetType_LifeExpectancy");
        String end = AssetPlusFXMLView.getInstance().getBundle().getString("key.AssetType_Years");
        Label lifeExp = new Label(intro + " "+ nbYears + " "+ end);  
        vbox.getChildren().add(name);
        vbox.getChildren().add(lifeExp);
        vbox.getChildren().add(hbox);
        vbox.setEffect(ds);
        grid.getChildren().add(vbox);
      }

    }

    private void pencilBtnClicked(String name){
        System.out.println("Updating with name: " + name);
        ModifyAssetTypePopUpController controller = (ModifyAssetTypePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssetTypeModifyPopUp.fxml", "Modify Asset Type");
        controller.setName(name);
        showAssetTypes();
    }

    private void trashBtnClicked(String name){
      System.out.println("Deleting with name: " + name);
      DeleteAssetTypePopUpController controller = (DeleteAssetTypePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssetTypeDeletePopUp.fxml", "Delete Asset Type");
      controller.setName(name);
      showAssetTypes();
    }

    private void assetBtnClicked(String name){
      System.out.println("Showing asset with asset type: " + name);
    }



    class CustomEvent extends Event {

    private String assetTypeName;

    public static final EventType<CustomEvent> CUSTOM = new EventType(ANY, "CUSTOM");
    public CustomEvent(String assetTypeName) {
        super(CustomEvent.CUSTOM);
        this.assetTypeName = assetTypeName;
    }

    public String getAssetTypeName() {
        return this.assetTypeName;
    }

}
      
}


