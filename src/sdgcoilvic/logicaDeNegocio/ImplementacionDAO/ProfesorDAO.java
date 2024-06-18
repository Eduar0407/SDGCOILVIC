package sdgcoilvic.logicaDeNegocio.implementacionDAO;

import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;
import org.apache.log4j.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.interfaces.IProfesor;


public class ProfesorDAO implements IProfesor {
    private static final Logger LOG = Logger.getLogger(ProfesorDAO.class);
    private static final String INSERTAR_PROFESOR = """
            INSERT INTO profesor (nombre, apellidoPaterno, apellidoMaterno, correo, idIdiomas, idAcceso, estadoProfesor, Institucion_claveInstitucional)
            VALUES ( ?, ?, ?, ?, ?, ?, ?, ?); """;
    private static final String AGREGAR_ACCESO = "{call insertarAcceso(?, ?, ?, ?)}";
    private static final String OBTENER_TODOS_LOS_PROFESORES = """
            SELECT p.idProfesor, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.correo, p.idIdiomas, p.idAcceso, p.estadoProfesor, i.nombreInstitucion 
            FROM profesor p JOIN institucion i ON p.Institucion_claveInstitucional = i.claveInstitucional;""";
    private static final String OBTENER_LISTA_DE_IDIOMA = "SELECT idIdiomas, nombreIdioma FROM idiomas;";
    private static final String OBTENER_LISTA_DE_INTITUCIONES = "SELECT claveInstitucional, nombreInstitucion FROM institucion;";
    private static final String VERIFICAR_EXISTENCIA_NOMBRE_PROFESOR = "SELECT COUNT(*) AS number_of_matches FROM profesor WHERE (nombre = ?) AND (apellidoPaterno = ? AND apellidoMaterno = ?)";
    private static final String VERIFICAR_EXISTENCIA_CORREO = "SELECT COUNT(*) AS number_of_matches FROM profesor WHERE correo = ?";
    private static final String OBTENER_TODOS_LOS_ESTADOPROFESOR= "SELECT estadoProfesor FROM profesor";
    private static final String ELIMINAR_PROFESOR = """
            DELETE profesor, acceso FROM profesor JOIN acceso ON profesor.idAcceso = acceso.idAcceso
            WHERE profesor.correo = ? ;""";
    private static final String OBTENER_PROFESOR_CORREO = "SELECT * FROM profesor WHERE correo = ?;";
    private static final String OBTENER_NOMBRES_DE_INSTITUCION = "SELECT nombreInstitucion FROM institucion;";
    private static final String OBTENER_PROFESOR_POR_ID = "SELECT * FROM profesor WHERE idProfesor = ?;";
    private static final String OBTENER_PROFESORES_POR_NOMBRE= """
            SELECT idProfesor, nombre, apellidoPaterno, apellidoMaterno, correo, idIdiomas, 
                idAcceso, estadoProfesor, Institucion_claveInstitucional FROM profesor
            WHERE nombre LIKE ? OR apellidoPaterno LIKE ? OR apellidoMaterno LIKE ? ; """;
    private static final String IDENTIFICAR_PROFESOR = 
            "SELECT IF(puv.noPersonal IS NOT NULL, puv.noPersonal, p.idProfesor) AS resultado FROM profesor p " +
            "LEFT JOIN profesor_uv puv ON p.idProfesor = puv.Profesor_idProfesor WHERE p.correo = ?";
    private static final String ACTUALIZAR_PROFESOR = "UPDATE profesor SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " +
            "idIdiomas = ?, estadoProfesor = ?, Institucion_claveInstitucional = ? WHERE idProfesor = ?";
    private static final String VERIFICAR_QUE_EL_PROFESOR_NO_SE_REPITA = "SELECT COUNT(*) FROM profesor " +
            "WHERE nombre = ? AND apellidoPaterno = ? AND apellidoMaterno = ? AND correo = ? " +
            "AND idIdiomas = ? AND estadoProfesor = ? AND Institucion_claveInstitucional = ?";

