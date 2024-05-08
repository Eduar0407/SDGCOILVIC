package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.Alertas;

public class RegistrarInstitucionControlador  implements Initializable{
    private static final Logger LOG = Logger.getLogger(RegistrarInstitucionControlador.class);
   
    private Stage stage;
   
@FXML
    private TextField textField_Clave;

    @FXML
    private TextField textField_Nombre;

    @FXML
    private TextField textField_Correo;

    @FXML
    private TextField textField_Pais;

    @FXML
    private Button button_Agregar;

    @FXML
    private Button button_Cancelar;

    @FXML
    private Label label_Clave;

    @FXML
    private Label label_Nombre;

    @FXML
    private Label label_Correo;

    @FXML
    private Label label_Pais;
    
    @FXML
    void button_Agregar(ActionEvent event) {
        // Acción a realizar al hacer clic en el botón "Agregar"
    }

    @FXML
    void button_Cancelar(ActionEvent event) {
        if (Alertas.mostrarMensajeCancelar()) {
            Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
            myStage.close();
        }
    }

    private void applyLettersOnlyValidation(TextField textField) {
        UnaryOperator<Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^[a-zA-Z]*$") || newText.isEmpty()) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyLettersOnlyValidation(textField_Nombre);
        applyLettersOnlyValidation(textField_Pais);

        
    }
    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
