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
import sdgcoilvic.logicaDeNegocio.clases.PropuestaColaboracion;
import sdgcoilvic.logicaDeNegocio.interfaces.IPropuestaColaboracion;

public class PropuestaColaboracionDAO implements IPropuestaColaboracion {

    private static final String INSERTAR_PROPUESTA = "INSERT INTO propuesta_colaboracion (tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, PerfilDeLosEstudiantes, Idiomas_idIdiomas, Periodo_idPeriodo, Profesor_idProfesor, Estado_Propuesta_idEstadoPropuesta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String EVALUAR_PROPUESTA = "UPDATE propuesta_colaboracion SET Estado_Propuesta_idEstadoPropuesta = ? WHERE idPropuestaColaboracion = ?";
    private static final String CONSULTAR_PROPUESTAS = "SELECT idPropuestaColaboracion, tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, PerfilDeLosEstudiantes, Idiomas_idIdiomas, Periodo_idPeriodo, Profesor_idProfesor, Estado_Propuesta_idEstadoPropuesta FROM propuesta_colaboracion";
    private static final String CONSULTAR_PROPUESTAS_POR_PROFESOR = "SELECT * FROM propuesta_colaboracion WHERE Profesor_idProfesor = ?";
    private static final String CONSULTAR_PROPUESTAS_POR_ESTADO = "SELECT * FROM propuesta_colaboracion WHERE Estado_Propuesta_idEstadoPropuesta = ?";

    @Override
    public int someterPropuestaColaboracion(PropuestaColaboracion propuesta) {
        int resultado = 0;
        String consulta = INSERTAR_PROPUESTA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, propuesta.getTipoColaboracion());
            statement.setString(2, propuesta.getNombre());
            statement.setString(3, propuesta.getObjetivoGeneral());
            statement.setString(4, propuesta.getTemas());
            statement.setInt(5, propuesta.getNumeroEstudiante());
            statement.setString(6, propuesta.getInformacionAdicional());
            statement.setString(7, propuesta.getPerfilDeLosEstudiantes());
            statement.setInt(8, propuesta.getIdiomasId());
            statement.setInt(9, propuesta.getPeriodoId());
            statement.setInt(10, propuesta.getProfesorId());
            statement.setInt(11, propuesta.getEstadoPropuestaId());
            resultado = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public void evaluarPropuestaColaboracion(PropuestaColaboracion propuesta, boolean aprobada) {
        if (propuesta == null) {
            throw new IllegalArgumentException("La propuesta no puede ser nula");
        }
        String consulta = EVALUAR_PROPUESTA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, aprobada ? 1 : 0);
            statement.setInt(2, propuesta.getIdPropuestaColaboracion());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PropuestaColaboracion> consultarPropuestasColaboracion() {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        String consulta = CONSULTAR_PROPUESTAS;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idPropuestaColaboracion = resultSet.getInt("idPropuestaColaboracion");
                String tipoColaboracion = resultSet.getString("tipoColaboracion");
                String nombre = resultSet.getString("nombre");
                String objetivoGeneral = resultSet.getString("objetivoGeneral");
                String temas = resultSet.getString("temas");
                int numeroEstudiante = resultSet.getInt("numeroEstudiante");
                String informacionAdicional = resultSet.getString("informacionAdicional");
                String perfilDeLosEstudiantes = resultSet.getString("PerfilDeLosEstudiantes");
                int idiomasId = resultSet.getInt("Idiomas_idIdiomas");
                int periodoId = resultSet.getInt("Periodo_idPeriodo");
                int profesorId = resultSet.getInt("Profesor_idProfesor");
                int estadoPropuestaId = resultSet.getInt("Estado_Propuesta_idEstadoPropuesta");

                PropuestaColaboracion propuesta = new PropuestaColaboracion(idPropuestaColaboracion, tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, perfilDeLosEstudiantes, idiomasId, periodoId, profesorId, estadoPropuestaId);
                propuestas.add(propuesta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return propuestas;
    }

    @Override
    public List<PropuestaColaboracion> consultarPropuestasColaboracionPorProfesor(int idProfesor) {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        String consulta = CONSULTAR_PROPUESTAS_POR_PROFESOR;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, idProfesor);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idPropuestaColaboracion = resultSet.getInt("idPropuestaColaboracion");
                    String tipoColaboracion = resultSet.getString("tipoColaboracion");
                    String nombre = resultSet.getString("nombre");
                    String objetivoGeneral = resultSet.getString("objetivoGeneral");
                    String temas = resultSet.getString("temas");
                    int numeroEstudiante = resultSet.getInt("numeroEstudiante");
                    String informacionAdicional = resultSet.getString("informacionAdicional");
                    String perfilDeLosEstudiantes = resultSet.getString("PerfilDeLosEstudiantes");
                    int idiomasId = resultSet.getInt("Idiomas_idIdiomas");
                    int periodoId = resultSet.getInt("Periodo_idPeriodo");
                    int profesorId = resultSet.getInt("Profesor_idProfesor");
                    int estadoPropuestaId = resultSet.getInt("Estado_Propuesta_idEstadoPropuesta");

                    PropuestaColaboracion propuesta = new PropuestaColaboracion(idPropuestaColaboracion, tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, perfilDeLosEstudiantes, idiomasId, periodoId, profesorId, estadoPropuestaId);
                    propuestas.add(propuesta);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return propuestas;
    }

