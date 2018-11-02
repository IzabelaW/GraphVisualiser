package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class RadialBasedAlgorithm {

    private Graph graph;
    private Graph tree;
    private ArrayList<Edge> removedEdges;

    public RadialBasedAlgorithm(Graph graph) {
        this.graph = graph;
        removedEdges = new ArrayList<>();
    }

    public Graph doLogic() {
        Vertex centralVertex = graph.findCentralVertex();
        tree = BFS(centralVertex);
        removeEdgesFromSpanningTree();
        countLeafsForAllVertices();

        System.out.println("ALGO");

        double screenWidth = Screen.getPrimary().getBounds().getWidth() - 200;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() - 200;
        double R = Math.min(screenWidth, screenHeight) / 10;
        double arc;
        double parentArc;
        double lastAngle = 0;
        double lastArc = 0;

        centralVertex.setX(screenWidth / 2);
        centralVertex.setY(screenHeight / 2);
        centralVertex.setAngle(Math.PI / 2);
        centralVertex.setEccentricity(0);

        for (int i = 1; i < graph.getVertices().size(); i++) {

            Vertex vertex = tree.getVertex(i);
            Vertex parentVertex = vertex.getParent();

            arc = (vertex.getNumberOfLeafs() * 2 * Math.PI) / centralVertex.getNumberOfLeafs();
            parentArc = (parentVertex.getNumberOfLeafs() * 2 * Math.PI) / centralVertex.getNumberOfLeafs();

            if (!parentVertex.equals((tree.getVertex(i - 1)).getParent())) {
                vertex.setAngle(parentVertex.getAngle() - parentArc / 2 + arc / 2);
            } else {
                vertex.setAngle(lastAngle + lastArc / 2 + arc / 2);
            }

            tree.getVertex(i).setX(centralVertex.getX() + R * vertex.getLevel() * Math.cos(vertex.getAngle()));
            tree.getVertex(i).setY(centralVertex.getY() + R * vertex.getLevel() * Math.sin(vertex.getAngle()));

            lastAngle = vertex.getAngle();
            lastArc = arc;
        }


        addRemovedEdgesToSpanningTree();

        return tree;
    }

    private Graph BFS(Vertex centralVertex) {

        System.out.println("BFS");

        int numberOfVertices = graph.getVertices().size();
        boolean marked[] = new boolean[numberOfVertices];
        LinkedList<Vertex> queue = new LinkedList<>();
        Graph tree = new Graph();

        marked[centralVertex.getIndex()] = true;
        queue.add(centralVertex);
        centralVertex.setLevel(0);

        int index = 0;
        int level = 1;
        while (queue.size() != 0) {
            Vertex currentVertex = queue.poll();
            currentVertex.setIndex(index);
            tree.addVertex(currentVertex);

            for (Edge edge : currentVertex.getEdges()) {
                Vertex adjacentVertex;

                if (currentVertex.equals(edge.getSource())) {
                    adjacentVertex = edge.getTarget();
                } else {
                    adjacentVertex = edge.getSource();
                }

                if (!marked[adjacentVertex.getIndex()]) {
                    marked[adjacentVertex.getIndex()] = true;
                    adjacentVertex.setParent(currentVertex);
                    currentVertex.addChild(adjacentVertex);
                    queue.add(adjacentVertex);
                    adjacentVertex.setLevel(level);
                    tree.addEdge(edge);
                }
            }
            level++;
            index++;
        }

        return tree;
    }

    private void countPaths(Vertex root) {
        int numberOfVertices = graph.getVertices().size();
        int marked[] = new int[numberOfVertices];

        for (int i = 0; i  < numberOfVertices; i++){
            marked[i] = -1;
        }

        marked[root.getIndex()] = 0;


    }

    private void countLeafsForAllVertices() {

        System.out.println("COUNT LEAFS");
        for (Vertex vertex : tree.getVertices()) {
            int numberOfLeafs = vertex.countLeafs();
            vertex.setNumberOfLeafs(numberOfLeafs);
        }
    }

    private void removeEdgesFromSpanningTree() {

        System.out.println("REMOVE EDGES");
        ArrayList<Edge> edges;
        for (Vertex vertex : tree.getVertices()) {
            edges = new ArrayList<>(vertex.getEdges());
            for (Edge edge : edges) {
                if (!tree.getEdges().contains(edge) && !removedEdges.contains(edge)) {
                    vertex.removeEdge(edge);
                    removedEdges.add(edge);
                }
            }
        }
    }

    private void addRemovedEdgesToSpanningTree() {

        System.out.println("ADD EDGES");
        for (Edge edge : removedEdges) {
            tree.addEdge(edge);
        }
        System.out.println(removedEdges.size());

    }

}