package com.example.project2;

import com.example.project2.visualization.TaskAllocationResult;

import java.util.*;

public class Search {

    ICalculator calculator;
    Scheduler scheduler;
    HashMap<Integer, Processor> processorList;

    Queue<Dag.Node> nodeQueue;

    int processNum;
    Dag dag;
    // variables for visualization
    int id = 0;
    int startTime;
    int allocatedProcessor;
    ArrayList<TaskAllocationResult> results = new ArrayList<TaskAllocationResult>();

    public Search(Dag dag, int processNum){
        this.dag = dag;
        this.processNum = processNum;
        this.calculator = new Calculator(dag, processNum);
        this.processorList = calculator.getProcessorList();
        this.scheduler = new Scheduler(dag, processorList);
        this.nodeQueue = scheduler.getNodePriorityQueue();
        aStar();
    }

    public ICalculator getCalculator() {
        return this.calculator;
    }

    public ArrayList<TaskAllocationResult> getResults() {
        return results;
    }

    public void aStar(){

        ArrayList<Dag.Node> nodes = new ArrayList<>();
        ArrayList<Integer> parentProcessors;

        while(!nodeQueue.isEmpty()) {

            System.out.println(nodeQueue);


            this.calculator.getFinishTimeFromList();

            PriorityQueue<IdleWeight> idleQueue = new PriorityQueue();

            int gn = 0;
            int[][] fn = new int[0][];
            for (Dag.Node node : nodeQueue) {
                System.out.println("This node is :" + node);


                /**
                 int[processorNum][thisProcessor]
                 int[processorNum][starting time]
                 int[processorNum][idle time]
                 **/

                fn = new int[processNum][3];
                gn = 0;
                int childIdle = -1;

                for (int i = 1; i <= processNum; i++) {

                    System.out.println("On processor " + i);
                    gn = this.calculator.getStartTime(node, i);
                    fn[i - 1][0] = i;
                    fn[i - 1][1] = gn;


//                    calculator.addTask(node, i);
//
//                    for (Dag.Edge childEdge : node.getSuccessors()) {
//                        childIdle = this.calculator.getEarliestStartTime(dag.getNodeByName(childEdge.name), processNum);
//                        System.out.println("Child Idle: "+ childIdle);
//                    }
//                    calculator.removeTask(node, i);
//
//                    fn[i-1][2] = gn - Processor.getProcessorById(fn[i-1][0]).getProcessTime() + childIdle;

                    System.out.println("Start at " + gn);
                    startTime = gn;

                }
                Arrays.sort(fn, (a, b) -> a[1] - b[1]);

                int idleTime = fn[0][1] - Processor.getProcessorById(fn[0][0]).getProcessTime();
                System.out.println("IdleTime: " + idleTime);

                idleQueue.add(new IdleWeight(node, idleTime, fn[0][0]));

                allocatedProcessor = fn[0][0];

            }


            System.out.println(idleQueue);


            int process = idleQueue.peek().getProcessorNum();
            Dag.Node node = idleQueue.poll().getNode();

            idleQueue.clear();
            nodeQueue.remove(node);

            this.calculator.getFinishTimeFromList();

            System.out.println("***************************************************place " + node + "at " + process);

            TaskAllocationResult result = new TaskAllocationResult(id, fn[0][0], node.getNodeName(), node.getNodeWeight(), gn);
            results.add(result);
            id++;

            calculator.addTask(node, process);
            this.calculator.getFinishTimeFromList();

            if (!node.getSuccessors().isEmpty()) {
                scheduler.queue(dag, node);
                aStar();
            }
        }
    }

    public class IdleWeight implements Comparable<IdleWeight>{
        Dag.Node node;
        Integer idlePer;
        Integer processorNum;

        public IdleWeight(Dag.Node node, Integer idlePer, Integer processorNum){
            this.node = node;
            this.idlePer = idlePer;
            this.processorNum = processorNum;
        }


        public Integer getProcessorNum() {
            return processorNum;
        }

        @Override
        public int compareTo(IdleWeight otherNode) {
            return this.getIdlePer().compareTo(otherNode.getIdlePer());
        }

        @Override
        public String toString() {
            return this.node.getNodeName();
        }

        public Dag.Node getNode() {
            return node;
        }

        public Integer getIdlePer() {
            return idlePer;
        }
    }

}

