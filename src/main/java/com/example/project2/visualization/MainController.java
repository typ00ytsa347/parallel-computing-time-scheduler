package com.example.project2.visualization;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class MainController implements Initializable {

    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MainController.class);
    private Thread uiOrchestrator;

    @FXML
    private VBox inputGraphVBox;
    @FXML
    private VBox outputGraphVBox;
    @FXML
    private Label currentCostLabel;
    @FXML
    private PieChart dataChart;
    @FXML
    private Label timeElapsedLabel;
    @FXML
    private Label progressBarInfo;

    @FXML
    private VBox cpuVBox;
    private LineChart<Number, Number> cpuChart;
    private List<XYChart.Series<Number, Number>> cpuUsageSeries = new ArrayList<>();
    private double cpuStartTime;

    @FXML
    private VBox memoryVbox;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    private XYChart.Series<Number, Number> memoryUsageSeries = new XYChart.Series<>();
    private double memoryStartTime;

    private BigDecimal schedulesUpperBound;
    private double schedulesUpperBoundLog;

    @FXML
    private Label programStatusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;
    private IGraph graph;

    private void initialiseMemoryUsage() {
        NumberAxis memoryXAxis = new NumberAxis();
        memoryXAxis.setLabel("Time (s)");
        NumberAxis memoryYAxis = new NumberAxis();
        memoryYAxis.setLabel("Memory Usage (Mb)");
        LineChart<Number, Number> memoryChart = new LineChart<>(memoryXAxis, memoryYAxis);

        memoryChart.getData().add(memoryUsageSeries);
        memoryChart.setLegendVisible(false);
        this.memoryVbox.getChildren().add(memoryChart);
        memoryChart.prefWidthProperty().bind(this.memoryVbox.widthProperty());
        memoryChart.prefHeightProperty().bind(this.memoryVbox.heightProperty());
    }

    private void initialiseCPUUsage() {
        NumberAxis cpuXAxis = new NumberAxis();
        cpuXAxis.setLabel("Time (s)");
        NumberAxis cpuYAxis = new NumberAxis();
        cpuYAxis.setLabel("CPU Usage %");
        cpuYAxis.setForceZeroInRange(true);
        cpuYAxis.setAutoRanging(false);
        cpuYAxis.setUpperBound(100);
        cpuYAxis.setLowerBound(0);

        cpuChart = new LineChart<>(cpuXAxis, cpuYAxis);
        cpuChart.setLegendVisible(false);
        this.cpuVBox.getChildren().add(cpuChart);
        cpuChart.prefWidthProperty().bind(this.cpuVBox.widthProperty());
        cpuChart.prefHeightProperty().bind(this.cpuVBox.heightProperty());
    }

    private void initialiseChartData() {
        pieChartData.add(new PieChart.Data("Log Searched States", 0));
        pieChartData.add(new PieChart.Data("Log All States", 0));
        dataChart.setData(pieChartData);
        dataChart.setLegendVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialiseMemoryUsage();
        initialiseCPUUsage();
        initialiseChartData();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        programStatusLabel.setVisible(false);
//        this.schedulesUpperBound = this.graph.getSchedulesUpperBound();
//        this.schedulesUpperBoundLog = this.graph.getSchedulesUpperBoundLog();

        uiOrchestrator.start();
    }


    public void gracefulStop() {
        logger.info("Gracefully shutting down UI");
        uiOrchestrator.interrupt();
    }
}
