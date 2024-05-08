
package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class EvidenciasDeActividadControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(EvidenciasDeActividadControlador.class);
    private Stage stage;

    @FXML
    private AnchorPane anchorPane_Main;

    @FXML
    private Pane pane_Content;

    @FXML
    private TitledPane titledPane_Nombre;

    @FXML
    private ImageView imageView_Logo;

    @FXML
    private Button button_Regresar;

    @FXML
    private Label label_Evidencias;

    @FXML
    private TableView<?> tableView_Evidencias;

    @FXML
    private TableColumn<?, ?> column_Archivo;

    @FXML
    private TableColumn<?, ?> column_Visualizar;

    @FXML
    void button_Regresar(ActionEvent event) {
         Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        myStage.close();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