    @Override
    public int registrarProfesor(Profesor profesor, Acceso acceso) throws SQLException{
       int numeroFilasAfectada = -1;
       int idAccesoGenerado = -1;
       int idProfesorGenerado = -1;

       try {
                Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
                conexion.setAutoCommit(false);
                CallableStatement callableStatement = conexion.prepareCall(AGREGAR_ACCESO);
                callableStatement.registerOutParameter(1, Types.INTEGER); 
                callableStatement.setString(2, acceso.getContrasenia());
                callableStatement.setString(3, acceso.getUsuario());
                callableStatement.setString(4, acceso.getTipoUsuario());
                callableStatement.execute();

                idAccesoGenerado = callableStatement.getInt(1);

                PreparedStatement statement = conexion.prepareStatement(INSERTAR_PROFESOR, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, profesor.getNombre());
                statement.setString(2, profesor.getApellidoPaterno());
                statement.setString(3, profesor.getApellidoMaterno());
                statement.setString(4, profesor.getCorreo());
                statement.setInt(5, profesor.getIdIdiomas());
                statement.setInt(6, idAccesoGenerado);
                statement.setString(7, profesor.getEstadoProfesor());
                statement.setString(8, profesor.getClaveInstitucional());
                numeroFilasAfectada = statement.executeUpdate();
                
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idProfesorGenerado = generatedKeys.getInt(1);
                    profesor.setIdProfesor(idProfesorGenerado);
                }

                conexion.commit();
       } catch (SQLException ex) {
           
           ManejadorBaseDeDatos.obtenerConexion().rollback();
       } finally {
           ManejadorBaseDeDatos.obtenerConexion().close();
       }
       return numeroFilasAfectada;
    }
    
    @Override
    public int actualizarInformacionDelProfesor(Profesor profesor, String idProfesor) throws SQLException{
        int resutado = -1;
        String query = ACTUALIZAR_PROFESOR;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, profesor.getNombre());
        statement.setString(2, profesor.getApellidoPaterno());
        statement.setString(3, profesor.getApellidoMaterno());
        statement.setString(4, profesor.getCorreo());
        statement.setInt(5, profesor.getIdIdiomas());
        statement.setString(6, profesor.getEstadoProfesor());
        statement.setString(7, profesor.getClaveInstitucional());
        statement.setString(8, idProfesor);
        resutado = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return resutado; 
    }
    
    @Override
    public boolean verificarProfesorDuplicado(Profesor profesor) throws SQLException {
        boolean existeProfesor = false;
            Connection connection = ManejadorBaseDeDatos.obtenerConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(VERIFICAR_QUE_EL_PROFESOR_NO_SE_REPITA);
            preparedStatement.setString(1, profesor.getNombre());
            preparedStatement.setString(2, profesor.getApellidoPaterno());
            preparedStatement.setString(3, profesor.getApellidoMaterno());
            preparedStatement.setString(4, profesor.getCorreo());
            preparedStatement.setInt(5, profesor.getIdIdiomas());
            preparedStatement.setString(6, profesor.getEstadoProfesor());
            preparedStatement.setString(7, profesor.getClaveInstitucional());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                existeProfesor = count > 0;
            }
        
        ManejadorBaseDeDatos.cerrarConexion();
        return existeProfesor;
    }
   
    
     @Override
     public List<Profesor> obtenerListaProfesoresPorNombre(String criterioBusqueda) throws SQLException {
        String consulta = OBTENER_PROFESORES_POR_NOMBRE;
            List<Profesor> listaProfesoresPorNombre = new ArrayList<>();
            Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
                String criterioBusquedaLike = "%" + criterioBusqueda + "%";
                statement.setString(1, criterioBusquedaLike);
                statement.setString(2, criterioBusquedaLike);
                statement.setString(3, criterioBusquedaLike);
            ResultSet resultado = statement.executeQuery(); 
            listaProfesoresPorNombre = obtenerListaProfesores(resultado);
         
            ManejadorBaseDeDatos.cerrarConexion();
        return listaProfesoresPorNombre;
     }
               
    @Override
    public int eliminarProfesor(String correo) {
        int columnaAfectada = -1;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement preparedStatement = conexion.prepareStatement(ELIMINAR_PROFESOR);
            preparedStatement.setString(1, correo);
            columnaAfectada = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        
        return columnaAfectada;
    }
    
    @Override
    public String identitificarProfesorUVOProfesorExterno(String correo) {
        String resultado = "";
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement preparedStatement = conexion.prepareStatement(IDENTIFICAR_PROFESOR);
            preparedStatement.setString(1, correo);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultado = resultSet.getString("resultado");
            }
        
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        
        return resultado;
    }

    @Override
    public boolean verificarExistenciaProfesor(String nombre, String apellidoPaterno, String apellidoMaterno) throws SQLException {
        boolean existenciaProfesor = true;
        String consulta = VERIFICAR_EXISTENCIA_NOMBRE_PROFESOR;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, nombre);
        statement.setString(2, apellidoPaterno);
        statement.setString(3, apellidoMaterno);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            int NOT_MATCHES = 0;
            
            if (resultado.getInt("number_of_matches") == NOT_MATCHES) {
                existenciaProfesor = false;
            }
        }
        ManejadorBaseDeDatos.cerrarConexion();
        
        return existenciaProfesor;
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
    public List<List<String>> obtenerListaDeIdiomas() {
    List<List<String>> listaDeIdiomas = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_IDIOMA;
         try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                int idIdioma = resultado.getInt("idIdiomas");
                String nombreIdioma = resultado.getString("nombreIdioma");
                List<String> idioma = new ArrayList<>();
                idioma.add(Integer.toString(idIdioma));
                idioma.add(nombreIdioma);
                listaDeIdiomas.add(idioma);
        }  
        } catch (SQLException ex) {
            LOG.error(ex);
        }  
        return listaDeIdiomas;
    }
    
    @Override
     public List<String>obtenerListaDeTodosLosEstadoProfesor() throws SQLException{
         List<String> listaEstadoProfesor = new ArrayList<>();
         String consulta = OBTENER_TODOS_LOS_ESTADOPROFESOR;
         Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                String estadoProfesor = resultado.getString("estadoProfesor");
                listaEstadoProfesor.add(estadoProfesor);
            }
        ManejadorBaseDeDatos.cerrarConexion();
        return listaEstadoProfesor;
     }
     
         @Override
     public List<String>obtenerListaDeNombreInstitucion() throws SQLException{
         List<String> listaEstadoProfesor = new ArrayList<>();
         String consulta = OBTENER_NOMBRES_DE_INSTITUCION;
         Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                String estadoProfesor = resultado.getString("nombreInstitucion");
                listaEstadoProfesor.add(estadoProfesor);
            }
        ManejadorBaseDeDatos.cerrarConexion();
        return listaEstadoProfesor;
     }
    
    @Override
    public List<List<String>> obtenerListaDeInstituciones() {
    List<List<String>> listaDeInstituciones = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_INTITUCIONES;
         try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
            String clave = resultado.getString("claveInstitucional");
            String nombreInstitucion = resultado.getString("nombreInstitucion");
            List<String> institucion = new ArrayList<>();
            institucion.add(clave);
            institucion.add(nombreInstitucion);
            listaDeInstituciones.add(institucion);
        }
        } catch (SQLException ex) {
            LOG.error(ex);
        }  
        return listaDeInstituciones;
     }


    @Override
    public Profesor obtenerProfesorPorCorreo (String correo) throws SQLException {
        Profesor profesor = new Profesor();
        String query = OBTENER_PROFESOR_CORREO;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, correo);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            profesor = obtenerProfesor(resultado);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesor;

    }


    @Override
    public List<Profesor> obtenerListaTodosLosProfesores() throws SQLException {
    List<Profesor> listaProfesores = new ArrayList<>();
    Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
    PreparedStatement statement = conexion.prepareStatement(OBTENER_TODOS_LOS_PROFESORES);
    ResultSet resultado = statement.executeQuery();

    while (resultado.next()) {
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(resultado.getInt("idProfesor"));
        profesor.setNombre(resultado.getString("nombre"));
        profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
        profesor.setCorreo(resultado.getString("correo"));
        profesor.setIdIdiomas(resultado.getInt("idIdiomas"));
        profesor.setIdAcceso(resultado.getInt("idAcceso"));
        profesor.setEstadoProfesor(resultado.getString("estadoProfesor"));
        profesor.setClaveInstitucional(resultado.getString("nombreInstitucion"));

        listaProfesores.add(profesor);
    }

    ManejadorBaseDeDatos.cerrarConexion();
    return listaProfesores;
    }
    
        @Override
    public Profesor obtenerProfesorPorID(String idProfesor) throws SQLException {
        Profesor profesor = new Profesor();
        String query = OBTENER_PROFESOR_POR_ID;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, idProfesor);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            profesor = obtenerProfesor(resultado);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesor;

    }

    
    private List<Profesor> obtenerListaProfesores(ResultSet result) throws SQLException {
        List<Profesor> listaProfesores = new ArrayList<>();
        
        while (result.next()) {
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
            listaProfesores.add(profesor);
        }
        return listaProfesores;
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
