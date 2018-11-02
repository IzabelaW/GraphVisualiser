import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class GraphGenerator {

    private Random random;
    private Graph initialGraph;
    private int numberOfVertices;


    public GraphGenerator(int numberOfVertices){
        random = new Random();
        this.numberOfVertices = numberOfVertices;
        initialGraph = new Graph(numberOfVertices);
    }

    public Graph generateGNP(double probability) throws FileNotFoundException, UnsupportedEncodingException {

        System.out.println("GENERATE GRAPH");

        for (int i = 0; i < numberOfVertices; i++){
            for (int j = i + 1; j < numberOfVertices; j++){

                Vertex source;
                Vertex target;

                if(initialGraph.containsVertex(i)){
                    source = initialGraph.getVertex(i);
                }
                else {
                    source = new Vertex(i, random.nextDouble() * 1000, random.nextDouble() * 500);
                    initialGraph.addVertex(source);
                }

                if (initialGraph.containsVertex(j)){
                    target = initialGraph.getVertex(j);
                }
                else {
                    target = new Vertex(j, random.nextDouble() * 1000, random.nextDouble() * 500);
                    initialGraph.addVertex(target);
                }

                if (random.nextDouble() <= probability){
                    Edge edge = new Edge(source, target);

                    source.addEdge(edge);
                    target.addEdge(edge);

                    initialGraph.addEdge(edge);
                    initialGraph.addEdgeToIncidenceMatrix( source.getIndex(), target.getIndex() );
                }
            }
        }
        return initialGraph;
    }

    public Graph generatePreferentialAttachementGraph(int numberOfEdgesFromNewVertex) {

        ArrayList<Vertex> addedVertices = new ArrayList<>();
        ArrayList<Vertex> connected = new ArrayList<>();

        Vertex initialVertex = new Vertex(numberOfEdgesFromNewVertex, random.nextDouble() * 1000, random.nextDouble() * 500);

        for (int i = 0; i < numberOfEdgesFromNewVertex; i++){
            Vertex vertex = new Vertex(i, random.nextDouble() * 1000, random.nextDouble() * 500);
            initialGraph.addVertex(vertex);
            addedVertices.add(vertex);
            Edge edge = new Edge(initialVertex, vertex);
            initialGraph.addEdge(edge);
            initialGraph.addEdgeToIncidenceMatrix( initialVertex.getIndex(), vertex.getIndex() );
            vertex.addEdge(edge);
            initialVertex.addEdge(edge);
        }
        initialGraph.addVertex(initialVertex);
        addedVertices.add(initialVertex);

        for (int i = addedVertices.size(); i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfEdgesFromNewVertex; j++) {
                for (Vertex addedVertex : addedVertices) {
                    double probability = ((double) addedVertex.getDegree()) / ((double) initialGraph.getEdges().size() * 2);

                    if (random.nextDouble() <= probability) {
                        Vertex vertex = new Vertex(i, random.nextDouble() * 1000, random.nextDouble() * 500);
                        Edge edge = new Edge(vertex, addedVertex);

                        vertex.addEdgeWithoutIncreasingDegree(edge);
                        addedVertex.addEdgeWithoutIncreasingDegree(edge);

                        initialGraph.addEdge(edge);
                        initialGraph.addVertex(vertex);
                        initialGraph.addEdgeToIncidenceMatrix( vertex.getIndex(), addedVertex.getIndex() );

                        connected.add(addedVertex);
                        break;
                    }
                }
            }

            for(Vertex vertex : connected){
                vertex.setDegree(vertex.getEdges().size());
                addedVertices.add(vertex);
            }
        }

        for(Edge edge : initialGraph.getEdges()) {
            System.out.println(edge.getSource().getIndex() + " " + edge.getTarget().getIndex());
        }
        return initialGraph;
    }
}
