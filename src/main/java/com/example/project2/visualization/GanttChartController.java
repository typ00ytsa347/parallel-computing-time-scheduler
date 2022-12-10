package com.example.project2.visualization;

import com.example.project2.Search;


//GanttChartController provides the data required for visualization

public class GanttChartController {

    private static Search search;

    public static Search getSearch() {
        return search;
    }

    public static void setSearch(Search search) {
        GanttChartController.search = search;
    }

}
