package implementacion;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorUVDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;
import sdgcoilvic.logicaDeNegocio.enums.EnumTipoDeAcceso;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;

public class ProfesorUVDAOTest {
  @Test
    public void registrarProfesorUVExitoso() throws SQLException {
        ProfesorUVDAO instanciaProfesorUV = new ProfesorUVDAO();
        ProfesorUV profesorUV = new ProfesorUV();

        profesorUV.setNoPersonal("ttdfffffk");
        profesorUV.setDisciplina("ttffft");
        profesorUV.setIdRegion(1);
        profesorUV.setIdCategoriaContratacionUV(1);
        profesorUV.setIdTipoContratacionUV(1);
        profesorUV.setIdAreaAcademica(2);
        profesorUV.setNombre("Ericffk");
        profesorUV.setApellidoPaterno("Atziffn");
        profesorUV.setApellidoMaterno("Olafffrte");
        profesorUV.setCorreo("affftzin@example.com");
        profesorUV.setIdIdiomas(1);
        profesorUV.setEstadoProfesor("Activo");
        profesorUV.setClaveInstitucional("30MSU0940B");
        Acceso acceso = new Acceso();
        acceso.setUsuario("atzin@example.com");
        acceso.setContrasenia("contraseña"); // Ajusta la contraseña según lo esperado en tu implementación
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString()); // Ajusta el tipo de usuario según lo esperado en tu implementación

        int resultadoEsperado = 2; 
        int resultadoObtenido = instanciaProfesorUV.registrarProfesorUV(acceso, profesorUV);

        assertEquals(resultadoEsperado, resultadoObtenido);
    }
    

}
