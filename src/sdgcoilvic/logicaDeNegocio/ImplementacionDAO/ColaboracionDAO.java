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
import sdgcoilvic.logicaDeNegocio.clases.Colaboracion;
import sdgcoilvic.logicaDeNegocio.interfaces.IColaboracion;

public class ColaboracionDAO implements IColaboracion {
    private static final String INSERTAR_COLABORACION = "INSERT INTO colaboracion_coilvic (nombreCurso, descripcion, recursos, Periodo_idPeriodo, aprendizajesEsperados, detallesAsistenciaParticipacion, detallesEvaluacion, detallesEntorno, Estado_Colaboracion_idEstadoColaboracion, Calendario_Actividades_idCalendarioActividades, Propuesta_Colaboracion_idPropuestaColaboracion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FINALIZAR_COLABORACION = "UPDATE colaboracion_coilvic SET Estado_Colaboracion_idEstadoColaboracion = ? WHERE idColaboracion = ?";
    private static final String CERRAR_COLABORACION = "UPDATE colaboracion_coilvic SET Estado_Colaboracion_idEstadoColaboracion = ? WHERE idColaboracion = ?";
    private static final String INICIAR_COLABORACION = "UPDATE colaboracion_coilvic SET Estado_Colaboracion_idEstadoColaboracion = ? WHERE idColaboracion = ?";
    private static final String MODIFICAR_COLABORACION = "UPDATE colaboracion_coilvic SET nombreCurso = ?, descripcion = ?, recursos = ?, Periodo_idPeriodo = ?, aprendizajesEsperados = ?, detallesAsistenciaParticipacion = ?, detallesEvaluacion = ?, detallesEntorno = ?, Estado_Colaboracion_idEstadoColaboracion = ?, Calendario_Actividades_idCalendarioActividades = ?, Propuesta_Colaboracion_idPropuestaColaboracion = ? WHERE idColaboracion = ?";
    private static final String CONSULTAR_COLABORACION = "SELECT * FROM colaboracion_coilvic WHERE idColaboracion = ?";
    private static final String CONSULTAR_TODAS_COLABORACIONES = "SELECT * FROM colaboracion_coilvic";
    private static final String FILTRAR_COLABORACIONES = "SELECT * FROM colaboracion_coilvic WHERE nombreCurso LIKE ? OR descripcion LIKE ?";

