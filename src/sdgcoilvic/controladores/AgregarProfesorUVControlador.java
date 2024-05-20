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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.CorreoElectronico;
import sdgcoilvic.utilidades.GeneradorDeContrasenias;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorUVDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;
import sdgcoilvic.logicaDeNegocio.enums.EnumProfesor;
import sdgcoilvic.logicaDeNegocio.enums.EnumTipoDeAcceso;

public class AgregarProfesorUVControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AgregarProfesorUVControlador.class);
    private Stage stage;
    
    @FXML private Button button_Cancelar;
    @FXML private Button button_Guardar;
    @FXML private TextField textField_NumeroPersonal;
    @FXML private TextField textField_Disciplina;
    @FXML private TextField textField_Nombre;
    @FXML private TextField textField_ApellidoPaterno;
    @FXML private TextField textField_ApellidoMaterno;
    @FXML private TextField textField_Correo;
    @FXML private ComboBox<String> comboBox_Region;
    @FXML private ComboBox<String> comboBox_CategoriaContratacion;
    @FXML private ComboBox<String> comboBox_AreaAcademica;
    @FXML private ComboBox<String> comboBox_Idioma;
    @FXML private ComboBox<String> comboBox_TipoContratacion;

    private Runnable onCloseCallback;

    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }
    
    private void aplicarValidacion(TextField textField, String expresionRegular) {
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String nuevoTexto = cambio.getControlNewText();
            return (nuevoTexto.matches(expresionRegular) || nuevoTexto.isEmpty()) ? cambio : null;
        };

        textField.setTextFormatter(new TextFormatter<>(filtro));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aplicarValidacion(textField_NumeroPersonal, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\d]{1,20}");
        aplicarValidacion(textField_Disciplina, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{0,200}$");
        aplicarValidacion(textField_Nombre, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        aplicarValidacion(textField_ApellidoPaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        aplicarValidacion(textField_ApellidoMaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        llenarComboIdioma();
        llenarComboRegion(); 
        llenarComboCategoriaContratacion(); 
        llenarComboTipoContratacion();
        llenarComboAreaAcademica(); 
         
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void llenarComboIdioma() {
        try {
            List<List<String>> listaDeIdiomas = obtenerListaDeIdiomas();
            ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeIdiomas));
            comboBox_Idioma.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }

    private void llenarComboRegion() {
        try {
            List<List<String>> listaDeRegion = obtenerListaDeRegion();
            ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeRegion));
            comboBox_Region.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }
    
    private void llenarComboCategoriaContratacion() {
        try {
            List<List<String>> listaDeCategoria = obtenerListaDeCategoriaContratacion();
            ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeCategoria));
            comboBox_CategoriaContratacion.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }
        
    private void llenarComboTipoContratacion() {
        try {
            List<List<String>> listaTipoContratacion = obtenerListaDeTipoContratacion();
            ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaTipoContratacion));
            comboBox_TipoContratacion.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }
            
    private void llenarComboAreaAcademica() {
        try {
            List<List<String>> listaDeAreaAcademica = obtenerListaDeAreaAcademica();
            ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeAreaAcademica));
            comboBox_AreaAcademica.setItems(items);
        } catch (SQLException ex) {
            LOG.error(ex);
        }
    }
    
    private List<List<String>> obtenerListaDeIdiomas() throws SQLException {
        return new ProfesorDAO().obtenerListaDeIdiomas();
    }

    private List<List<String>> obtenerListaDeRegion() throws SQLException {
        return new ProfesorUVDAO().obtenerListaDeRegion();
    }
    
    private List<List<String>> obtenerListaDeCategoriaContratacion() throws SQLException {
        return new ProfesorUVDAO().obtenerListaDeCategoriaContratacion();
    }

    private List<List<String>> obtenerListaDeTipoContratacion() throws SQLException {
        return new ProfesorUVDAO().obtenerListaDeTipoContratacion();
    }
    
    private List<List<String>> obtenerListaDeAreaAcademica() throws SQLException {
        return new ProfesorUVDAO().obtenerListaDeAreaAcademica();
    }
    
    private List<String> obtenerListaNombres(List<List<String>> lista) {
        List<String> nombres = new ArrayList<>();
        lista.forEach(item -> nombres.add(item.get(1)));
        return nombres;
    }
    
    @FXML
    void cancelarRegistro(ActionEvent event) {
        if (Alertas.mostrarMensajeCancelar()) {
            Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
            myStage.close();
        }
    }

    private void asignarIdIdioma(ProfesorUV profesorUV) {
        int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
        if (indiceIdiomaSeleccionado >= 0) {
            try {
                List<List<String>> listaDeIdiomas = obtenerListaDeIdiomas();
                if (indiceIdiomaSeleccionado < listaDeIdiomas.size()) {
                    int idIdioma = Integer.parseInt(listaDeIdiomas.get(indiceIdiomaSeleccionado).get(0));
                    profesorUV.setIdIdiomas(idIdioma);
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex);
            }
        }
    }
    
    private void asignarIdCategoriaContratacion(ProfesorUV profesorUV) {
        int indiceCategoriaContratacionSeleccionado = comboBox_CategoriaContratacion.getSelectionModel().getSelectedIndex();
        if (indiceCategoriaContratacionSeleccionado >= 0) {
            try {
                List<List<String>> listaDeCategoriaContratacion = obtenerListaDeCategoriaContratacion();
                if (indiceCategoriaContratacionSeleccionado < listaDeCategoriaContratacion.size()) {
                    int IdCategoriaContratacion = Integer.parseInt(listaDeCategoriaContratacion.get(indiceCategoriaContratacionSeleccionado).get(0));
                    profesorUV.setIdCategoriaContratacionUV(IdCategoriaContratacion);
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex);
            }
        }
    }
    
    private void asignarIdTipoContratacion(ProfesorUV profesorUV) {
        int indiceTipoContratacionSeleccionado = comboBox_TipoContratacion.getSelectionModel().getSelectedIndex();
        if (indiceTipoContratacionSeleccionado >= 0) {
            try {
                List<List<String>> listaDeTipoContratacion= obtenerListaDeTipoContratacion();
                if (indiceTipoContratacionSeleccionado < listaDeTipoContratacion.size()) {
                    int IdTipoContratacion = Integer.parseInt(listaDeTipoContratacion.get(indiceTipoContratacionSeleccionado).get(0));
                    profesorUV.setIdTipoContratacionUV(IdTipoContratacion);
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex);
            }
        }
    }
        
    private void asignarIdRegion(ProfesorUV profesorUV) {
        int indiceRegionSeleccionado = comboBox_Region.getSelectionModel().getSelectedIndex();
        if (indiceRegionSeleccionado >= 0) {
            try {
                List<List<String>> listaDeRegion= obtenerListaDeRegion();
                if (indiceRegionSeleccionado < listaDeRegion.size()) {
                    int idRegion = Integer.parseInt(listaDeRegion.get(indiceRegionSeleccionado).get(0));
                    profesorUV.setIdRegion(idRegion);
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex);
            }
        }
    }
    
    private void asignarIdAreaAcademica(ProfesorUV profesorUV) {
        int indiceAreaAcademicaSeleccionado = comboBox_AreaAcademica.getSelectionModel().getSelectedIndex();
        if (indiceAreaAcademicaSeleccionado >= 0) {
            try {
                List<List<String>> listaDeAreaAcademica= obtenerListaDeAreaAcademica();
                if (indiceAreaAcademicaSeleccionado < listaDeAreaAcademica.size()) {
                    int idAreaAcademica = Integer.parseInt(listaDeAreaAcademica.get(indiceAreaAcademicaSeleccionado).get(0));
                    profesorUV.setIdAreaAcademica(idAreaAcademica);
                }
            } catch (SQLException | NumberFormatException ex) {
                LOG.error(ex);
            }
        }
    }
    
    private ProfesorUV crearProfesorUV() {
        String claveInstitucionalUV = "30MSU0940B";
        ProfesorUV profesorUV = new ProfesorUV();
        profesorUV.setNombre(textField_Nombre.getText());
        profesorUV.setApellidoPaterno(textField_ApellidoPaterno.getText());
        profesorUV.setApellidoMaterno(textField_ApellidoMaterno.getText());
        profesorUV.setCorreo(textField_Correo.getText());
        asignarIdIdioma(profesorUV);
        profesorUV.setEstadoProfesor(EnumProfesor.Activo.toString());
        profesorUV.setClaveInstitucional(claveInstitucionalUV);

        profesorUV.setNoPersonal(textField_NumeroPersonal.getText());
        profesorUV.setDisciplina(textField_Disciplina.getText());
        asignarIdRegion(profesorUV);
        asignarIdCategoriaContratacion(profesorUV);
        asignarIdTipoContratacion(profesorUV);
        asignarIdAreaAcademica(profesorUV);
        return profesorUV;
    }


    private Acceso crearAcceso() {
        Acceso acceso = new Acceso();
        acceso.setUsuario(textField_Correo.getText());
        acceso.setContrasenia(GeneradorDeContrasenias.generarContraseña());
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        return acceso;
    }

    @FXML
    void guardarRegistro(ActionEvent event){
        if (verificarInformacion()) {
            ProfesorUV profesorUV = crearProfesorUV();
            Acceso acceso = crearAcceso();
            if (registrarProfesorUV( acceso, profesorUV) == true) {
                Alertas.mostrarMensajeExito();
                Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
                myStage.close();
                if (onCloseCallback != null) {
                    onCloseCallback.run();
                }
            }
        }
    }
    
    private boolean registrarProfesorUV(Acceso acceso, ProfesorUV profesorUV) {
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean registroExitoso = false;              
            try {
                if (!profesorDAO.verificarSiExisteElCorreo(profesorUV.getCorreo())) {
                    if (!profesorDAO.verificarExistenciaProfesor(profesorUV.getNombre(), profesorUV.getApellidoPaterno(), profesorUV.getApellidoMaterno())) {
                        if (!profesorUVDAO.verificarSiExisteElNoPersonal(profesorUV.getNoPersonal())) {
                            if (profesorUVDAO.registrarProfesorUV( acceso, profesorUV) == 2) {
                                registroExitoso = true;
                                if (enviarCorreo(profesorUV, acceso) == false) {
                                    profesorUVDAO.eliminarProfesorUV(profesorUV.getNoPersonal());
                                     Alertas.mostrarMensajeElCorreoNoSePudoEnviar();
                                      registroExitoso = false;
                                }
                            } else {
                                Alertas.mostrarMensajeInformacionNoRegistrada();
                            }
                        } else {
                        Alertas.mostrarMensajeNumeroPersonalYaExistente();
                        }
                    } else {
                        Alertas.mostrarMensajeProfesorYaExistente();
                    }
                } else {
                    Alertas.mostrarMensajeEmailYaRegistrado();
                }
            } catch (SQLException sqlException) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            }
        return registroExitoso;
    }
    
    private boolean enviarCorreo(ProfesorUV profesorUV, Acceso acceso) {
        String mensaje = "Estimado profesor " + profesorUV.getNombre() + ",\n\n" +
                "Lo hemos registrado exitosamente como profesor. A continuación se muestran tus credenciales de acceso:\n\n" +
                "Usuario: " + acceso.getUsuario() + "\n" +
                "Contraseña: " + acceso.getContrasenia() + "\n\n" +
                "¡Gracias por su solicitud!\n" +
                "SDGCOILVIC";
        
        return CorreoElectronico.verificarEnvioCorreo(profesorUV.getCorreo(), "Credenciales de acceso", mensaje);
    
    }
    
    private boolean estaVacio() {
        int indiceRegionSeleccionada = comboBox_Region.getSelectionModel().getSelectedIndex();
        int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
        int indiceCategoriaContratacionSeleccionado = comboBox_CategoriaContratacion.getSelectionModel().getSelectedIndex();
        int indiceTipoContratacionSeleccionado = comboBox_TipoContratacion.getSelectionModel().getSelectedIndex();        
        int indiceAreaAcademicaSeleccionado = comboBox_AreaAcademica.getSelectionModel().getSelectedIndex();       
        
        return textField_Nombre.getText().isEmpty() ||
                textField_ApellidoPaterno.getText().isEmpty() ||
                textField_Correo.getText().isEmpty() ||
                indiceRegionSeleccionada < 0 ||
                indiceIdiomaSeleccionado < 0 ||
                indiceCategoriaContratacionSeleccionado < 0 ||
                indiceTipoContratacionSeleccionado < 0 ||
                indiceAreaAcademicaSeleccionado < 0;
    }


    private boolean verificarInformacion() {
        ProfesorUV profesorUV = new ProfesorUV();
        boolean validacion = true;

        if (!estaVacio()) {
            try {
                profesorUV.setNoPersonal(textField_NumeroPersonal.getText());
                profesorUV.setDisciplina(textField_Disciplina.getText());
                profesorUV.setNombre(textField_Nombre.getText());
                profesorUV.setApellidoPaterno(textField_ApellidoPaterno.getText());
                profesorUV.setApellidoMaterno(textField_ApellidoMaterno.getText());
            } catch (IllegalArgumentException nombrePaisException) {
                Alertas.mostrarMensajeInformacionInvalida();
                validacion = false;
             }

            
            try {
                profesorUV.setCorreo(textField_Correo.getText());
            } catch (IllegalArgumentException nombrePaisException) {
                Alertas.mostrarMensajeCorreoConFormatoInvalido();
                validacion = false;
            }

        } else {
            Alertas.mostrarMensajeCamposVacios();
            validacion = false;
        }
        return validacion;
    }  
}
