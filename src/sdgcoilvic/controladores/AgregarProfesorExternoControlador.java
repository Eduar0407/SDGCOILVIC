package sdgcoilvic.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.Utilidades.Alertas;
import sdgcoilvic.Utilidades.CorreoElectronico;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.Utilidades.GeneradorDeContrasenias;

public class AgregarProfesorExternoControlador implements Initializable {
    private static final Logger LOG = Logger.getLogger(AgregarProfesorExternoControlador.class);
    private Stage stage;

    @FXML
    private Button button_Cancelar;
    @FXML
    private Button button_Guardar;
    @FXML
    private ComboBox<String> comboBox_Idioma;
    @FXML
    private ComboBox<String> comboBox_Institucion;
    @FXML
    private Label label_ErrorNombre;
    @FXML
    private Label label_ErrorApellidoPaterno;
    @FXML
    private Label label_ErrorApellidoMaterno;
    @FXML
    private Label label_ErrorCorreo;
    @FXML
    private Label label_ErrorIdioma;
    @FXML
    private Label label_ErrorInstitucion;
    @FXML
    private TextField textField_Nombre;
    @FXML
    private TextField textField_ApellidoPaterno;
    @FXML
    private TextField textField_ApellidoMaterno;
    @FXML
    private TextField textField_Correo;

