package com.example.project2;

import java.util.HashMap;

public class Processor {

    private static int nextId = 1;
    private int id = 0;
    private int processTime = 0;
    private int lastFinishTime = 0;



    private static HashMap<Integer, Processor> processorHashMap = new HashMap<>();


    public Processor(){
        //System.out.println("Creating Processor: "+ nextId+ "\n");
        this.id = nextId;
        processorHashMap.put(id, this);
        nextId++;
    }


    public void addTask(Dag.Node node){
        this.lastFinishTime = processTime;
        this.processTime =  node.getStartTime() + node.getNodeWeight();
    }

    public void removeTask(){
        this.processTime = this.lastFinishTime;
    }

    public static Processor getProcessorById(int id){
        return processorHashMap.get(id);
    }

    public int getProcessTime(){
        return this.processTime;
    }

    public static HashMap<Integer, Processor> getProcessors(){
        return processorHashMap;
    }

    public static void reset(){
        nextId = 1;
    }

    @Override
    public String toString() {
        return "Processor "+this.id;
    }

}
