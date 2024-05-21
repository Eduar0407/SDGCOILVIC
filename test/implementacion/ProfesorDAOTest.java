package implementacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.enums.EnumTipoDeAcceso;

public class ProfesorDAOTest {
    

    @Test
    public void registrarProfesorExitoso() throws Exception {
        System.out.println("registrarProfesorExitoso");
        Profesor profesor = new Profesor();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        Acceso acceso = new Acceso();
        acceso.setUsuario("atzin@example.com");
        acceso.setContrasenia("contraseña"); // Ajusta la contraseña según lo esperado en tu implementación
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString()); // Ajusta el tipo de usuario según lo esperado en tu implementación

        int resultadoEsperado = 1; 
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }




}
