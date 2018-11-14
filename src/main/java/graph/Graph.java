package graph;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Graph {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private Edge[][] incidenceMatrix;
    private Vertex centralVertex;
    private int depth;
    private Group canvas;
    private ScrollPane scrollPane;

    public Graph(int numberOfVertices) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        incidenceMatrix = new Edge[numberOfVertices][numberOfVertices];
        canvas = new Group();
        scrollPane = new ScrollPane(canvas);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
            incidenceMatrix[edge.getSource().getIndex()][edge.getTarget().getIndex()] = edge;
            incidenceMatrix[edge.getTarget().getIndex()][edge.getSource().getIndex()] = edge;
            canvas.getChildren().add(edge);
        }
    }

    public void addVertex(Vertex vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            canvas.getChildren().add(vertex);
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean containsVertex(int vertexIndex) {
        return getVertex(vertexIndex) != null;

    }

    public Vertex getVertex(int vertexIndex) {
        for (Vertex vertex : vertices) {
            if (vertex.getIndex() == vertexIndex)
                return vertex;
        }
        return null;
    }

    public void findCentralVertex() {

        int maxLevel = 0;
        LinkedList<Vertex> queue = new LinkedList<>();
        int degree[] = new int[vertices.size()];
        int level[] = new int[vertices.size()];

        for (Vertex vertex : vertices) {
            degree[vertex.getIndex()] = vertex.getDegree();
            level[vertex.getIndex()] = 0;
        }

        for (Vertex vertex : vertices) {
            if (degree[vertex.getIndex()] == 1) {
                queue.add(vertex);
                level[vertex.getIndex()] = 0;
            }
        }

        while (queue.size() != 0) {
            Vertex vertex = queue.poll();

            for (Vertex adjacentVertex : vertices) {
                if (incidenceMatrix[vertex.getIndex()][adjacentVertex.getIndex()] != null) {
                    degree[adjacentVertex.getIndex()]--;

                    if (degree[adjacentVertex.getIndex()] == 1) {
                        queue.addLast(adjacentVertex);
                        level[adjacentVertex.getIndex()] = level[vertex.getIndex()] + 1;

                        if (level[adjacentVertex.getIndex()] > maxLevel) {
                            maxLevel = level[adjacentVertex.getIndex()];
                            this.centralVertex = adjacentVertex;
                        }
                    }
                }
            }
        }
    }

    public Edge getEdgeFromIncidenceMatrix(int source, int target) {
        return incidenceMatrix[source][target];
    }

    public Vertex getCentralVertex() {
        return centralVertex;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    private int getMaxDegree() {
        int maxDegree = 0;

        for (Vertex vertex : vertices){
            if (vertex.getDegree() > maxDegree)
                maxDegree = vertex.getDegree();
        }

        return maxDegree;
    }

    public void draw() {

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double factor = Math.min(screenWidth, screenHeight) / depth;

        int maxDegree = getMaxDegree();
        int step = maxDegree / 7;
        double size;

        for (Vertex vertex : vertices){
            size = factor * (vertex.getDegree() / (2.0 * edges.size()));
            vertex.setView(size, step);
        }

        for (Edge edge : edges){
            edge.setView();
        }

    }
//
//    public double getScale() {
//        return this.scrollPane.getScaleValue();
//    }
}

