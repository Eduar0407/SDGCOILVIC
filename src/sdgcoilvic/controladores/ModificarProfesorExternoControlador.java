package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class ModificarProfesorExternoControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ModificarProfesorExternoControlador.class);
   
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Guardar;

    @FXML
    private ComboBox<?> comboBox_Idioma;

    @FXML
    private ComboBox<?> comboBox_Institucion;

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
    void buttonCancelar(ActionEvent event) {
        // Lógica para cancelar la modificación del profesor externo
    }

    @FXML
    void buttonGuardar(ActionEvent event) {
        // Lógica para guardar los cambios del profesor externo
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
