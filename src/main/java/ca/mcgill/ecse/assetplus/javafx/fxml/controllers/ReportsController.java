package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.HashMap;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.Soundbank;


public class ReportsController {

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private BarChart<String, Number> assetTypeIssues;

    @FXML
    private RadioButton oneLastFive;

    @FXML
    private RadioButton oneLastMonth;

    @FXML
    private RadioButton oneLastWeek;

    @FXML
    private RadioButton oneLastYear;

    @FXML
    private RadioButton allTime;

    private HashMap<String, Integer> counts;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        oneLastFive.setToggleGroup(toggleGroup);
        oneLastMonth.setToggleGroup(toggleGroup);
        oneLastWeek.setToggleGroup(toggleGroup);
        oneLastYear.setToggleGroup(toggleGroup);
        allTime.setToggleGroup(toggleGroup);

        yAxis.setTickUnit(1);
        fixChart(0);
                
    }

    private static boolean isWithinRange(LocalDate dateToCheck, LocalDate currentDate, long amount, ChronoUnit unit) {
        LocalDate dateRangeStart = currentDate.minus(amount, unit);
        LocalDate dateRangeEnd = currentDate.plus(1, ChronoUnit.DAYS); 

        return dateToCheck.isAfter(dateRangeStart) && dateToCheck.isBefore(dateRangeEnd);
    }

    public HashMap<String, Integer> getCounts(int condition) {
        HashMap<String, Integer> count = new HashMap<>();

        for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()) {
            count.put(type.getName(), 0);
        }

        LocalDate currentDate = LocalDate.now();

        for (TOMaintenanceTicket ticket : AssetPlusFeatureSet6Controller.getTickets()) {
            if (count.containsKey(ticket.getAssetName())) {

                java.sql.Date raisedOnSqlDate = ticket.getRaisedOnDate();
                LocalDate raisedOnDate = raisedOnSqlDate.toLocalDate();

                switch (condition) {
                case 0:
                    count.put(ticket.getAssetName(), count.get(ticket.getAssetName()) + 1);
                    break;
                case 1:
                    if (isWithinRange(raisedOnDate, currentDate, 1, ChronoUnit.WEEKS)) {
                        count.put(ticket.getAssetName(), count.get(ticket.getAssetName()) + 1);
                    }
                    break;
                case 2:
                    if (isWithinRange(raisedOnDate, currentDate, 1, ChronoUnit.MONTHS)) {
                        count.put(ticket.getAssetName(), count.get(ticket.getAssetName()) + 1);
                    }
                    break;
                case 3:
                    if (isWithinRange(raisedOnDate, currentDate, 1, ChronoUnit.YEARS)) {
                        count.put(ticket.getAssetName(), count.get(ticket.getAssetName()) + 1);
                    }
                    break;
                case 4:
                    if (isWithinRange(raisedOnDate, currentDate, 5, ChronoUnit.YEARS)) {
                        count.put(ticket.getAssetName(), count.get(ticket.getAssetName()) + 1);
                    }
                    break;
                default:
                    System.out.println("Invalid condition");
            }
                
            }
        }        
        return count;
    }

    public void fixChart(int condition){
        counts = getCounts(condition);


        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (String asset : counts.keySet()) {
            series.getData().add(new XYChart.Data<>(asset, counts.get(asset)));
        }

        assetTypeIssues.getData().add(series);

    }
    

    @FXML
    void allTimeBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(0);
         System.out.println("All time");
    }

    @FXML
    void oneLastFiveBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(4);
    }

    @FXML
    void oneLastMonthBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(2);
    }

    @FXML
    void oneLastWeekBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(1);
    }

    @FXML
    void oneLastYearBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(3);
         System.out.println("1 year");
    }

}

    

