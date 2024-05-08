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
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.interfaces.IProfesor;

public class ProfesorDAO implements IProfesor {
    private static final String INSERTAR_PROFESOR = "INSERT INTO profesor (nombre, "
            + "apellidoPaterno, apellidoMaterno, correo, telefono, Institucion_claveInstitucional, "
            + "Idiomas_idIdiomas, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALIZAR_PROFESOR = "UPDATE profesor SET nombre = ?, apellidoPaterno = ?,"
            + " apellidoMaterno = ?, correo = ?, telefono = ?, Institucion_claveInstitucional = ?,"
            + " Idiomas_idIdiomas = ?, foto = ? WHERE idProfesor = ?";
    private static final String OBTENER_PROFESOR_POR_NOMBRE = "SELECT nombre, apellidoPaterno, "
            + "apellidoMaterno, correo, telefono "
            + "FROM profesor WHERE nombre = ?";
    private static final String OBTENER_TODOS_LOS_PROFESORES = "SELECT * FROM profesor";
    
    @Override
    public int registrarProfesor(Profesor profesor) {
        int columnaAfectada = 0;
        String consulta = INSERTAR_PROFESOR;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, profesor.getNombre());
            preparedStatement.setString(2, profesor.getApellidoPaterno());
            preparedStatement.setString(3, profesor.getApellidoMaterno());
            preparedStatement.setString(4, profesor.getCorreo());
            preparedStatement.setString(5, profesor.getTelefono());
            preparedStatement.setString(6, profesor.getClaveInstitucional());
            preparedStatement.setInt(7, profesor.getIdIdiomas());
            preparedStatement.setBlob(8, profesor.getFoto());
            columnaAfectada = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                profesor.setIdProfesor(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public int actualizarProfesor(Profesor profesor, int idProfesor) {
        String consulta = ACTUALIZAR_PROFESOR;
        int columnaAfectada = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            preparedStatement.setString(1, profesor.getNombre());
            preparedStatement.setString(2, profesor.getApellidoPaterno());
            preparedStatement.setString(3, profesor.getApellidoMaterno());
            preparedStatement.setString(4, profesor.getCorreo());
            preparedStatement.setString(5, profesor.getTelefono());
            preparedStatement.setString(6, profesor.getClaveInstitucional());
            preparedStatement.setInt(7, profesor.getIdIdiomas());
            preparedStatement.setBlob(8, profesor.getFoto());
            preparedStatement.setInt(9, profesor.getIdProfesor());
            columnaAfectada = preparedStatement.executeUpdate();
            
            } catch (SQLException ex) {
            Logger.getLogger(CalendarioActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public Profesor obtenerProfesorPorNombre(String nombreProfesor) {
        String consulta = OBTENER_PROFESOR_POR_NOMBRE;
        Profesor profesor = new Profesor(); 
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, nombreProfesor);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    profesor.setNombre(rs.getString("nombre"));
                    profesor.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    profesor.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    profesor.setCorreo(rs.getString("correo"));
                    profesor.setTelefono(rs.getString("telefono"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profesor;
    }

    @Override
    public List<Profesor> obtenerListaTodosLosProfesores() {
        List<Profesor> listaProfesores = new ArrayList<>();
        String consulta = OBTENER_TODOS_LOS_PROFESORES;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(resultSet.getInt("idProfesor"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreo(resultSet.getString("correo"));
                profesor.setTelefono(resultSet.getString("telefono"));
                profesor.setClaveInstitucional(resultSet.getString("Institucion_claveInstitucional"));
                profesor.setIdIdiomas(resultSet.getInt("Idiomas_idIdiomas"));
            
                listaProfesores.add(profesor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProfesores;
    }
 
}
