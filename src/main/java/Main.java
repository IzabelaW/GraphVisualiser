import algorithms.Algorithm;
import algorithms.RadialBasedAlgorithm;
import graph.Graph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * Created by izabelawojciak on 16/10/2018.
 */
public class Main extends Application {

    private boolean generateRandomGraph;
    private boolean erdosRenyiModel;
    private int numberOfVertices;
    private double probability;
    private int numberOfConnections;
    private String filePath;
    private int algorithmIndex;

    public Main(boolean generateRandomGraph, boolean erdosRenyiModel, int numberOfVertices, double probability, int numberOfConnections, String filePath, int algorithmIndex) {
        this.generateRandomGraph = generateRandomGraph;
        this.erdosRenyiModel = erdosRenyiModel;
        this.numberOfVertices = numberOfVertices;
        this.probability = probability;
        this.numberOfConnections = numberOfConnections;
        this.filePath = filePath;
        this.algorithmIndex = algorithmIndex;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Graph initialGraph;
        Graph finalGraph;

        if (generateRandomGraph) {
            GraphGenerator graphGenerator = new GraphGenerator(numberOfVertices);

            if (erdosRenyiModel) {
                initialGraph = graphGenerator.generateGNP(probability);
            }
            else {
                initialGraph = graphGenerator.generatePreferentialAttachmentGraph(numberOfConnections);
            }
        }
        else {
            GraphReader graphReader = new GraphReader(filePath);
            initialGraph = graphReader.readGraph();
        }

        Algorithm algorithm;
        switch (algorithmIndex) {
            case 0:
                algorithm = new RadialBasedAlgorithm(initialGraph);
                break;
            default:
                algorithm = new RadialBasedAlgorithm(initialGraph);
                break;
        }

        BorderPane root = new BorderPane();


        finalGraph = algorithm.doLogic();
        finalGraph.draw();

        root.setCenter(finalGraph.getScrollPane());

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph Visualiser");
        primaryStage.show();
    }
}
