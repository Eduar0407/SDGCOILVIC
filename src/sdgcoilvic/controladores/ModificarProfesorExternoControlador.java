package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public class ModificarProfesorExternoControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ModificarProfesorExternoControlador.class);
    public static int idProfesor;
    
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;
    @FXML
    private Button button_Guardar;
    @FXML
    private ComboBox<String> comboBox_Idioma;
    @FXML
    private ComboBox<String> comboBox_Institucion;
    @FXML
    private Label label_Idioma;
    @FXML
    private Label label_Institucion;
    @FXML
    private Label label_Nombre;
    @FXML
    private Label label_ApellidoPaterno;
    @FXML
    private Label label_ApellidoMaterno;
    @FXML
    private Label label_Correo;
    @FXML
    private TextField textField_Nombre;
    @FXML
    private TextField textField_ApellidoPaterno;
    @FXML
    private TextField textField_ApellidoMaterno;
    @FXML
    private TextField textField_Correo;

    @FXML
    void button_Cancelar(ActionEvent event) {
        if (Alertas.mostrarMensajeCancelar()) {
            Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
            myStage.close();
        }
    }

    @FXML
    void button_Guardar(ActionEvent event) {
        // Lógica para guardar los cambios del profesor externo
    }
    
    private void llenar_combo_Idioma() {
        try {
            ProfesorDAO profesorDAO = new ProfesorDAO();
            List<List<String>> listaDeIdiomas = profesorDAO.obtenerListaDeIdiomas();
            List<String> listaNombresIdiomas = new ArrayList<>();
            listaDeIdiomas.forEach(idioma -> listaNombresIdiomas.add(idioma.get(1)));
            ObservableList<String> items = FXCollections.observableArrayList(listaNombresIdiomas);
            comboBox_Idioma.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    private void llenar_combo_Institucion() {
        try {
            ProfesorDAO profesorDAO = new ProfesorDAO();
            List<List<String>> listaDeInstituciones = profesorDAO.obtenerListaDeInstituciones();
            List<String> listaNombresInstituciones = new ArrayList<>();
            for (List<String> institucion : listaDeInstituciones) {
                listaNombresInstituciones.add(institucion.get(1));
            }
            ObservableList<String> items = FXCollections.observableArrayList(listaNombresInstituciones);
            comboBox_Institucion.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }
    
    private void informacionProfesor() {
        Profesor profesor = new Profesor();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        try {
            profesor = profesorDAO.obtenerProfesor(idProfesor);
        } catch (SQLException sQLExcpetion) {
            Alertas.mostrarMensajeErrorBaseDatos();
        }
            int indiceInstitucionSeleccionada = comboBox_Institucion.getSelectionModel().getSelectedIndex();
            if (indiceInstitucionSeleccionada >= 0) {
                try {
                    List<List<String>> listaDeInstituciones = profesorDAO.obtenerListaDeInstituciones();
                    if (indiceInstitucionSeleccionada < listaDeInstituciones.size()) {
                        String claveInstitucion = listaDeInstituciones.get(indiceInstitucionSeleccionada).get(1);
                        profesor.setClaveInstitucional(claveInstitucion);
                    }
                } catch (SQLException ex) {
                    LOG.error(ex);
                }
            }

            int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
            if (indiceIdiomaSeleccionado >= 0) {
                try {
                    List<List<String>> listaDeIdiomas = profesorDAO.obtenerListaDeIdiomas();
                    if (indiceIdiomaSeleccionado < listaDeIdiomas.size()) {
                        int idIdioma = Integer.parseInt(listaDeIdiomas.get(indiceIdiomaSeleccionado).get(0));
                        profesor.setIdIdiomas(idIdioma);
                    }
                } catch (SQLException | NumberFormatException ex) {
                    LOG.error(ex);
                }
            }

            textField_Nombre.setText(profesor.getNombre());
            textField_ApellidoPaterno.setText(profesor.getApellidoPaterno());
            textField_ApellidoMaterno.setText(profesor.getApellidoMaterno());
            textField_Correo.setText(profesor.getCorreo());
            comboBox_Institucion.getSelectionModel().select(profesor.getClaveInstitucional());
            comboBox_Idioma.getSelectionModel().select(profesor.getIdIdiomas());

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            llenar_combo_Idioma();
    llenar_combo_Institucion();
    informacionProfesor(); 
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
