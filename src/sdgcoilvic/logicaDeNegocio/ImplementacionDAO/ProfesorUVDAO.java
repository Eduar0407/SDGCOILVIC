package sdgcoilvic.logicaDeNegocio.implementacionDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;
import sdgcoilvic.logicaDeNegocio.interfaces.IProfesorUV;

public class ProfesorUVDAO implements IProfesorUV {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProfesorUVDAO.class);
    private static final String INSERTAR_PROFESOR = """
                                INSERT INTO profesor (nombre, apellidoPaterno, apellidoMaterno, correo, idIdiomas, idAcceso, estadoProfesor, Institucion_claveInstitucional)
                                VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);
                                """;
    private static final String INSERTAR_PROFESOR_UV = """
                                                       INSERT INTO profesor_uv (noPersonal, disciplina, idRegion, idCategoriaContratacionUV, idTipoContratacionUV, idAreaAcademica, Profesor_idProfesor)
                                                       VALUES (?, ?, ?, ?, ?, ?, ?);"""; 
    private static final String AGREGAR_ACCESO = "{call insertarAcceso(?, ?, ?, ?)}";
    private static final String VERIFICAR_EXISTENCIA_NOPERSONAL = "SELECT COUNT(*) AS number_of_matches FROM profesor_uv WHERE noPersonal = ?";
    private static final String OBTENER_LISTA_DE_CATEGORIA_CONTRATACION = "SELECT idCategoriaContratacionUV, categoriaContratacion FROM categoria_contratacion_uv;";
    private static final String OBTENER_LISTA_DE_TIPO_CONSTRATACION = "SELECT idTipoContratacionUV, tipoContratacion FROM tipo_contratacion_uv;";
    private static final String OBTENER_LISTA_DE_REGION = "SELECT idRegion, nombre FROM region;";
    private static final String OBTENER_LISTA_DE_AREA_ACADEMICA = "SELECT idAreaAcademica, area FROM area_academica;";
    private static final String ELIMINAR_PROFESOR = """
                                                    DELETE profesor_uv, profesor, acceso
                                                    FROM profesor_uv
                                                    JOIN profesor ON profesor_uv.Profesor_idProfesor = profesor.idProfesor
                                                    JOIN acceso ON profesor.idAcceso = acceso.idAcceso
                                                    WHERE profesor_uv.noPersonal = ?;
                                                    """;
    private static final String ACTUALIZAR_PROFESOR = 
        "UPDATE profesor SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, " +
        "correo = ?, idIdiomas = ?, estadoProfesor = ? " +
        "WHERE idProfesor = ?";

    private static final String ACTUALIZAR_PROFESOR_UV = 
        "UPDATE profesor_uv SET noPersonal = ?,disciplina = ?, idRegion = ?, idCategoriaContratacionUV = ?, " +
        "idTipoContratacionUV = ?, idAreaAcademica = ? WHERE Profesor_idProfesor = ?";
    private static final String OBTENER_ID_PROFESOR = 
        "SELECT Profesor_idProfesor FROM profesor_uv WHERE noPersonal = ?";
    private static final String OBTENER_PROFESORUV_POR_NUMPERSONAL = """
                                                                     SELECT profesor_uv.*, profesor.nombre, profesor.apellidoPaterno, profesor.apellidoMaterno, profesor.correo, profesor.idIdiomas, profesor.estadoProfesor, profesor.Institucion_claveInstitucional
                                                                     FROM profesor_uv
                                                                     INNER JOIN profesor ON profesor_uv.Profesor_idProfesor = profesor.idProfesor
                                                                     WHERE noPersonal = ?;""";

    @Override
    public int registrarProfesorUV(Acceso acceso,  ProfesorUV profesorUV) throws SQLException{
       int numeroFilasAfectada = -1;
       int idAccesoGenerado = -1;
       int idProfesorGenerado = -1;
       try {
                Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
                conexion.setAutoCommit(false);
                CallableStatement accesoStatement = conexion.prepareCall(AGREGAR_ACCESO);
                accesoStatement.registerOutParameter(1, Types.INTEGER); 
                accesoStatement.setString(2, acceso.getContrasenia());
                accesoStatement.setString(3, acceso.getUsuario());
                accesoStatement.setString(4, acceso.getTipoUsuario());
                accesoStatement.execute();

                idAccesoGenerado = accesoStatement.getInt(1);
                
                PreparedStatement profesorStatement = conexion.prepareStatement(INSERTAR_PROFESOR, Statement.RETURN_GENERATED_KEYS);
                profesorStatement.setString(1, profesorUV.getNombre());
                profesorStatement.setString(2, profesorUV.getApellidoPaterno());
                profesorStatement.setString(3, profesorUV.getApellidoMaterno());
                profesorStatement.setString(4, profesorUV.getCorreo());
                profesorStatement.setInt(5, profesorUV.getIdIdiomas());
                profesorStatement.setInt(6, idAccesoGenerado);
                profesorStatement.setString(7, profesorUV.getEstadoProfesor());
                profesorStatement.setString(8, profesorUV.getClaveInstitucional());
                numeroFilasAfectada = profesorStatement.executeUpdate();
                
                ResultSet generatedKeys = profesorStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idProfesorGenerado = generatedKeys.getInt(1);
                    profesorUV.setIdProfesor(idProfesorGenerado);
                }
                
                PreparedStatement profesorUVstatement = conexion.prepareStatement(INSERTAR_PROFESOR_UV);
                profesorUVstatement.setString(1, profesorUV.getNoPersonal());
                profesorUVstatement.setString(2, profesorUV.getDisciplina());
                profesorUVstatement.setInt(3, profesorUV.getIdRegion());
                profesorUVstatement.setInt(4, profesorUV.getIdCategoriaContratacionUV());
                profesorUVstatement.setInt(5, profesorUV.getIdTipoContratacionUV());
                profesorUVstatement.setInt(6, profesorUV.getIdAreaAcademica());
                profesorUVstatement.setInt(7,idProfesorGenerado );
                numeroFilasAfectada += profesorUVstatement.executeUpdate();
                conexion.commit();
                
       } catch (SQLException ex) {
           ManejadorBaseDeDatos.obtenerConexion().rollback();
       } finally {
           ManejadorBaseDeDatos.obtenerConexion().close();
       }
       return numeroFilasAfectada;
    }
    
        @Override
    public int eliminarProfesorUV(String noPersonal) {
        int columnaAfectada = -1;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement preparedStatement = conexion.prepareStatement(ELIMINAR_PROFESOR);
            preparedStatement.setString(1, noPersonal);
            columnaAfectada = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        
        return columnaAfectada;
    }

        @Override
    public boolean verificarSiExisteElNoPersonal(String noPersonal) throws SQLException {
        boolean existeCorreo = true;
        String consulta = VERIFICAR_EXISTENCIA_NOPERSONAL;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, noPersonal);
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
    public List<List<String>> obtenerListaDeRegion() {
        List<List<String>> listaDeRegion = new ArrayList<>();
         String consulta = OBTENER_LISTA_DE_REGION;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery(); 
               while (resultado.next()) {
               int idRegion = resultado.getInt("idRegion");
               String nombre = resultado.getString("nombre");
               List<String> region = new ArrayList<>();
               region.add(Integer.toString(idRegion));
               region.add(nombre);
               listaDeRegion.add(region);
           }
        } catch (SQLException ex) {
            LOG.error(ex);
        }
            
        return listaDeRegion;
    }

    @Override
    public List<List<String>> obtenerListaDeCategoriaContratacion() {
        List<List<String>> listaDeCategoriaContratacion = new ArrayList<>();
        String consulta = OBTENER_LISTA_DE_CATEGORIA_CONTRATACION;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                int idCategoriaContratacionUV = resultado.getInt("idCategoriaContratacionUV");
                String categoriaContratacion = resultado.getString("categoriaContratacion");
                List<String> categoria = new ArrayList<>();
                categoria.add(Integer.toString(idCategoriaContratacionUV));
                categoria.add(categoriaContratacion);
                listaDeCategoriaContratacion.add(categoria);
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return listaDeCategoriaContratacion;
    }

    @Override
    public List<List<String>> obtenerListaDeTipoContratacion(){
        List<List<String>> listaDeTipoContratacion = new ArrayList<>();
        String consulta = OBTENER_LISTA_DE_TIPO_CONSTRATACION;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                int idTipoContratacionUV = resultado.getInt("idTipoContratacionUV");
                String tipoContratacion = resultado.getString("tipoContratacion");
                List<String> tipo = new ArrayList<>();
                tipo.add(Integer.toString(idTipoContratacionUV));
                tipo.add(tipoContratacion);
                listaDeTipoContratacion.add(tipo);
            }
            
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return listaDeTipoContratacion;
    }

    @Override
    public List<List<String>> obtenerListaDeAreaAcademica()  {
        List<List<String>> listaDeAreaAcademica = new ArrayList<>();
        String consulta = OBTENER_LISTA_DE_AREA_ACADEMICA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                int idAreaAcademica = resultado.getInt("idAreaAcademica");
                String area = resultado.getString("area");
                List<String> areaAcademica = new ArrayList<>();
                areaAcademica.add(Integer.toString(idAreaAcademica));
                areaAcademica.add(area);
                listaDeAreaAcademica.add(areaAcademica);
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return listaDeAreaAcademica;
    }
    
    @Override
    public ProfesorUV obtenerProfesorUVPorNumPersonal (String numPersonal) throws SQLException {
        ProfesorUV profesorUV = new ProfesorUV();
        String query = OBTENER_PROFESORUV_POR_NUMPERSONAL;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, numPersonal);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            profesorUV = obtenerProfesorUV(resultado);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesorUV;

    }  
    
    private ProfesorUV obtenerProfesorUV(ResultSet result) throws SQLException {
        ProfesorUV profesorUV = new ProfesorUV();
        profesorUV.setNoPersonal(result.getString("noPersonal"));
        profesorUV.setDisciplina(result.getString("disciplina"));
        profesorUV.setIdRegion(result.getInt("idRegion"));
        profesorUV.setIdCategoriaContratacionUV(result.getInt("idCategoriaContratacionUV"));
        profesorUV.setIdTipoContratacionUV(result.getInt("idTipoContratacionUV"));
        profesorUV.setIdAreaAcademica(result.getInt("idAreaAcademica")); 
        
        return profesorUV;
    }
        
    @Override
    public int actualizarInformacionDelProfesorUV(Profesor profesor,ProfesorUV profesorUV, String noPersonal) throws SQLException {
        int resutado = 0;
        int idProfesor = -1;
        try {
            Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            conexion.setAutoCommit(false);
            PreparedStatement obtenerIdStatement = conexion.prepareStatement(OBTENER_ID_PROFESOR);
                obtenerIdStatement.setString(1, noPersonal);
                ResultSet rs = obtenerIdStatement.executeQuery();

                if (rs.next()) {
                    idProfesor = rs.getInt("Profesor_idProfesor");
                }
            PreparedStatement actualizarProfesorStatement = conexion.prepareStatement(ACTUALIZAR_PROFESOR);
            actualizarProfesorStatement.setString(1, profesor.getNombre());
            actualizarProfesorStatement.setString(2, profesor.getApellidoPaterno());
            actualizarProfesorStatement.setString(3, profesor.getApellidoMaterno());
            actualizarProfesorStatement.setString(4, profesor.getCorreo());
            actualizarProfesorStatement.setInt(5, profesor.getIdIdiomas());
            actualizarProfesorStatement.setString(6, profesor.getEstadoProfesor());
            actualizarProfesorStatement.setInt(7, idProfesor);
            resutado = actualizarProfesorStatement.executeUpdate();
            
            PreparedStatement actualizarProfesorUVStatement = conexion.prepareStatement(ACTUALIZAR_PROFESOR_UV);
            actualizarProfesorUVStatement.setString(1, profesorUV.getNoPersonal());
            actualizarProfesorUVStatement.setString(2, profesorUV.getDisciplina());
            actualizarProfesorUVStatement.setInt(3, profesorUV.getIdRegion());
            actualizarProfesorUVStatement.setInt(4, profesorUV.getIdCategoriaContratacionUV());
            actualizarProfesorUVStatement.setInt(5, profesorUV.getIdTipoContratacionUV());
            actualizarProfesorUVStatement.setInt(6, profesorUV.getIdAreaAcademica());
            actualizarProfesorUVStatement.setInt(7, idProfesor);
            resutado += actualizarProfesorUVStatement.executeUpdate();
            conexion.commit();
        } catch (SQLException ex) {
            ManejadorBaseDeDatos.obtenerConexion().rollback();
        }
        return resutado;
    }

    @Override
    public ProfesorUV obtenerProfesorPorNumPersonal(String numPersonal) throws SQLException {
        ProfesorUV profesorUV = new ProfesorUV();
        String query = OBTENER_PROFESORUV_POR_NUMPERSONAL;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, numPersonal);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
        profesorUV.setNoPersonal(resultado.getString("noPersonal"));
        profesorUV.setDisciplina(resultado.getString("disciplina"));
        profesorUV.setIdRegion(resultado.getInt("idRegion"));
        profesorUV.setIdCategoriaContratacionUV(resultado.getInt("idCategoriaContratacionUV"));
        profesorUV.setIdTipoContratacionUV(resultado.getInt("idTipoContratacionUV"));
        profesorUV.setIdAreaAcademica(resultado.getInt("idAreaAcademica")); 
        profesorUV.setIdProfesor(resultado.getInt("Profesor_idProfesor"));
        profesorUV.setNombre(resultado.getString("nombre"));
        profesorUV.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        profesorUV.setApellidoMaterno(resultado.getString("apellidoMaterno"));
        profesorUV.setCorreo(resultado.getString("correo"));
        profesorUV.setIdIdiomas(resultado.getInt("idIdiomas"));
        profesorUV.setEstadoProfesor(resultado.getString("estadoProfesor"));
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesorUV;
    }
    
}
