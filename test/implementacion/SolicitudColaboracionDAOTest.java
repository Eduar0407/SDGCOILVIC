package implementacion;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.SolicitudColaboracionDAO;
import sdgcoilvic.logicaDeNegocio.clases.SolicitudColaboracion;

public class SolicitudColaboracionDAOTest {

    @Test
    public void testElaborarSolicitudColaboracion() {
        SolicitudColaboracionDAO dao = new SolicitudColaboracionDAO();
        SolicitudColaboracion solicitud = new SolicitudColaboracion(0, "2024-04-12", "Mensaje de prueba", 1, 1);
        int resultado = dao.elaborarSolicitudColaboracion(solicitud);
        assertEquals(1, resultado);
    }

    @Test
    public void testEvaluarSolicitudColaboracion() {
        SolicitudColaboracionDAO dao = new SolicitudColaboracionDAO();
        SolicitudColaboracion solicitud = new SolicitudColaboracion(1, "2024-04-12", "Mensaje de prueba", 1, 1);
        int resultado = dao.evaluarSolicitudColaboracion(solicitud, true);
        assertEquals(1, resultado);
    }

    @Test
    public void testConsultarSolicitudesColaboracion() {
        SolicitudColaboracionDAO dao = new SolicitudColaboracionDAO();
        List<SolicitudColaboracion> solicitudes = dao.consultarSolicitudesColaboracion();
        assertNotNull(solicitudes);
    }

    @Test
    public void testConsultarSolicitudesColaboracionPorProfesor() {
        SolicitudColaboracionDAO dao = new SolicitudColaboracionDAO();
        List<SolicitudColaboracion> solicitudes = dao.consultarSolicitudesColaboracionPorProfesor(1);
        assertNotNull(solicitudes);
    }

    @Test
    public void testConsultarSolicitudesColaboracionPorEstado() {
        SolicitudColaboracionDAO dao = new SolicitudColaboracionDAO();
        List<SolicitudColaboracion> solicitudes = dao.consultarSolicitudesColaboracionPorEstado(true);
        assertNotNull(solicitudes);
    }
}
