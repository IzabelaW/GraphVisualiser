package graph;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Graph {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private ArrayList<Edge> removedEdges;
    private Edge[][] incidenceMatrix;
    private Vertex centralVertex;
    private int depth;
    private Group canvas;
    private ScrollPane scrollPane;
    private int numberOfVertices;

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        removedEdges = new ArrayList<>();
        incidenceMatrix = new Edge[numberOfVertices][numberOfVertices];
        canvas = new Group();
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        scrollPane.setContent(canvas);
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

    public void addRemovedEdge(Edge edge) {
        if (!removedEdges.contains(edge) && !edges.contains(edge)){
            removedEdges.add(edge);
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

        if (vertices.size() != numberOfVertices) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input");
            alert.setContentText("This graph is disconnected!");
            alert.show();

            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.close();
        }
        else {

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

        double screenWidth = scrollPane.getViewportBounds().getWidth();
        double screenHeight = scrollPane.getViewportBounds().getHeight();
        double factor = Math.log(screenWidth * screenHeight);

        int maxDegree = getMaxDegree();
        int step = maxDegree / 7;
        double size;

        for (Edge edge : edges){
            edge.setView();
        }

        for (Edge edge : removedEdges){
            edge.setLighterView();
        }

        for (Vertex vertex : vertices){
            size = (factor * Math.sqrt(vertex.getDegree())) / Math.sqrt(vertices.size());
            vertex.setView(size, step);
        }

    }

    public Graph clone() {
        Graph clonedGraph = new Graph(vertices.size());

        for (Vertex vertex : vertices) {
            Vertex clonedVertex = new Vertex(vertex.getIndex());
            clonedVertex.setX(vertex.getX());
            clonedVertex.setY(vertex.getY());
            clonedGraph.addVertex(clonedVertex);
        }

        for (Edge edge : edges) {
            Vertex clonedSourceVertex = clonedGraph.getVertex(edge.getSource().getIndex());
            Vertex clonedTargetVertex = clonedGraph.getVertex(edge.getTarget().getIndex());

            Edge clonedEdge = new Edge(clonedSourceVertex, clonedTargetVertex);
            clonedGraph.addEdge(clonedEdge);
        }

        return  clonedGraph;
    }
}

