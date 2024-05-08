package ImplementacionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public class ProfesorDAOTest {
    @Test
    public void registrarProfesorExitoso() throws Exception {
        System.out.println("registrarProfesorExterno");
        Profesor profesor = new Profesor();
        ProfesorDAO instancia = new ProfesorDAO();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setTelefono("7841233040");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdIdiomas(1);
        profesor.setFoto(null);
        int resultadoEsperado = 1;
        int resultadoObtenido = instancia.registrarProfesor(profesor);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    @Test
    public void testModificarCalendarioActividades() throws SQLException {
        System.out.println("Modificar Profesor");
        ProfesorDAO profesorDAO = new ProfesorDAO();
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(1); 
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@gmail.com");
        profesor.setTelefono("7841233040");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdIdiomas(1);
        profesor.setFoto(null);

        int expResult = 1;
        int result = profesorDAO.actualizarProfesor(profesor, profesor.getIdProfesor());


        assertEquals(expResult, result);
    }

    @Test
    public void obtenerProfesorExistentePorNombre() throws SQLException {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        String nombreProfesorExistente = "Juan";
        Profesor profesorObtenido = profesorDAO.obtenerProfesorPorNombre(nombreProfesorExistente);
        assertEquals(nombreProfesorExistente, profesorObtenido.getNombre());
    }
    @Test
       public void obtenerListaTodosLosProfesores() throws SQLException {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> ProfesoresObtenidos = profesorDAO.obtenerListaTodosLosProfesores();
        List<Profesor> ProfesoresEsperados = new ArrayList();
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(1);
        profesor.setNombre("Juan");
        profesor.setApellidoPaterno("Perez");
        profesor.setApellidoMaterno("Gomez");
        profesor.setCorreo("juan@example.com");
        profesor.setTelefono("1234567890");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdIdiomas(1);
        profesor.setFoto(null);
        ProfesoresEsperados.add(profesor);
        
        assertEquals(ProfesoresObtenidos,ProfesoresEsperados);

    }
}
