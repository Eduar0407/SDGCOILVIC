package ImplementacionDAO;

import java.sql.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import sdgcoilvic.logicaDeNegocio.clases.Periodo;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.PeriodoDAO;

public class PeriodoDAOTest {

    @Test
    public void testAgregarPeriodo() {
        PeriodoDAO dao = new PeriodoDAO();
        Date fechaInicio = Date.valueOf("2024-01-01");
        Date fechaFin = Date.valueOf("2024-06-30");
        Periodo periodo = new Periodo(0, fechaInicio, fechaFin, "Primer Semestre 2024");
        int resultado = dao.agregarPeriodo(periodo);
        assertEquals(1, resultado);
    }

    @Test
    public void testConsultarTodosPeriodos() {
        PeriodoDAO dao = new PeriodoDAO();
        List<Periodo> periodos = dao.consultarTodosPeriodos();
        assertNotNull(periodos);
    }

    @Test
    public void testConsultarPeriodoPorFecha() {
        PeriodoDAO dao = new PeriodoDAO();
        Date fecha = Date.valueOf("2024-01-01");
        List<Periodo> periodos = dao.consultarPeriodoPorFecha(fecha);
        assertNotNull(periodos);
    }

    @Test
    public void testConsultarPeriodoPorNombre() {
        PeriodoDAO dao = new PeriodoDAO();
        String nombrePeriodo = "Primer Semestre 2024";
        List<Periodo> periodos = dao.consultarPeriodoPorNombre(nombrePeriodo);
        assertNotNull(periodos);
    }
}
