import graph.Edge;
import graph.Graph;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * Created by izabelawojciak on 16/10/2018.
 */
public class Main extends Application{

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));

        GraphGenerator graphGenerator = new GraphGenerator();
        Graph graph = graphGenerator.generatePreferentialAttachementGraph(1000, 5);

//        GraphReader graphReader = new GraphReader();
//        Graph graph = graphReader.readGraph();

        Group root = draw(graph);

        primaryStage.setTitle("Graph Visualiser");
        primaryStage.setScene(new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()));
        primaryStage.show();
    }


    private Group draw(Graph graph) {

        System.out.println("DRAW");

        Group group = new Group();

        for (Edge edge : graph.getEdges()) {
            Line line  = new Line();

            line.setStartX(edge.getSource().getX());
            line.setStartY(edge.getSource().getY());

            line.setEndX(edge.getTarget().getX());
            line.setEndY(edge.getTarget().getY());

            group.getChildren().add(line);
        }
        return group;
    }
}
