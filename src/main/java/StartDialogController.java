import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class StartDialogController {

    private ObservableList<String> algorithmsList = FXCollections.observableArrayList("Radial based algorithm");

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
    private RadioButton barabasiAlbertModelRadioButton;

    @FXML
    private RadioButton readGraphFromFileRadioButton;

    @FXML
    private VBox erdosRenyiVBox;

    @FXML
    private GridPane erdosRenyiGridPane;

    @FXML
    private VBox barabasiAlbertVBox;

    @FXML

    private GridPane barabasiAlbertGridPane;

    @FXML
    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void okButtonAction() throws Exception {
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
                    numberOfVertices = Integer.parseInt(numberOfVerticesERTextField.getText());
                    probability = Double.parseDouble(probabilityTextField.getText());
                }
                else {
                    erdosRenyiModel = false;
                    numberOfVertices = Integer.parseInt(numberOfVerticesBATextfield.getText());
                    numberOfConnections = Integer.parseInt(numberOfConnectionsTextField.getText());
                }
        }
        else {
            generateRandomGraph = false;
            filePath = filePathTextField.getText();
        }

        algorithm = algorithmChoiceBox.getSelectionModel().getSelectedIndex();

        Stage mainStage = new Stage();
        Main main = new Main(generateRandomGraph, erdosRenyiModel, numberOfVertices, probability, numberOfConnections, filePath, algorithm);
        main.start(mainStage);

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
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
                    filePathTextField.setDisable(true);
                }
                else {
                    erdosRenyiVBox.setDisable(true);
                    barabasiAlbertVBox.setDisable(true);
                    filePathTextField.setDisable(false);
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

        generateRandomGraphRadioButton.setSelected(true);
        erdosRenyiModelRadioButton.setSelected(true);
    }


}
