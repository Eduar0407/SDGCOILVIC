package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.utilidades.AccesoSingleton;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.ImagesSetter;

public class ProfesorMenuControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(ProfesorMenuControlador.class);

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
    }
   
    @FXML
    private void cerrarSesion(MouseEvent event) {
        if (Alertas.mostrarConfirmacion("Cerrar Sesión", "¿Seguro que desea cerrar sesión?")) {
            AccesoSingleton.getInstance().borrarInstancia();
            Stage myStage = (Stage) imageSalir.getScene().getWindow();
            SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

            try {
                sdgcoilvic.mostrarVentanaAcceso(myStage);
            } catch (IOException ex) {
                LOG.error( ex);
            }
        }
    }
    
    @FXML
    private void imageColaboracion(MouseEvent event) {
  
    }
    
    @FXML
    private void imageOfertasDeColaboracion(MouseEvent event) {
          Stage myStage = (Stage) imagePropuestasColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarColaboracionesDisponibles(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    private void imagePropuestasColaboracion(MouseEvent event) {
        Stage myStage = (Stage) imagePropuestasColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarPropuestasDeColaboracion(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    private void imageActividadesColaboracion(MouseEvent event) {
        Stage myStage = (Stage) imageActividadesColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarActividades(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
    
    @FXML
    private void imageSolicitudesColaboracion(MouseEvent event) {
        Stage myStage = (Stage) imageSolicitudesColaboracion.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaAdministrarSolicitudes(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
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
