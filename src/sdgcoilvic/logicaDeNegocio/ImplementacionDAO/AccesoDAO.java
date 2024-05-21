package sdgcoilvic.logicaDeNegocio.implementacionDAO;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.interfaces.IAcceso;

public class AccesoDAO implements IAcceso {
    private static final Logger LOG = Logger.getLogger(AccesoDAO.class);
    private final String AGREGAR_ACCESO = "{CALL agregarAcceso(?, ?, ?)}";
    private final String ACCESO_EXISTENTE = "SELECT COUNT(*) FROM acceso WHERE usuario = ? AND contraseña = SHA2(?, 256)";
    private static final String OBTENER_ACCESO = "SELECT * FROM acceso WHERE usuario = ?";
    private static final String OBTENER_IDPROFESOR = """
                                                     SELECT idProfesor
                                                     FROM profesor
                                                     WHERE idAcceso = (SELECT idAcceso FROM acceso WHERE usuario = ?);""";
    private static final String OBTENER_PROFESOR_POR_ID = "SELECT * FROM profesor WHERE idProfesor = ?;";
    
    
    @Override
    public int agregarAcceso(Acceso acceso) throws SQLException {
        int resultado;
        String consulta = AGREGAR_ACCESO;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        CallableStatement statement = (CallableStatement) conexion.prepareCall(consulta);  
        statement.setString(1, acceso.getContrasenia());
        statement.setString(2, acceso.getUsuario());
        statement.setString(3, acceso.getTipoUsuario());
        resultado = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return resultado;    
    }

    @Override
    public int verificarExistenciaAcceso(String usuario, String contrasenia) throws SQLException {
        int existeAcceso = 0;
        String consulta = ACCESO_EXISTENTE;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, usuario);
        statement.setString(2, contrasenia);
        ResultSet resultado = statement.executeQuery();
        resultado.next();
        existeAcceso = resultado.getInt(1);
        ManejadorBaseDeDatos.cerrarConexion();
        return existeAcceso;
    }

    @Override
    public String obtenerTipoUsuario(String usuario) throws SQLException {
        String consulta = OBTENER_ACCESO;
        String tipoUsuario = "NoExiste";
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, usuario);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            tipoUsuario = resultado.getString("tipoUsuario");
        }

        return tipoUsuario;
    }
        @Override
    public int obtenerIdProfesor(String usuario) {
        String consulta = OBTENER_IDPROFESOR;
        int idProfesor = -1;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, usuario);
            ResultSet resultado = statement.executeQuery();
            if (resultado.next()) {
                idProfesor = resultado.getInt("idProfesor");
            }
        }catch (SQLException sqlException){
            LOG.fatal(sqlException.getMessage());
        }
        return idProfesor;
    }
    
            @Override
    public Profesor obtenerProfesorPorID(int idProfesor) throws SQLException {
        Profesor profesor = new Profesor();
        String query = OBTENER_PROFESOR_POR_ID;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProfesor);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            profesor = obtenerProfesor(resultado);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesor;

    }
    
        
    private Profesor obtenerProfesor(ResultSet result) throws SQLException {
        Profesor profesor = new Profesor();
            profesor.setIdProfesor(result.getInt("idProfesor"));
            profesor.setNombre(result.getString("nombre"));
            profesor.setApellidoPaterno(result.getString("apellidoPaterno"));
            profesor.setApellidoMaterno(result.getString("apellidoMaterno"));
            profesor.setCorreo(result.getString("correo"));
            profesor.setIdIdiomas(result.getInt("idIdiomas"));
            profesor.setEstadoProfesor(result.getString("estadoProfesor"));
            profesor.setIdAcceso(result.getInt("idAcceso"));
            profesor.setClaveInstitucional(result.getString("Institucion_claveInstitucional"));
        return profesor;
    }
   
}
