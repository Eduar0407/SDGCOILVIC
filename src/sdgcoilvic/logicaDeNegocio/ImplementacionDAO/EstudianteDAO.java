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
import sdgcoilvic.logicaDeNegocio.clases.Estudiante;
import sdgcoilvic.logicaDeNegocio.interfaces.IEstudiante;

public class EstudianteDAO implements IEstudiante {
    private static final String INSERTAR_ESTUDIANTE = "INSERT INTO estudiante (nombre, apellidoPaterno, "
            + "apellidoMaterno, correo, Institucion_claveInstitucional) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String OBTENER_ESTUDIANTE_POR_NOMBRE = "SELECT nombre, apellidoPaterno, "
            + "apellidoMaterno, correo, Institucion_claveInstitucional "
            + "FROM estudiante WHERE nombre = ?";
    private static final String OBTENER_TODOS_LOS_ESTUDIANTES = "SELECT * FROM estudiante";
    @Override
    public int registrarEstudiante(Estudiante estudiante) {
        int columnaAfectada = 0;
        String consulta = INSERTAR_ESTUDIANTE;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, estudiante.getNombre());
            preparedStatement.setString(2, estudiante.getApellidoPaterno());
            preparedStatement.setString(3, estudiante.getApellidoMaterno());
            preparedStatement.setString(4, estudiante.getCorreo());
            preparedStatement.setString(5, estudiante.getClaveInstitucional());
            columnaAfectada = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                estudiante.setIdEstudiante(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public Estudiante obtenerEstudiantePorNombre(String nombreEstudiante) {
        String consulta = OBTENER_ESTUDIANTE_POR_NOMBRE;
        Estudiante estudiante = new Estudiante();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, nombreEstudiante);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    estudiante.setNombre(resultSet.getString("nombre"));
                    estudiante.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                    estudiante.setCorreo(resultSet.getString("correo"));
                    estudiante.setClaveInstitucional(resultSet.getString("Institucion_claveInstitucional"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        String consulta = OBTENER_TODOS_LOS_ESTUDIANTES;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(resultSet.getInt("idEstudiante"));
                estudiante.setNombre(resultSet.getString("nombre"));
                estudiante.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                estudiante.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                estudiante.setCorreo(resultSet.getString("correo"));
                estudiante.setClaveInstitucional(resultSet.getString("Institucion_claveInstitucional"));
                listaEstudiantes.add(estudiante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstudiantes;
    }

}
