
package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class AdministrarSolicitudesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AdministrarSolicitudesControlador.class);
   
    @FXML
    private ImageView imageSubMenu;

    @FXML
    private TableView<?> tableView_Solicitudes;

    @FXML
    private TableColumn<?, ?> column_NombreProfesor;

    @FXML
    private TableColumn<?, ?> column_Institucion;

    @FXML
    private TableColumn<?, ?> column_Pais;

    @FXML
    private TableColumn<?, ?> column_Idioma;

    @FXML
    private TableColumn<?, ?> column_Mensaje;

    @FXML
    private TableColumn<?, ?> column_Fecha;

    @FXML
    private ComboBox<?> comboBox_Idioma;

    @FXML
    private ComboBox<?> comboBox_Pais;

    @FXML
    private ComboBox<?> comboBox_Institucion;

    @FXML
    private Button button_Regresar;

    @FXML
    private Button button_RechazarSolicitud;

    @FXML
    private Label label_Universidad;

    @FXML
    private Button button_AceptarSolicitud;

    @FXML
    private Label label_SDGCOILVIC;

    @FXML
    private Label label_SolicitudesDeColaboracion;

    @FXML
    void button_RechazarSolicitud(ActionEvent event) {
        // Lógica para el botón de rechazar solicitud
    }

    @FXML
    void button_AceptarSolicitud(ActionEvent event) {
        // Lógica para el botón de aceptar solicitud
    }

    @FXML
    void comboIdiomaSeleccionado(ActionEvent event) {
        // Lógica para cuando se selecciona un idioma en el ComboBox
    }

    @FXML
    void comboPaisSeleccionado(ActionEvent event) {
        // Lógica para cuando se selecciona un país en el ComboBox
    }

    @FXML
    void comboInstitucionSeleccionada(ActionEvent event) {
        // Lógica para cuando se selecciona una institución en el ComboBox
    }
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
   
    @FXML
    private void button_Regresar(ActionEvent event) {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaProfesorMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarImagen();
    }
}
