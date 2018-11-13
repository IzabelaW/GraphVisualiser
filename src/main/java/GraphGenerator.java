import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class GraphGenerator {

    private Random random;
    private Graph initialGraph;
    private int numberOfVertices;


    public GraphGenerator(int numberOfVertices) {
        this.random = new Random();
        this.numberOfVertices = numberOfVertices;
        this.initialGraph = new Graph(numberOfVertices);
    }

    public Graph generateGNP(double probability) throws FileNotFoundException, UnsupportedEncodingException {

        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = i + 1; j < numberOfVertices; j++) {

                Vertex source;
                Vertex target;

                if (initialGraph.containsVertex(i)) {
                    source = initialGraph.getVertex(i);
                } else {
                    source = new Vertex(i);
                    initialGraph.addVertex(source);
                }

                if (initialGraph.containsVertex(j)) {
                    target = initialGraph.getVertex(j);
                } else {
                    target = new Vertex(j);
                    initialGraph.addVertex(target);
                }

                if (random.nextDouble() <= probability) {
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

        for (int i = 0; i <= numberOfEdgesFromNewVertex; i++) {
            for (int j = i + 1; j <= numberOfEdgesFromNewVertex; j++) {
                Vertex source;
                Vertex target;

                if (initialGraph.containsVertex(i)) {
                    source = initialGraph.getVertex(i);
                } else {
                    source = new Vertex(i);
                    initialGraph.addVertex(source);
                    addedVertices.add(source);
                }

                if (initialGraph.containsVertex(j)) {
                    target = initialGraph.getVertex(j);
                } else {
                    target = new Vertex(j);
                    initialGraph.addVertex(target);
                    addedVertices.add(target);
                }

                Edge edge = new Edge(source, target);
                initialGraph.addEdge(edge);

                source.addEdge(edge);
                target.addEdge(edge);
            }
        }

        for (Vertex vertex : addedVertices) {
            vertex.setProbability(((double) vertex.getDegree()) / ((double) initialGraph.getEdges().size() * 2.0));
        }


        for (int i = numberOfEdgesFromNewVertex + 1; i < numberOfVertices; i++) {
            Vertex vertex = new Vertex(i);
            initialGraph.addVertex(vertex);

            int connections = 0;
            while (connections < numberOfEdgesFromNewVertex) {

                    int index = random.nextInt(addedVertices.size());
                    Vertex addedVertex = initialGraph.getVertex(index);
                    double probability = addedVertex.getProbability();

                    if (random.nextDouble() <= probability) {
                        Edge edge = new Edge(vertex, addedVertex);

                        vertex.addEdge(edge);
                        addedVertex.addEdge(edge);
                        initialGraph.addEdge(edge);

                        connections++;
                    }
            }
            addedVertices.add(vertex);

            for (Vertex addedVertex : addedVertices) {
                vertex.setProbability(((double) addedVertex.getDegree()) / ((double) initialGraph.getEdges().size() * 2.0));
            }
        }

        return initialGraph;
    }
}
