package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.ImagesSetter;

public class NuevaColaboraciónControlador implements Initializable{
    private static final Logger LOG= Logger.getLogger(NuevaColaboraciónControlador.class);
    private Stage stage;
    
    @FXML
    private Button button_Cancelar;

    @FXML
    private Button button_Someter;

    @FXML
    private ImageView imageSubMenu;
    
    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
     @FXML
    void button_Cancelar(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }

    @FXML
    void button_Someter() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    mostrarImagen();
    }
        public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
