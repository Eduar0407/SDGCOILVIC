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
import sdgcoilvic.utilidades.ImagesSetter;

public class AdministrarActividadesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AdministrarActividadesControlador.class);
   
     @FXML
    private AnchorPane anchorPane_Principal;
    @FXML
    private Pane pane_Contenedor;
    @FXML
    private TextField textField_BuscarProfesor;
    @FXML
    private Label label_Nombre;
    @FXML
    private TableView<?> tableView_Actividades;
    @FXML
    private TableColumn<?, ?> column_Nombre;
    @FXML
    private TableColumn<?, ?> column_Instrucción;
    @FXML
    private TableColumn<?, ?> column_Puntaje;
    @FXML
    private TableColumn<?, ?> column_Colaboración;
    @FXML
    private TableColumn<?, ?> column_Tema;
    @FXML
    private TableColumn<?, ?> column_Herramienta;
    @FXML
    private TableColumn<?, ?> column_Evidencia;
    @FXML
    private TableColumn<?, ?> column_Inicia;
    @FXML
    private TableColumn<?, ?> column_Termina;
    @FXML
    private TableColumn<?, ?> column_Modificar;
    @FXML
    private Button button_Buscar;
    @FXML
    private Label label_Universidad;
    @FXML
    private Button button_Regresar;
    @FXML
    private Button button_AgregarActividad;
    @FXML
    private Label label_SDGCOILVIC;
    @FXML
    private Button button_Abierto;
    @FXML
    private Button button_Completado;
    @FXML
    private Button button_Vencida;
    @FXML
    private Button button_Todos;

    @FXML
    void button_Buscar() {
        // Método que se llama cuando se presiona el botón de buscar
    }

    @FXML
    void button_Regresar(ActionEvent event) {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaProfesorMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }

    @FXML
    void button_AgregarActividad(ActionEvent event) {
       Stage stage = (Stage) button_AgregarActividad.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAgregarActividad(stage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }

    @FXML
    void button_Abierto(ActionEvent event) {
        // Método que se llama cuando se presiona el botón de filtro "Abierto"
    }

    @FXML
    void button_Completado(ActionEvent event) {
        // Método que se llama cuando se presiona el botón de filtro "Completado"
    }

    @FXML
    void button_Vencida(ActionEvent event) {
        // Método que se llama cuando se presiona el botón de filtro "Vencida"
    }

    @FXML
    void button_Todos(ActionEvent event) {
        // Método que se llama cuando se presiona el botón de filtro "Todos"
    }
    @FXML
    private ImageView imageSubMenu;
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarImagen();
    }
}
