
package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.logicaDeNegocio.clases.TablaSolicitudesColaboracion;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.SolicitudColaboracionDAO;
import sdgcoilvic.utilidades.AccesoSingleton;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.CorreoElectronico;
import sdgcoilvic.utilidades.ImagesSetter;

public class AdministrarSolicitudesControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AdministrarSolicitudesControlador.class);
    ObservableList<TablaSolicitudesColaboracion> lista = FXCollections.observableArrayList();
    private AccesoSingleton accesoSingleton;
    @FXML private ImageView imageSubMenu;
    @FXML private TableView<TablaSolicitudesColaboracion> tableView_Solicitudes;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_NombreColaboracion;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_NombreProfesor;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_Institucion;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_Idioma;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_Mensaje;
    @FXML private TableColumn<TablaSolicitudesColaboracion, String> column_Fecha;
    @FXML private Button button_Regresar;
    @FXML private Button button_RechazarSolicitud;
    @FXML private Button button_AceptarSolicitud;

    private void mostrarImagen() {
        imageSubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarImagen();
        accesoSingleton = AccesoSingleton.getInstance();
        configurarColumnas();
        llenarTabla();
    }

    private void configurarColumnas() {
        column_NombreColaboracion.setCellValueFactory(new PropertyValueFactory<>("nombreColaboracion"));
        column_NombreProfesor.setCellValueFactory(new PropertyValueFactory<>("nombreProfesor"));
        column_Institucion.setCellValueFactory(new PropertyValueFactory<>("InstitucionEducativa"));
        column_Idioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        column_Mensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        column_Fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        
    }
    
    @FXML
        void button_RechazarSolicitud(ActionEvent event) {
            TablaSolicitudesColaboracion solicitudSeleccionada = tableView_Solicitudes.getSelectionModel().getSelectedItem();
            if (solicitudSeleccionada != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Estás seguro de que quieres rechazar esta solicitud?");
                alert.setContentText("Si presionas 'Aceptar', la solicitud será rechazada.");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        SolicitudColaboracionDAO solicitudColaboracionDAO = new SolicitudColaboracionDAO();
                        int idSolicitudColaboracion = solicitudSeleccionada.getIdSolicitudColaboracion();
                        try {
                            if (solicitudColaboracionDAO.rechazarSolicitud(idSolicitudColaboracion) == 1) {
                                String colaboracion = solicitudSeleccionada.getNombreColaboracion();
                                String correo = solicitudSeleccionada.getCorreo();
                                String avaluacion = "rechazada";
                                if (enviarCorreo(colaboracion, correo, avaluacion)) {
                                    Alertas.mostrarMensajeExito();
                                    llenarTabla();
                                } else {
                                    Alertas.mostrarMensajeElCorreoNoSePudoEnviar();
                                    solicitudColaboracionDAO.reevertirEvaluacion(idSolicitudColaboracion);
                                }
                            } else {
                                Alertas.mostrarMensajeInformacionNoRegistrada();
                            }
                        } catch (SQLException sqlException) {
                            Alertas.mostrarMensajeErrorBaseDatos();
                            LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
                        }
                    }
                });  
            } else {
                Alertas.mostrarMensajeSolicitudNoSeleccionado();
            }
        }

    @FXML
    void button_AceptarSolicitud(ActionEvent event) {
        TablaSolicitudesColaboracion solicitudSeleccionada = tableView_Solicitudes.getSelectionModel().getSelectedItem();
        if (solicitudSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro de que quieres aceptar esta solicitud?");
            alert.setContentText("Si presionas 'Aceptar', la solicitud será aceptada.");

            alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                    SolicitudColaboracionDAO solicitudColaboracionDAO = new SolicitudColaboracionDAO();
                    int idSolicitudColaboracion = solicitudSeleccionada.getIdSolicitudColaboracion();
                    try {
                        if (solicitudColaboracionDAO.aceptarSolicitud(idSolicitudColaboracion) == 1) {
                            String colaboracion = solicitudSeleccionada.getNombreColaboracion();
                            String correo = solicitudSeleccionada.getCorreo();
                            String avaluacion = "aceptada";
                            if (enviarCorreo(colaboracion, correo, avaluacion)) {
                                Alertas.mostrarMensajeExito();
                                llenarTabla();
                            } else {
                                Alertas.mostrarMensajeElCorreoNoSePudoEnviar();
                                solicitudColaboracionDAO.reevertirEvaluacion(idSolicitudColaboracion);
                            }
                        } else {
                            Alertas.mostrarMensajeInformacionNoRegistrada();
                        }
                    } catch (SQLException sqlException) {
                        Alertas.mostrarMensajeErrorBaseDatos();
                        LOG.fatal("Error en la base de datos en la clase " + this.getClass().getName() + ", método " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + sqlException.getMessage(), sqlException);
                    }
                }
            });
            
        } else {
            Alertas.mostrarMensajeSolicitudNoSeleccionado();
        }
    }
    
    private boolean enviarCorreo(String colaboracion, String correo,String avaluacion) {
        String mensaje = "Estimado profesor " +
                "Queremos informarte que su solicitud a la colaboración " + colaboracion + " ha sido " + avaluacion + ".\n\n" +
                "¡Gracias por tu interés en colaborar con nosotros!\n\n" +
                "Atentamente,\n" +
                "Equipo de SDGCOILVIC";

        return CorreoElectronico.verificarEnvioCorreo(correo, "Estado de su solicitud de colaboración", mensaje);
    }
    
    private void llenarTabla() {
            SolicitudColaboracionDAO solicitudColaboracionDAO = new SolicitudColaboracionDAO();
            int idAcceso = accesoSingleton.getAccesoId();
            try {
                lista.clear();
                List<List<String>> resultados = solicitudColaboracionDAO.consultarSolicitudesColaboracion(idAcceso);
                for (List<String> fila : resultados) {
                    TablaSolicitudesColaboracion solicitud = new TablaSolicitudesColaboracion(
                        Integer.parseInt(fila.get(0)),
                        fila.get(1), 
                        fila.get(2), 
                        fila.get(3), 
                        fila.get(4), 
                        fila.get(5),
                        fila.get(6),
                        fila.get(7)
                    );
                    lista.add(solicitud);
                }
                tableView_Solicitudes.setItems(lista);
            } catch (SQLException ex) {
                Alertas.mostrarMensajeErrorBaseDatos();
                LOG.error(ex);
            }
    }

    @FXML
    private void button_Regresar(ActionEvent event) {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();

        try {
            sdgcoilvic.mostrarVentanaProfesorMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }
}
