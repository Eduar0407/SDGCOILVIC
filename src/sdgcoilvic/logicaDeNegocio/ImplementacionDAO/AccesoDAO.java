package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.interfaces.IAcceso;

public class AccesoDAO implements IAcceso {
    private static final Logger LOG = Logger.getLogger(AccesoDAO.class);
    private final String AGREGAR_ACCESO = "{CALL agregarAcceso(?, ?, ?)}";
    private final String ACCESO_EXISTENTE = "SELECT COUNT(*) FROM acceso WHERE usuario = ? AND contraseña = SHA2(?, 256)";
    private static final String OBTENER_ACCESO = "SELECT * FROM acceso WHERE usuario = ?";

    /**
     * @param acceso el acceso a agregar
     * @return número de filas afectadas
     * @throws SQLException si hubo un problema con el acceso a la base de datos
     */
    @Override
    public int agregarAcceso(Acceso acceso) throws SQLException {
        int resultado;
        String consulta = AGREGAR_ACCESO;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        CallableStatement statement = (CallableStatement) conexion.prepareCall(consulta);  
        statement.setString(1, acceso.getContrasenia());
        statement.setString(2, acceso.getUsuario());
        statement.setInt(3, acceso.getTipoUsuario());
        resultado = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return resultado;    
    }

    /**
     * Comprueba si existe un acceso con las credenciales proporcionadas en la base de datos.
     * @param usuario el nombre de usuario
     * @param contrasenia la contraseña
     * @return número de coincidencias de la información ingresada
     * @throws SQLException si hubo un problema con el acceso a la base de datos
     */
    @Override
    public int existeAcceso(String usuario, String contrasenia) throws SQLException {
        int cantidad = 0;
        String consulta = ACCESO_EXISTENTE;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, usuario);
        statement.setString(2, contrasenia);
        ResultSet resultado = statement.executeQuery();
        resultado.next();
        cantidad = resultado.getInt(1);
        ManejadorBaseDeDatos.cerrarConexion();
        return cantidad;
    }

    /**
     * @param usuario el nombre de usuario
     * @return una instancia de Acceso
     * @throws SQLException si hubo un problema con el acceso a la base de datos
     */
    @Override
    public int obtenerTipoUsuario(String usuario) throws SQLException {
        String consulta = OBTENER_ACCESO;
        int tipoUsuario = -1;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta)){
        statement.setString(1, usuario);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            tipoUsuario = resultado.getInt("tipoUsuario");
        }
        }catch (SQLException ex){
        LOG.fatal(ex);
        }
        return tipoUsuario;
    }
   
}
