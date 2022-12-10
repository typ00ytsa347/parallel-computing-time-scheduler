package com.example.project2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;



public class DagIO {

    //Required parameter
    private static String fileName;
    private static int processNum;

    //Optional parameter
    private static int multiCoreNum = 1;
    private static boolean isVisualize = false;
    private static String outputFileName = "output";

    private static boolean isValidInput = false;
    private static ArrayList<String> nodesName = new ArrayList<>();

    /**
    Parse Input argument into different option variable and


    **/

    public static Dag parseInputArgs(String[] args){
        Dag dag = new Dag();
        try {

            if (args[0].endsWith(".dot")) {
                fileName = args[0];


                createDagFromFile(dag, fileName);
            } else {
                System.err.println("Invalid FileName");
                return null;
            }


            processNum = Integer.valueOf(args[1]);
            if (processNum <= 0) {
                System.err.println("Invalid Process Number");
                return null;
            }

            for (int index = 0; index < args.length; index++){
                int nextIndex = index + 1;

                switch (args[index].toLowerCase()) {
                    case "-p":
                        if ((args[nextIndex].equals("-v") || args[nextIndex].equals("-o") )) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        System.out.println("Use " + args[nextIndex] + "Cores for processing");

                        multiCoreNum = Integer.valueOf(args[nextIndex]);
                        if (multiCoreNum <= 0) {
                            System.err.println("Invalid Multi-core processing Number");
                            return null;
                        }

                        break;
                    case "-v":
                        System.out.println("Visualization On");

                        isVisualize = true;

                        break;
                    case "-o":
                        if ((args[nextIndex].equals("-p") || args[nextIndex].equals("-v") )) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        System.out.println("Output name changed to " + args[nextIndex]);

                        outputFileName = args[nextIndex];

                        break;
                }
            }

            isValidInput = true;
            return dag;

        }catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid Option parameter");
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid processor number");
        }
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: [FileName] [ProcessorNumber]");
        }
        return null;
    }
    
    private static void createDagFromFile(Dag dag, String fileName){

        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String currLine = br.readLine();

            //get name of the digraph
            dag.name = currLine.substring(8,currLine.length()-2); //hardcoding, better if tokenized.

            currLine = br.readLine();
            //dot file ends with }
            while(!currLine.contains("}")){

                //inbetween space removed
                currLine = currLine.replaceAll("\\s","");
                //separate nodes and weight
                StringTokenizer st1 = new StringTokenizer(currLine,"[Weight=];");
                //
                String nodes = st1.nextToken();

                if(nodes.contains("->")){ //if edge

                    StringTokenizer st2 = new StringTokenizer(nodes, "->");
                    //setting parenting edge and succeeding edge
                    String parentNode = st2.nextToken();
                    String successorNode = st2.nextToken();
                    int edgeWeight = Integer.valueOf(st1.nextToken());

                    Dag.Edge parent = new Dag.Edge();
                    parent.setName(parentNode);
                    parent.setEdgeWeight(edgeWeight);
                    dag.getNodes().get(nodesName.indexOf(successorNode)).getParents().add(parent);

                    Dag.Edge successor = new Dag.Edge();
                    successor.setSource(parentNode);
                    successor.setName(successorNode);
                    successor.setEdgeWeight(edgeWeight);
                    dag.getNodes().get(nodesName.indexOf(parentNode)).getSuccessors().add(successor);


                }else{ //if node

                    //allocating node in dag
                    Dag.Node node = new Dag.Node();
                    node.setNodeName(nodes);
                    node.setNodeWeight(Integer.valueOf(st1.nextToken()));
                    dag.addNode(node);

                    //allocating node's name to the same index of nodes in dag
                    nodesName.add(nodes);
                }
                currLine = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.err.println("Failed to find " + fileName);
        }
    }





    public static void outputToFile(Dag dag) {
        try {
            FileOutputStream out = createOutputFile(outputFileName);

            if(out == null) {
                System.out.println("File creation failed");
            }
            writeStringToOutput(out, "digraph "+ dag.getDagName() +" {");
            out.write('\n');

            // loop through all nodes
            ArrayList<Dag.Node> nodes = dag.getNodes();
            for (Dag.Node node : nodes) {
                writeStringToOutput(out, "    " + node.getNodeName() + "       ");
                writeStringToOutput(out, "[Weight=" + node.getNodeWeight());
                writeStringToOutput(out, ",Start=" + node.getStartTime());
                writeStringToOutput(out, ",Processor=" + node.getProcessor() + "];");
                out.write('\n');

                ArrayList<Dag.Edge> parents = node.getParents();
                for(Dag.Edge edge : parents) {
                    writeStringToOutput(out, "    " + dag.getNodeByName(edge.getName()) + " -> ");
                    writeStringToOutput(out, node.getNodeName());
                    writeStringToOutput(out, "  [Weight=" + edge.getEdgeWeight() + "];");
                    out.write('\n');
                }
            }

            writeStringToOutput(out, "}");
            out.flush();
        } catch(IOException e) {
            System.out.println("Output Dag failed");
        }
    }

    private static FileOutputStream createOutputFile(String fileName) {
        try {
            File file = new File(fileName + ".dot");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            return out;
        } catch(IOException e) {
            System.out.println("File creation failed");
        }
        return null;
    }

    private static void writeStringToOutput(FileOutputStream out, String str) {
        try {
            out.write(str.getBytes());
        } catch(IOException e) {
            System.out.println("Line output failed");
        }
    }


    public static boolean isFileInputLoaded(){
        if (nodesName.size() != 0 ){
            return true;
        }
        return false;
    }

    public static int getProcessNum(){
        return processNum;
    }
    public static boolean getVaildibility(){
        return isValidInput;
    }


}
