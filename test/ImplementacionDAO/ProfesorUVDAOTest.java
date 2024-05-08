package ImplementacionDAO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.ProfesorUVDAO;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;

public class ProfesorUVDAOTest {
  @Test
    public void registrarProfesorUVExitoso() throws SQLException {
        ProfesorUVDAO instanciaProfesorUV = new ProfesorUVDAO();
        ProfesorUV profesorUV = new ProfesorUV();
        profesorUV.setNoPersonal("12345");
        profesorUV.setDisciplina("Informática");
        profesorUV.setIdProfesor(1); 
        profesorUV.setIdRegion(1);
        profesorUV.setIdCategoriaContratacionUV(1);
        profesorUV.setIdTipoContratacionUV(1);
        profesorUV.setIdAreaAcademica(1);

        int resultadoEsperado = 1;
        int resultadoObtenido = instanciaProfesorUV.registrarProfesorUV(profesorUV);
        assertEquals(resultadoEsperado, resultadoObtenido);
    }

        @Test
    public void testActualizarProfesorUV() throws SQLException {
        System.out.println("Modificar Profesor UV");
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        ProfesorUV profesorUV = new ProfesorUV();
        profesorUV.setIdProfesorUV(1);
        profesorUV.setNoPersonal("12345");
        profesorUV.setDisciplina("Informática");
        profesorUV.setIdProfesor(1);
        profesorUV.setIdRegion(1);
        profesorUV.setIdCategoriaContratacionUV(1);
        profesorUV.setIdTipoContratacionUV(1);
        profesorUV.setIdAreaAcademica(1);

        int expResult = 1;
        int result = profesorUVDAO.actualizarProfesor(profesorUV, profesorUV.getIdProfesorUV());

        assertEquals(expResult, result);
    }

    @Test
    public void testObtenerProfesorUVExistentePorNoPersonal() throws SQLException {
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        String noPersonalProfesorExistente = "12345";
        ProfesorUV profesorUVObtenido = profesorUVDAO.obtenerProfesorPorNumeroPersonal(noPersonalProfesorExistente);
        assertNotNull(profesorUVObtenido);
        assertEquals(noPersonalProfesorExistente, profesorUVObtenido.getNoPersonal());
    }

    @Test
    public void testObtenerListaTodosLosProfesoresUV() throws SQLException {
        ProfesorUVDAO profesorUVDAO = new ProfesorUVDAO();
        List<ProfesorUV> listaProfesoresUV = profesorUVDAO.obtenerListaTodosLosProfesores();
        assertEquals(1, listaProfesoresUV.size());
        Set<Integer> idsUnicos = new HashSet<>();

        for (ProfesorUV profesorUV : listaProfesoresUV) {
            assertTrue(idsUnicos.add(profesorUV.getIdProfesorUV()));
            assertNotNull(profesorUV.getNoPersonal());
            assertNotNull(profesorUV.getDisciplina());
            assertNotNull(profesorUV.getIdProfesor());
            assertNotNull(profesorUV.getIdRegion());
            assertNotNull(profesorUV.getIdCategoriaContratacionUV());
            assertNotNull(profesorUV.getIdTipoContratacionUV());
            assertNotNull(profesorUV.getIdAreaAcademica());
        }
    }
}
