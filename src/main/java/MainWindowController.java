import graph.Graph;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by izabelawojciak on 19/11/2018.
 */
public class MainWindowController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem newMenuItem;

    @FXML
    private MenuItem aboutGraphVisualiserMenuItem;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label numberOfVerticesLabel;

    @FXML
    private Label numberOfEdgesLabel;

    @FXML
    private VBox graphInfoVbox;

    private File fileToSave;
    private Graph finalGraph;

    @FXML
    private void initialize() {
        finalGraph = FinalGraphContainer.getInstance().getFinalGfraph();
        finalGraph.setScrollPane(scrollPane);

        quitMenuItem.setOnAction((ActionEvent event) -> {
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();
        });

        newMenuItem.setOnAction((ActionEvent event) -> {
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();

                Stage mainStage = new Stage();
                StartDialog startDialog = new StartDialog();
                try {
                    startDialog.start(mainStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });

        saveAsMenuItem.setOnAction((ActionEvent event) -> {
            saveGraphToFile();
        });

        saveMenuItem.setOnAction((ActionEvent event) -> {
            if (fileToSave != null) {
                WritableImage img = finalGraph.getScrollPane().getContent().snapshot(new SnapshotParameters(), null);
                BufferedImage img2 = SwingFXUtils.fromFXImage(img, null);
                try {
                    ImageIO.write(img2, "png", fileToSave);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                saveGraphToFile();
            }
        } );

        aboutGraphVisualiserMenuItem.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About App");
            alert.setHeaderText("Graph Visualiser");
            alert.setContentText("Author: Izabela Wójciak\nWrocław University of Technology");

            alert.showAndWait();
        });

        numberOfVerticesLabel.setText(String.valueOf(finalGraph.getVertices().size()));
        numberOfEdgesLabel.setText(String.valueOf(finalGraph.getEdges().size()));
    }

    private void saveGraphToFile() {
        WritableImage img = finalGraph.getScrollPane().getContent().snapshot(new SnapshotParameters(), null);
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        BufferedImage img2 = SwingFXUtils.fromFXImage(img, null);
        File file = chooser.showSaveDialog(null);

        if (file != null) {
            try {
                fileToSave = file.getAbsoluteFile();
                ImageIO.write(img2, "png", fileToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
