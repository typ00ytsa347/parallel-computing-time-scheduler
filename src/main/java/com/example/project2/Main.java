package com.example.project2;

import com.example.project2.visualization.*;
import javafx.application.Application;

public class Main {

    public static void main(String[] args) {

        Dag dag = DagIO.parseInputArgs(args);

        if (DagIO.getVaildibility()) {
            Search search = new Search(dag, DagIO.getProcessNum());
            // start visualization, will add another class for handling all visualization once merged
            GanttChartController.setSearch(search);
            Application.launch(GanttChartSample.class, args);

        }

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

    }
}