    @Override
    public int crearColaboracion(Colaboracion colaboracion) {
        int resultado = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(INSERTAR_COLABORACION)) {
            preparedStatement.setString(1, colaboracion.getNombreCurso());
            preparedStatement.setString(2, colaboracion.getDescripcion());
            preparedStatement.setString(3, colaboracion.getRecursos());
            preparedStatement.setInt(4, colaboracion.getIdPeriodo());
            preparedStatement.setString(5, colaboracion.getAprendizajesEsperados());
            preparedStatement.setString(6, colaboracion.getDetallesAsistenciaParticipacion());
            preparedStatement.setString(7, colaboracion.getDetallesEvaluacion());
            preparedStatement.setString(8, colaboracion.getDetallesEntorno());
            preparedStatement.setInt(9, colaboracion.getIdEstadoColaboracion());
            preparedStatement.setInt(10, colaboracion.getIdCalendarioActividades());
            preparedStatement.setInt(11, colaboracion.getIdPropuestaColaboracion());
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
public int finalizarColaboracion(int idColaboracion) {
    int idEstadoFinalizado = 2;
    return actualizarEstadoColaboracion(idColaboracion, idEstadoFinalizado, FINALIZAR_COLABORACION);
}


    @Override
    public int cerrarColaboracion(int idColaboracion) {
    int idEstadoCerrado = 3;
    return actualizarEstadoColaboracion(idColaboracion, idEstadoCerrado, CERRAR_COLABORACION);
}

@Override
    public int iniciarColaboracion(int idColaboracion) {
    int idEstadoActivo = 1;
    return actualizarEstadoColaboracion(idColaboracion, idEstadoActivo, INICIAR_COLABORACION);
}


    @Override
    public int modificarColaboracion(Colaboracion colaboracion) {
        int resultado = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(MODIFICAR_COLABORACION)) {
            preparedStatement.setString(1, colaboracion.getNombreCurso());
            preparedStatement.setString(2, colaboracion.getDescripcion());
            preparedStatement.setString(3, colaboracion.getRecursos());
            preparedStatement.setInt(4, colaboracion.getIdPeriodo());
            preparedStatement.setString(5, colaboracion.getAprendizajesEsperados());
            preparedStatement.setString(6, colaboracion.getDetallesAsistenciaParticipacion());
            preparedStatement.setString(7, colaboracion.getDetallesEvaluacion());
            preparedStatement.setString(8, colaboracion.getDetallesEntorno());
            preparedStatement.setInt(9, colaboracion.getIdEstadoColaboracion());
            preparedStatement.setInt(10, colaboracion.getIdCalendarioActividades());
            preparedStatement.setInt(11, colaboracion.getIdPropuestaColaboracion());
            preparedStatement.setInt(12, colaboracion.getIdColaboracion());
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public Colaboracion consultarColaboracion(int idColaboracion) {
        Colaboracion colaboracion = null;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(CONSULTAR_COLABORACION)) {
            preparedStatement.setInt(1, idColaboracion);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultSet.getInt("idColaboracion"));
                    colaboracion.setNombreCurso(resultSet.getString("nombreCurso"));
                    colaboracion.setDescripcion(resultSet.getString("descripcion"));
                    colaboracion.setRecursos(resultSet.getString("recursos"));
                    colaboracion.setIdPeriodo(resultSet.getInt("Periodo_idPeriodo"));
                    colaboracion.setAprendizajesEsperados(resultSet.getString("aprendizajesEsperados"));
                    colaboracion.setDetallesAsistenciaParticipacion(resultSet.getString("detallesAsistenciaParticipacion"));
                    colaboracion.setDetallesEvaluacion(resultSet.getString("detallesEvaluacion"));
                    colaboracion.setDetallesEntorno(resultSet.getString("detallesEntorno"));
                    colaboracion.setIdEstadoColaboracion(resultSet.getInt("Estado_Colaboracion_idEstadoColaboracion"));
                    colaboracion.setIdCalendarioActividades(resultSet.getInt("Calendario_Actividades_idCalendarioActividades"));
                    colaboracion.setIdPropuestaColaboracion(resultSet.getInt("Propuesta_Colaboracion_idPropuestaColaboracion"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colaboracion;
    }

    @Override
    public List<Colaboracion> consultarTodasColaboraciones() {
        List<Colaboracion> colaboraciones = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(CONSULTAR_TODAS_COLABORACIONES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Colaboracion colaboracion = new Colaboracion();
                colaboracion.setIdColaboracion(resultSet.getInt("idColaboracion"));
                colaboracion.setNombreCurso(resultSet.getString("nombreCurso"));
                colaboracion.setDescripcion(resultSet.getString("descripcion"));
                colaboracion.setRecursos(resultSet.getString("recursos"));
                colaboracion.setIdPeriodo(resultSet.getInt("Periodo_idPeriodo"));
                colaboracion.setAprendizajesEsperados(resultSet.getString("aprendizajesEsperados"));
                colaboracion.setDetallesAsistenciaParticipacion(resultSet.getString("detallesAsistenciaParticipacion"));
                colaboracion.setDetallesEvaluacion(resultSet.getString("detallesEvaluacion"));
                colaboracion.setDetallesEntorno(resultSet.getString("detallesEntorno"));
                colaboracion.setIdEstadoColaboracion(resultSet.getInt("Estado_Colaboracion_idEstadoColaboracion"));
                colaboracion.setIdCalendarioActividades(resultSet.getInt("Calendario_Actividades_idCalendarioActividades"));
                colaboracion.setIdPropuestaColaboracion(resultSet.getInt("Propuesta_Colaboracion_idPropuestaColaboracion"));
                colaboraciones.add(colaboracion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colaboraciones;
    }

    @Override
    public List<Colaboracion> filtrarColaboraciones(String filtro) {
        List<Colaboracion> colaboraciones = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(FILTRAR_COLABORACIONES)) {
            preparedStatement.setString(1, "%" + filtro + "%");
            preparedStatement.setString(2, "%" + filtro + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultSet.getInt("idColaboracion"));
                    colaboracion.setNombreCurso(resultSet.getString("nombreCurso"));
                    colaboracion.setDescripcion(resultSet.getString("descripcion"));
                    colaboracion.setRecursos(resultSet.getString("recursos"));
                    colaboracion.setIdPeriodo(resultSet.getInt("Periodo_idPeriodo"));
                    colaboracion.setAprendizajesEsperados(resultSet.getString("aprendizajesEsperados"));
                    colaboracion.setDetallesAsistenciaParticipacion(resultSet.getString("detallesAsistenciaParticipacion"));
                    colaboracion.setDetallesEvaluacion(resultSet.getString("detallesEvaluacion"));
                    colaboracion.setDetallesEntorno(resultSet.getString("detallesEntorno"));
                    colaboracion.setIdEstadoColaboracion(resultSet.getInt("Estado_Colaboracion_idEstadoColaboracion"));
                    colaboracion.setIdCalendarioActividades(resultSet.getInt("Calendario_Actividades_idCalendarioActividades"));
                    colaboracion.setIdPropuestaColaboracion(resultSet.getInt("Propuesta_Colaboracion_idPropuestaColaboracion"));
                    colaboraciones.add(colaboracion);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colaboraciones;
    }

    private int actualizarEstadoColaboracion(int idColaboracion, int idEstado, String consultaSQL) {
    int resultado = 0;
    try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
         PreparedStatement preparedStatement = conexion.prepareStatement(consultaSQL)) {
        preparedStatement.setInt(1, idEstado);
        preparedStatement.setInt(2, idColaboracion);
        resultado = preparedStatement.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return resultado;
}

}
