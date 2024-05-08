package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.CalendarioActividades;
import sdgcoilvic.logicaDeNegocio.interfaces.ICalendarioActividades;

public class CalendarioActividadesDAO implements ICalendarioActividades {

    private static final String CONSULTA_AGREGAR_CALENDARIO = "INSERT INTO calendario_actividades (fechaInicio, fechaFin, tema, herramientas) VALUES (?, ?, ?, ?)";
    private static final String CONSULTA_MODIFICAR_CALENDARIO = "UPDATE calendario_actividades SET fechaInicio = ?, fechaFin = ?, tema = ?, herramientas = ? WHERE idCalendarioActividades = ?";
    private static final String CONSULTA_CONSULTAR_CALENDARIO = "SELECT * FROM calendario_actividades";

    @Override
    public int agregarCalendarioActividades(CalendarioActividades calendario) {
        int resultado = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(CONSULTA_AGREGAR_CALENDARIO)) {
            declaracion.setDate(1, calendario.getFechaInicio());
            declaracion.setDate(2, calendario.getFechaFin());
            declaracion.setString(3, calendario.getTema());
            declaracion.setString(4, calendario.getHerramientas());
            resultado = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalendarioActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public int modificarCalendarioActividades(CalendarioActividades calendario, int idCalendarioActividades) {
        int rowsAffected = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(CONSULTA_MODIFICAR_CALENDARIO)) {
            declaracion.setDate(1, calendario.getFechaInicio());
            declaracion.setDate(2, calendario.getFechaFin());
            declaracion.setString(3, calendario.getTema());
            declaracion.setString(4, calendario.getHerramientas());
            declaracion.setInt(5, calendario.getIdCalendarioActividades());
            rowsAffected = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CalendarioActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowsAffected;
    }

    @Override
    public List<CalendarioActividades> consultarUnCalendarioActividades() {
        List<CalendarioActividades> calendarios = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(CONSULTA_CONSULTAR_CALENDARIO);
             ResultSet resultado = declaracion.executeQuery()) {
            while (resultado.next()) {
                int idCalendario = resultado.getInt("idCalendarioActividades");
                Date fechaInicio = resultado.getDate("fechaInicio");
                Date fechaFin = resultado.getDate("fechaFin");
                String tema = resultado.getString("tema");
                String herramientas = resultado.getString("herramientas");
                CalendarioActividades calendario = new CalendarioActividades(idCalendario, fechaInicio, fechaFin, tema, herramientas);
                calendarios.add(calendario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CalendarioActividadesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calendarios;
    }
}