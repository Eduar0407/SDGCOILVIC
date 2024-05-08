package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.ActividadColaborativa;
import sdgcoilvic.logicaDeNegocio.interfaces.IActividadColaborativa;

public class ActividadColaborativaDAO implements IActividadColaborativa {
    private static final String INSERTAR_ACTIVIDAD_COLABORATIVA = "INSERT INTO actividad_colaborativa (nombreActividad, descripcion, puntaje, Calendario_Actividades_idCalendarioActividades) "
            + "VALUES (?, ?, ?, ?)";
    private static final String MODIFICAR_ACTIVIDAD_COLABORATIVA = "UPDATE actividad_colaborativa SET nombreActividad = ?, descripcion = ?, puntaje = ? WHERE idActividadColaborativa = ?";
    private static final String CONSULTAR_ACTIVIDAD_COLABORATIVA = "SELECT nombreActividad, descripcion, puntaje "
            + "FROM actividad_colaborativa WHERE idActividadColaborativa = ?";
    
    @Override
    public int agregarActividadColaborativa(ActividadColaborativa actividadColaborativa) {
        int columnaAfectada = 0;
        String consulta = INSERTAR_ACTIVIDAD_COLABORATIVA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, actividadColaborativa.getNombreActividad());
            preparedStatement.setString(2, actividadColaborativa.getDescripcion());
            preparedStatement.setInt(3, actividadColaborativa.getPuntaje());
            preparedStatement.setInt(4, actividadColaborativa.getIdCalendarioActividades());
            columnaAfectada = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                actividadColaborativa.setIdActividadColaborativa(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadColaborativaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public int modificarActividadColaborativa(ActividadColaborativa actividadColaborativa, int idActividadColaborativa) {
        int columnaAfectada = 0;
        String consulta = MODIFICAR_ACTIVIDAD_COLABORATIVA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            preparedStatement.setString(1, actividadColaborativa.getNombreActividad());
            preparedStatement.setString(2, actividadColaborativa.getDescripcion());
            preparedStatement.setInt(3, actividadColaborativa.getPuntaje());
            preparedStatement.setInt(4, idActividadColaborativa);
            columnaAfectada = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ActividadColaborativaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public ActividadColaborativa consultarActividadColaborativa(int idActividadColaborativa) {
        ActividadColaborativa actividad = new ActividadColaborativa();
        String consulta = CONSULTAR_ACTIVIDAD_COLABORATIVA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, idActividadColaborativa);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    actividad.setIdActividadColaborativa(idActividadColaborativa);
                    actividad.setNombreActividad(resultSet.getString("nombreActividad"));
                    actividad.setDescripcion(resultSet.getString("descripcion"));
                    actividad.setPuntaje(resultSet.getInt("puntaje"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActividadColaborativaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actividad;
    }
}
