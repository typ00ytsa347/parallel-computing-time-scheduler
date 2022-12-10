package com.example.project2;

import java.util.ArrayList;
import java.util.HashMap;


public interface ICalculator {

    void addTask(Dag.Node node,int processor);

    void removeTask(Dag.Node node, int processor);

    HashMap<Integer, Processor> getProcessorList();

    int getStartTime(Dag.Node node, int processor);

    int getEarliestStartTime(Dag.Node node, int processNum);

    ArrayList<Integer> getParentProcessors(Dag.Node node);

    int getFinishTimeFromList();
}
