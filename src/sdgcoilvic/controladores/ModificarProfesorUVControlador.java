package sdgcoilvic.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorUVDAO;
import sdgcoilvic.utilidades.Alertas;


public class ModificarProfesorUVControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ModificarProfesorUVControlador.class);
    private static String correoAntiguo;
    private static String estadoProfesorAnterior;
    private static String noPersonalAnterior;
    private Stage stage;
    public static String noPersonal;
    
    @FXML private Button button_Cancelar;
    @FXML private Button button_Guardar;
    
    @FXML private ComboBox<String> comboBox_EstadoProfesor;
    @FXML private ComboBox<String> comboBox_AreaAcademica;
    @FXML private ComboBox<String> comboBox_CategoriaContratacion;
    @FXML private ComboBox<String> comboBox_Idioma;
    @FXML private ComboBox<String> comboBox_Region;
    @FXML private ComboBox<String> comboBox_TipoContratacion;
    
    @FXML private TextField textField_ApellidoMaterno;
    @FXML private TextField textField_ApellidoPaterno;
    @FXML private TextField textField_Nombre;
    @FXML private TextField textField_NumPersonal;
    @FXML private TextField textField_Correo;
    @FXML private TextField textField_Disciplina;

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
        aplicarValidacion(textField_NumPersonal, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,20}");
        aplicarValidacion(textField_Disciplina, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{0,200}$");
        aplicarValidacion(textField_Nombre, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        aplicarValidacion(textField_ApellidoPaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        aplicarValidacion(textField_ApellidoMaterno, "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s',;\\-_:\\.]{1,60}$");
        llenarComboBoxIdioma();
        llenarComboBoxRegion(); 
        llenarComboBoxCategoriaContratacion(); 
        llenarComboBoxTipoContratacion();
        llenarComboBoxAreaAcademica(); 
        llenarComboBoxEstadoProfesor();
        informacionProfesorUV();
        noPersonalAnterior = textField_NumPersonal.getText(); 
        correoAntiguo = textField_Correo.getText(); 
        estadoProfesorAnterior = comboBox_EstadoProfesor.getValue();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void llenarComboBoxEstadoProfesor() {
        ObservableList<String> items = FXCollections.observableArrayList("Activo", "Archivado");
        comboBox_EstadoProfesor.setItems(items);
        
        comboBox_EstadoProfesor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                confirmarCambioEstadoProfesor(oldValue, newValue);
            }
        });
    }
    
    private void llenarComboBoxIdioma() {
        List<List<String>> listaDeIdiomas = obtenerListaDeIdiomas();
        ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeIdiomas));
        comboBox_Idioma.setItems(items);
    }

    private void llenarComboBoxRegion() {
        List<List<String>> listaDeRegion = obtenerListaDeRegion();
        ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeRegion));
        comboBox_Region.setItems(items);
    }
    
    private void llenarComboBoxCategoriaContratacion() {
        List<List<String>> listaDeCategoria = obtenerListaDeCategoriaContratacion();
        ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeCategoria));
        comboBox_CategoriaContratacion.setItems(items);
    }
        
    private void llenarComboBoxTipoContratacion() {
        List<List<String>> listaTipoContratacion = obtenerListaDeTipoContratacion();
        ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaTipoContratacion));
        comboBox_TipoContratacion.setItems(items);
    }
            
    private void llenarComboBoxAreaAcademica() {
        List<List<String>> listaDeAreaAcademica = obtenerListaDeAreaAcademica();
        ObservableList<String> items = FXCollections.observableArrayList(obtenerListaNombres(listaDeAreaAcademica));
        comboBox_AreaAcademica.setItems(items);
    }
    
    private List<List<String>> obtenerListaDeIdiomas(){
        return new ProfesorDAO().obtenerListaDeIdiomas();
    }

    private List<List<String>> obtenerListaDeRegion() {
        return new ProfesorUVDAO().obtenerListaDeRegion();
    }
    
    private List<List<String>> obtenerListaDeCategoriaContratacion() {
        return new ProfesorUVDAO().obtenerListaDeCategoriaContratacion();
    }

    private List<List<String>> obtenerListaDeTipoContratacion() {
        return new ProfesorUVDAO().obtenerListaDeTipoContratacion();
    }
    
    private List<List<String>> obtenerListaDeAreaAcademica() {
        return new ProfesorUVDAO().obtenerListaDeAreaAcademica();
    }
    
    private List<String> obtenerListaNombres(List<List<String>> lista) {
        List<String> nombres = new ArrayList<>();
        lista.forEach(item -> nombres.add(item.get(1)));
        return nombres;
    }
    
    @FXML
    void button_Cancelar(ActionEvent event) {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
    }
    
    @FXML
    void button_Guardar(ActionEvent event) {
        if (verificarInformacion()) {
            Profesor profesor = crearProfesor();
            ProfesorUV profesorUV = crearProfesorUV();
            if (actualizarProfesorUV( profesor,profesorUV) == true) {
                Alertas.mostrarMensajeExito();
               cerrarVentana();
            }
        }
    }
    
    private void cerrarVentana() {
        Stage myStage = (Stage) button_Cancelar.getScene().getWindow();
        myStage.close();
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
    }

    
    private void confirmarCambioEstadoProfesor(String oldValue, String newValue) {
        if (oldValue != null && oldValue.equals("Activo") && newValue.equals("Archivado")) {
            boolean resultado = Alertas.mostrarMensajeConfirmacionArchivarProfesor();
            if (resultado) {
                actualizarEstadoProfesor(newValue);
            } else {
                comboBox_EstadoProfesor.setValue(estadoProfesorAnterior);
            }
        } else if (oldValue != null && oldValue.equals("Archivado") && newValue.equals("Activo")) {
            boolean resultado = Alertas.mostrarMensajeConfirmacionActivarProfesor();
            if (resultado) {
                actualizarEstadoProfesor(newValue);
            } else {
                comboBox_EstadoProfesor.setValue(estadoProfesorAnterior);
            }
        } else {
            estadoProfesorAnterior = newValue;
        }
    }
         
    private void actualizarEstadoProfesor(String nuevoEstado) {
        Profesor profesor = crearProfesor();
        profesor.setEstadoProfesor(nuevoEstado);
    }
    
    private Profesor crearProfesor() {
        Profesor profesor = new Profesor();
        profesor.setNombre(textField_Nombre.getText());
        profesor.setApellidoPaterno(textField_ApellidoPaterno.getText());
        profesor.setApellidoMaterno(textField_ApellidoMaterno.getText());
        profesor.setCorreo(textField_Correo.getText());
        asignarIdIdioma(profesor);
        String estadoProfesor = comboBox_EstadoProfesor.getValue();
        if (estadoProfesor != null) {
            profesor.setEstadoProfesor(estadoProfesor);
        } else {
            profesor.setEstadoProfesor(""); 
        }
        return profesor;
    }
    
    private ProfesorUV crearProfesorUV() {
        ProfesorUV profesorUV = new ProfesorUV();
        profesorUV.setNoPersonal(textField_NumPersonal.getText());
        profesorUV.setDisciplina(textField_Disciplina.getText());
        asignarIdRegion(profesorUV);
        asignarIdCategoriaContratacion(profesorUV);
        asignarIdTipoContratacion(profesorUV);
        asignarIdAreaAcademica(profesorUV);
        return profesorUV;
    }
        
    private boolean actualizarProfesorUV( Profesor profesor, ProfesorUV profesorUV) {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        boolean actualizacionExitosa = false;
        String nuevoCorreo = profesor.getCorreo();
        String nuevoNumeroPersonal = profesorUV.getNoPersonal();
            try {
                Profesor profesorActual = profesorDAO.obtenerProfesorPorCorreo(profesor.getCorreo());   
                ProfesorUV profesorUVActual = profesorUVDAO.obtenerProfesorUVPorNumPersonal(profesorUV.getNoPersonal());
                if (!hayCambiosEnDatosProfesor(profesorActual, profesor, profesorUVActual, profesorUV)) {
                    Alertas.mostrarMensajeDatosIguales();
                    return false;
                }
                if (nuevoCorreo.equals(correoAntiguo) || nuevoNumeroPersonal.equals(noPersonalAnterior)) {
                    if (profesorUVDAO.actualizarInformacionDelProfesorUV(profesor,profesorUV, noPersonal) == 2) {
                        actualizacionExitosa = true;
                    } else {
                        Alertas.mostrarMensajeInformacionNoRegistrada();
                    }
                } else {
                    if (!profesorDAO.verificarSiExisteElCorreo(nuevoCorreo)) {
                        if(!profesorUVDAO.verificarSiExisteElNoPersonal(profesorUV.getNoPersonal())){
                            if (profesorUVDAO.actualizarInformacionDelProfesorUV(profesor,profesorUV, noPersonal) == 2) {
                                actualizacionExitosa = true;
                            } else {
                                Alertas.mostrarMensajeInformacionNoRegistrada();
                            }
                        }else{
                            Alertas.mostrarMensajeNumeroPersonalYaExistente();
                        }
                    } else {
                       Alertas.mostrarMensajeEmailYaRegistrado();
                    }
                }
            } catch (SQLException sqlException ) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.fatal("Error en la base de datos" + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            }

        
        return actualizacionExitosa;
    }   
    
    private boolean hayCambiosEnDatosProfesor(Profesor profesorActual, Profesor nuevoProfesor,ProfesorUV profesorUVActual, ProfesorUV nuevoUVProfesor) {
        return (profesorActual.getNombre() == null ? nuevoProfesor.getNombre() != null : !profesorActual.getNombre().equals(nuevoProfesor.getNombre())) ||
               (profesorActual.getApellidoPaterno() == null ? nuevoProfesor.getApellidoPaterno() != null : !profesorActual.getApellidoPaterno().equals(nuevoProfesor.getApellidoPaterno())) ||
               (profesorActual.getApellidoMaterno() == null ? nuevoProfesor.getApellidoMaterno() != null : !profesorActual.getApellidoMaterno().equals(nuevoProfesor.getApellidoMaterno())) ||
               (profesorActual.getCorreo() == null ? nuevoProfesor.getCorreo() != null : !profesorActual.getCorreo().equals(nuevoProfesor.getCorreo())) ||
                profesorActual.getIdIdiomas() != nuevoProfesor.getIdIdiomas() ||
               (profesorActual.getEstadoProfesor() == null ? nuevoProfesor.getEstadoProfesor() != null : !profesorActual.getEstadoProfesor().equals(nuevoProfesor.getEstadoProfesor())) ||
               (profesorUVActual.getNoPersonal() == null ? nuevoUVProfesor.getNoPersonal() != null : !profesorUVActual.getNoPersonal().equals(nuevoUVProfesor.getNoPersonal())) ||
               (profesorUVActual.getDisciplina() == null ? nuevoUVProfesor.getDisciplina() != null : !profesorUVActual.getDisciplina().equals(nuevoUVProfesor.getDisciplina())) ||
                profesorUVActual.getIdRegion() != nuevoUVProfesor.getIdRegion() ||
                profesorUVActual.getIdCategoriaContratacionUV() != nuevoUVProfesor.getIdCategoriaContratacionUV() ||  
                profesorUVActual.getIdTipoContratacionUV() != nuevoUVProfesor.getIdTipoContratacionUV() ||
                profesorUVActual.getIdAreaAcademica() != nuevoUVProfesor.getIdAreaAcademica();       
    }
    
    private void asignarIdIdioma(Profesor profesor) {
        asignarId(comboBox_Idioma, obtenerListaDeIdiomas(), profesor::setIdIdiomas);
    }

    private void asignarIdCategoriaContratacion(ProfesorUV profesorUV) {
        asignarId(comboBox_CategoriaContratacion, obtenerListaDeCategoriaContratacion(), profesorUV::setIdCategoriaContratacionUV);
    }

    private void asignarIdTipoContratacion(ProfesorUV profesorUV) {
        asignarId(comboBox_TipoContratacion, obtenerListaDeTipoContratacion(), profesorUV::setIdTipoContratacionUV);
    }

     private void asignarIdRegion(ProfesorUV profesorUV) {
         asignarId(comboBox_Region, obtenerListaDeRegion(), profesorUV::setIdRegion);
    }

    private void asignarIdAreaAcademica(ProfesorUV profesorUV) {
        asignarId(comboBox_AreaAcademica, obtenerListaDeAreaAcademica(), profesorUV::setIdAreaAcademica);
    }

    private void asignarId(ComboBox<String> comboBox, List<List<String>> lista, Consumer<Integer> setId) {
         int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
         if (selectedIndex >= 0 && selectedIndex < lista.size()) {
             try {
                 int id = Integer.parseInt(lista.get(selectedIndex).get(0));
                 setId.accept(id);
             } catch (NumberFormatException ex) {
                 LOG.error(ex);
             }
         }
     }

        private boolean estaVacio() {
            int indiceRegionSeleccionada = comboBox_Region.getSelectionModel().getSelectedIndex();
            int indiceIdiomaSeleccionado = comboBox_Idioma.getSelectionModel().getSelectedIndex();
            int indiceCategoriaContratacionSeleccionada = comboBox_CategoriaContratacion.getSelectionModel().getSelectedIndex();
            int indiceTipoContratacionSeleccionada = comboBox_TipoContratacion.getSelectionModel().getSelectedIndex();
            int indiceAreaAcademicaSeleccionada = comboBox_AreaAcademica.getSelectionModel().getSelectedIndex();

            return textField_Nombre.getText().isEmpty() ||
                   textField_ApellidoPaterno.getText().isEmpty() ||
                   textField_Correo.getText().isEmpty() ||
                    textField_NumPersonal.getText().isEmpty() ||
                    textField_Disciplina.getText().isEmpty() ||
                   indiceRegionSeleccionada < 0 ||
                   indiceIdiomaSeleccionado < 0 ||
                   indiceCategoriaContratacionSeleccionada < 0 ||
                   indiceTipoContratacionSeleccionada < 0 ||
                   indiceAreaAcademicaSeleccionada < 0;
        }


    private boolean verificarInformacion() {
        ProfesorUV profesorUV = new ProfesorUV();
        boolean validacion = true;

        if (!estaVacio()) {
            try {
                profesorUV.setNoPersonal(textField_NumPersonal.getText());
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
    
        private void informacionProfesorUV() {
            
        ProfesorUV profesorUV = new ProfesorUV();
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        try {
            profesorUV = profesorUVDAO.obtenerProfesorPorNumPersonal(noPersonal);
        } catch (SQLException sQLExcpetion) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.error(sQLExcpetion);
        }
            textField_NumPersonal.setText(profesorUV.getNoPersonal());
            textField_Nombre.setText(profesorUV.getNombre());
            textField_ApellidoPaterno.setText(profesorUV.getApellidoPaterno());
            textField_ApellidoMaterno.setText(profesorUV.getApellidoMaterno());
            textField_Correo.setText(profesorUV.getCorreo());
            textField_Disciplina.setText(profesorUV.getDisciplina());
            asignarNombreSeleccionado(profesorUV.getIdTipoContratacionUV(), comboBox_TipoContratacion, () -> obtenerListaDeTipoContratacion());
            asignarNombreSeleccionado(profesorUV.getIdCategoriaContratacionUV(), comboBox_CategoriaContratacion, () -> obtenerListaDeCategoriaContratacion());
            asignarNombreSeleccionado(profesorUV.getIdAreaAcademica(), comboBox_AreaAcademica, () -> obtenerListaDeAreaAcademica());
            asignarNombreSeleccionado(profesorUV.getIdIdiomas(), comboBox_Idioma, () -> obtenerListaDeIdiomas());
            asignarNombreSeleccionado(profesorUV.getIdRegion(), comboBox_Region, () -> obtenerListaDeRegion());
            comboBox_EstadoProfesor.setValue(profesorUV.getEstadoProfesor());
            estadoProfesorAnterior = profesorUV.getEstadoProfesor();
    }
    
        private void asignarNombreSeleccionado(int idSeleccionado, ComboBox<String> comboBox, Supplier<List<List<String>>> listaSupplier) {
            List<List<String>> listaDeItems = listaSupplier.get();
            for (List<String> listItem : listaDeItems) {
                try {
                    int itemId = Integer.parseInt(listItem.get(0));
                    if (itemId == idSeleccionado) {
                        comboBox.setValue(listItem.get(1));
                        return;
                    }
                } catch (NumberFormatException ex) {
                    LOG.error(ex);
                }
            }
        }
}
