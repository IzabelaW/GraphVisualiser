package graph;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Graph {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge))
            edges.add(edge);
    }

    public void addVertex(Vertex vertex) {
        if (!vertices.contains(vertex))
            vertices.add(vertex);
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

    public Vertex findCentralVertex() {

        System.out.println("FIND CENTRAL VERTEX");

        int maxDegree = 0;
        Vertex centralVertex = null;

        for (Vertex vertex : vertices) {
            if (vertex.getDegree() > maxDegree) {
                maxDegree = vertex.getDegree();
                centralVertex = vertex;
            }
        }

        return centralVertex;
    }
}
