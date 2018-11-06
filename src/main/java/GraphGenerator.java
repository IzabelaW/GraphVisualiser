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
        this.random = new Random();
        this.numberOfVertices = numberOfVertices;
        this.initialGraph = new Graph(numberOfVertices);
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
                }
            }
        }
        return initialGraph;
    }

    public Graph generatePreferentialAttachmentGraph(int numberOfEdgesFromNewVertex) {

        ArrayList<Vertex> addedVertices = new ArrayList<>();
        ArrayList<Vertex> connected;

        Vertex initialVertex = new Vertex(numberOfEdgesFromNewVertex, random.nextDouble() * 1000, random.nextDouble() * 500);
        initialGraph.addVertex(initialVertex);
        addedVertices.add(initialVertex);

        for (int i = 0; i < numberOfEdgesFromNewVertex; i++){
            Vertex vertex = new Vertex(i, random.nextDouble() * 1000, random.nextDouble() * 500);
            initialGraph.addVertex(vertex);
            addedVertices.add(vertex);
            Edge edge = new Edge(initialVertex, vertex);
            initialGraph.addEdge(edge);
            vertex.addEdge(edge);
            initialVertex.addEdge(edge);
        }

        for (Vertex vertex : addedVertices){
            vertex.setProbability(((double) vertex.getDegree()) / ((double) initialGraph.getEdges().size() * 2));
        }


        for (int i = numberOfEdgesFromNewVertex + 1; i < numberOfVertices; i++) {
            Vertex vertex = new Vertex(i, random.nextDouble() * 1000, random.nextDouble() * 500);
            initialGraph.addVertex(vertex);
            int connections = 0;
            connected = new ArrayList<>();
            while (connections < numberOfEdgesFromNewVertex) {
                for (Vertex addedVertex : addedVertices) {
                    double probability = addedVertex.getProbability();

                    if (random.nextDouble() <= probability) {
                        Edge edge = new Edge(vertex, addedVertex);

                        vertex.addEdge(edge);
                        addedVertex.addEdge(edge);
                        initialGraph.addEdge(edge);

                        connected.add(addedVertex);
                        connections++;
                        break;
                    }
                }
            }

            for(Vertex connectedVertex : connected){
                addedVertices.add(connectedVertex);
            }

            for (Vertex addedVertex : addedVertices){
                vertex.setProbability(((double) addedVertex.getDegree()) / ((double) initialGraph.getEdges().size() * 2));
            }
        }

        for(Edge edge : initialGraph.getEdges()) {
            System.out.println(edge.getSource().getIndex() + " " + edge.getTarget().getIndex());
        }
        return initialGraph;
    }
}
