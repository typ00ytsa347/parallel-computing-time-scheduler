package com.example.project2.visualization;

import com.example.project2.Search;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class GanttChartSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Gantt Chart Sample");

        String[] processors = new String[] { "Processor 3", "Processor 2", "Processor 1" };

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(processors)));

        chart.setTitle("Task Allocation");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        String processor;

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();


        ArrayList<TaskAllocationResult> tasks = GanttChartController.getSearch().getResults();
        ArrayList<Integer> tasksID = new ArrayList<>();

        for(TaskAllocationResult task : tasks) {

            if(tasksID.contains(task.id)) {
                break;
            } else {
                tasksID.add(task.id);
            }

            if(task.allocatedProcessor == 1) {
                processor = processors[2];
                series1.getData().add(new XYChart.Data(task.startTime, processor, new GanttChart.ExtraData( task.nodeWeight, "status-red")));
            } else if(task.allocatedProcessor == 2) {
                processor = processors[1];
                series1.getData().add(new XYChart.Data(task.startTime, processor, new GanttChart.ExtraData( task.nodeWeight, "status-green")));
            } else if(task.allocatedProcessor == 3) {
                processor = processors[0];
                series1.getData().add(new XYChart.Data(task.startTime, processor, new GanttChart.ExtraData( task.nodeWeight, "status-blue")));
            } else {
                break;
            }

        }

        chart.getData().addAll(series1, series2, series3);

        Scene scene  = new Scene(chart,620,350);
        scene.getStylesheets().add("ganttchart.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
