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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class GestionDeOfertasDeColaboracionControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(GestionDeOfertasDeColaboracionControlador.class);
   
    @FXML
    private ImageView imageSubMenu;
    
    @FXML
    private TableView<?> tableView_Colaboraciones;

    @FXML
    private TableColumn<?, ?> column_Nombre;

    @FXML
    private TableColumn<?, ?> column_Profesor;

    @FXML
    private TableColumn<?, ?> column_Tipo;

    @FXML
    private TableColumn<?, ?> column_Objetivo;

    @FXML
    private TableColumn<?, ?> column_Estudiantes;

    @FXML
    private Label label_UniversidadVeracruzana;

    @FXML
    private Button button_Regresar;

    @FXML
    private Label label_SDGCOILVIC;

    @FXML
    private Label label_ColaboracionesAceptadas;

    @FXML
    private Button button_Ofertar;

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

    @FXML
    void button_Ofertar(ActionEvent event) {
        // Aquí va la lógica cuando se presiona el botón de ofertar
    }
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        mostrarImagen();
        
    }
}
