package sdgcoilvic.controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.ImagesSetter;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.TablaProfesor;

public class GestionDeProfesoresControlador implements Initializable  {
    private static final Logger LOG = Logger.getLogger(GestionDeProfesoresControlador.class);
   
    ObservableList<TablaProfesor> lista = FXCollections.observableArrayList();
    @FXML private ImageView imageSubMenu;
    @FXML private Button button_Regresar;   
    @FXML private Button button_Buscar;
    @FXML private Button button_ModificarProfesor;
    @FXML private Button button_AgregarProfesorUV;
    @FXML private Button button_AgregarProfesorExterno;
    @FXML private TextField textField_BuscarProfesor;
    @FXML private ComboBox<String> comboBox_Estado;
    @FXML private ComboBox<String> comboBox_Institucion;
    @FXML private TableView<TablaProfesor> tableView_Profesores;
    @FXML private TableColumn<TablaProfesor, String> column_Nombre;
    @FXML private TableColumn<TablaProfesor, String> column_ApellidoPaterno;
    @FXML private TableColumn<TablaProfesor, String> column_ApellidoMaterno;
    @FXML private TableColumn<TablaProfesor, String> column_Correo; 
    @FXML private TableColumn<TablaProfesor, String> column_Universidad; 
    @FXML private TableColumn<TablaProfesor, String> column_Estado; 
    
    
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
       
    private void aplicarValidacion(TextField textField, String expresionRegular) {
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String nuevoTexto = cambio.getControlNewText();
            return (nuevoTexto.matches(expresionRegular) || nuevoTexto.isEmpty()) ? cambio : null;
        };

