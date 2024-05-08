package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class ModificarProfesorUVControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ModificarProfesorUVControlador.class);
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Guardar;

    @FXML
    private ComboBox<?> comboBox_Idioma;

    @FXML
    private ComboBox<?> comboBox_TipoContratacion;

    @FXML
    private ComboBox<?> comboBox_Region;

    @FXML
    private ComboBox<?> comboBox_CategoriaContratacion;

    @FXML
    private ComboBox<?> comboBox_AreaAcademica;

    @FXML
    private Label label_Idioma;

    @FXML
    private Label label_TipoContratacion;

    @FXML
    private Label label_NumPersonal;

    @FXML
    private Label label_ApellidoPaterno;

    @FXML
    private Label label_ApellidoMaterno;

    @FXML
    private Label label_Correo;

    @FXML
    private Label label_Region;

    @FXML
    private Label label_CategoriaContratacion;

    @FXML
    private Label label_AreaAcademica;

    @FXML
    private TextField textField_NumPersonal;

    @FXML
    private TextField textField_Correo;

    @FXML
    private TextField textField_ApellidoPaterno;

    @FXML
    private TextField textField_ApellidoMaterno;

    @FXML
    private TextField textField_Nombre;

    @FXML
    void buttonCancelar(ActionEvent event) {
        // Lógica para cancelar la modificación del profesor UV
    }

    @FXML
    void buttonGuardar(ActionEvent event) {
        // Lógica para guardar los cambios del profesor UV
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
