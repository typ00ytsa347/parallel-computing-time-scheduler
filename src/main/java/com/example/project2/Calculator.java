package com.example.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Calculator implements ICalculator{

    private HashMap<Integer, Processor> processorList;
    int processNum;
    Dag nodes;

    public Calculator(Dag dag,int processNum) {
        this.nodes = dag;
        for(int i = 0; i < processNum; i++){
            new Processor();
        }
        Processor.reset();
        this.processNum = processNum;
        this.processorList = Processor.getProcessors();
    }

    public int getStartTime(Dag.Node node, int processor){
        int earliestStartTime = processorList.get(processor).getProcessTime();
        
        ArrayList<Dag.Edge> parentList = node.getParents();

        for (Dag.Edge parentEdge: parentList) {
            //System.out.println("This edge is:" + parentEdge);

            //Get all parent nodes
            Dag.Node parentNode = nodes.getNodeByName(parentEdge.getName());


            //System.out.println("Parent finish at: " + (parentNode.getStartTime() + parentNode.getNodeWeight()));
            //System.out.println("Parent processor: " + parentNode.getProcessor());

            int parentFinishTime =  parentNode.getStartTime() + parentNode.getNodeWeight();
            int parentProcessor =  parentNode.getProcessor();

            System.out.println("Parent node " + parentNode + " finish at " + parentFinishTime + " at processor " + parentProcessor);

            int transferCost = parentEdge.getEdgeWeight();
            int parentCost;



            if (parentProcessor == processor) {
                //System.out.println(node.getNodeName()+processor);
                parentCost = parentFinishTime;
            }else {
                //System.out.println(node.getNodeName()+processor);
                parentCost = parentFinishTime + transferCost;
            }

            if ( earliestStartTime < parentCost) {
                earliestStartTime = parentCost;
            }
           
        }
        return earliestStartTime;
    }

    public int getEarliestStartTime(Dag.Node node, int processNum){

        int[][] fn = new int[processNum][3];
        int gn = 0;

        for (int i = 1; i <= processNum; i++){
            gn = this.getStartTime(node,i);
            fn[i-1][0] = i;
            fn[i-1][1] = gn;
            fn[i-1][2] = gn - Processor.getProcessorById(fn[i-1][0]).getProcessTime();
        }
        Arrays.sort(fn, (a, b) -> a[2] - b[2]);
        return fn[0][2];

    }




    public void addTask(Dag.Node node, int processor){
        //System.out.println(this.processorList.get(processor).getProcessTime());
        node.setStartTime(getStartTime(node, processor));
        node.setProcessor(processor);
        this.processorList.get(processor).addTask(node);
    }

    public void removeTask(Dag.Node node, int processor){
        //System.out.println(this.processorList.get(processor).getProcessTime());
        node.setStartTime(-1);
        node.setProcessor(-1);
        this.processorList.get(processor).removeTask();
    }




    public ArrayList<Integer> getParentProcessors(Dag.Node node){
        ArrayList<Integer> processors = new ArrayList<>();
        ArrayList<Dag.Edge> parentList = node.getParents();

        for (Dag.Edge parentEdge: parentList) {
            //System.out.println("This edge is:" + parentEdge);

            //Get all parent nodes
            Dag.Node parentNode = nodes.getNodeByName(parentEdge.getName());
            processors.add(parentNode.getProcessor());
        }
        return processors;
    }

    public HashMap<Integer, Processor> getProcessorList(){
        return this.processorList;
    }

    public int getFinishTimeFromList(){
        int finishTime = -1;

        System.out.println("================Process Result==================");

        for (Object processorNum: this.processorList.keySet()) {
            int processTime = Processor.getProcessorById( (Integer) processorNum).getProcessTime();
            System.out.println("Processor "+ processorNum + " finish at : " + processTime);

            if (processTime > finishTime ){
                finishTime = processTime;
            }
        }

        System.out.println("FINAL FINISH TIME: " + finishTime);

        System.out.println("================================================");

        return finishTime;
    }


}
