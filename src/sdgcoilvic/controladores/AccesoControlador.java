package sdgcoilvic.controladores;
import sdgcoilvic.Utilidades.ImagesSetter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.AccesoDAO;
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.Utilidades.AccesoSingleton;

public class AccesoControlador implements Initializable {
    private static final Logger LOG = Logger.getLogger(AccesoControlador.class);
    public static final int ADMINISTRATIVO = 0;
    public static final int PROFESOR = 1;

    @FXML
    private TextField textField_Usuario;
    @FXML
    private Button button_Login;
    @FXML
    private ImageView imageAccesoFondo;
    @FXML
    private PasswordField paswordField_Contrasenia;

    private void mostrarImagen() {
        imageAccesoFondo.setImage(ImagesSetter.getImageAccesoFondo());
    }

    private boolean estaVacio() {
        return textField_Usuario.getText().isEmpty() || paswordField_Contrasenia.getText().isEmpty();
    }

    private int verificarAcceso() {
        if (!estaVacio()) {
            AccesoDAO accesoDao = new AccesoDAO();

            try {
                int existeAcceso = accesoDao.existeAcceso(textField_Usuario.getText(), paswordField_Contrasenia.getText());
                if (existeAcceso > 0) {
                    return existeAcceso;
                } else {
                    Alertas.mostrarMensajeInicioSesionFallido();
                }
            } catch (SQLException sqlException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal(sqlException);
            }
        } else {
            Alertas.mostrarMensajeCamposVacios();
        }
        return 0;
    }
    
    private void abrirVentanaAdministrativoMenu() {
        Stage myStage = (Stage) button_Login.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaAdministrativoMenu(myStage);
        } catch (IOException | NullPointerException ex) {
            LOG.error("Error al abrir la ventana administrativa: " + ex.getMessage());
            Alertas.mostrarMensajeErrorCambioPantalla();
        }
    }

    private void abrirVentanaProfesorMenu() {
        Stage myStage = (Stage) button_Login.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaProfesorMenu(myStage);
        }catch (IOException | NullPointerException ex) {
            LOG.error("Error al abrir la ventana de profesor: " + ex.getMessage());
            Alertas.mostrarMensajeErrorCambioPantalla();
        }
    }
    
    @FXML
    private void button_Login(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (verificarAcceso() != 0) {
            AccesoSingleton.getInstance().setAccesoId(textField_Usuario.getText());
            AccesoDAO accesoDAO = new AccesoDAO();
            try {
                switch (accesoDAO.obtenerTipoUsuario(textField_Usuario.getText())) {
                    case ADMINISTRATIVO -> abrirVentanaAdministrativoMenu();
                    case PROFESOR -> abrirVentanaProfesorMenu();
                }
            } catch (SQLException sql) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal(sql);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarImagen();
    }
}
