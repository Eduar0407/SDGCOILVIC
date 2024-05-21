package implementacion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ColaboracionDAO;
import sdgcoilvic.logicaDeNegocio.clases.Colaboracion;

public class ColaboracionDAOTest {

    @Test
    public void testCrearColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setNombreCurso("Curso de prueba 1");
        colaboracion.setDescripcion("Descripción de prueba");
        colaboracion.setRecursos("Recursos de prueba");
        colaboracion.setIdPeriodo(1);
        colaboracion.setAprendizajesEsperados("Aprendizajes esperados de prueba");
        colaboracion.setDetallesAsistenciaParticipacion("Detalles de asistencia y participación de prueba");
        colaboracion.setDetallesEvaluacion("Detalles de evaluación de prueba");
        colaboracion.setDetallesEntorno("Detalles de entorno de prueba");
        colaboracion.setIdEstadoColaboracion(1);
        colaboracion.setIdCalendarioActividades(1);
        colaboracion.setIdPropuestaColaboracion(1);
        int resultado = dao.crearColaboracion(colaboracion);
        assertEquals(1, resultado);
    }

    @Test
    public void testFinalizarColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        int idColaboracion = 1; // ID de colaboración existente
        int resultado = dao.finalizarColaboracion(idColaboracion);
        assertEquals(1, resultado);
    }

    @Test
    public void testCerrarColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        int idColaboracion = 1; // ID de colaboración existente
        int resultado = dao.cerrarColaboracion(idColaboracion);
        assertEquals(1, resultado);
    }

    @Test
    public void testIniciarColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        int idColaboracion = 1; // ID de colaboración existente
        int resultado = dao.iniciarColaboracion(idColaboracion);
        assertEquals(1, resultado);
    }

    @Test
    public void testModificarColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        Colaboracion colaboracion = new Colaboracion();
        colaboracion.setIdColaboracion(1); // ID de colaboración existente
        colaboracion.setNombreCurso("Curso modificado");
        colaboracion.setDescripcion("Descripción modificada");
        colaboracion.setRecursos("Recursos modificados");
        colaboracion.setIdPeriodo(1);
        colaboracion.setAprendizajesEsperados("Aprendizajes esperados modificados");
        colaboracion.setDetallesAsistenciaParticipacion("Detalles de asistencia y participación modificados");
        colaboracion.setDetallesEvaluacion("Detalles de evaluación modificados");
        colaboracion.setDetallesEntorno("Detalles de entorno modificados");
        colaboracion.setIdEstadoColaboracion(1);
        colaboracion.setIdCalendarioActividades(1);
        colaboracion.setIdPropuestaColaboracion(1);
        int resultado = dao.modificarColaboracion(colaboracion);
        assertEquals(1, resultado);
    }

    @Test
    public void testConsultarColaboracion() {
        ColaboracionDAO dao = new ColaboracionDAO();
        int idColaboracion = 1; // ID de colaboración existente
        Colaboracion colaboracion = dao.consultarColaboracion(idColaboracion);
        assertNotNull(colaboracion);
    }

    @Test
    public void testConsultarTodasColaboraciones() {
        ColaboracionDAO dao = new ColaboracionDAO();
        List<Colaboracion> colaboraciones = dao.consultarTodasColaboraciones();
        assertNotNull(colaboraciones);
    }

    @Test
    public void testFiltrarColaboraciones() {
        ColaboracionDAO dao = new ColaboracionDAO();
        String filtro = "prueba"; // Filtro de búsqueda
        List<Colaboracion> colaboraciones = dao.filtrarColaboraciones(filtro);
        assertNotNull(colaboraciones);
    }
}
