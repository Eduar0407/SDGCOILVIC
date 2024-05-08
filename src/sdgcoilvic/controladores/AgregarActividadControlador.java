package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AgregarActividadControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AgregarActividadControlador.class);
    private Stage stage;
    
    @FXML
    private ImageView image_view_logo;

    @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Aceptar;

    @FXML
    private DatePicker datepicker_FechaInicio;

    @FXML
    private DatePicker datepicker_FechaCierre;

    @FXML
    private TextField textfield_Nombre;

    @FXML
    private TextField textfield_Instruccion;

    @FXML
    private TextField textfield_Herramientas;

    @FXML
    private TextField textfield_Tema;

    @FXML
    private TextField textfield_Descripcion;

    @FXML
    private TextField textfield_Puntaje;

    @FXML
    void button_Cancelar(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }

    @FXML
    void button_Aceptar() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
