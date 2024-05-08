
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class GestionDeInstitucionesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(GestionDeInstitucionesControlador.class);
   
    @FXML
    private ImageView imageSubMenu;
    
    @FXML
    private Button button_Regresar;

    @FXML
    private Button button_RegistrarInstitucion;

    @FXML
    private TextField textField_BuscarInstitucion;

    @FXML
    private Label label_Nombre;

    @FXML
    private TableView<?> tabla_Instituciones;

    @FXML
    private TableColumn<?, ?> column_Clave;

    @FXML
    private TableColumn<?, ?> column_NombreInstitucion;

    @FXML
    private TableColumn<?, ?> column_Pais;

    @FXML
    private TableColumn<?, ?> column_Correo;

    @FXML
    private Button button_Buscar;

    @FXML
    private Label label_Filtrar;

    @FXML
    private ComboBox<?> comboBox_Pais;;
    
    
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
            sdgcoilvic.mostrarVentanaRegistrarInstitucion(stage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    void button_Buscar(ActionEvent event) {
        // Manejar el evento del botón btnBuscar aquí
    }
    @FXML
            
    void comboPaisSeleccionado(ActionEvent event) {
        // Manejar el evento del botón btnBuscar aquí
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        mostrarImagen();
        
    }
}