    @Override
    public List<PropuestaColaboracion> consultarPropuestasColaboracionPorEstado(boolean aprobada) {
        List<PropuestaColaboracion> propuestas = new ArrayList<>();
        String consulta = CONSULTAR_PROPUESTAS_POR_ESTADO;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setInt(1, aprobada ? 1 : 0);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idPropuestaColaboracion = resultSet.getInt("idPropuestaColaboracion");
                    String tipoColaboracion = resultSet.getString("tipoColaboracion");
                    String nombre = resultSet.getString("nombre");
                    String objetivoGeneral = resultSet.getString("objetivoGeneral");
                    String temas = resultSet.getString("temas");
                    int numeroEstudiante = resultSet.getInt("numeroEstudiante");
                    String informacionAdicional = resultSet.getString("informacionAdicional");
                    String perfilDeLosEstudiantes = resultSet.getString("PerfilDeLosEstudiantes");
                    int idiomasId = resultSet.getInt("Idiomas_idIdiomas");
                    int periodoId = resultSet.getInt("Periodo_idPeriodo");
                    int profesorId = resultSet.getInt("Profesor_idProfesor");
                    int estadoPropuestaId = resultSet.getInt("Estado_Propuesta_idEstadoPropuesta");

                    PropuestaColaboracion propuesta = new PropuestaColaboracion(idPropuestaColaboracion, tipoColaboracion, nombre, objetivoGeneral, temas, numeroEstudiante, informacionAdicional, perfilDeLosEstudiantes, idiomasId, periodoId, profesorId, estadoPropuestaId);
                    propuestas.add(propuesta);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return propuestas;
    }
    
    
public PropuestaColaboracion obtenerPropuestaPorId(int idPropuesta) throws SQLException {
    PropuestaColaboracion propuesta = null;
    Connection conexion = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        conexion = ManejadorBaseDeDatos.obtenerConexion();
        String consulta = "SELECT * FROM propuesta_colaboracion WHERE idPropuestaColaboracion = ?";
        statement = conexion.prepareStatement(consulta);
        statement.setInt(1, idPropuesta);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("idPropuestaColaboracion");
            String tipoColaboracion = resultSet.getString("tipoColaboracion");
            String nombre = resultSet.getString("nombre");
            String objetivoGeneral = resultSet.getString("objetivoGeneral");
            String temas = resultSet.getString("temas");
            int numeroEstudiante = resultSet.getInt("numeroEstudiante");
            String informacionAdicional = resultSet.getString("informacionAdicional");
            String perfilDeLosEstudiantes = resultSet.getString("PerfilDeLosEstudiantes");
            int idiomasId = resultSet.getInt("Idiomas_idIdiomas");
            int periodoId = resultSet.getInt("Periodo_idPeriodo");
            int profesorId = resultSet.getInt("Profesor_idProfesor");
            int estadoPropuestaId = resultSet.getInt("Estado_Propuesta_idEstadoPropuesta");
            propuesta = new PropuestaColaboracion(id, tipoColaboracion, nombre, objetivoGeneral, temas, 
                                                  numeroEstudiante, informacionAdicional, perfilDeLosEstudiantes, 
                                                  idiomasId, periodoId, profesorId, estadoPropuestaId);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PropuestaColaboracionDAO.class.getName()).log(Level.SEVERE, "Error al obtener la propuesta por ID", ex);
    }
    return propuesta;
}


}
