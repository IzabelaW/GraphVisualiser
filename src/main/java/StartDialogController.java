import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Objects;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class StartDialogController {

    private ObservableList<String> algorithmsList = FXCollections.observableArrayList("Radial based algorithm", "Forced directed algorithm");

    @FXML
    private ChoiceBox algorithmChoiceBox;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField numberOfVerticesERTextField;

    @FXML
    private TextField numberOfVerticesBATextfield;

    @FXML
    private TextField probabilityTextField;

    @FXML
    private TextField numberOfConnectionsTextField;

    @FXML
    private TextField filePathTextField;

    @FXML
    private RadioButton generateRandomGraphRadioButton;

    @FXML
    private RadioButton erdosRenyiModelRadioButton;

    @FXML
    private VBox erdosRenyiVBox;

    @FXML
    private GridPane erdosRenyiGridPane;

    @FXML
    private VBox barabasiAlbertVBox;

    @FXML
    private GridPane barabasiAlbertGridPane;

    @FXML
    private HBox readFromFileHBox;

//    @FXML
//    private HBox methodHBox;
//
//    @FXML
//    private RadioButton kamadaKawaiiRadioButton;
//
//    @FXML
//    private RadioButton fruchtermanReingoldRadioButton;

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void chooseFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            filePathTextField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void okButtonAction() throws Exception {
        boolean kamadaKawaii = false;
        boolean generateRandomGraph;
        boolean erdosRenyiModel = false;
        int numberOfVertices = 0;
        double probability = 0.0;
        int numberOfConnections = 0;
        String filePath = "";
        int algorithm;

        if (generateRandomGraphRadioButton.isSelected()){
            generateRandomGraph = true;
                if (erdosRenyiModelRadioButton.isSelected()){
                    erdosRenyiModel = true;
                    try {
                        numberOfVertices = Integer.parseInt(numberOfVerticesERTextField.getText());

                        if (numberOfVertices < 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Wrong input");
                            alert.setContentText("Number of vertices must be positive!");

                            alert.showAndWait();
                        }
                    }
                    catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong input");
                        alert.setContentText("Number of vertices must be a number!");

                        alert.showAndWait();
                    }
                    try {
                        probability = Double.parseDouble(probabilityTextField.getText());

                        if (probability < 0 || probability > 1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Wrong input");
                            alert.setContentText("Probability must be a value between 0 and 1!");

                            alert.showAndWait();
                        }
                        else if (probability == 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Wrong input");
                            alert.setContentText("Increase probability otherwise no graph will be generated!");

                            alert.showAndWait();
                        }
                    }
                    catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong input");
                        alert.setContentText("Probability must be a number!");

                        alert.showAndWait();
                    }
                }
                else {
                    erdosRenyiModel = false;
                    try {
                        numberOfVertices = Integer.parseInt(numberOfVerticesBATextfield.getText());

                        if (numberOfVertices < 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Wrong input");
                            alert.setContentText("Number of vertices must be positive!");

                            alert.showAndWait();
                        }
                    }
                    catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong input");
                        alert.setContentText("Number of vertices must be a number!");

                        alert.showAndWait();
                    }

                    try {
                        numberOfConnections = Integer.parseInt(numberOfConnectionsTextField.getText());

                        if (numberOfConnections < 0) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Wrong input");
                            alert.setContentText("Number of connections must be positive!");

                            alert.showAndWait();
                        }
                    }
                    catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong input");
                        alert.setContentText("Number of connections must be a number!");

                        alert.showAndWait();
                    }
                }
        }
        else {
            generateRandomGraph = false;
            filePath = filePathTextField.getText();

            if (Objects.equals(filePath, "")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong input");
                alert.setContentText("Empty file path!");

                alert.showAndWait();
            }
        }

        algorithm = algorithmChoiceBox.getSelectionModel().getSelectedIndex();


        if (!Objects.equals(numberOfVerticesERTextField.getText(), "") && !Objects.equals(probabilityTextField.getText(), "")
                || !Objects.equals(numberOfVerticesBATextfield.getText(), "") && !Objects.equals(numberOfConnectionsTextField.getText(), "")
                || !Objects.equals(filePathTextField.getText(), "")) {
            Stage mainStage = new Stage();
            Main main = new Main(generateRandomGraph, erdosRenyiModel, numberOfVertices, probability, numberOfConnections, filePath, algorithm, kamadaKawaii);
            main.start(mainStage);

            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void initialize() {
        algorithmChoiceBox.setItems(algorithmsList);
        algorithmChoiceBox.setValue("Radial based algorithm");

        generateRandomGraphRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected){
                    erdosRenyiVBox.setDisable(false);
                    barabasiAlbertVBox.setDisable(false);
                    readFromFileHBox.setDisable(true);
                }
                else {
                    erdosRenyiVBox.setDisable(true);
                    barabasiAlbertVBox.setDisable(true);
                    readFromFileHBox.setDisable(false);
                }
            }
        });

        erdosRenyiModelRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected){
                    barabasiAlbertGridPane.setDisable(true);
                    erdosRenyiGridPane.setDisable(false);
                }
                else {
                    erdosRenyiGridPane.setDisable(true);
                    barabasiAlbertGridPane.setDisable(false);
                }
            }
        });

//        algorithmChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (algorithmChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
//                    methodHBox.setDisable(true);
//                }
//                else {
//                    methodHBox.setDisable(false);
//                }
//            }
//        });

        generateRandomGraphRadioButton.setSelected(true);
        erdosRenyiModelRadioButton.setSelected(true);
//        fruchtermanReingoldRadioButton.setSelected(true);

//        if (algorithmChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
//            methodHBox.setDisable(true);
//        }
//        else {
//            methodHBox.setDisable(false);
//        }
    }


}
