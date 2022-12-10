package com.example.project2;

import java.util.*;

public class Scheduler {
    

    private HashMap<Dag.Edge, NodeWeight> historyMap = new HashMap<>();


    private Queue<Dag.Node> nodePriorityQueue = new LinkedList<>();
    private HashMap<Dag.Node, NodeWeight> nodeHistoryMap = new HashMap<>();

    private PriorityQueue<Dag.Node> usagePriorityQueue = new PriorityQueue<>();


    public Scheduler(Dag dag, HashMap<Integer, Processor> processorList){
        //starts from root
        dag.getNodes().get(0).setProcessor(1);
        dag.getNodes().get(0).setStartTime(0);
        processorList.get(1).addTask(dag.getNodes().get(0));
        queue(dag,dag.getNodes().get(0));
    }

    //create priority queue by using c^2/w (c is much greater than w)
    public void queue(Dag dag, Dag.Node node){
        //using hashmap sorting to sort c2w with the corresponding node
        HashMap<Dag.Edge, NodeWeight> sort = new HashMap<>();
        HashMap<Dag.Node, NodeWeight> nodeSort = new HashMap<>();

        ArrayList<Dag.Edge> successors = node.getSuccessors();
        for (Dag.Edge edge : successors){
                
            edge = checkParent(dag, edge);


            Dag.Node thisNode = dag.getNodeByName(edge.getName());

            if (!checkAllParent(dag, thisNode)){
                break;
            }


            /**

             Below is the priority calculation

             **/

            //int totalPathWeight =  (thisNode.calPathWeight(dag) * 2)  - (edge.getEdgeWeight() / 2);
            //int totalPathWeight = thisNode.calPathWeight(dag);

            int totalPathWeight =  (int) Math.round(thisNode.calPathWeight(dag, edge) + edge.getEdgeWeight() / 10);



            //System.out.println("This edge is : "+ edge.toString() + " Node weight: "+ totalPathWeight);

            NodeWeight addc2w = new NodeWeight(totalPathWeight,edge);

            NodeWeight nodeWeight = new NodeWeight(totalPathWeight, thisNode);

            historyMap.put(addc2w.getEdge(), addc2w);
            nodeHistoryMap.put(thisNode, nodeWeight);

            sort.put(addc2w.getEdge(), addc2w);
            nodeSort.put(thisNode, nodeWeight);
        }

        sortingQueue(sort, nodeSort);

    }

    private Queue sortingQueue(HashMap sort, HashMap nodeSort){

        System.out.println(nodeSort);



        for (Dag.Node node : nodePriorityQueue) {
            nodeSort.put(node, nodeHistoryMap.get(node));
        }

        nodePriorityQueue.clear();



        ArrayList<NodeWeight> sortByValue = new ArrayList<>(sort.values());
        ArrayList<NodeWeight> sortNodeByValue = new ArrayList<>(nodeSort.values());
        Collections.sort(sortByValue,Collections.reverseOrder());
        Collections.sort(sortNodeByValue,Collections.reverseOrder());


        for (NodeWeight i : sortNodeByValue){
            nodePriorityQueue.add(i.getNode());
        }


        return nodePriorityQueue;
    }

    public boolean checkAllParent(Dag dag, Dag.Node node){
        for(Dag.Edge i: node.getParents()){
            if(dag.getNodeByName(i.getName()).getProcessor() == -1){
                System.out.println("All Parents Not scheduled");
                return false;
            }
        }
        return true;
    }


    public Dag.Edge checkParent(Dag dag, Dag.Edge edge){
        for(Dag.Edge i: dag.getNodeByName(edge.getName()).getParents()){
            if(dag.getNodeByName(i.getName()).getProcessor() == -1){
                checkParent(dag,i);
            }
        }
        return edge;
    }

    //helper class to implement hashmap sorting
    private class NodeWeight implements Comparable<NodeWeight>{
        private double nodeWeight;
        private Dag.Edge edge;
        private Dag.Node node;

        public NodeWeight(double nodeWeight, Dag.Edge Edge){
            this.nodeWeight = nodeWeight;
            this.edge = Edge;
        }

        public NodeWeight(double nodeWeight, Dag.Node node){
            this.nodeWeight = nodeWeight;
            this.node = node;
        }

        public Dag.Edge getEdge(){
            return this.edge;
        }

        public Dag.Node getNode() {
            return  this.node;
        }

        @Override
        public int compareTo(NodeWeight sort){
            if(this.nodeWeight < sort.nodeWeight){
                return -1;
            }else if(sort.nodeWeight < this.nodeWeight){
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Node weight: " + nodeWeight;
        }
    }


    public Queue<Dag.Node> getNodePriorityQueue(){
        return this.nodePriorityQueue;
    }



}


