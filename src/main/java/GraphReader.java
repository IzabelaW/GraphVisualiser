//import algorithms.RadialBasedAlgorithm;
//import graph.Edge;
//import graph.Graph;
//import graph.Vertex;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.UnsupportedEncodingException;
//import java.util.Random;
//import java.util.Scanner;
//
///**
// * Created by izabelawojciak on 20/10/2018.
// */
//public class GraphReader {
//
//    private Scanner scanner;
//
//    public GraphReader() {
//        try {
//            scanner = new Scanner(new File("facebook_combined.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Graph readGraph() throws FileNotFoundException, UnsupportedEncodingException {
//
//        Random random = new Random();
//
//        System.out.println("READ GRAPH");
//
////        Graph initialGraph = new Graph();
//
//        while(scanner.hasNextInt()){
//            Vertex source;
//            Vertex target;
//
//            int sourceIndex = scanner.nextInt();
//            int targetIndex = scanner.nextInt();
//
//            if(initialGraph.containsVertex(sourceIndex)){
//                source = initialGraph.getVertex(sourceIndex);
//            }
//            else {
//                source = new Vertex(sourceIndex, random.nextDouble() * 1000, random.nextDouble() * 500);
//                initialGraph.addVertex(source);
//            }
//
//            if (initialGraph.containsVertex(targetIndex)){
//                target = initialGraph.getVertex(targetIndex);
//            }
//            else {
//                target = new Vertex(targetIndex, random.nextDouble() * 1000, random.nextDouble() * 500);
//                initialGraph.addVertex(target);
//            }
//
//            Edge edge = new Edge(source, target);
//
//            source.addEdge(edge);
//            target.addEdge(edge);
//
//            initialGraph.addEdge(edge);
//        }
//
//        RadialBasedAlgorithm radialBasedAlgorithm = new RadialBasedAlgorithm(initialGraph);
//
//        System.out.println(initialGraph.getEdges().size());
//        return radialBasedAlgorithm.doLogic();
//    }
//}