    private Runnable onCloseCallback;

    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    private void llenar_combo_Idioma() {
        try {
            ProfesorDAO profesorDAO = new ProfesorDAO();
            List<List<String>> listaDeIdiomas = profesorDAO.obtenerListaDeIdiomas();
            List<String> listaNombresIdiomas = new ArrayList<>();
            listaDeIdiomas.forEach(idioma -> listaNombresIdiomas.add(idioma.get(1)));
            ObservableList<String> items = FXCollections.observableArrayList(listaNombresIdiomas);
            comboBox_Idioma.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    private void llenar_combo_Institucion() {
        try {
            ProfesorDAO profesorDAO = new ProfesorDAO();
            List<List<String>> listaDeInstituciones = profesorDAO.obtenerListaDeInstituciones();
            List<String> listaNombresInstituciones = new ArrayList<>();
            for (List<String> institucion : listaDeInstituciones) {
                listaNombresInstituciones.add(institucion.get(1));
            }
            ObservableList<String> items = FXCollections.observableArrayList(listaNombresInstituciones);
            comboBox_Institucion.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    @FXML
    void cancelarRegistro(ActionEvent event) {
        if (Alertas.mostrarMensajeCancelar()) {
            Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
            myStage.close();
        }
    }

    @FXML
    void guardarRegistro(ActionEvent event) {
        etiquetasDeError();
        int PROFESOR = 1;
        int SINCOLABORACION = 2;
        if (verificarInformacion()) {
            Profesor profesor = new Profesor();
            profesor.setNombre(textField_Nombre.getText());
            profesor.setApellidoPaterno(textField_ApellidoPaterno.getText());
            profesor.setApellidoMaterno(textField_ApellidoMaterno.getText());
            profesor.setCorreo(textField_Correo.getText());

            int indiceInstitucionSeleccionada = comboBox_Institucion.getSelectionModel().getSelectedIndex();
            if (indiceInstitucionSeleccionada >= 0) {
                try {
                    ProfesorDAO profesorDAO = new ProfesorDAO();
                    List<List<String>> listaDeInstituciones = profesorDAO.obtenerListaDeInstituciones();
                    if (indiceInstitucionSeleccionada < listaDeInstituciones.size()) {
                        String claveInstitucion = listaDeInstituciones.get(indiceInstitucionSeleccionada).get(0);
                        profesor.setClaveInstitucional(claveInstitucion);
                    }
                } catch (SQLException ex) {
                    LOG.error(ex);
                }
            }

            int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
            if (indiceIdiomaSeleccionado >= 0) {
                try {
                    ProfesorDAO profesorDAO = new ProfesorDAO();
                    List<List<String>> listaDeIdiomas = profesorDAO.obtenerListaDeIdiomas();
                    if (indiceIdiomaSeleccionado < listaDeIdiomas.size()) {
                        int idIdioma = Integer.parseInt(listaDeIdiomas.get(indiceIdiomaSeleccionado).get(0));
                        profesor.setIdIdiomas(idIdioma);
                    }
                } catch (SQLException | NumberFormatException ex) {
                    LOG.error(ex);
                }
            }
            profesor.setIdEstado(SINCOLABORACION);

            Acceso acceso = new Acceso();
            acceso.setUsuario(textField_Correo.getText());
            acceso.setContrasenia(GeneradorDeContrasenias.generarContraseña());
            acceso.setTipoUsuario(PROFESOR);

            ProfesorDAO profesorDAO = new ProfesorDAO();
            try {
                if (!profesorDAO.verificarSiExisteElCorreo(profesor.getCorreo())) {
                    if (!profesorDAO.verificarExistenciaProfesor(profesor.getNombre(), profesor.getApellidoPaterno(), profesor.getApellidoMaterno())) {
                        try {
                                if (profesorDAO.registrarProfesor(profesor, acceso) == 1) {
                                String mensaje = "Estimado profesor " + profesor.getNombre() + ",\n\n" +
                                        "Lo hemos registrado exitosamente como profesor. A continuación se muestran tus credenciales de acceso:\n\n" +
                                        "Usuario: " + acceso.getUsuario() + "\n" +
                                        "Contraseña: " + acceso.getContrasenia() + "\n\n" +
                                        "¡Gracias por su solicitud!\n" +
                                        "SDGCOILVIC";
                                boolean correoEnviado = CorreoElectronico.verificarEnvioCorreo(profesor.getCorreo(), "Credenciales de acceso", mensaje);

                                if (correoEnviado) {
                                    Alertas.mostrarMensajeExito();
                                    Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
                                    myStage.close();
                                    if (onCloseCallback != null) {
                                        onCloseCallback.run();
                                    }
                                } else {
                                    
                                    Alertas.mostrarMensaje(Alert.AlertType.ERROR, "Error al enviar correo",
                                            "El correo no se pudo enviar. El registro del profesor no se puede realizar.");
                                }
                            } else {
                                Alertas.mostrarMensajeInformacionNoRegistrada();
                            }
                        } catch (SQLException ex) {
                            LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage(), ex);
                            Alertas.mostrarMensajeErrorBaseDatos();
                        }

                    } else {
                        Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO",
                                "El Profesor ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
                    }
                } else {
                    Alertas.mostrarMensajeEmailYaRegistrado();
                }

            } catch (SQLException sqlException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            }

        } else {

        }
    }

    private boolean estaVacio() {
        int indiceInstitucionSeleccionada = comboBox_Institucion.getSelectionModel().getSelectedIndex();
        int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
        return textField_Nombre.getText().isEmpty() ||
                textField_ApellidoPaterno.getText().isEmpty() ||
                textField_ApellidoMaterno.getText().isEmpty() ||
                textField_Correo.getText().isEmpty() ||
                indiceInstitucionSeleccionada < 0 ||
                indiceIdiomaSeleccionado < 0;
    }


    private boolean verificarInformacion() {
        Profesor profesor = new Profesor();
        boolean validacion = true;

        if (!estaVacio()) {
            try {
                profesor.setNombre(textField_Nombre.getText());
            } catch (IllegalArgumentException claveInstitucionalException) {
                label_ErrorNombre.setVisible(true);
                validacion = false;
            }

            try {
                profesor.setApellidoPaterno(textField_ApellidoPaterno.getText());
            } catch (IllegalArgumentException nombreException) {
                label_ErrorApellidoPaterno.setVisible(true);
                validacion = false;
            }

            try {
                profesor.setApellidoMaterno(textField_ApellidoMaterno.getText());
            } catch (IllegalArgumentException coreoException) {
                label_ErrorApellidoMaterno.setVisible(true);
                validacion = false;
            }

            try {
                profesor.setCorreo(textField_Correo.getText());
            } catch (IllegalArgumentException nombrePaisException) {
                label_ErrorCorreo.setVisible(true);
                Alertas.mostrarMensaje(Alert.AlertType.INFORMATION, "AVISO",
                        "El correo electrónico es inválido. "
                                + "Por favor, asegúrate de que tenga el formato "
                                + "correcto, por ejemplo: usuario@dominio.com");
                validacion = false;
            }

        } else {
            label_ErrorIdioma.setVisible(true);
            label_ErrorInstitucion.setVisible(true);
            Alertas.mostrarMensajeCamposVacios();
            validacion = false;
        }
        return validacion;

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
        label_ErrorNombre.setVisible(false);
        label_ErrorApellidoPaterno.setVisible(false);
        label_ErrorApellidoMaterno.setVisible(false);
        label_ErrorCorreo.setVisible(false);
        label_ErrorIdioma.setVisible(false);
        label_ErrorInstitucion.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aplicarValidacion(textField_Nombre, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s]{1,60}$");
        aplicarValidacion(textField_ApellidoPaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s]{1,60}$");
        aplicarValidacion(textField_ApellidoMaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s]{1,60}$");
        llenar_combo_Idioma();
        llenar_combo_Institucion();
        etiquetasDeError();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
