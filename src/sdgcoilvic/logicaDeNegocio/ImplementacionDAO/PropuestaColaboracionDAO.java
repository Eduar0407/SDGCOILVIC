package sdgcoilvic.logicaDeNegocio.implementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.PropuestaColaboracion;
import sdgcoilvic.logicaDeNegocio.interfaces.IPropuestaColaboracion;

public class PropuestaColaboracionDAO implements IPropuestaColaboracion {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PropuestaColaboracionDAO.class);
    
    private static final String INSERTAR_PROPUESTA = """
                                                     INSERT INTO propuesta_colaboracion (tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, perfilDeLosEstudiantes, idIdiomas, idPeriodo, Profesor_idProfesor, estadoPropuesta)
                                                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";
    private static final String EVALUAR_PROPUESTA = """
                                                    UPDATE propuesta_colaboracion
                                                    SET estadoPropuesta = ?
                                                    WHERE idPropuestaColaboracion = ?;""";
    private static final String CONSULTAR_PROPUESTA_POR_IDPROPUESTA = "SELECT * FROM propuesta_colaboracion WHERE idPropuestaColaboracion = ?";
    private static final String CONSULTAR_PROPUESTAS_POR_PROFESOR = "SELECT * FROM propuesta_colaboracion WHERE Profesor_idProfesor = ?";
    private static final String CONSULTAR_CORREO_PROFESOR = """
                                                            SELECT profesor.correo
                                                            FROM propuesta_colaboracion
                                                            JOIN profesor ON propuesta_colaboracion.Profesor_idProfesor = profesor.idProfesor
                                                            WHERE propuesta_colaboracion.idPropuestaColaboracion = ?; """;
    private static final String CONSULTAR_PROFESOR_POR_ID = """
                                                            SELECT CONCAT(p.nombre, ' ', p.apellidoPaterno, ' ', IFNULL(p.apellidoMaterno, '')) AS nombre_completo,
                                                                   i.nombreInstitucion AS nombre_institucion
                                                            FROM profesor p
                                                            JOIN institucion i ON p.Institucion_claveInstitucional = i.claveInstitucional
                                                            WHERE p.idProfesor = ?;""";
    private static final String ACTUALIZAR_PROPUESTA = "UPDATE propuesta_colaboracion SET tipoColaboracion = ?, nombre = ?, " +
            "objetivoGeneral = ?, temas = ?, numeroEstudiante = ?, informacionAdicional = ?, perfilDeLosEstudiantes = ?, idIdiomas = ?, " +
            "idPeriodo = ?, estadoPropuesta = ? WHERE idPropuestaColaboracion = ?";
    private static final String CONSULTAR_TODAS_LAS_PROPUESTAS_EN_ESPERA = "SELECT * FROM propuesta_colaboracion where estadoPropuesta='EnEspera';";
    private static final String CONSULTAR_TODAS_LAS_PROPUESTAS_ACEPTADAS = "SELECT * FROM propuesta_colaboracion where estadoPropuesta='Aceptada';";
    private static final String CONSULTAR_TODAS_LAS_PROPUESTAS_OFERTADAS = """
                                                                              SELECT * 
                                                                              FROM propuesta_colaboracion 
                                                                              WHERE estadoPropuesta = 'Ofertada' 
                                                                              AND Profesor_idProfesor != ?;""";
    private static final String CONSULTAR_LISRTA_NOMBRES_PROFESORES = """
                                                                                 SELECT idProfesor, 
                                                                                        CONCAT(nombre, ' ', apellidoPaterno, ' ', COALESCE(apellidoMaterno, '')) AS nombreCompleto 
                                                                                 FROM profesor;""";
 
    
    @Override
    public int agregarPropuestaColaboracion(PropuestaColaboracion propuestaColaboracion) throws SQLException{
        int filasAfectadas = -1;
        String consulta = INSERTAR_PROPUESTA;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, propuestaColaboracion.getTipoColaboracion());
            statement.setString(2, propuestaColaboracion.getNombre());
            statement.setString(3, propuestaColaboracion.getObjetivoGeneral());
            statement.setString(4, propuestaColaboracion.getTemas());
            statement.setInt(5, propuestaColaboracion.getNumeroEstudiante());
            statement.setString(6, propuestaColaboracion.getInformacionAdicional());
            statement.setString(7, propuestaColaboracion.getPerfilDeLosEstudiantes());
            statement.setInt(8, propuestaColaboracion.getIdIdiomas());
            statement.setInt(9, propuestaColaboracion.getIdPeriodo());
            statement.setInt(10, propuestaColaboracion.getIdProfesor());
            statement.setString(11, propuestaColaboracion.getEstadoPropuesta());
            filasAfectadas = statement.executeUpdate();

        return filasAfectadas;
    }
    
     @Override
    public int actualizarInformacionDeLaPropuesta (PropuestaColaboracion propuestaColaboracion, int idPropuesta) throws SQLException{
        int filasAfectadas = -1;
        String query = ACTUALIZAR_PROPUESTA; 
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, propuestaColaboracion.getTipoColaboracion());
        statement.setString(2, propuestaColaboracion.getNombre());
        statement.setString(3, propuestaColaboracion.getObjetivoGeneral());
        statement.setString(4, propuestaColaboracion.getTemas());
        statement.setInt(5, propuestaColaboracion.getNumeroEstudiante());
        statement.setString(6, propuestaColaboracion.getInformacionAdicional());
        statement.setString(7, propuestaColaboracion.getPerfilDeLosEstudiantes());
        statement.setInt(8, propuestaColaboracion.getIdIdiomas());
        statement.setInt(9, propuestaColaboracion.getIdPeriodo());
        statement.setString(10, propuestaColaboracion.getEstadoPropuesta());
        statement.setInt(  11, idPropuesta);
        filasAfectadas = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
       return filasAfectadas; 
       }
    
    @Override
    public int evaluarPropuestaColaboracion(int idPropuesta, String evaluacion)throws SQLException {
        int columnaAfectada =  -1;
        String consulta = EVALUAR_PROPUESTA;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, evaluacion);
        statement.setInt(2, idPropuesta);
        columnaAfectada = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return columnaAfectada;

    }
    
        @Override
    public int reevertirEstado(int idPropuesta)throws SQLException {
        int columnaAfectada =  -1;
        String consulta = EVALUAR_PROPUESTA;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setString(1, "EnEspera");
        statement.setInt(2, idPropuesta);
        columnaAfectada = statement.executeUpdate();
        ManejadorBaseDeDatos.cerrarConexion();
        return columnaAfectada;

    }

    @Override
    public List<PropuestaColaboracion> consultarTodasLasPropuestasColaboracionEnEspera() throws SQLException {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(CONSULTAR_TODAS_LAS_PROPUESTAS_EN_ESPERA);
        ResultSet resultado = statement.executeQuery();
        while (resultado.next()) {
            propuestas.add(obtenerPropuesta(resultado));
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return propuestas;
    }
    
    @Override
    public List<PropuestaColaboracion> consultarTodasLasPropuestasColaboracionAceptadas() throws SQLException {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(CONSULTAR_TODAS_LAS_PROPUESTAS_ACEPTADAS);
        ResultSet resultado = statement.executeQuery();
        while (resultado.next()) {
            propuestas.add(obtenerPropuesta(resultado));
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return propuestas;
    }
    
    @Override
    public List<PropuestaColaboracion> consultarTodasLasPropuestasColaboracionOfertadas(int idProfesor) throws SQLException {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(CONSULTAR_TODAS_LAS_PROPUESTAS_OFERTADAS);
        statement.setInt(1, idProfesor);
        ResultSet resultado = statement.executeQuery();
        while (resultado.next()) {
            propuestas.add(obtenerPropuesta(resultado));
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return propuestas;
    }

    @Override
    public List<PropuestaColaboracion> consultarPropuestasColaboracionPorProfesor(int idProfesor) throws SQLException {
        
        String consulta = CONSULTAR_PROPUESTAS_POR_PROFESOR;
        Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1, idProfesor);
        ResultSet resultado = statement.executeQuery();
        List<PropuestaColaboracion> listaPropuestas = new ArrayList<>();
        listaPropuestas = obtenerListPropuestaColaboracion(resultado);
        ManejadorBaseDeDatos.cerrarConexion();
        return listaPropuestas;
    }
    
    @Override
    public PropuestaColaboracion obtenerPropuestasColaboracionPorIdPropuesta(int idPropuesta) throws SQLException {
       PropuestaColaboracion propuestaColaboracion = new PropuestaColaboracion();
       String consulta = CONSULTAR_PROPUESTA_POR_IDPROPUESTA;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idPropuesta);
        ResultSet resultado = statement.executeQuery();
        if (resultado.next()) {
            propuestaColaboracion = obtenerPropuesta(resultado);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        
        return propuestaColaboracion;
    }
    
    private List<PropuestaColaboracion> obtenerListPropuestaColaboracion(ResultSet result) throws SQLException {
        List<PropuestaColaboracion> listaPropuestaColaboracion = new ArrayList<>();
        
        while (result.next()) {
            PropuestaColaboracion propuestaColaboracion = new PropuestaColaboracion();
            propuestaColaboracion.setIdPropuestaColaboracion(result.getInt("idPropuestaColaboracion"));
            propuestaColaboracion.setTipoColaboracion(result.getString("tipoColaboracion"));
            propuestaColaboracion.setNombre(result.getString("nombre"));
            propuestaColaboracion.setObjetivoGeneral(result.getString("objetivoGeneral"));
            propuestaColaboracion.setTemas(result.getString("temas"));
            propuestaColaboracion.setNumeroEstudiante(result.getInt("numeroEstudiante"));
            propuestaColaboracion.setInformacionAdicional(result.getString("informacionAdicional"));
            propuestaColaboracion.setPerfilDeLosEstudiantes(result.getString("perfilDeLosEstudiantes"));
            propuestaColaboracion.setIdIdiomas(result.getInt("idIdiomas"));
            propuestaColaboracion.setIdPeriodo(result.getInt("idPeriodo"));
            propuestaColaboracion.setIdProfesor(result.getInt("Profesor_idProfesor"));
            propuestaColaboracion.setEstadoPropuesta(result.getString("estadoPropuesta"));
            listaPropuestaColaboracion.add(propuestaColaboracion);
        }
        return listaPropuestaColaboracion;
    }

    private PropuestaColaboracion obtenerPropuesta(ResultSet result) throws SQLException {
        PropuestaColaboracion propuestaColaboracion = new PropuestaColaboracion();
            propuestaColaboracion.setIdPropuestaColaboracion(result.getInt("idPropuestaColaboracion"));
            propuestaColaboracion.setTipoColaboracion(result.getString("tipoColaboracion"));
            propuestaColaboracion.setNombre(result.getString("nombre"));
            propuestaColaboracion.setObjetivoGeneral(result.getString("objetivoGeneral"));
            propuestaColaboracion.setTemas(result.getString("temas"));
            propuestaColaboracion.setNumeroEstudiante(result.getInt("numeroEstudiante"));
            propuestaColaboracion.setInformacionAdicional(result.getString("informacionAdicional"));
            propuestaColaboracion.setPerfilDeLosEstudiantes(result.getString("perfilDeLosEstudiantes"));
            propuestaColaboracion.setIdIdiomas(result.getInt("idIdiomas"));
            propuestaColaboracion.setIdPeriodo(result.getInt("idPeriodo"));
            propuestaColaboracion.setIdProfesor(result.getInt("Profesor_idProfesor"));
            propuestaColaboracion.setEstadoPropuesta(result.getString("estadoPropuesta"));
        return propuestaColaboracion;
    }

    @Override
   public List<List<String>> obtenerListaDeNomnbreProfesorPorIdProfesor () throws SQLException{
    List<List<String>> listaDeProfesores = new ArrayList<>();
         String consulta = CONSULTAR_LISRTA_NOMBRES_PROFESORES;
         try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion()){
         PreparedStatement statement = conexion.prepareStatement(consulta);
         ResultSet resultado = statement.executeQuery(); 
            while (resultado.next()) {
                int idProfesor = resultado.getInt("idProfesor");
                String nombreCompleto = resultado.getString("nombreCompleto");
                List<String> Profesor = new ArrayList<>();
                Profesor.add(Integer.toString(idProfesor));
                Profesor.add(nombreCompleto);
                listaDeProfesores.add(Profesor);
        }  
        } catch (SQLException ex) {
            LOG.error(ex);
        }  
        return listaDeProfesores;
    }
   
    @Override
    public Profesor obtenerProfesorPorid (int idProfesor) throws SQLException {
        Profesor profesor = new Profesor();
        String consulta = CONSULTAR_PROFESOR_POR_ID;
        Connection connection = ManejadorBaseDeDatos.obtenerConexion();
        PreparedStatement statement = connection.prepareStatement(consulta);
        statement.setInt(1, idProfesor);
        ResultSet resultado = statement.executeQuery();

        if (resultado.next()) {
            String nombreCompleto = resultado.getString("nombre_completo");
            String nombreInstitucion = resultado.getString("nombre_institucion");
            profesor.setIdProfesor(idProfesor);
            profesor.setNombre(nombreCompleto);
            profesor.setClaveInstitucional(nombreInstitucion);
        }
        ManejadorBaseDeDatos.cerrarConexion();
        return profesor;
    }

    @Override
    public String obtenerCorreoPorIdPropuesta(int idPropuesta) {
        String correo = "";
        try (Connection connection = ManejadorBaseDeDatos.obtenerConexion()) {
            PreparedStatement statement = connection.prepareStatement(CONSULTAR_CORREO_PROFESOR);
            statement.setInt(1, idPropuesta);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                correo = resultSet.getString("correo");
            }
        } catch (SQLException ex) {
            LOG.error(ex);
        }
        return correo;
    }



}
