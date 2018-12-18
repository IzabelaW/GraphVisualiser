import algorithms.Algorithm;
import algorithms.ForcedDirectedAlgorithm;
import algorithms.RadialBasedAlgorithm;
import graph.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private boolean kamadaKawaii;

    public Main(boolean generateRandomGraph, boolean erdosRenyiModel, int numberOfVertices, double probability, int numberOfConnections, String filePath, int algorithmIndex, boolean kamadaKawaii) {
        this.generateRandomGraph = generateRandomGraph;
        this.erdosRenyiModel = erdosRenyiModel;
        this.numberOfVertices = numberOfVertices;
        this.probability = probability;
        this.numberOfConnections = numberOfConnections;
        this.filePath = filePath;
        this.algorithmIndex = algorithmIndex;
        this.kamadaKawaii = kamadaKawaii;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Graph initialGraph;

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
            case 1:
                algorithm = new ForcedDirectedAlgorithm(initialGraph, kamadaKawaii);
                break;
            default:
                algorithm = new RadialBasedAlgorithm(initialGraph);
                break;
        }

        FinalGraphContainer.getInstance().setFinalGraph(initialGraph);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow/MainWindow.fxml"));
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph Visualiser");
        primaryStage.show();

        algorithm.doLogic();
        algorithm.getFinalGraph().draw();
    }
}
