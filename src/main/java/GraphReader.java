import algorithms.RadialBasedAlgorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by izabelawojciak on 20/10/2018.
 */
public class GraphReader {

    private Scanner scanner;

    public GraphReader(String filePath) {
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("File not found");
            alert.setContentText("Insert correct path to the file!");

            alert.showAndWait();
        }
    }

    public Graph readGraph() throws FileNotFoundException, UnsupportedEncodingException {

        Graph graph = new Graph(scanner.nextInt());

        while (scanner.hasNextInt()) {
            Vertex source;
            Vertex target;

            int sourceIndex = scanner.nextInt();
            int targetIndex = scanner.nextInt();

            if (graph.containsVertex(sourceIndex)) {
                source = graph.getVertex(sourceIndex);
            } else {
                source = new Vertex(sourceIndex);
                graph.addVertex(source);
            }

            if (graph.containsVertex(targetIndex)) {
                target = graph.getVertex(targetIndex);
            } else {
                target = new Vertex(targetIndex);
                graph.addVertex(target);
            }

            Edge edge = new Edge(source, target);

            source.addEdge(edge);
            target.addEdge(edge);

            graph.addEdge(edge);
        }

        return graph;
    }
}
