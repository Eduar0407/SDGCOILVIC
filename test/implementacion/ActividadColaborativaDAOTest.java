package implementacion;

import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ActividadColaborativaDAO;
import sdgcoilvic.logicaDeNegocio.clases.ActividadColaborativa;

public class ActividadColaborativaDAOTest {
    @Test
    public void agregarActividadColaborativaExitosa() throws SQLException {
        System.out.println("agregarActividadColaborativaExitosa");
        ActividadColaborativa actividad = new ActividadColaborativa();
        ActividadColaborativaDAO instancia = new ActividadColaborativaDAO();
        actividad.setNombreActividad("Actividad de prueba");
        actividad.setDescripcion("Descripción de la actividad de prueba");
        actividad.setPuntaje(10);
        actividad.setIdCalendarioActividades(1); 

        int resultadoEsperado = 1;
        int resultadoObtenido = instancia.agregarActividadColaborativa(actividad);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void modificarActividadColaborativaExistente() throws SQLException {
        ActividadColaborativaDAO actividadDAO = new ActividadColaborativaDAO();
        ActividadColaborativa actividad = new ActividadColaborativa();
        actividad.setIdActividadColaborativa(1); 
        actividad.setNombreActividad("Nueva actividad modificada");
        actividad.setDescripcion("Nueva descripción modificada");
        actividad.setPuntaje(15);

        int resultadoEsperado = 1;
        int resultadoObtenido = actividadDAO.modificarActividadColaborativa(actividad, actividad.getIdActividadColaborativa());

        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void consultarActividadColaborativaExistente() throws SQLException {
        ActividadColaborativaDAO actividadDAO = new ActividadColaborativaDAO();
        int idActividadExistente = 1; 

        ActividadColaborativa actividadObtenida = actividadDAO.consultarActividadColaborativa(idActividadExistente);

        assertNotNull(actividadObtenida);
        assertEquals(idActividadExistente, actividadObtenida.getIdActividadColaborativa());
    }
}
