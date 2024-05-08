package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class GestionDeColaboracionesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(GestionDeColaboracionesControlador.class);
   
    @FXML
    private AnchorPane anchorPane_Principal;

    @FXML
    private ImageView imageView_SubMenu;

    @FXML
    private Pane pane_Contenedor;

    @FXML
    private TextField textField_BuscarColaboracion;

    @FXML
    private Label label_Nombre;

    @FXML
    private TableView tableView_Colaboraciones;

    @FXML
    private TableColumn tableColumn_Nombre;

    @FXML
    private TableColumn tableColumn_Tipo;

    @FXML
    private TableColumn tableColumn_Periodo;

    @FXML
    private TableColumn tableColumn_NumeroActividades;

    @FXML
    private TableColumn tableColumn_NumeroEvidencias;

    @FXML
    private Button button_Buscar;

    @FXML
    private Label label_FiltraBusqueda;

    @FXML
    private Button button_Regresar;

    @FXML
    private Button button_GenerarInforme;

    @FXML
    private Label label_UniversidadVeracruzana;

    @FXML
    private Button button_DarBajaColaboracion;

    @FXML
    private Button button_VisualizarAvances;

    @FXML
    private Button button_FiltroCerrado;

    @FXML
    private Button button_FiltroFinalizado;

    @FXML
    private Button button_FiltroEnCurso;

    @FXML
    private Button button_FiltroTodos;
    
    @FXML
    private Button button_VisualizarEvidencia;

    @FXML
    private Label label_SDGCOILVIC;

    @FXML
    private Label label_GestionColaboraciones;
    
    @FXML
    private ImageView imageSubMenu;

    // Métodos de manipulación de eventos
    @FXML
    private void button_Buscar() {
        // Lógica para manejar el evento del botón Buscar
    }

    @FXML
    private void button_VisualizarEvidencia() {
        Stage myStage = (Stage) button_VisualizarEvidencia.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaEvidenciasDeActividad(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    private void button_Regresar() {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrativoMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }

    @FXML
    private void button_GenerarInforme() {
        // Lógica para manejar el evento del botón Generar Informe
    }

    @FXML
    private void button_DarBajaColaboracion() {
        // Lógica para manejar el evento del botón Dar de baja la colaboración
    }

    @FXML
    private void button_VisualizarAvances() {

    }

    @FXML
    private void button_FiltroCerrado() {
        // Lógica para manejar el evento del botón Filtro Cerrado
    }

    @FXML
    private void button_FiltroFinalizado() {
        // Lógica para manejar el evento del botón Filtro Finalizado
    }

    @FXML
    private void button_FiltroEnCurso() {
        // Lógica para manejar el evento del botón Filtro En Curso
    }

    @FXML
    private void button_FiltroTodos() {
        // Lógica para manejar el evento del botón Filtro Todos
    }
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        mostrarImagen();
        
    }

}
