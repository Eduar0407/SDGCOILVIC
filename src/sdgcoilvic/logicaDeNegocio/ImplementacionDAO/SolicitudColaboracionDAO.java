package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.SolicitudColaboracion;
import sdgcoilvic.logicaDeNegocio.interfaces.ISolicitudColaboracion;

public class SolicitudColaboracionDAO implements ISolicitudColaboracion {
    private static final String INSERTAR_SOLICITUD_COLABORACION = "INSERT INTO solicitud_colaboracion (fechaCreacion, mensaje, Profesor_idProfesor, Estado_Solicitud_idEstadoSolicitud) VALUES (?, ?, ?, ?)";
    private static final String EVALUAR_SOLICITUD_COLABORACION = "UPDATE solicitud_colaboracion SET Estado_Solicitud_idEstadoSolicitud = ? WHERE idSolicitudColaboracion = ?";
    private static final String CONSULTAR_SOLICITUDES_COLABORACION = "SELECT * FROM solicitud_colaboracion";
    private static final String CONSULTAR_SOLICITUDES_COLABORACION_POR_PROFESOR = "SELECT * FROM solicitud_colaboracion WHERE Profesor_idProfesor = ?";
    private static final String CONSULTAR_SOLICITUDES_COLABORACION_POR_ESTADO = "SELECT * FROM solicitud_colaboracion WHERE Estado_Solicitud_idEstadoSolicitud = ?";

    @Override
    public int elaborarSolicitudColaboracion(SolicitudColaboracion solicitud) {
        int resultado = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(INSERTAR_SOLICITUD_COLABORACION)) {
            preparedStatement.setString(1, solicitud.getFechaCreacion());
            preparedStatement.setString(2, solicitud.getMensaje());
            preparedStatement.setInt(3, solicitud.getIdProfesor());
            preparedStatement.setInt(4, solicitud.getIdEstadoSolicitud());
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public int evaluarSolicitudColaboracion(SolicitudColaboracion solicitud, boolean aprobada) {
        int resultado = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(EVALUAR_SOLICITUD_COLABORACION)) {
            int estado = aprobada ? 1 : 0;
            preparedStatement.setInt(1, estado);
            preparedStatement.setInt(2, solicitud.getIdSolicitudColaboracion());
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public List<SolicitudColaboracion> consultarSolicitudesColaboracion() {
        List<SolicitudColaboracion> solicitudes = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(CONSULTAR_SOLICITUDES_COLABORACION);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                SolicitudColaboracion solicitud = new SolicitudColaboracion();
                solicitud.setIdSolicitudColaboracion(resultSet.getInt("idSolicitudColaboracion"));
                solicitud.setFechaCreacion(resultSet.getString("fechaCreacion"));
                solicitud.setMensaje(resultSet.getString("mensaje"));
                solicitud.setIdProfesor(resultSet.getInt("Profesor_idProfesor"));
                solicitud.setIdEstadoSolicitud(resultSet.getInt("Estado_Solicitud_idEstadoSolicitud"));
                solicitudes.add(solicitud);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return solicitudes;
    }

    @Override
    public List<SolicitudColaboracion> consultarSolicitudesColaboracionPorProfesor(int idProfesor) {
        List<SolicitudColaboracion> solicitudes = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(CONSULTAR_SOLICITUDES_COLABORACION_POR_PROFESOR)) {
            preparedStatement.setInt(1, idProfesor);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    SolicitudColaboracion solicitud = new SolicitudColaboracion();
                    solicitud.setIdSolicitudColaboracion(resultSet.getInt("idSolicitudColaboracion"));
                    solicitud.setFechaCreacion(resultSet.getString("fechaCreacion"));
                    solicitud.setMensaje(resultSet.getString("mensaje"));
                    solicitud.setIdProfesor(resultSet.getInt("Profesor_idProfesor"));
                    solicitud.setIdEstadoSolicitud(resultSet.getInt("Estado_Solicitud_idEstadoSolicitud"));
                    solicitudes.add(solicitud);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return solicitudes;
    }

    @Override
    public List<SolicitudColaboracion> consultarSolicitudesColaboracionPorEstado(boolean aprobada) {
        List<SolicitudColaboracion> solicitudes = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(CONSULTAR_SOLICITUDES_COLABORACION_POR_ESTADO)) {
            int estado = aprobada ? 1 : 0;
            preparedStatement.setInt(1, estado);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    SolicitudColaboracion solicitud = new SolicitudColaboracion();
                    solicitud.setIdSolicitudColaboracion(resultSet.getInt("idSolicitudColaboracion"));
                    solicitud.setFechaCreacion(resultSet.getString("fechaCreacion"));
                    solicitud.setMensaje(resultSet.getString("mensaje"));
                    solicitud.setIdProfesor(resultSet.getInt("Profesor_idProfesor"));
                    solicitud.setIdEstadoSolicitud(resultSet.getInt("Estado_Solicitud_idEstadoSolicitud"));
                    solicitudes.add(solicitud);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return solicitudes;
    }
}
