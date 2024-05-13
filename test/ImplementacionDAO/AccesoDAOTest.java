package ImplementacionDAO;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.ImplementacionDAO.AccesoDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;

public class AccesoDAOTest {
    @Test
    public void agregarAccesoAlSistemaExitoso() throws Exception {
        System.out.println("registrarAcceso");
        Acceso acceso = new Acceso();
        AccesoDAO accesoDAO = new AccesoDAO();
        acceso.setContrasenia("eduardo01@");
        acceso.setUsuario("administrativo");
        acceso.setTipoUsuario(0);
        int resultadoEsperado = 1;
        int resultadoObtenido = accesoDAO.agregarAcceso(acceso);
        assertEquals(resultadoEsperado , resultadoObtenido);
    }
    
    @Test
    public void aexisteAccesosExitoso() throws Exception {
        AccesoDAO accesoDAO = new AccesoDAO();
        String usuarioExistente= "administrativo";
        String contraseniaExistente= "eduardo01@";
        int resultadoEsperado = 1;
        int resultadoObtenido = accesoDAO.existeAcceso(usuarioExistente,contraseniaExistente); 
        assertEquals(resultadoEsperado , resultadoObtenido);
    }
}
