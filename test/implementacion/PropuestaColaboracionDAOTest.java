package implementacion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sdgcoilvic.logicaDeNegocio.implementacionDAO.PropuestaColaboracionDAO;
import sdgcoilvic.logicaDeNegocio.clases.PropuestaColaboracion;

public class PropuestaColaboracionDAOTest {
    private PropuestaColaboracionDAO propuestaDAO;

    @Before
    public void setUp() {
        propuestaDAO = new PropuestaColaboracionDAO();
    }

    @After
    public void tearDown() {
        propuestaDAO = null;
    }

    @Test
    public void pruebaSometerPropuestaColaboracion() throws SQLException {
        PropuestaColaboracion propuesta = new PropuestaColaboracion(
            0,
            "Tipo de Colaboración",
            "Nombre de la Propuesta",
            "Objetivo General",
            "Temas",
            5,
            "Información Adicional",
            "Perfil de los Estudiantes",
            1,
            1,
            1,
            1
        );

        int resultado = propuestaDAO.someterPropuestaColaboracion(propuesta);

        assertTrue(resultado > 0);
    }

    @Test
public void pruebaEvaluarPropuestaColaboracion() throws SQLException {
    int idPropuesta = 1;
    PropuestaColaboracionDAO propuestaDAO = new PropuestaColaboracionDAO(); 
    PropuestaColaboracion propuestaExistente = propuestaDAO.obtenerPropuestaPorId(idPropuesta);
    assertNotNull(propuestaExistente);
    propuestaDAO.evaluarPropuestaColaboracion(propuestaExistente, true);
    int estadoEsperado = 1;
    assertEquals(estadoEsperado, propuestaExistente.getEstadoPropuestaId());
}


    @Test
    public void pruebaConsultarPropuestasColaboracion() throws SQLException {
        List<PropuestaColaboracion> propuestas = propuestaDAO.consultarPropuestasColaboracion();

        assertNotNull(propuestas);
        assertFalse(propuestas.isEmpty());
    }

    @Test
    public void pruebaConsultarPropuestasColaboracionPorProfesor() throws SQLException {
        int idProfesor = 1;

        List<PropuestaColaboracion> propuestas = propuestaDAO.consultarPropuestasColaboracionPorProfesor(idProfesor);

        assertNotNull(propuestas);
        assertFalse(propuestas.isEmpty());
    }

    @Test
    public void pruebaConsultarPropuestasColaboracionPorEstado() throws SQLException {
        List<PropuestaColaboracion> propuestasAprobadas = propuestaDAO.consultarPropuestasColaboracionPorEstado(true);

        assertNotNull(propuestasAprobadas);
        assertFalse(propuestasAprobadas.isEmpty());
    }
    private PropuestaColaboracion obtenerPropuestaExistente() throws SQLException {
        return null;
    }
}
