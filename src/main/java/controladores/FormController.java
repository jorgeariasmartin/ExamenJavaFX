package controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelos.Holocron;
import enumerados.Pasillo;

public class FormController {

    @FXML
    private TextField autorTextField;

    @FXML
    private TextField tituloTextField;

    @FXML
    private TextField numeroEdicionTextField;

    @FXML
    private TextField editorialTextField;

    @FXML
    private TextField lugarPublicacionTextField;

    @FXML
    private TextField numeroPaginasTextField;

    @FXML
    private TextField stockTextField;

    @FXML
    private TextField pvpTextField;

    @FXML
    private TextField anoPublicacionDatePicker;

    @FXML
    private TextField isbnTextField;

    @FXML
    private TextField anoEdicionDatePicker;

    @FXML
    private ComboBox<Pasillo> pasilloComboBox;

    @FXML
    private Button guardarButton;

    @FXML
    public void initialize() {
        pasilloComboBox.getItems().setAll(Pasillo.values());

        guardarButton.setOnAction(event -> guardarHolocron());
    }

    private void guardarHolocron() {
        if (!todosLosCamposLlenos()) {
            mostrarAlerta("Error", "Por favor, llene todos los campos del formulario.");
            return;
        }

        String autor = autorTextField.getText();
        String titulo = tituloTextField.getText();
        int numeroEdicion = Integer.parseInt(numeroEdicionTextField.getText());
        String editorial = editorialTextField.getText();
        String lugarPublicacion = lugarPublicacionTextField.getText();
        int numeroPaginas = Integer.parseInt(numeroPaginasTextField.getText());
        int stock = Integer.parseInt(stockTextField.getText());
        double pvp = Double.parseDouble(pvpTextField.getText());
        int anioPublicacion = Integer.parseInt(anoPublicacionDatePicker.getText());
        String isbn = isbnTextField.getText();
        int anioEdicion = Integer.parseInt(anoEdicionDatePicker.getText());
        Pasillo pasillo = pasilloComboBox.getValue();

        Holocron holocron = new Holocron(0, autor, titulo, numeroEdicion, editorial, lugarPublicacion, numeroPaginas, stock, pvp, anioPublicacion, isbn, anioEdicion, pasillo);
        if (holocron.saveToDatabase()) {
            mostrarAlerta("Éxito", "Holocron insertado exitosamente en la base de datos.");
        }
    }


    private boolean todosLosCamposLlenos() {
        return !autorTextField.getText().isEmpty() &&
                !tituloTextField.getText().isEmpty() &&
                !numeroEdicionTextField.getText().isEmpty() &&
                !editorialTextField.getText().isEmpty() &&
                !lugarPublicacionTextField.getText().isEmpty() &&
                !numeroPaginasTextField.getText().isEmpty() &&
                !stockTextField.getText().isEmpty() &&
                !pvpTextField.getText().isEmpty() &&
                !anoPublicacionDatePicker.getText().isEmpty() &&
                !isbnTextField.getText().isEmpty() &&
                !anoEdicionDatePicker.getText().isEmpty() &&
                pasilloComboBox.getValue() != null;
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
