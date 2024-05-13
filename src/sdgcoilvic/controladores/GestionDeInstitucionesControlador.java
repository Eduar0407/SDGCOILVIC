
package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
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
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.Utilidades.ImagesSetter;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.InstitucionDAO;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;
import sdgcoilvic.logicaDeNegocio.clases.TablaInstitucion;

public class GestionDeInstitucionesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(GestionDeInstitucionesControlador.class);
   
    ObservableList<TablaInstitucion> lista = FXCollections.observableArrayList();
    @FXML
    private TableView<TablaInstitucion> tabla_Instituciones;
    @FXML
    private TableColumn<TablaInstitucion, String> column_Clave;
    @FXML
    private TableColumn<TablaInstitucion, String> column_NombreInstitucion;
    @FXML
    private TableColumn<TablaInstitucion, String> column_Pais;
    @FXML
    private TableColumn<TablaInstitucion, String> column_Correo;
    @FXML
    private ImageView imageSubMenu;   
    @FXML
    private Button button_Regresar;
    @FXML
    private Button button_RegistrarInstitucion;
    @FXML
    private Button button_Buscar;
    @FXML
    private TextField textField_BuscarInstitucion;
    @FXML
    private ComboBox<String> comboBox_Pais;;
    
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
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
    private void button_RegistrarInstitucion(ActionEvent event) {
        Stage stage = (Stage) button_RegistrarInstitucion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaRegistrarInstitucion(stage, this::actualizarDatos);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    

    
    @FXML
    void button_Buscar(ActionEvent event) {
        String criterioBusqueda = textField_BuscarInstitucion.getText();
        if (!criterioBusqueda.isEmpty()) {
            InstitucionDAO institucionDAO = new InstitucionDAO();
            try {
                List<Institucion> institucionesEncontradas = institucionDAO.obtenerListaInstitucionesPorNombre(criterioBusqueda);
                if (!institucionesEncontradas.isEmpty()) {
                    ObservableList<TablaInstitucion> items = FXCollections.observableArrayList();
                    for (Institucion institucion : institucionesEncontradas) {
                        items.add(new TablaInstitucion(institucion.getClaveInstitucional(), institucion.getNombreInstitucion(), institucion.getNombrePais(), institucion.getCorreo()));
                    }
                    tabla_Instituciones.setItems(items);
                } else {
                    Alertas.mostrarMensajeSinResultados();
                }
            } catch (SQLException ex) {
                LOG.error("Error al buscar instituciones: " + ex);
                Alertas.mostrarMensajeErrorBaseDatos();
            }
        } else {
            Alertas.mostrarMensaje(Alert.AlertType.INFORMATION,"AVISO", "Por favor ingresa un criterio de búsqueda.");
        }
    }
    
    @FXML      
    void comboPaisSeleccionado(ActionEvent event) {
        String paisSeleccionado = comboBox_Pais.getValue();
        if (paisSeleccionado != null && !paisSeleccionado.equals("Todos")) {
            ObservableList<TablaInstitucion> items = FXCollections.observableArrayList();
            for (TablaInstitucion institucion : lista) {
                if (institucion.getPais().equals(paisSeleccionado)) {
                    items.add(institucion);
                }
            }
            tabla_Instituciones.setItems(items);
        } else {
            tabla_Instituciones.setItems(lista);
        }
    }
    
    private void actualizarDatos() {
        llenarComboBox();
        llenarTabla();
    }
    private void llenarComboBox() {
        InstitucionDAO institucionDAO = new InstitucionDAO();
        try {
            List<String> listaDePaises = institucionDAO.obtenerListaDePaises();
            listaDePaises.add(0, "Todos");
            ObservableList<String> items = FXCollections.observableArrayList(listaDePaises);
            comboBox_Pais.setItems(items); 
        } catch (SQLException ex) {
             LOG.error(ex);
        }
    }
    
    private void llenarTabla() {
        InstitucionDAO institucionDAO = new InstitucionDAO();
        List<Institucion> institucionLista = null;

        try {
            institucionLista = institucionDAO.obtenerTodasLasInstituciones();
        } catch (SQLException ex) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.error(ex);
        }
        if (institucionLista == null) {
            Alertas.mostrarMensajeNoHayInstitucionesRegistradas();
        } else {
            lista.clear();
            for (int i = 0; i < institucionLista.size(); i++) {
                Institucion institucion = institucionLista.get(i);
                lista.add(new TablaInstitucion(institucion.getClaveInstitucional(), institucion.getNombreInstitucion(), institucion.getNombrePais(),institucion.getCorreo()));
            }
            tabla_Instituciones.setItems(lista);
            column_Clave.setCellValueFactory(new PropertyValueFactory<>("clave"));
            column_NombreInstitucion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            column_Pais.setCellValueFactory(new PropertyValueFactory<>("pais"));
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
        aplicarValidacion(textField_BuscarInstitucion, "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,200}$");
        mostrarImagen(); 
        llenarTabla();
        llenarComboBox();
        
        
    }
}
