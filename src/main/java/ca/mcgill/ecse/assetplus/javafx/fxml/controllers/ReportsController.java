package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.HashMap;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
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
import org.codehaus.jettison.Node;


public class ReportsController {

    private int maxNumberOfTicketForAnAsset;

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
    private PieChart priorityPieChart;

    @FXML
    private PieChart statusPieChart;

    @FXML
    void initialize() {
        maxNumberOfTicketForAnAsset = 1;
        ToggleGroup toggleGroup = new ToggleGroup();
        oneLastFive.setToggleGroup(toggleGroup) ;
        oneLastMonth.setToggleGroup(toggleGroup);
        oneLastWeek.setToggleGroup(toggleGroup);
        oneLastYear.setToggleGroup(toggleGroup);
        allTime.setToggleGroup(toggleGroup);

        allTime.setSelected(true);
        fixChart(0);
        fixColors();

        assetTypeIssues.setAnimated(false);
        assetTypeIssues.setLegendVisible(false);

        setStatusPieChart();
        setPriorityPieChart();
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
            int numberOfTicketForAnAsset = counts.get(asset);
            series.getData().add(new XYChart.Data<>(asset, numberOfTicketForAnAsset));

            if (numberOfTicketForAnAsset > maxNumberOfTicketForAnAsset) {
                maxNumberOfTicketForAnAsset = numberOfTicketForAnAsset;
            }
        }

        assetTypeIssues.getData().add(series);
        assetTypeIssues.setCategoryGap(300/series.getData().size());
        yAxis.setUpperBound(maxNumberOfTicketForAnAsset+1);
    }
    

    public void fixColors(){
        String barColor = "rgb(135, 104, 242)"; 
        for (XYChart.Series<String, Number> series : assetTypeIssues.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                javafx.scene.Node node = data.getNode();
                node.setStyle("-fx-bar-fill: " + barColor + ";");
            }
        }
    }

    @FXML
    void allTimeBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(0);
        fixColors();
    }

    @FXML
    void oneLastFiveBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(4);
        fixColors();
    }

    @FXML
    void oneLastMonthBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(2);
        fixColors();
    }

    @FXML
    void oneLastWeekBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(1);
        fixColors();
    }

    @FXML
    void oneLastYearBtn(ActionEvent event) {
        assetTypeIssues.getData().clear();
        fixChart(3);
         System.out.println("1 year");
         fixColors();
    }

    private void setStatusPieChart() {

        int openCount = ViewUtils.getNumberOfTickets("Open"); 
        int assignedCount = ViewUtils.getNumberOfTickets("Assigned");
        int inProgressCount = ViewUtils.getNumberOfTickets("InProgress");
        int resolvedCount = ViewUtils.getNumberOfTickets("Resolved");
        int closedCount = ViewUtils.getNumberOfTickets("Closed");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        addData(pieChartData, openCount, "Open");
        addData(pieChartData, assignedCount, "Assigned");
        addData(pieChartData, inProgressCount, "In Progress");
        addData(pieChartData, resolvedCount, "Resolved");
        addData(pieChartData, closedCount, "Closed");

        statusPieChart.setData(pieChartData);
        statusPieChart.setLegendVisible(false);

        for (PieChart.Data data : pieChartData) {
            switch (data.getName()) {
                case "Open":
                    data.getNode().setStyle("-fx-pie-color: #696969;");
                    break;
                case "Assigned":
                    data.getNode().setStyle("-fx-pie-color: #0066CC");
                    break;
                case "In Progress":
                    data.getNode().setStyle("-fx-pie-color: #CD6200;");
                    break;
                case "Resolved":
                    data.getNode().setStyle("-fx-pie-color: #1F9254;");
                    break;
                case "Closed":
                    data.getNode().setStyle("-fx-pie-color: #A30D11;");
                    break;
            }
        }
    }

    private void addData(ObservableList<PieChart.Data> data, int count, String name) {
        if (count > 0) {
            data.add(new PieChart.Data(name, count));
        }
    }

    private void setPriorityPieChart() {

        int lowCount = ViewUtils.getNumberOfTickets("Low"); 
        int normalCount = ViewUtils.getNumberOfTickets("Normal");
        int urgentCount = ViewUtils.getNumberOfTickets("Urgent");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        addData(pieChartData, lowCount, "Low");
        addData(pieChartData, normalCount, "Normal");
        addData(pieChartData, urgentCount, "Urgent");

        priorityPieChart.setData(pieChartData);
        priorityPieChart.setLegendVisible(false);

        for (PieChart.Data data : pieChartData) {
            switch (data.getName()) {
                case "Low":
                    data.getNode().setStyle("-fx-pie-color: #696969;");
                    break;
                case "Normal":
                    data.getNode().setStyle("-fx-pie-color: #1F9254");
                    break;
                case "Urgent":
                    data.getNode().setStyle("-fx-pie-color: #A30D11;");
                    break;
            }
        }
    }

}

    

