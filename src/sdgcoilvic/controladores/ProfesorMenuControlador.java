package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.sql.SQLException;
import java.util.logging.Level;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.AccesoDAO;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.PropuestaColaboracionDAO;
import sdgcoilvic.utilidades.AccesoSingleton;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.ColaboracionEnCursoSinglenton;
import sdgcoilvic.utilidades.ImagesSetter;

public class ProfesorMenuControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ProfesorMenuControlador.class);
    
    @FXML 
    private Label label_NombreProfesor;
    @FXML
    private ImageView imageSalir;
    @FXML
    private ImageView imageMenuProfesor;
    @FXML
    private ImageView imageColaboracion;
    @FXML
    private ImageView imageOfertasDeColaboracion;
    @FXML
    private ImageView imagePropuestasColaboracion;
    @FXML
    private ImageView imageActividadesColaboracion;
    @FXML
    private ImageView imageSolicitudesColaboracion;
    
    private void mostrarImagen() {
        imageMenuProfesor.setImage(ImagesSetter.getImageMenuProfesor());
        mostrarNombreProfesor();
    }
    
    private void mostrarNombreProfesor(){
        AccesoDAO accesoDAO = new AccesoDAO();
        int idProfesor = AccesoSingleton.getInstance().getAccesoId();
        try {
            Profesor profesor = accesoDAO.obtenerProfesorPorID(idProfesor);
             label_NombreProfesor.setText(profesor.getNombre() +" "+ profesor.getApellidoPaterno() +" "+ profesor.getApellidoMaterno());
        }catch (SQLException sqlException ) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.fatal("No hay conexión con la base de datos :" +this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
            
        }
    }
   
    @FXML
    private void cerrarSesion(MouseEvent event) {
        if (Alertas.mostrarConfirmacion("Cerrar Sesión", "¿Seguro que desea cerrar sesión?")) {
            AccesoSingleton.getInstance().borrarInstancia();
            ColaboracionEnCursoSinglenton.getInstance().destruirColaboracionEnCursoSinglenton();
            Stage escenario = (Stage) imageSalir.getScene().getWindow();
            SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

            try {
                sdgcoilvic.mostrarVentanaAcceso(escenario);
            } catch (IOException ex) {
                LOG.error( ex);
            }
        }
    }
    
    @FXML
    private void imageColaboracion(MouseEvent event) {
        int idColaboracionEnCurso = ColaboracionEnCursoSinglenton.getInstance().getIdColaboracionEnCurso();
            
        if(idColaboracionEnCurso>=1){
            Stage escenario = (Stage) imageColaboracion.getScene().getWindow();
            SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

            try {
                sdgcoilvic.mostrarVentanaAdministrarColaboracionActiva(escenario);
            } catch (IOException ex) {
                Alertas.mostrarMensajeErrorCambioPantalla();
                LOG.error( ex);
            }
        }else{
           Alertas.mostrarMensajeNoHayColaboracionEnCurso(); 
        }
    }
    
    @FXML
    private void imageOfertasDeColaboracion(MouseEvent event) {
        Stage escenario = (Stage) imageOfertasDeColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarColaboracionesDisponibles(escenario);
        } catch (IOException ex) {
            Alertas.mostrarMensajeErrorCambioPantalla();
            LOG.error( ex);
        }
    }
    
    @FXML
    private void imagePropuestasColaboracion(MouseEvent event) {
        Stage escenario = (Stage) imagePropuestasColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        int idColaboracionEnCurso = ColaboracionEnCursoSinglenton.getInstance().getIdColaboracionEnCurso();
            
        if(idColaboracionEnCurso>=1){
            Alertas.mostrarMensajeColaboracionEnCurso(); 
        }else{
            try {
                sdgcoilvic.mostrarVentanaAdministrarPropuestasDeColaboracion(escenario);
            } catch (IOException ex) {
                Alertas.mostrarMensajeErrorCambioPantalla();
                LOG.error( ex);
            }
        }
    }
    
    @FXML
    private void imageActividadesColaboracion(MouseEvent event) {
        Stage escenario = (Stage) imageActividadesColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarActividades(escenario);
        } catch (IOException ex) {
            Alertas.mostrarMensajeErrorCambioPantalla();
            LOG.error( ex);
        }
    }
    
    @FXML
    private void imageSolicitudesColaboracion(MouseEvent event) {
        
        Stage escenario = (Stage) imageSolicitudesColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        PropuestaColaboracionDAO propuestaColaboracioDAO = new PropuestaColaboracionDAO();
        int idProfesor =  AccesoSingleton.getInstance().getAccesoId();
        boolean verificar = false;
        try {
            verificar = propuestaColaboracioDAO.verificarEstadoPropuestaColaboracion(idProfesor);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ProfesorMenuControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(verificar){
            try {
                sdgcoilvic.mostrarVentanaAdministrarSolicitudes(escenario);
            } catch (IOException ex) {
                Alertas.mostrarMensajeErrorCambioPantalla();
                LOG.error( ex);
            }
        }else{
           Alertas.mostrarMensajeNoHayColaboracionOfertda(); 
        }
    }
   
    public void establecerMensajesImagen() {
        Tooltip tooltipCerrarSesion = new Tooltip("Cerrar Sesión");
        Tooltip tooltipColaboracion = new Tooltip("Mis Colaboraciones");
        Tooltip tooltipOfertasColaboraciones = new Tooltip("Ofertas de Colaboracion");
        Tooltip tooltipPropuestas = new Tooltip("Mis Propuesta de Colaboracion");
        Tooltip tooltipActividadesColaboracion = new Tooltip("Mis Actividades de Colaboracion");
        Tooltip tooltipSolicitudesColaboracion = new Tooltip("Solicitudes de Colaboracion");
        Tooltip.install(imageSalir, tooltipCerrarSesion);
        Tooltip.install(imageColaboracion, tooltipColaboracion);
        Tooltip.install(imageOfertasDeColaboracion, tooltipOfertasColaboraciones);
        Tooltip.install(imagePropuestasColaboracion, tooltipPropuestas);
        Tooltip.install(imageActividadesColaboracion, tooltipActividadesColaboracion);
        Tooltip.install(imageSolicitudesColaboracion, tooltipSolicitudesColaboracion);
    }
    
    public void ocultarOpciones() {
        imageSalir.setVisible(true);
        imageColaboracion.setVisible(true);
        imageOfertasDeColaboracion.setVisible(true);
        imagePropuestasColaboracion.setVisible(true);
        imageActividadesColaboracion.setVisible(true);
        imageSolicitudesColaboracion.setVisible(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        establecerMensajesImagen();
        mostrarImagen();
        ocultarOpciones();
    }
}