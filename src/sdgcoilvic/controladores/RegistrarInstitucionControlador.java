package sdgcoilvic.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.InstitucionDAO;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;

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
    private Label label_ErrorClave;
    @FXML
    private Label label_ErrorNombre;
    @FXML
    private Label label_ErrorCorreo;
    @FXML
    private Label label_ErrorPais;
    
    private Runnable onCloseCallback;

    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }
    
    @FXML
    private void button_Agregar(ActionEvent event) {
        etiquetasDeError();
        if (verificarInformacion()){
            Institucion institucion = new Institucion();
            institucion.setClaveInstitucional(textField_Clave.getText());
            institucion.setNombreInstitucion(textField_Nombre.getText());
            institucion.setNombrePais(textField_Pais.getText());
            institucion.setCorreo(textField_Correo.getText());
            InstitucionDAO institucionDAO = new InstitucionDAO();
            try{
                if (!institucionDAO.verificarSiExisteLaClave(institucion.getClaveInstitucional())){
                    if (!institucionDAO.verificarSiExisteElNombreInstitucion(institucion.getNombreInstitucion())){
                        if (!institucionDAO.verificarSiExisteElCorreo(institucion.getCorreo())){
                            try {
                                if (institucionDAO.insertarInstitucion(institucion) == 1) {
                                    Alertas.mostrarMensajeExito();
                                    Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
                                    myStage.close();
                                    if (onCloseCallback != null) {
                                        onCloseCallback.run();
                                    }
                                } else {
                                      Alertas.mostrarMensajeInformacionNoRegistrada();
                                }
                            } catch (SQLException ex) {
                                  Alertas.mostrarMensajeErrorBaseDatos();
                            }
                        }else {
                            Alertas.mostrarMensajeEmailYaRegistrado();
                        }
                    } else {
                        Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO", 
                "El Nombre de la institucion ingresada ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
                    }
                } else {
                     Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO", 
                "La clave ingresada ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
                    
                }
                
            } catch (SQLException sqlException){
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            }
        }
    }

    private boolean estaVacio() {
        return textField_Clave.getText().isEmpty()||textField_Nombre.getText().isEmpty()||textField_Correo.getText().isEmpty()||textField_Pais.getText().isEmpty();
    
    }
    
    private boolean verificarInformacion(){
        Institucion institucion = new   Institucion();
        boolean validacion = true;
        
        if (!estaVacio()){
            try{
                institucion.setClaveInstitucional(textField_Clave.getText());
            } catch (IllegalArgumentException claveInstitucionalException){
                label_ErrorClave.setVisible(true);
                validacion = false;
            }

            try{
                institucion.setNombreInstitucion(textField_Nombre.getText());
            } catch (IllegalArgumentException nombreException){
                label_ErrorNombre.setVisible(true);
                validacion = false;
            } 

            try{
                institucion.setCorreo(textField_Correo.getText());
            } catch (IllegalArgumentException coreoException){
                label_ErrorCorreo.setVisible(true);
                Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO", 
                    "El correo electrónico es inválida. "
                    + "Por favor, asegúrate de que tenga el formato "
                    + "correcto, por ejemplo: usuario@dominio.com");
                validacion = false;
                } 

            try{
                institucion.setNombrePais(textField_Pais.getText());
            } catch (IllegalArgumentException nombrePaisException){
                label_ErrorPais.setVisible(true);
                validacion = false;
                }
        
        }else {
          Alertas.mostrarMensajeCamposVacios();
          validacion = false;  
        }
        return validacion;
        
    }
    
    @FXML
    void button_Cancelar(ActionEvent event) {
        if (Alertas.mostrarMensajeCancelar()) {
            Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
            myStage.close();
        }
    }

    private void aplicarValidacion(TextField textField, String regex) {
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String nuevoTexto = cambio.getControlNewText();
            if (nuevoTexto.matches(regex) || nuevoTexto.isEmpty()) {
                return cambio;
            }
            return null;
        };
        TextFormatter<String> formateadorTexto = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateadorTexto);
    }

    
    private void etiquetasDeError() {
        label_ErrorClave.setVisible(false);
        label_ErrorNombre.setVisible(false);
        label_ErrorCorreo.setVisible(false);
        label_ErrorPais.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aplicarValidacion(textField_Clave, "^[A-Z0-9]{1,20}$");
        aplicarValidacion(textField_Nombre, "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,200}$");
        aplicarValidacion(textField_Pais, "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,60}$");

        etiquetasDeError();
        
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
