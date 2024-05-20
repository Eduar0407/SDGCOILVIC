package sdgcoilvic.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class AgregarEstudianteControlador implements Initializable{
    private Stage stage;
    
    public static  int idColaboracion;
    private Runnable onCloseCallback;

    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
}
