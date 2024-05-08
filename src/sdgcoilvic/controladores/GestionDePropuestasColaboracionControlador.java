package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class GestionDePropuestasColaboracionControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(GestionDePropuestasColaboracionControlador.class);
        
    @FXML
    private Button button_Regresar;

    @FXML
    private TableView<?> tableView_Instituciones;

    @FXML
    private TableColumn<?, ?> column_NombrePropuesta;

    @FXML
    private TableColumn<?, ?> column_Correo;

    @FXML
    private Label label_Universidad;

    @FXML
    private Label label_SDGCOILVIC;

    @FXML
    private Label label_GestionInstituciones;

    @FXML
    void button_Regresar(ActionEvent event) {
       Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrativoMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
}
    
