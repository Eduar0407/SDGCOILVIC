package ImplementacionDAO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.CalendarioActividadesDAO;
import sdgcoilvic.logicaDeNegocio.clases.CalendarioActividades;



public class CalendarioActividadesDAOTest {

    @Test
    public void testAgregarCalendarioActividades() throws SQLException {
    CalendarioActividadesDAO calendarioActividadesDAO = new CalendarioActividadesDAO();
    CalendarioActividades calendarioActividades = new CalendarioActividades();
    java.sql.Date fechaInicio = java.sql.Date.valueOf("2024-04-01");
    java.sql.Date fechaFin = java.sql.Date.valueOf("2024-04-05");
    calendarioActividades.setFechaInicio(fechaInicio);
    calendarioActividades.setFechaFin(fechaFin);
    calendarioActividades.setTema("Test tema");
    calendarioActividades.setHerramientas("Test herramientas");
    int resultado = calendarioActividadesDAO.agregarCalendarioActividades(calendarioActividades);
    assertTrue(resultado > 0);
    }

   @Test
    public void testModificarCalendarioActividades() throws SQLException {
        System.out.println("Modificar Calendario");
        CalendarioActividadesDAO calendarioActividadesDAO = new CalendarioActividadesDAO();
        CalendarioActividades calendarioActividades = new CalendarioActividades();
        calendarioActividades.setIdCalendarioActividades(1); 
        java.sql.Date fechaInicio = java.sql.Date.valueOf("2024-04-01");
        java.sql.Date fechaFin = java.sql.Date.valueOf("2024-04-05");
        calendarioActividades.setFechaInicio(fechaInicio); 
        calendarioActividades.setFechaFin(fechaFin); 
        calendarioActividades.setTema("Nuevo tema");
        calendarioActividades.setHerramientas("Nuevas herramientas");

        int expResult = 1;
        int result = calendarioActividadesDAO.modificarCalendarioActividades(calendarioActividades, calendarioActividades.getIdCalendarioActividades());


        assertEquals(expResult, result);
    }

    @Test
    public void testConsultarCalendarioActividades() throws SQLException {
        CalendarioActividadesDAO calendarioActividadesDAO = new CalendarioActividadesDAO();
        CalendarioActividades calendarioActividades = new CalendarioActividades();
        calendarioActividadesDAO.agregarCalendarioActividades(calendarioActividades);
        List<CalendarioActividades> calendarios = calendarioActividadesDAO.consultarUnCalendarioActividades();
        assertNotNull(calendarios);
        assertTrue(!calendarios.isEmpty());
    }

}