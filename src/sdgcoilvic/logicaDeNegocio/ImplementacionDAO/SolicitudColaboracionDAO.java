package sdgcoilvic.logicaDeNegocio.implementacionDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.interfaces.ISolicitudColaboracion;

public class SolicitudColaboracionDAO implements ISolicitudColaboracion {
    private static final String BUSCAR_SOLICITUDES = "{CALL buscarSolicitudesColaboracionPorProfesor(?)}";
    private static final String ENVIAR_SOLICITUD_DE_COLABORACION = "{CALL enviarSolicitudColaboracion(?, ?, ?, ?)}";
    private static final String RECHAZAR_SOLICITUD= """
                                                    UPDATE solicitud_colaboracion
                                                    SET estadoSolicitud = 'Rechazada'
                                                    WHERE idSolicitudColaboracion = ?;""";
    private static final String ACEPTAR_SOLICITUD  = """
                                                     UPDATE solicitud_colaboracion
                                                     SET estadoSolicitud = 'Aceptada'
                                                     WHERE idSolicitudColaboracion = ?;""";
    private static final String REEVERTIR_EVALUACION  = """
                                                     UPDATE solicitud_colaboracion
                                                     SET estadoSolicitud = 'EnEspera'
                                                     WHERE idSolicitudColaboracion = ?;""";
    private static final String EXISTE_ALMENOS_UNA_SOLICITUD  = """
                                                                SELECT 
                                                                    COALESCE(GROUP_CONCAT(sc.idSolicitudColaboracion), -1) AS solicitud_ids
                                                                FROM 
                                                                    solicitud_propuesta sp
                                                                JOIN 
                                                                    solicitud_colaboracion sc ON sp.idSolicitudColaboracion = sc.idSolicitudColaboracion
                                                                WHERE 
                                                                    sp.idPropuestaColaboracion = ? AND sc.estadoSolicitud = 'Aceptada';""";
    private static final String VERIFICAR_ESTADO_COLABORACION= """
                                                               SELECT COUNT(*) AS colaboraciones_en_curso
                                                               FROM colaboracion_coilvic AS cc
                                                               JOIN Profesor_has_Colaboracion_CoilVic AS phcc ON cc.idColaboracion = phcc.Colaboracion_CoilVic_idColaboracion
                                                               WHERE phcc.Profesor_idProfesor = ?
                                                               AND cc.estadoColaboracion = 'EnCurso';""";
    
    @Override
    public List<List<String>> consultarSolicitudesColaboracion(int idProfesor) throws SQLException {
        List<List<String>> solicitudes = new ArrayList<>();
            Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            CallableStatement callableStatement = conexion.prepareCall(BUSCAR_SOLICITUDES);
            callableStatement.setInt(1, idProfesor);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                            List<String> solicitud = new ArrayList<>();
                            solicitud.add(resultSet.getString("idSolicitudColaboracion"));
                            solicitud.add(resultSet.getString("nombrePropuesta"));
                            solicitud.add(resultSet.getString("nombreProfesorSolicitud"));
                            solicitud.add(resultSet.getString("nombreInstitucion"));
                            solicitud.add(resultSet.getString("idiomaPropuesta"));
                            solicitud.add(resultSet.getString("mensaje"));
                            solicitud.add(resultSet.getString("fechaSolicitud"));
                            solicitud.add(resultSet.getString("correoProfesorSolicitud"));
                            solicitudes.add(solicitud);
            }
            ManejadorBaseDeDatos.cerrarConexion(); 
        return solicitudes;
    }
    
    @Override
    public int enviarSolicitudDeColaboracion(int idPropuestaColaboracion, String mensaje, int idProfesor) throws SQLException {
        int resultado = -1;
            Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            CallableStatement callableStatement = conexion.prepareCall(ENVIAR_SOLICITUD_DE_COLABORACION);
            callableStatement.setInt(1, idPropuestaColaboracion);
            callableStatement.setString(2, mensaje);
            callableStatement.setInt(3, idProfesor);
            callableStatement.registerOutParameter(4, java.sql.Types.INTEGER);

            callableStatement.execute();

            resultado = callableStatement.getInt(4);

        ManejadorBaseDeDatos.cerrarConexion();     
        return resultado;
    }

    @Override
    public int rechazarSolicitud(int idSolicitudColaboracion) throws SQLException {
        int columnaAfectada =  -1;
        String consulta = RECHAZAR_SOLICITUD;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idSolicitudColaboracion);
        columnaAfectada = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return columnaAfectada;
    }

    @Override
    public int aceptarSolicitud(int idSolicitudColaboracion) throws SQLException {
        int columnaAfectada =  -1;
        String consulta = ACEPTAR_SOLICITUD;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idSolicitudColaboracion);
        columnaAfectada = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return columnaAfectada;
    }
    
        @Override
    public int reevertirEvaluacion(int idSolicitudColaboracion) throws SQLException {
        int columnaAfectada =  -1;
        String consulta = REEVERTIR_EVALUACION;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idSolicitudColaboracion);
        columnaAfectada = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return columnaAfectada;
    }
    
    @Override
    public String obtenerSolicitudesAprobadas(int idSolicitudColaboracion) throws SQLException {
        
        String resultado = "-1";
        String consulta = EXISTE_ALMENOS_UNA_SOLICITUD;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idSolicitudColaboracion);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    resultado = resultSet.getString("solicitud_ids");
                }
            }
        ManejadorBaseDeDatos.cerrarConexion();
        return resultado;
    }
    
    
    @Override
    public boolean verificarEstadoColaboracion(int idProfesor) throws SQLException {
        boolean hayColaboracionesEnCurso  = false;
        String consulta = VERIFICAR_ESTADO_COLABORACION;

        try (Connection connection = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(consulta)) {
            statement.setInt(1, idProfesor);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                int colaboracionesEnCurso = resultSet.getInt("colaboraciones_en_curso");
               hayColaboracionesEnCurso = (colaboracionesEnCurso > 0);
                }
            }
        }
        return hayColaboracionesEnCurso;
    }
}
