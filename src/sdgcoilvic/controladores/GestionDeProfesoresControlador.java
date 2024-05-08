package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class GestionDeProfesoresControlador implements Initializable  {
    private static final Logger LOG = Logger.getLogger(GestionDeProfesoresControlador.class);
   
    
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
    private TableView<?> tableView_Profesores;

    @FXML
    private TableColumn<?, ?> column_Nombre;

    @FXML
    private TableColumn<?, ?> column_ApellidoPaterno;

    @FXML
    private TableColumn<?, ?> column_ApellidoMaterno;

    @FXML
    private TableColumn<?, ?> column_Correo;
    
    
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @FXML
    private void button_Buscar(ActionEvent event) {
        // Manejar la acción del ComboBox aquí
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
        // Manejar la acción del ComboBox aquí
    }
    @FXML
    private void button_AgregarProfesorUV(ActionEvent event) {
        Stage stage = (Stage) button_AgregarProfesorUV.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAgregarProfesorUV(stage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    @FXML
    private void button_AgregarProfesorExterno(ActionEvent event) {
        Stage stage = (Stage) button_AgregarProfesorExterno.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAgregarProfesorExterno(stage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        mostrarImagen();
        
    }
}
