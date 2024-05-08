package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class AgregarProfesorUVControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AgregarProfesorUVControlador.class);
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Guardar;

    @FXML
    private Label label_Idioma;

    @FXML
    private Label label_TipoContratacion;

    @FXML
    private ComboBox<?> combo_Idioma;

    @FXML
    private ComboBox<?> combo_TipoContratacion;

    @FXML
    private Label label_NumeroPersonal;

    @FXML
    private TextField textField_NumeroPersonal;

    @FXML
    private Label label_Correo;

    @FXML
    private TextField textField_Correo;

    @FXML
    private Label label_ApellidoPaterno;

    @FXML
    private Label label_ApellidoMaterno;

    @FXML
    private Label label_Nombre;

    @FXML
    private Label label_Region;

    @FXML
    private ComboBox<?> combo_Region;

    @FXML
    private Label label_CategoriaContratacion;

    @FXML
    private ComboBox<?> combo_CategoriaContratacion;

    @FXML
    private Label label_AreaAcademica;

    @FXML
    private ComboBox<?> combo_AreaAcademica;

    @FXML
    private TextField txt_Nombre;


    @FXML
    void button_Cancelar(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }

    @FXML
    void button_Guardar(ActionEvent event) {
        // Lógica para el botón Guardar
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
