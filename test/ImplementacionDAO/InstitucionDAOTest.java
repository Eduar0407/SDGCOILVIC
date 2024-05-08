package ImplementacionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.InstitucionDAO;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;


public class InstitucionDAOTest {
    @Test
    public void insertarInstitucionExitoso() throws Exception {
        System.out.println("registrarInstitucion");
        Institucion institucion = new Institucion();
        InstitucionDAO instancia = new InstitucionDAO();
        institucion.setClaveInstitucional("09PSU0175T");
        institucion.setNombreInstitucion("Instituto Tecnológico y de Estudios "
                                       + "Superiores de Monterrey CAMPUS SANTA FE");
        institucion.setNombrePais("México");
        int resultadoEsperado = 1;
        int resultadoObtenido = instancia.insertarInstitucion(institucion);
        assertEquals(resultadoEsperado , resultadoObtenido);
    }
    @Test
    public void consultarInstitucionPorClaveExitoso() throws SQLException {
    InstitucionDAO institucionDAO = new InstitucionDAO();
    String claveInstitucional = "30MSU0940B";
    Institucion institucionObtenida = (Institucion) institucionDAO.obtenerInstitucionPorClave(claveInstitucional);
    assertNotNull(institucionObtenida);
    assertEquals(claveInstitucional, institucionObtenida.getClaveInstitucional());
}

    @Test
    public void testObtenerTodosLosEstudiantes() {
        InstitucionDAO institucionDAO = new InstitucionDAO();
        List<Institucion> listaPreliminar = new ArrayList<>();
        
        Institucion institucion1 = new Institucion();
        institucion1.setClaveInstitucional("30MSU0940B");
        institucion1.setNombreInstitucion("Universidad Veracruzana");
        institucion1.setNombrePais("México");
     
        listaPreliminar.add(institucion1);
        List<Institucion> listaDesdeBD = institucionDAO.obtenerTodasLasInstituciones();
        assertEquals(listaPreliminar.size(), listaDesdeBD.size());
       }

    @Test
    public void testObtenerListaInstitucionesPorPaisExitoso() throws SQLException {
        String nombrePais = "México";
        InstitucionDAO institucionDAO = new InstitucionDAO();
        List<Institucion> listaDesdeBD = institucionDAO.obtenerListaInstitucionesPorPais(nombrePais);

        List<Institucion> listaPreliminar = ListaPreliminar();

        assertEquals(listaPreliminar.size(), listaDesdeBD.size());

       for (Institucion institucion : listaPreliminar) {
            assertTrue(listaDesdeBD.contains(institucion));
        }
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
