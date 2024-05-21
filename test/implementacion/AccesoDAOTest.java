package implementacion;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.enums.EnumTipoDeAcceso;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.AccesoDAO;

public class AccesoDAOTest {
    @Test
    public void testAgregarAccesoAlSistemaExitoso() throws Exception {
        System.out.println("registrarAcceso");
        Acceso acceso = new Acceso();
        AccesoDAO accesoDAO = new AccesoDAO();
        acceso.setContrasenia("eduardo01@");
        acceso.setUsuario("administrativo");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Administrativo.toString());
        int resultadoEsperado = 1;
        int resultadoObtenido = accesoDAO.agregarAcceso(acceso);
        assertEquals(resultadoEsperado , resultadoObtenido);
    }
    
    @Test
    public void testExisteAccesosExitoso() throws Exception {
        AccesoDAO accesoDAO = new AccesoDAO();
        String usuarioExistente= "administrativo";
        String contraseniaExistente= "eduardo01@";
        int resultadoEsperado = 1;
        int resultadoObtenido = accesoDAO.verificarExistenciaAcceso(usuarioExistente,contraseniaExistente); 
        assertEquals(resultadoEsperado , resultadoObtenido);
    }
}
