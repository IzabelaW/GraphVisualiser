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
public class RadialBasedAlgorithm implements Algorithm {

    private Graph graph;
    private Graph tree;
    private ArrayList<Edge> removedEdges;
    private LinkedList<Vertex> radialQueue;

    public RadialBasedAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public Graph doLogic() {
        BFS(graph.getVertex(0));
        removeEdgesFromSpanningTree();
        tree.findCentralVertex();
        Vertex centralVertex = tree.getCentralVertex();
        int depth = tree.getDepth();

        addRemovedEdgesToSpanningTree();
        BFS(centralVertex);
        removeEdgesFromSpanningTree();
        countLeafsForAllVertices();

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double R = 0.95 * (Math.min(screenWidth, screenHeight) / (2.0 * (double) depth));
        double arc;
        double parentArc;
        double lastAngle = 0;
        double lastArc = 0;

        centralVertex.setX(screenWidth / 2.0);
        centralVertex.setY(screenHeight / 2.0);
        centralVertex.setAngle(Math.PI / 2.0);

        Vertex previousVertex = radialQueue.poll();
        for (int i = 1; i < tree.getVertices().size(); i++) {

            Vertex vertex = radialQueue.poll();
            Vertex parentVertex = vertex.getVertexParent();

            arc = ((double) vertex.getNumberOfLeafs() * 2.0 * Math.PI) / ((double) centralVertex.getNumberOfLeafs());
            parentArc = ((double) parentVertex.getNumberOfLeafs() * 2.0 * Math.PI) / ((double) centralVertex.getNumberOfLeafs());

            if (!parentVertex.equals(previousVertex.getVertexParent())) {
                vertex.setAngle(parentVertex.getAngle() - (parentArc / 2.0) + (arc / 2.0));
            } else {
                vertex.setAngle(lastAngle + (lastArc / 2.0) + (arc / 2.0));
            }

            vertex.setX(centralVertex.getX() + (R * vertex.getLevel() * Math.cos(vertex.getAngle())));
            vertex.setY(centralVertex.getY() + (R * vertex.getLevel() * Math.sin(vertex.getAngle())));

            lastAngle = vertex.getAngle();
            lastArc = arc;
            previousVertex = vertex;
        }


//        addRemovedEdgesToSpanningTree();

        for (Edge edge : tree.getEdges()){
            edge.setView();
        }

        for (Vertex vertex : tree.getVertices()){
            vertex.setView(tree.getEdges().size());
        }

        return tree;
    }

    private void BFS(Vertex centralVertex) {

        int numberOfVertices = graph.getVertices().size();
        boolean marked[] = new boolean[numberOfVertices];
        LinkedList<Vertex> queue = new LinkedList<>();
        tree = new Graph(numberOfVertices);
        removedEdges = new ArrayList<>();
        radialQueue = new LinkedList<>();

        marked[centralVertex.getIndex()] = true;
        queue.add(centralVertex);
        centralVertex.setLevel(0);

        int level = 1;
        while (queue.size() != 0) {
            Vertex currentVertex = queue.poll();

            radialQueue.add(currentVertex);
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
                    queue.addLast(adjacentVertex);
                    level = adjacentVertex.getVertexParent().getLevel() + 1;
                    adjacentVertex.setLevel(level);
                    tree.addEdge(edge);
                }
            }
        }

        tree.setDepth(level);
    }

    private void countLeafsForAllVertices() {
        for (Vertex vertex : tree.getVertices()) {
            int numberOfLeafs = countLeafs(vertex);
            vertex.setNumberOfLeafs(numberOfLeafs);
        }
    }

    private int countLeafs(Vertex rootVertex) {

        int numberOfLeafs = 0;
        ArrayList<Vertex> adjacentVertices = new ArrayList<>();

        for (Vertex vertex : tree.getVertices()) {
            if (tree.getEdgeFromIncidenceMatrix(rootVertex.getIndex(), vertex.getIndex()) != null) {
                adjacentVertices.add(vertex);
            }
        }


        if (adjacentVertices.size() == 1 && !adjacentVertices.get(0).equals(tree.getCentralVertex()) && !rootVertex.equals(adjacentVertices.get(0).getVertexParent())) {
            return 1;
        } else {
            for (Vertex adjacentVertex : adjacentVertices) {
                if (rootVertex.getVertexParent() == null || !rootVertex.getVertexParent().equals(adjacentVertex))
                    numberOfLeafs += countLeafs(adjacentVertex);
            }
            return numberOfLeafs;
        }
    }

    private void removeEdgesFromSpanningTree() {

        for (Edge edge : graph.getEdges()) {
            if (!tree.getEdges().contains(edge) && !removedEdges.contains(edge)) {
                edge.getSource().removeEdge(edge);
                edge.getTarget().removeEdge(edge);
                removedEdges.add(edge);
            }
        }
    }

    private void addRemovedEdgesToSpanningTree() {

        for (Edge edge : removedEdges) {
            tree.addEdge(edge);
        }

    }
}
