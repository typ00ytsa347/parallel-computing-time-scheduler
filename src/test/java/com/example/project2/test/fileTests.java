package com.example.project2.test;


import com.example.project2.*;

import org.junit.Ignore;

import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class fileTests {

    @Test
    public void manual_test(){
        String args[] = {"./TestInput/Nodes_8_Random.dot", "2", "-o", "./TestOutput/manual_test.dot"};


        Dag dag = DagIO.parseInputArgs(args);


        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }


        ICalculator manualCal = new Calculator(dag, 2);
        HashMap<Integer, Processor> processorList = manualCal.getProcessorList();
        Scheduler manualScheduler = new Scheduler(dag, processorList);

        manualCal.addTask(dag.getNodeByName("0"), 1);
        manualCal.addTask(dag.getNodeByName("2"), 2);
        manualCal.addTask(dag.getNodeByName("1"), 1);
        manualCal.addTask(dag.getNodeByName("4"), 2);
        manualCal.addTask(dag.getNodeByName("3"), 1);
        manualCal.addTask(dag.getNodeByName("5"), 2);
        manualCal.addTask(dag.getNodeByName("6"), 1);
        manualCal.addTask(dag.getNodeByName("7"), 1);


        getFinishTimeFromList(processorList);

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }
    }



    @Test
    public void manual_test(){
        String args[] = {"./TestInput/Nodes_8_Random.dot", "2", "-o", "./TestOutput/manual_test.dot"};


        Dag dag = DagIO.parseInputArgs(args);


        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }


        ICalculator manualCal = new Calculator(dag, 2);
        HashMap<Integer, Processor> processorList = manualCal.getProcessorList();
        Scheduler manualScheduler = new Scheduler(dag, processorList);

        manualCal.addTask(dag.getNodeByName("1"), 2);
        manualCal.addTask(dag.getNodeByName("2"), 1);
        manualCal.addTask(dag.getNodeByName("3"), 2);
        manualCal.addTask(dag.getNodeByName("4"), 1);
        manualCal.addTask(dag.getNodeByName("5"), 2);
        manualCal.addTask(dag.getNodeByName("6"), 1);
        manualCal.addTask(dag.getNodeByName("7"), 2);


        getFinishTimeFromList(processorList);

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }
    }

    @Test
    public void test_Node_7_OutTree_TwoProcessor(){

        String args[] = {"./TestInput/Nodes_7_OutTree.dot", "2", "-o", "./TestOutput/output_Nodes_7_OutTree.dot"};


        Dag dag = DagIO.parseInputArgs(args);



        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(28, getFinishTime(search));

    }



    @Test
    public void test_Nodes_8_Random_TwoProcessor(){

        String args[] = {"./TestInput/Nodes_8_Random.dot", "2", "-o", "./TestOutput/output_Nodes_8_Random.dot"};


        Dag dag = DagIO.parseInputArgs(args);


        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(581, getFinishTime(search));

    }

    @Test
    public void test_Node_9_OutTree_TwoProcessor(){

        String args[] = {"./TestInput/Nodes_9_SeriesParallel.dot", "2", "-o", "./TestOutput/output_Nodes_9_SeriesParallel.dot"};


        Dag dag = DagIO.parseInputArgs(args);



        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(55, getFinishTime(search));

    }


    @Test
    public void test_Node_11_OutTree_TwoProcessor(){

        String args[] = {"./TestInput/Nodes_11_OutTree.dot", "2", "-o", "./TestOutput/output_Nodes_11_OutTree.dot"};


        Dag dag = DagIO.parseInputArgs(args);



        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(350, getFinishTime(search));

    }

    @Ignore
    @Test
    public void test_Node_8_OutTree_FourProcessor(){

        String args[] = {"./TestInput/Nodes_8_Random.dot", "4", "-o", "./TestOutput/output_Nodes_8_Random.dot"};


        Dag dag = DagIO.parseInputArgs(args);



        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(350, getFinishTime(search));

    }


    @Ignore
    @Test
    public void test_Node_11_OutTree_FourProcessor(){

        String args[] = {"./TestInput/Nodes_11_OutTree.dot", "4", "-o", "./TestOutput/output_Nodes_11_OutTree.dot"};


        Dag dag = DagIO.parseInputArgs(args);



        if (!DagIO.getVaildibility()) {
            System.err.println("Dag not valid");
        }

        Search search = new Search(dag, DagIO.getProcessNum());

        if (DagIO.isFileInputLoaded()) {
            DagIO.outputToFile(dag);
        }

        assertEquals(227, getFinishTime(search));

    }



    private int getFinishTime(Search search){
        HashMap processorList = search.getCalculator().getProcessorList();
        int finishTime = -1;

        System.out.println("================Process Result==================");

        for (Object processorNum: processorList.keySet()) {
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

    private int getFinishTimeFromList(HashMap processorList){
        int finishTime = -1;

        System.out.println("================Process Result==================");

        for (Object processorNum: processorList.keySet()) {
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


            if (processTime > finishTime ){
                finishTime = processTime;
            }
        }

        System.out.println("FINAL FINISH TIME: " + finishTime);

        System.out.println("================================================");

        return finishTime;
    }

}
