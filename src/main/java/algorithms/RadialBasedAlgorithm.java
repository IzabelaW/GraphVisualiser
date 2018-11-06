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
    private LinkedList<Vertex> radialQueue;

    public RadialBasedAlgorithm(Graph graph) {
        this.graph = graph;
        removedEdges = new ArrayList<>();
        radialQueue = new LinkedList<>();
    }

    public Graph doLogic() {
        BFS(graph.getVertex(0));
        removeEdgesFromSpanningTree();
        tree.findCentralVertex();
        Vertex centralVertex = graph.getCentralVertex();
        BFS(centralVertex);
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

        Vertex previousVertex = radialQueue.poll();
        System.out.println("QUEUE: " + radialQueue.size());
        System.out.println("VERTICES: " + graph.getVertices().size());
        for (int i = 1; i < graph.getVertices().size(); i++) {

            Vertex vertex = radialQueue.poll();
            Vertex parentVertex = vertex.getParent();

            arc = (vertex.getNumberOfLeafs() * 2 * Math.PI) / centralVertex.getNumberOfLeafs();
            parentArc = (parentVertex.getNumberOfLeafs() * 2 * Math.PI) / centralVertex.getNumberOfLeafs();

            if (!parentVertex.equals(previousVertex.getParent())) {
                vertex.setAngle(parentVertex.getAngle() - parentArc / 2 + arc / 2);
            } else {
                vertex.setAngle(lastAngle + lastArc / 2 + arc / 2);
            }

            vertex.setX(centralVertex.getX() + R * vertex.getLevel() * Math.cos(vertex.getAngle()));
            vertex.setY(centralVertex.getY() + R * vertex.getLevel() * Math.sin(vertex.getAngle()));

            lastAngle = vertex.getAngle();
            lastArc = arc;
            previousVertex = vertex;
        }


        addRemovedEdgesToSpanningTree();

        return tree;
    }

    private void BFS(Vertex centralVertex) {

        System.out.println("BFS");

        int numberOfVertices = graph.getVertices().size();
        boolean marked[] = new boolean[numberOfVertices];
        LinkedList<Vertex> queue = new LinkedList<>();
        tree = new Graph(numberOfVertices);

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
                    queue.add(adjacentVertex);
                    adjacentVertex.setLevel(level);
                    tree.addEdge(edge);
                }
            }
            level++;
        }
    }

    private void countLeafsForAllVertices() {
        for (Vertex vertex : tree.getVertices()){
            int numberOfLeafs = countLeafs( vertex );
            vertex.setNumberOfLeafs( numberOfLeafs );
        }
    }

    private int countLeafs(Vertex rootVertex){

        int numberOfLeafs = 0;
        ArrayList<Vertex> adjacentVertices = new ArrayList<>();

        for (Vertex vertex : tree.getVertices()) {
            if (tree.getEdgeFromIncidenceMatrix( rootVertex.getIndex(), vertex.getIndex() ) != null){
                adjacentVertices.add( vertex );
            }
        }


        if (adjacentVertices.size() == 1 && !adjacentVertices.get(0).equals(tree.getCentralVertex()) && !rootVertex.equals(adjacentVertices.get(0).getParent())){
            return 1;
        }
        else
        {
            for( Vertex adjacentVertex : adjacentVertices ){
                if (rootVertex.getParent() == null || !rootVertex.getParent().equals(adjacentVertex))
                    numberOfLeafs += countLeafs( adjacentVertex );
            }
            return numberOfLeafs;
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