        textField.setTextFormatter(new TextFormatter<>(filtro));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aplicarValidacion(textField_BuscarProfesor, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,200}$");
        llenarTabla();
        llenarComboBoxInstitucion();
        llenarComboBoxEstadoProfesor();
        mostrarImagen();
        
        
    }
    
    @FXML
    private void button_Buscar(ActionEvent event) {
        String criterioBusqueda = textField_BuscarProfesor.getText();
        if (criterioBusqueda.isEmpty()) {
            Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO", "Por favor ingresa un criterio de búsqueda.");
            return;
        }
        buscarProfesorPorNombre(criterioBusqueda);
    }
    
    private void buscarProfesorPorNombre(String criterioBusqueda) {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        try {
            List<Profesor> profesoresEncontrados = profesorDAO.obtenerListaProfesoresPorNombre(criterioBusqueda);
            actualizarTablaConProfesoresEncontrados(profesoresEncontrados);
        } catch (SQLException ex) {
            LOG.error("Error al buscar instituciones:", ex);
            Alertas.mostrarMensajeErrorBaseDatos();
        }
    }
    
    private void actualizarTablaConProfesoresEncontrados(List<Profesor> profesoresEncontrados) {
        if (profesoresEncontrados.isEmpty()) {
            Alertas.mostrarMensajeSinResultados();
            return;
        }
        ObservableList<TablaProfesor> items = FXCollections.observableArrayList();
        for (Profesor profesor : profesoresEncontrados) {
            items.add(new TablaProfesor(
                    profesor.getIdProfesor(),
                    profesor.getNombre(),
                    profesor.getApellidoPaterno(),
                    profesor.getApellidoMaterno(),
                    profesor.getCorreo(),
                    profesor.getClaveInstitucional(),
                    profesor.getEstadoProfesor()));
        }
        tableView_Profesores.setItems(items);
    }

    
    @FXML
    private void comboEstadoSeleccionado(ActionEvent event) {
        String estadoSeleccionado = comboBox_Estado.getValue();
        if (estadoSeleccionado != null && !estadoSeleccionado.equals("Todos")) {
            ObservableList<TablaProfesor> items = FXCollections.observableArrayList();
            for (TablaProfesor estadoProfesor : lista) {
                if (estadoProfesor.getEstadoProfesor().equals(estadoSeleccionado)) {
                    items.add(estadoProfesor);
                }
            }
            tableView_Profesores.setItems(items);
        } else {
            tableView_Profesores.setItems(lista);
        }
    }   
    
    @FXML
    private void comboInstitucionSeleccionada(ActionEvent event) {
        String institucionSeleccionado = comboBox_Institucion.getValue();
        if (institucionSeleccionado != null && !institucionSeleccionado.equals("Todos")) {
            ObservableList<TablaProfesor> items = FXCollections.observableArrayList();
            for (TablaProfesor institucion : lista) {
                if (institucion.getClaveInstitucional().equals(institucionSeleccionado)) {
                    items.add(institucion);
                }
            }
            tableView_Profesores.setItems(items);
        } else {
            tableView_Profesores.setItems(lista);
        }
    }
    
    private void llenarComboBoxInstitucion() {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        try {
            List<String> listaDeInstituciones = profesorDAO.obtenerListaDeNombreInstitucion();
            listaDeInstituciones.add(0, "Todos");
            ObservableList<String> items = FXCollections.observableArrayList(listaDeInstituciones);
            comboBox_Institucion.setItems(items); 
        } catch (SQLException ex) {
             LOG.error(ex);
        }
    }
    
    private void llenarComboBoxEstadoProfesor() {
        ProfesorDAO profesorDAO = new ProfesorDAO();
           try {
               List<String> listaDeEstadoProfesor = profesorDAO.obtenerListaDeTodosLosEstadoProfesor();
               Set<String> conjuntoDeEstados = new HashSet<>(listaDeEstadoProfesor);
               listaDeEstadoProfesor = new ArrayList<>(conjuntoDeEstados);
               listaDeEstadoProfesor.add(0, "Todos");
               ObservableList<String> items = FXCollections.observableArrayList(listaDeEstadoProfesor);
               comboBox_Estado.setItems(items); 
           } catch (SQLException ex) {
               LOG.error(ex);
           }
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
        ProfesorDAO profesorDAO = new ProfesorDAO();
        if (!tableView_Profesores.getSelectionModel().isEmpty()) {
            try {
            int idProfesorSeleccionado  = tableView_Profesores.getSelectionModel().getSelectedItem().getIdProfesor();
            String correo = tableView_Profesores.getSelectionModel().getSelectedItem().getCorreo();
            String identificador = profesorDAO.identitificarProfesorUVOProfesorExterno(correo);
            Stage stage = (Stage) this.button_ModificarProfesor.getScene().getWindow();
            SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
            
                if (esNumero(identificador)) {
                    int idProfesor = Integer.parseInt(identificador);
                    if (idProfesor == idProfesorSeleccionado) {
                        ModificarProfesorExternoControlador.idProfesor = identificador;
                        sdgcoilvic.mostrarVentanaModificarProfesorExterno(stage, this::actualizarDatos);
                    } else {
                        ModificarProfesorUVControlador.noPersonal = identificador;
                        sdgcoilvic.mostrarVentanaModificarProfesorUV(stage, this::actualizarDatos);
                    }
                } else {
                        ModificarProfesorUVControlador.noPersonal = identificador;
                        sdgcoilvic.mostrarVentanaModificarProfesorUV(stage, this::actualizarDatos);
                }
            }catch (IOException ioexception ){
                LOG.error("Error al abrir la ventana de profesor: " + ioexception.getMessage());
                Alertas.mostrarMensajeErrorCambioPantalla();
            }
        } else {
            Alertas.mostrarMensajeProfesorNoSeleccionado();
        }
    }
    
    private boolean esNumero(String cadena) {
    for (char c : cadena.toCharArray()) {
        if (!Character.isDigit(c)) {
            return false;
        }
    }
    return true;
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
        llenarComboBoxEstadoProfesor();
        llenarTabla();
    }
    
    private void llenarTabla() {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> profesorLista = null;
        try {
            profesorLista = profesorDAO.obtenerListaTodosLosProfesores();
            if (profesorLista == null) {
                Alertas.mostrarMensajeNoHayInstitucionesRegistradas();
            } else {
                lista.clear();
                for (int i = 0; i < profesorLista.size(); i++) {
                    Profesor profesor = profesorLista.get(i);
                    lista.add(new TablaProfesor(
                            profesor.getIdProfesor(),
                            profesor.getNombre(), 
                            profesor.getApellidoPaterno(), 
                            profesor.getApellidoMaterno(),
                            profesor.getCorreo(),
                            profesor.getClaveInstitucional(),
                            profesor.getEstadoProfesor()));

                }
                tableView_Profesores.setItems(lista);
                column_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                column_ApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
                column_ApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
                column_Correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
                column_Universidad.setCellValueFactory(new PropertyValueFactory<>("claveInstitucional"));
                column_Estado.setCellValueFactory(new PropertyValueFactory<>("estadoProfesor"));
            }
        } catch (SQLException ex) {
            
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.error(ex);
        }
 
    }
}
