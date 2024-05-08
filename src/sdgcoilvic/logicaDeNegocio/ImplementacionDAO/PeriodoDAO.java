package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Periodo;
import sdgcoilvic.logicaDeNegocio.interfaces.IPeriodo;

public class PeriodoDAO implements IPeriodo {
    private static final String CONSULTA_AGREGAR_PERIODO = "INSERT INTO periodo (fechaInicio, fechaFin, nombrePeriodo) VALUES (?, ?, ?)";
    private static final String CONSULTA_CONSULTAR_TODOS_PERIODOS = "SELECT * FROM periodo";
    private static final String CONSULTA_CONSULTAR_PERIODO_POR_FECHA = "SELECT * FROM periodo WHERE fechaInicio = ?";
    private static final String CONSULTA_CONSULTAR_PERIODO_POR_NOMBRE = "SELECT * FROM periodo WHERE nombrePeriodo = ?";

    @Override
    public int agregarPeriodo(Periodo periodo) {
        int resultado = 0;
        String consulta = CONSULTA_AGREGAR_PERIODO;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
            declaracion.setDate(1, periodo.getFechaInicio());
            declaracion.setDate(2, periodo.getFechaFin());
            declaracion.setString(3, periodo.getNombrePeriodo());
            resultado = declaracion.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public List<Periodo> consultarTodosPeriodos() {
        List<Periodo> periodos = new ArrayList<>();
        String consulta = CONSULTA_CONSULTAR_TODOS_PERIODOS;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(consulta);
             ResultSet resultado = declaracion.executeQuery()) {
            while (resultado.next()) {
                int idPeriodo = resultado.getInt("idPeriodo");
                Date fechaInicio = resultado.getDate("fechaInicio");
                Date fechaFin = resultado.getDate("fechaFin");
                String nombrePeriodo = resultado.getString("nombrePeriodo");
                Periodo periodo = new Periodo(idPeriodo, fechaInicio, fechaFin, nombrePeriodo);
                periodos.add(periodo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return periodos;
    }

    @Override
    public List<Periodo> consultarPeriodoPorFecha(Date fecha) {
        List<Periodo> periodos = new ArrayList<>();
        String consulta = CONSULTA_CONSULTAR_PERIODO_POR_FECHA;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
            declaracion.setDate(1, fecha);
            try (ResultSet resultado = declaracion.executeQuery()) {
                while (resultado.next()) {
                    int idPeriodo = resultado.getInt("idPeriodo");
                    Date fechaInicio = resultado.getDate("fechaInicio");
                    Date fechaFin = resultado.getDate("fechaFin");
                    String nombrePeriodo = resultado.getString("nombrePeriodo");
                    Periodo periodo = new Periodo(idPeriodo, fechaInicio, fechaFin, nombrePeriodo);
                    periodos.add(periodo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return periodos;
    }

    @Override
    public List<Periodo> consultarPeriodoPorNombre(String nombrePeriodo) {
        List<Periodo> periodos = new ArrayList<>();
        String consulta = CONSULTA_CONSULTAR_PERIODO_POR_NOMBRE;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement declaracion = conexion.prepareStatement(consulta)) {
            declaracion.setString(1, nombrePeriodo);
            try (ResultSet resultado = declaracion.executeQuery()) {
                while (resultado.next()) {
                    int idPeriodo = resultado.getInt("idPeriodo");
                    Date fechaInicio = resultado.getDate("fechaInicio");
                    Date fechaFin = resultado.getDate("fechaFin");
                    Periodo periodo = new Periodo(idPeriodo, fechaInicio, fechaFin, nombrePeriodo);
                    periodos.add(periodo);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return periodos;
    }
}
