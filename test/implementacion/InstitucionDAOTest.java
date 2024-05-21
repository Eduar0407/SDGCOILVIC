package implementacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.InstitucionDAO;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;


public class InstitucionDAOTest {
    @Test
    public void insertarInstitucionExitoso() throws Exception {
        System.out.println("registrarInstitucion");
        Institucion institucion = new Institucion();
        InstitucionDAO instancia = new InstitucionDAO();
        institucion.setClaveInstitucional("330SU0175T");
        institucion.setNombreInstitucion(" Tecnológico y de Estudios "
                                       + "Superiores de Monterrey CAMPUS SANTA FE");
        institucion.setNombrePais("México");
        institucion.setCorreo("meo@gmail.com");
        int resultadoEsperado = 1;
        int resultadoObtenido = instancia.insertarInstitucion(institucion);
        assertEquals(resultadoEsperado , resultadoObtenido);
    }

    
    public static List<Institucion> ListaPreliminar() {
        List<Institucion> instituciones = new ArrayList<>();
        Institucion institucion1 = new Institucion();
        institucion1.setClaveInstitucional("30MSU0940B");
        institucion1.setNombreInstitucion("Universidad Veracruzana");
        institucion1.setNombrePais("México");
        instituciones.add(institucion1);
        
        return instituciones;
    }
   
}
