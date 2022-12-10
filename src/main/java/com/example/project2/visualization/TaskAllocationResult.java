package com.example.project2.visualization;

public class TaskAllocationResult {

    int id;
    int allocatedProcessor;
    String node;
    int nodeWeight;
    int startTime;

    public TaskAllocationResult(int id, int allocatedProcessor, String node, int nodeWeight, int startTime) {
        this.id = id;
        this.allocatedProcessor = allocatedProcessor;
        this.node = node;
        this.nodeWeight = nodeWeight;
        this.startTime = startTime;
    }
}
