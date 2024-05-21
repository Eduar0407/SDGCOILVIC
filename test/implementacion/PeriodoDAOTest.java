package implementacion;

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

}
