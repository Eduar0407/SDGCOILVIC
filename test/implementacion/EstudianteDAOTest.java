package implementacion;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.EstudianteDAO;
import sdgcoilvic.logicaDeNegocio.clases.Estudiante;

public class EstudianteDAOTest {
    @Test
    public void registrarEstudianteExitoso() throws SQLException {
        System.out.println("registrarEstudiante");
        Estudiante estudiante = new Estudiante();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        estudiante.setNombre("Juan Eduardo");
        estudiante.setApellidoPaterno("Cumplido");
        estudiante.setApellidoMaterno("Negrete");
        estudiante.setCorreo("zs22020936@estudiantes.uv.mx");
        estudiante.setClaveInstitucional("30MSU0940B");

        int resultadoEsperado = 1;
        int resultadoObtenido = estudianteDAO.registrarEstudiante(estudiante);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    
    @Test
    public void obtenerProfesorExistentePorNombre() throws SQLException {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        String nombreEstudianteExistente = "Juan Eduardo";
        Estudiante estudianteObtenido = estudianteDAO.obtenerEstudiantePorNombre(nombreEstudianteExistente);
        assertNotNull(estudianteObtenido);
        assertEquals(nombreEstudianteExistente, estudianteObtenido.getNombre());
    }
    
    @Test
    public void testObtenerTodosLosEstudiantes() {
         EstudianteDAO estudianteDAO = new EstudianteDAO();
         List<Estudiante> listaEstudiantes = estudianteDAO.obtenerTodosLosEstudiantes();
         assertEquals(1, listaEstudiantes.size()); 
         Set<Integer> idsUnicos = new HashSet<>();

         for (Estudiante estudiante : listaEstudiantes) {
             assertTrue(idsUnicos.add(estudiante.getIdEstudiante()));
             assertNotNull(estudiante.getNombre());
             assertNotNull(estudiante.getApellidoPaterno());
             assertNotNull(estudiante.getApellidoMaterno());
             assertNotNull(estudiante.getCorreo());
             assertNotNull(estudiante.getClaveInstitucional());
         }
     }

   
}