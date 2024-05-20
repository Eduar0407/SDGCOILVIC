package sdgcoilvic.controladores;
import sdgcoilvic.utilidades.ImagesSetter;
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
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.enums.EnumProfesor;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.AccesoDAO;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.AccesoSingleton;

public class AccesoControlador implements Initializable {
    private static final Logger LOG = Logger.getLogger(AccesoControlador.class);

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
                int existeAcceso = accesoDao.verificarExistenciaAcceso(textField_Usuario.getText(), paswordField_Contrasenia.getText());
                if (existeAcceso > 0) {
                    return existeAcceso;
                } else {
                    Alertas.mostrarMensajeInicioSesionFallido();
                }
            } catch (SQLException sqlException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("No hay conexión con la base de datos :" +this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
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
            AccesoDAO accesoDAO = new AccesoDAO();
            int IdAcceso = accesoDAO.obtenerIdProfesor(textField_Usuario.getText());
            AccesoSingleton.getInstance().setAccesoId(IdAcceso);
            try {
                String tipoUsuario=accesoDAO.obtenerTipoUsuario(textField_Usuario.getText());
                switch (tipoUsuario) {
                    case "Administrativo" -> abrirVentanaAdministrativoMenu();
                    case "Profesor" -> validarEstadoProfesor(IdAcceso);
                }
            } catch (SQLException sqlException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("No hay conexión con la base de datos :" +this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            }
        }
    }
    
    public void validarEstadoProfesor(int idAcceso){
        AccesoDAO accesoDAO = new AccesoDAO();
        try {
            Profesor profesor = accesoDAO.obtenerProfesorPorID(idAcceso);
            if(profesor != null && profesor.getEstadoProfesor().equals(EnumProfesor.Archivado.toString())){
                Alertas.mostrarMensajeAccesoDenegado();
            }else{
                abrirVentanaProfesorMenu();
            }
        }catch (SQLException sqlException ) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.fatal("No hay conexión con la base de datos :" +this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            
        } catch (Exception e) {
            LOG.error(e);
    }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarImagen();
    }
}
