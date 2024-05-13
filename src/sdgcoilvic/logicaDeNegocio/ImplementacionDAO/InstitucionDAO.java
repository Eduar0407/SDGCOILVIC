package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;
import sdgcoilvic.logicaDeNegocio.interfaces.IInstitucion;

public class InstitucionDAO implements IInstitucion {
    private static final String AGREGAR_INSTITUCION = "{CALL agregarInstitucion(?, ?, ?, ?)}";
    private static final String VERIFICAR_EXISTENCIA_NOMBRE_INSTITUCION = "SELECT COUNT(*) AS number_of_matches FROM institucion WHERE nombreInstitucion = ?";
    private static final String VERIFICAR_EXISTENCIA_CORREO = "SELECT COUNT(*) AS number_of_matches FROM institucion WHERE correo = ?";
    private static final String VERIFICAR_EXISTENCIA_CLAVE = "SELECT COUNT(*) AS number_of_matches FROM institucion WHERE claveInstitucional = ?";
    private static final String CONSULTA_TODAS_LAS_INSTITUCIONES = """
                                                                   SELECT i.claveInstitucional, i.nombreInstitucion, p.nombre AS nombrePais, i.correo
                                                                   FROM institucion i 
                                                                   INNER JOIN pais p ON i.Pais_idPais = p.idPais;""";
    private static final String OBTENER_LISTA_DE_PAISES = "SELECT nombre FROM pais;";
    private static final String OBTENER_INSTITUCIONES_POR_NOMBRE= "SELECT i.claveInstitucional, i.nombreInstitucion, p.nombre AS nombrePais, i.correo " +
                                                                    "FROM institucion i " +
                                                                    "INNER JOIN pais p ON i.Pais_idPais = p.idPais " +
                                                                    "WHERE i.nombreInstitucion LIKE ?";

    
     /**
     * 
     * @param institucion La institución que se va a insertar en la base de datos.
     * @return El número de filas afectadas por la inserción. Devuelve 1 si se insertó correctamente, de lo contrario 0.
     * @throws SQLException Si ocurre algún error durante la inserción en la base de datos.
     */

    @Override
    public int insertarInstitucion(Institucion institucion) throws SQLException{
        int resultado = 0;
        try {
            ManejadorBaseDeDatos.obtenerConexion().setAutoCommit(false);
            CallableStatement statement = (CallableStatement) ManejadorBaseDeDatos.obtenerConexion().prepareCall(AGREGAR_INSTITUCION);
            statement.setString(1, institucion.getClaveInstitucional());
            statement.setString(2, institucion.getNombreInstitucion());
            statement.setString(3, institucion.getNombrePais());
            statement.setString(4, institucion.getCorreo());
            resultado = statement.executeUpdate();
            ManejadorBaseDeDatos.obtenerConexion().commit();
        } catch (SQLException ex) {
            ManejadorBaseDeDatos.obtenerConexion().rollback();
        } finally {
            ManejadorBaseDeDatos.obtenerConexion().close();
        }
        return resultado;
    }
    
    @Override
    public boolean verificarSiExisteElNombreInstitucion(String nombreInstitucion) throws SQLException {
        boolean existeNombreInstitucion = true;
        String consulta = VERIFICAR_EXISTENCIA_NOMBRE_INSTITUCION;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, nombreInstitucion);
        ResultSet resultado = statement.executeQuery();
        
        if (resultado.next()) {
            int NOT_MATCHES = 0;
            
            if (resultado.getInt("number_of_matches") == NOT_MATCHES) {
                existeNombreInstitucion = false;
            }
        }
        ManejadorBaseDeDatos.obtenerConexion().close();
        return existeNombreInstitucion;
    }
    
    @Override
    public boolean verificarSiExisteElCorreo(String correo) throws SQLException {
        boolean existeCorreo = true;
        String consulta = VERIFICAR_EXISTENCIA_CORREO;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, correo);
        ResultSet resultado = statement.executeQuery();
        
        if (resultado.next()) {
            int NOT_MATCHES = 0;
            
            if (resultado.getInt("number_of_matches") == NOT_MATCHES) {
                existeCorreo = false;
            }
        }
        ManejadorBaseDeDatos.obtenerConexion().close();
        return existeCorreo;
    }
    
    @Override
    public boolean verificarSiExisteLaClave(String claveInstitucion) throws SQLException {
        boolean existeClaveInstitucion = true;
        String consulta = VERIFICAR_EXISTENCIA_CLAVE;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, claveInstitucion);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            int NOT_MATCHES = 0;
            
            if (resultado.getInt("number_of_matches") == NOT_MATCHES) {
                existeClaveInstitucion = false;
            }
        }
        ManejadorBaseDeDatos.obtenerConexion().close();
        return existeClaveInstitucion;
    }
    
    @Override
     public List<Institucion> obtenerTodasLasInstituciones() throws SQLException {
        String consulta = CONSULTA_TODAS_LAS_INSTITUCIONES;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        Statement statement = (Statement) conexion.createStatement();
        ResultSet resultado = statement.executeQuery(consulta);
        List<Institucion> listaInstituciones = new ArrayList<>();  
        listaInstituciones = obtenerListInstitucion(resultado);
        ManejadorBaseDeDatos.cerrarConexion();
     
        return listaInstituciones; 
     }

     @Override
     public List<Institucion> obtenerListaInstitucionesPorNombre(String criterioBusqueda) throws SQLException {
        String consulta = OBTENER_INSTITUCIONES_POR_NOMBRE;
            List<Institucion> listaInstitucionesPorPais = new ArrayList<>();
            Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, "%" + criterioBusqueda + "%");
            ResultSet resultado = statement.executeQuery(); 
                while (resultado.next()) {
                    Institucion institucion = new Institucion();
                    institucion.setClaveInstitucional(resultado.getString("claveInstitucional"));
                    institucion.setNombreInstitucion(resultado.getString("nombreInstitucion"));
                    institucion.setNombrePais(resultado.getString("nombrePais"));
                    institucion.setCorreo(resultado.getString("correo"));
                    listaInstitucionesPorPais.add(institucion);
                }
            ManejadorBaseDeDatos.cerrarConexion();
        return listaInstitucionesPorPais;
     }
     
     
     @Override
     public List<String>obtenerListaDePaises() throws SQLException{
         List<String> paises = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_PAISES;
         Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                String nombre = resultado.getString("nombre");
                paises.add(nombre);
            }
        ManejadorBaseDeDatos.cerrarConexion();
        return paises;
     }
     
     
    /**
    * 
    * @param result el resultado de una consulta SQL 
    * @return una lista de instituciones
    * @throws SQLException si hubo un problema con el acceso a la base de datos 
    */

    private List<Institucion> obtenerListInstitucion(ResultSet result) throws SQLException {
        List<Institucion> listaInstitucion = new ArrayList<>();
        
        while (result.next()) {
            Institucion institucion = new Institucion();
            institucion.setClaveInstitucional(result.getString("claveInstitucional"));
            institucion.setNombreInstitucion(result.getString("nombreInstitucion"));
            institucion.setNombrePais(result.getString("nombrePais"));
            institucion.setCorreo(result.getString("correo"));
            listaInstitucion.add(institucion);
        }
        return listaInstitucion;
    }

}
