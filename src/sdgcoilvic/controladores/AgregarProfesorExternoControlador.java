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
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class AgregarProfesorExternoControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AgregarProfesorExternoControlador.class);
    private Stage stage;
    
     @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Guardar;

    @FXML
    private Label label_Idioma;

    @FXML
    private Label label_Institucion;

    @FXML
    private ComboBox<?> combo_Idioma;

    @FXML
    private ComboBox<?> combo_Institucion;

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
    void cancelarRegistro(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }

    @FXML
    void guardarRegistro(ActionEvent event) {
        // Lógica para el botón Guardar
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
