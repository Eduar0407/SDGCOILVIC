package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.interfaces.IProfesor;

public class ProfesorDAO implements IProfesor {
    private static final int COLABORANDO = 1;
    private static final int SINCOLABORACION = 2;
    private static final int ARCHIVADO = 3;
    
    private static final String INSERTAR_PROFESOR = """
                                INSERT INTO profesor (idProfesor, nombre, apellidoPaterno, apellidoMaterno, correo, claveInstitucional, idIdiomas, idEstado_Profesor, idAcceso)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                                """;
    private static final String AGREGAR_ACCESO = "{call insertarAcceso(?, ?, ?, ?)}";
    private static final String ACTUALIZAR_PROFESOR = "UPDATE profesor SET nombre = ?, apellidoPaterno = ?,"
                                + " apellidoMaterno = ?, correo = ?, telefono = ?, Institucion_claveInstitucional = ?,"
                                + " Idiomas_idIdiomas = ?, foto = ? WHERE idProfesor = ?";
    private static final String OBTENER_PROFESOR_POR_NOMBRE = "SELECT nombre, apellidoPaterno, "
                                + "apellidoMaterno, correo, telefono "
                                + "FROM profesor WHERE nombre = ?";
    private static final String OBTENER_TODOS_LOS_PROFESORES = "SELECT * FROM profesor";
    private static final String OBTENER_LISTA_DE_IDIOMA = "SELECT idIdiomas, nombreIdioma FROM idiomas;";
    private static final String OBTENER_LISTA_DE_INTITUCIONES = "SELECT claveInstitucional, nombreInstitucion FROM institucion;";
    private static final String VERIFICAR_PROFESOR_EXISTENTE = "SELECT COUNT(*) AS number_of_matches FROM profesor WHERE (nombre = ?) AND (apellidoPaterno = ? AND apellidoMaterno = ?)";
    private static final String VERIFICAR_EXISTENCIA_CORREO = "SELECT COUNT(*) AS number_of_matches FROM profesor WHERE correo = ?";
    private static final String OBTENER_PROFESOR = "SELECT * FROM profesor WHERE idProfesor = ?";
    

    @Override
    public int registrarProfesor(Profesor profesor, Acceso acceso) throws SQLException{
       int columnaAfectada = -1;
       int idAccesoGenerado = -1;

       try {
                Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
                conexion.setAutoCommit(false);
                CallableStatement callableStatement = conexion.prepareCall(AGREGAR_ACCESO);
                callableStatement.registerOutParameter(1, Types.INTEGER); 
                callableStatement.setString(2, acceso.getContrasenia());
                callableStatement.setString(3, acceso.getUsuario());
                callableStatement.setInt(4, acceso.getTipoUsuario());
                callableStatement.execute();

                idAccesoGenerado = callableStatement.getInt(1);

                PreparedStatement statement = conexion.prepareStatement(INSERTAR_PROFESOR, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, profesor.getIdProfesor());
                statement.setString(2, profesor.getNombre());
                statement.setString(3, profesor.getApellidoPaterno());
                statement.setString(4, profesor.getApellidoMaterno());
                statement.setString(5, profesor.getCorreo());
                statement.setString(6, profesor.getClaveInstitucional());
                statement.setInt(7, profesor.getIdIdiomas());
                statement.setInt(8, profesor.getIdEstado());
                statement.setInt(9, idAccesoGenerado);

                columnaAfectada = statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    profesor.setIdProfesor(generatedKeys.getInt(1));
                }
                conexion.commit();
       } catch (SQLException ex) {
           ManejadorBaseDeDatos.obtenerConexion().rollback();
       } finally {
           ManejadorBaseDeDatos.obtenerConexion().close();
       }

       return columnaAfectada;
    }
    
    @Override
    public boolean verificarExistenciaProfesor(String nombre, String apellidoPaterno, String apellidoMaterno) throws SQLException {
        boolean existenciaProfesor = true;
        String consulta = VERIFICAR_PROFESOR_EXISTENTE;
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
    public List<List<String>> obtenerListaDeIdiomas() throws SQLException {
    List<List<String>> listaDeIdiomas = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_IDIOMA;
         Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
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
            
        return listaDeIdiomas;
    }
    
    @Override
    public List<List<String>> obtenerListaDeInstituciones() throws SQLException {
    List<List<String>> listaDeInstituciones = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_INTITUCIONES;
         Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
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
            
        return listaDeInstituciones;
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
            preparedStatement.setString(6, profesor.getClaveInstitucional());
            preparedStatement.setInt(7, profesor.getIdIdiomas());
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
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profesor;
    }

    @Override
    public List<Profesor> obtenerListaTodosLosProfesores() throws SQLException {
        String consulta = OBTENER_TODOS_LOS_PROFESORES;
        List<Profesor> listaProfesores = new ArrayList<>();
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        ResultSet resultado = statement.executeQuery();
        listaProfesores = obtenerListaProfesores(resultado);
            
        ManejadorBaseDeDatos.cerrarConexion();
        return listaProfesores;
    }
    
        @Override
    public Profesor obtenerProfesor(int idProfesor) throws SQLException {
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(-1);
        String query = OBTENER_PROFESOR;
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
    
    private List<Profesor> obtenerListaProfesores(ResultSet result) throws SQLException {
        List<Profesor> listaProfesores = new ArrayList<>();
        
        while (result.next()) {
            Profesor profesor = new Profesor();
            profesor.setIdProfesor(result.getInt("idProfesor"));  
            profesor.setNombre(result.getString("nombre"));
            profesor.setApellidoPaterno(result.getString("apellidoPaterno"));
            profesor.setApellidoMaterno(result.getString("apellidoMaterno"));
            profesor.setCorreo(result.getString("correo"));
            profesor.setClaveInstitucional(result.getString("claveInstitucional"));
            profesor.setIdIdiomas(result.getInt("idIdiomas"));
            profesor.setIdEstado(result.getInt("idEstado_Profesor"));
            profesor.setIdAcceso(result.getInt("idAcceso"));
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
            profesor.setClaveInstitucional(result.getString("claveInstitucional"));
            profesor.setIdIdiomas(result.getInt("idIdiomas"));
            profesor.setIdEstado(result.getInt("idEstado_Profesor"));
            profesor.setIdAcceso(result.getInt("idAcceso"));
        return profesor;
    }

 
}
