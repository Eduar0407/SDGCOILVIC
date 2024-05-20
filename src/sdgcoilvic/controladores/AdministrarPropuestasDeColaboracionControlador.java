package sdgcoilvic.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sdgcoilvic.logicaDeNegocio.clases.PropuestaColaboracion;
import sdgcoilvic.logicaDeNegocio.clases.TablaPropuestasColaboracion;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.PeriodoDAO;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.PropuestaColaboracionDAO;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.SolicitudColaboracionDAO;
import sdgcoilvic.utilidades.AccesoSingleton;
import sdgcoilvic.utilidades.Alertas;
import sdgcoilvic.utilidades.ImagesSetter;

public class AdministrarPropuestasDeColaboracionControlador implements Initializable{
    private static final Logger LOG = Logger.getLogger(AdministrarPropuestasDeColaboracionControlador.class);
    private AccesoSingleton accesoSingleton;
    ObservableList<TablaPropuestasColaboracion> lista = FXCollections.observableArrayList();
    @FXML private Button button_Regresar;
    @FXML private Button button_NuevaPropuesta;
    @FXML private TableView<TablaPropuestasColaboracion> tableView_Propuestas;
    @FXML private TableColumn<TablaPropuestasColaboracion, String> tableColumn_NombrePropuesta;
    @FXML private TableColumn<TablaPropuestasColaboracion, String> tableColumn_Tipo;
    @FXML private TableColumn<TablaPropuestasColaboracion, String> tableColumn_Periodo;
    @FXML private TableColumn<TablaPropuestasColaboracion, String> tableColumn_Estado;
    @FXML private TableColumn<TablaPropuestasColaboracion, Void> tableColumn_Opcion;
    @FXML private ImageView imageView_SubMenu;   
    
    private void mostrarImagen() {
        imageView_SubMenu.setImage(ImagesSetter.getImageSubMenu());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accesoSingleton = AccesoSingleton.getInstance();
        mostrarImagen();
        llenarTabla();
        
        tableColumn_Opcion.setCellFactory(param -> new TableCell<>() {
            private final Button button_Corregir = new Button("Corregir");
            private final Button button_IniciarColaboracion = new Button("Iniciar Colaboración");

            {
                button_Corregir.setOnAction(event -> {
                    TablaPropuestasColaboracion data = getTableView().getItems().get(getIndex());
                    try {
                        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
                        Stage stage = (Stage) button_Corregir.getScene().getWindow();
                        ActualizarPropuestaControlador.idPropuestaColaboracion = data.getIdPropuestaColaboracion();
                        sdgcoilvic.mostrarVentanaActualizarPropuestaPropuesta(stage);
                    } catch (IOException ioexception) {
                        LOG.error(ioexception.getMessage());
                        Alertas.mostrarMensajeErrorCambioPantalla();
                    }
                });

                button_IniciarColaboracion.setOnAction(event -> {
                    SolicitudColaboracionDAO solicitudColaboracionDAO = new SolicitudColaboracionDAO();
       
                    TablaPropuestasColaboracion data = getTableView().getItems().get(getIndex());
                    try {
                        int idProfesor = accesoSingleton.getAccesoId();
                        if (solicitudColaboracionDAO.verificarEstadoColaboracion(idProfesor)) {
                            Alertas.mostrarMensajeErrorColaboracionEnCurso();
                            return;
                        }
                        
                        String resultado = solicitudColaboracionDAO.obtenerSolicitudesAprobadas(data.getIdPropuestaColaboracion());
                        if ("-1".equals(resultado)) {
                            Alertas.mostrarMensajeNoPuedesIniciarColaboracion();
                            return;
                        }

                        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
                        Stage stage = (Stage) button_IniciarColaboracion.getScene().getWindow();
                        IniciarColaboracionControlador.idPropuestaColaboracion = data.getIdPropuestaColaboracion();
                        sdgcoilvic.mostrarVentanaIniciarColaboracion(stage);

                    } catch (IOException | SQLException e) {
                        LOG.error(e.getMessage());
                        Alertas.mostrarMensajeErrorCambioPantalla();
                    }
                });
            }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        TablaPropuestasColaboracion propuesta = getTableView().getItems().get(getIndex());
                        if (null == propuesta.getEstadoPropuesta()) {
                            setGraphic(null);
                        } else switch (propuesta.getEstadoPropuesta()) {
                            case "Rechazada":
                                setGraphic(button_Corregir);
                                break;
                            case "Ofertada":
                                setGraphic(button_IniciarColaboracion);
                                break;
                            default:
                                setGraphic(null);
                                break;
                        }
                    }
                }
            });
    }
    
    private void llenarTabla() {
        PropuestaColaboracionDAO propuestaColaboracionDAO = new PropuestaColaboracionDAO();
        List<PropuestaColaboracion> propuestasLista = null;

        try {
            List<List<String>> listaPeriodos = obtenerListaDePeriodo();
            int idAcceso = accesoSingleton.getAccesoId();
            propuestasLista = propuestaColaboracionDAO.consultarPropuestasColaboracionPorProfesor(idAcceso);
            lista.clear();

            for (PropuestaColaboracion propuestaColaboracion : propuestasLista) {
                String periodoInfo = "";
                for (List<String> periodo : listaPeriodos) {
                    int id = Integer.parseInt(periodo.get(0));
                    if (id == propuestaColaboracion.getIdPeriodo()) {
                        String nombrePeriodo = periodo.get(1);
                        LocalDate fechaInicio = LocalDate.parse(periodo.get(2)); 
                        LocalDate fechaFin = LocalDate.parse(periodo.get(3)); 
                        periodoInfo = nombrePeriodo + " (" + fechaInicio + " - " + fechaFin + ")";
                        break;
                    }
                }

                lista.add(new TablaPropuestasColaboracion(
                        propuestaColaboracion.getIdPropuestaColaboracion(),
                        propuestaColaboracion.getTipoColaboracion(),
                        propuestaColaboracion.getNombre(),
                        propuestaColaboracion.getObjetivoGeneral(),
                        propuestaColaboracion.getTemas(),
                        periodoInfo,
                        propuestaColaboracion.getEstadoPropuesta(),
                        String.valueOf(propuestaColaboracion.getIdProfesor()),
                        String.valueOf(propuestaColaboracion.getIdIdiomas())
                ));
            }

            tableView_Propuestas.setItems(lista);
            tableColumn_NombrePropuesta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tableColumn_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipoColaboracion"));
            tableColumn_Periodo.setCellValueFactory(new PropertyValueFactory<>("idPeriodo"));
            tableColumn_Estado.setCellValueFactory(new PropertyValueFactory<>("estadoPropuesta"));

        } catch (SQLException ex) {
            Alertas.mostrarMensajeErrorBaseDatos();
            LOG.error(ex);
        }
    }

    
    private List<List<String>> obtenerListaDePeriodo() throws SQLException {
        return new PeriodoDAO().obtenerListaDePeriodos();
    }
       
    @FXML
    void button_Regresar(ActionEvent event) {
        Stage myStage = (Stage) button_Regresar.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaProfesorMenu(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }

    @FXML
    void button_NuevaPropuesta(ActionEvent event) {
        Stage myStage = (Stage) button_NuevaPropuesta.getScene().getWindow();
        SDGCOILVIC sdgcoilvic = new SDGCOILVIC();
        try {
            sdgcoilvic.mostrarVentanaNuevaPropuesta(myStage);
        } catch (IOException ex) {
            LOG.error( ex);
        }
    }


}
