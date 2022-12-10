package com.example.project2;

import java.util.ArrayList;

public class Dag {

    String name;
    ArrayList<Node> nodes = new ArrayList<>();

    public String getDagName(){
        return name;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getNodeByName(String name) {
        for (Node node: getNodes()) {
            if (node.getNodeName().equals(name)){
                return node;
            }
        }
        return null;
    }

    public static class Node{
        String nodeName;
        int nodeWeight;
        int pathWeight;
        ArrayList<Edge> parents = new ArrayList<>();
        ArrayList<Edge> successors = new ArrayList<>();
        private int startTime;
        private int processor;

        public Node(){
            startTime = -1;
            processor = -1;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public int getNodeWeight() {
            return nodeWeight;
        }

        public double calPathWeight(Dag nodes, Dag.Edge edge) {
            double totalWeight = (Math.pow(edge.getEdgeWeight(),2)/nodeWeight);

            ArrayList<Dag.Edge> successors = this.getSuccessors();
            for (Edge thisEdge : successors) {
                Node thisNode = nodes.getNodeByName(thisEdge.name);
                totalWeight = totalWeight + thisNode.calPathWeight(nodes,thisEdge);
            }

            return totalWeight;
        }

        public int getPathWeight() {
            return pathWeight;
        }

        public void setPathWeight(int pathWeight) {
            this.pathWeight = pathWeight;
        }

        public void setNodeWeight(int nodeWeight) {
            this.nodeWeight = nodeWeight;
        }

        public ArrayList<Edge> getParents() {
            return parents;
        }

        public ArrayList<Edge> getSuccessors() {
            return successors;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getProcessor() {
            return processor;
        }

        public Processor getProcessorObj() {
            return Processor.getProcessorById(processor);
        }

        public void setProcessor(int processor) {
            this.processor = processor;
        }


        @Override
        public boolean equals(Object obj) {
            Node node = (Node) obj;
            return this.getNodeName().equals(node.getNodeName());
        }

        @Override
        public String toString() {
            return this.nodeName;
        }
    }

    public static class Edge{
        String source;
        String name;
        int edgeWeight;



        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getEdgeWeight() {
            return edgeWeight;
        }

        public void setEdgeWeight(int edgeWeight) {
            this.edgeWeight = edgeWeight;
        }

        @Override
        public String toString() {
            return this.source + " -> " + this.name;
        }
    }

}