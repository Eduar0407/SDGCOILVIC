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
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public class ProfesorDAOTest {
    
    @Test
    public void registrarProfesorExitoso() throws Exception {
        System.out.println("registrarProfesorExitoso");
        Profesor profesor = new Profesor();
        ProfesorDAO instancia = new ProfesorDAO();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdIdiomas(1);
        profesor.setIdEstado(2);
        Acceso acceso = new Acceso();
        acceso.setUsuario("atzin@example.com");
        acceso.setContrasenia("contraseña"); // Ajusta la contraseña según lo esperado en tu implementación
        acceso.setTipoUsuario(1); // Ajusta el tipo de usuario según lo esperado en tu implementación

        // Ajusta el resultado esperado según tu lógica de negocio
        // Aquí se espera que la inserción del profesor y el acceso sea exitosa, por lo que se espera que la columna afectada sea mayor que cero.
        int resultadoEsperado = 1; // Dependiendo de la lógica de tu aplicación podría ser otro valor

        int resultadoObtenido = instancia.registrarProfesor(profesor, acceso);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }



    @Test
    public void obtenerProfesorExistentePorNombre() throws SQLException {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        String nombreProfesorExistente = "Juan";
        Profesor profesorObtenido = profesorDAO.obtenerProfesorPorNombre(nombreProfesorExistente);
        assertEquals(nombreProfesorExistente, profesorObtenido.getNombre());
    }

}
