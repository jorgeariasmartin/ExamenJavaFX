package controladores;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Holocron;

import java.io.IOException;
import java.util.List;

import static modelos.Holocron.getAllHolocronsFromDatabase;

public class TablaController {
    @FXML
    private TableView<Holocron> tablaLibros;

    @FXML
    private TableColumn<Holocron, String> colAutor;

    @FXML
    private TableColumn<Holocron, String> colTitulo;

    @FXML
    private TableColumn<Holocron, Integer> colEdicion;

    @FXML
    private TableColumn<Holocron, String> colEditorial;

    @FXML
    private TableColumn<Holocron, String> colLugar;

    @FXML
    private TableColumn<Holocron, Integer> colStock;

    @FXML
    private TableColumn<Holocron, Double> colPVP;

    @FXML
    private TableColumn<Holocron, Integer> colAnoPublicacion;

    @FXML
    private TableColumn<Holocron, String> colISBN;

    @FXML
    private TextField textISBN;

    @FXML
    private TextField textNombre;

    private FilteredList<Holocron> filteredData;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador");

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colEdicion.setCellValueFactory(new PropertyValueFactory<>("numeroEdicion"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugarPublicacion"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPVP.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        colAnoPublicacion.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        List<Holocron> holocrons = getAllHolocronsFromDatabase();
        System.out.println("Holocrons obtenidos: " + holocrons.size());

        // Crear una lista filtrada a partir de la lista original
        filteredData = new FilteredList<>(javafx.collections.FXCollections.observableArrayList(holocrons), p -> true);

        // Añadir un listener a los campos de texto para filtrar la tabla
        textISBN.textProperty().addListener((observable, oldValue, newValue) -> filterTable());
        textNombre.textProperty().addListener((observable, oldValue, newValue) -> filterTable());

        // Envolver la lista filtrada en una lista ordenada
        SortedList<Holocron> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablaLibros.comparatorProperty());

        // Asignar la lista ordenada a la tabla
        tablaLibros.setItems(sortedData);
    }

    private void filterTable() {
        filteredData.setPredicate(holocron -> {
            // Si ambos campos están vacíos, mostrar todos los datos
            if ((textISBN.getText() == null || textISBN.getText().isEmpty()) &&
                    (textNombre.getText() == null || textNombre.getText().isEmpty())) {
                return true;
            }

            // Filtrar por ISBN
            boolean matchISBN = textISBN.getText() == null || textISBN.getText().isEmpty() ||
                    holocron.getISBN().toLowerCase().contains(textISBN.getText().toLowerCase());

            // Filtrar por Nombre (título o autor)
            boolean matchNombre = textNombre.getText() == null || textNombre.getText().isEmpty() ||
                    holocron.getTitulo().toLowerCase().contains(textNombre.getText().toLowerCase()) ||
                    holocron.getAutor().toLowerCase().contains(textNombre.getText().toLowerCase());

            // Devolver true si ambos criterios coinciden
            return matchISBN && matchNombre;
        });
    }

    @FXML
    private void abrirFormulario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/form-view.fxml"));
        Parent root = loader.load();

        FormController controller = loader.getController();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);

        stage.setScene(scene);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        stage.show();
    }

}
