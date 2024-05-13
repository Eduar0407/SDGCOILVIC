package sdgcoilvic.controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.Utilidades.ImagesSetter;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.TablaProfesor;

public class GestionDeProfesoresControlador implements Initializable  {
    private static final Logger LOG = Logger.getLogger(GestionDeProfesoresControlador.class);
   
    ObservableList<TablaProfesor> lista = FXCollections.observableArrayList();
    @FXML
    private ImageView imageSubMenu;
    @FXML
    private Button button_Regresar;   
    @FXML
    private Button button_Buscar;
    @FXML
    private Button button_ModificarProfesor;
    @FXML
    private Button button_AgregarProfesorUV;
    @FXML
    private Button button_AgregarProfesorExterno;
    @FXML
    private TextField textField_BuscarProfesor;
    @FXML
    private ComboBox<?> comboBox_Idioma;
    @FXML
    private ComboBox<?> comboBox_Pais;
    @FXML
    private ComboBox<?> comboBox_Institucion;
    @FXML
    private TableView<TablaProfesor> tableView_Profesores;
    @FXML
    private TableColumn<TablaProfesor, String> column_Identificador;
    @FXML
    private TableColumn<TablaProfesor, String> column_Nombre;
    @FXML
    private TableColumn<TablaProfesor, String> column_ApellidoPaterno;
    @FXML
    private TableColumn<TablaProfesor, String> column_ApellidoMaterno;
    @FXML
    private TableColumn<TablaProfesor, String> column_Correo;    
    @FXML
    
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @FXML
    private void button_Buscar(ActionEvent event) {

    }
    
    @FXML
    private void comboIdiomaSeleccionado(ActionEvent event) {
        // Manejar la acción del ComboBox aquí
    }
    
    @FXML
    private void comboPaisSeleccionado(ActionEvent event) {
        // Manejar la acción del ComboBox aquí
    }
    
    @FXML
    private void comboInstitucionSeleccionada(ActionEvent event) {
        // Manejar la acción del ComboBox aquí
    }
    
    @FXML
    private void button_Regresar(ActionEvent event) {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrativoMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    private void button_ModificarProfesor(ActionEvent event) {
        if (!tableView_Profesores.getSelectionModel().isEmpty()) {
            int identificator = tableView_Profesores.getSelectionModel().getSelectedItem().getIdProfesor();
            ModificarProfesorExternoControlador.idProfesor = identificator;
            Stage stage = (Stage) this.button_ModificarProfesor.getScene().getWindow();
            SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
            try {
                sdgcoilvic.mostrarVentanaModificarProfesorExterno(stage);
            } catch (IOException iOException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.error(iOException);
            }
        } else {
            Alertas.mostrarMensajeProfesorNoSeleccionado();
        }
    }
    
    @FXML
    private void button_AgregarProfesorUV(ActionEvent event) {
        Stage stage = (Stage) button_AgregarProfesorUV.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAgregarProfesorUV(stage, this::actualizarDatos);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    @FXML
    private void button_AgregarProfesorExterno(ActionEvent event) {
        Stage stage = (Stage) button_AgregarProfesorExterno.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAgregarProfesorExterno(stage, this::actualizarDatos);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    private void actualizarDatos() {
        llenarTabla();
    }
    
    private void llenarTabla() {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> profesorLista = null;

        try {
            profesorLista = profesorDAO.obtenerListaTodosLosProfesores();
        } catch (SQLException ex) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.error(ex);
        }
        if (profesorLista == null) {
            Alertas.mostrarMensajeNoHayInstitucionesRegistradas();
        } else {
            lista.clear();
            for (int i = 0; i < profesorLista.size(); i++) {
                Profesor profesor = profesorLista.get(i);
                lista.add(new TablaProfesor(profesor.getIdProfesor(),
                        profesor.getNombre(), 
                        profesor.getApellidoPaterno(), 
                        profesor.getApellidoMaterno(),
                        profesor.getCorreo()));
            }
            tableView_Profesores.setItems(lista);
            column_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            column_ApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
            column_ApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
            column_Correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
       
        }
    }
    
    private void aplicarValidacion(TextField textField, String regex) {
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String nuevoTexto = cambio.getControlNewText();
            if (nuevoTexto.matches(regex) || nuevoTexto.isEmpty()) {
                return cambio;
            }
            return null;
        };
        TextFormatter<String> formateadorTexto = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateadorTexto);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aplicarValidacion(textField_BuscarProfesor, "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,200}$");
        llenarTabla();
        mostrarImagen();
        
    }
}
