package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class EvaluarPropuestaColaboracionControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(EvaluarPropuestaColaboracionControlador.class);
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;
    @FXML
    private Button button_Enviar;
    @FXML
    private DatePicker datePicker_Inicio;
    @FXML
    private DatePicker datePicker_Cierre;
    @FXML
    private Label label_Institucion;
    @FXML
    private Label label_FechaCierre;
    @FXML
    private Label label_FechaInicio;
    @FXML
    private TextArea txtArea_Descripcion;
    @FXML
    private Label label_Pais;
    @FXML
    private Label label_Modalidad;
    @FXML
    private Label label_Descripcion;
    @FXML
    private TextArea txtArea_Objetivo;
    @FXML
    private Label label_Objetivo;
    @FXML
    private TextField textField_Idioma;
    @FXML
    private TextField textField_Titulo;
    @FXML
    private Label label_Idioma;
    @FXML
    private TextField textField_Profesor;
    @FXML
    private Label label_Titulo;
    @FXML
    private Label label_Profesor;
    @FXML
    private ComboBox<?> comboBox_Evaluacion;
    @FXML
    private TextArea txtArea_Justificacion;
    
    @FXML
    void button_Cancelar(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }

    @FXML
    void button_Enviar(ActionEvent event) {
        // Lógica para el botón Enviar
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
