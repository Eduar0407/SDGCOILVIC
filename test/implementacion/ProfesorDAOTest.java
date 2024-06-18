package implementacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import sdgcoilvic.logicaDeNegocio.implementacionDAO.ProfesorDAO;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;
import sdgcoilvic.logicaDeNegocio.enums.EnumTipoDeAcceso;

public class ProfesorDAOTest {
    
    @Test    
    public void testRegistrarProfesorExitoso() throws Exception {
        System.out.println("Registrando Profesor");
        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test (expected = AssertionError.class)
    public void testRegistrarProfesorNombreDuplicado() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("erick@example.com");
        profesor.setIdIdiomas(2);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("erick@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean duplicado = profesorDAO.verificarExistenciaProfesor("Erick","Atzin","Olarte");

        if (!duplicado) {
            int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
            assertEquals(1, resultadoObtenido);
        } else {
            fail("Ya existe un usuario con este correo en la base de datos.");
        }
    } 
    
    @Test(expected = AssertionError.class)
    public void testRegistrarProfesorCorreoDuplicado() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Cumplido");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean duplicado = profesorDAO.verificarSiExisteElCorreo("atzin@example.com");
        if (!duplicado) {
            int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
            assertEquals(1, resultadoObtenido);
        } else {
            assertEquals("El correo ya existe en la base de datos, no se debe registrar el profesor", false, duplicado);
        }
    }
    
    @Test(expected = AssertionError.class)
    public void testRegistrarProfesorDuplicado() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean duplicado = profesorDAO.verificarProfesorDuplicado(profesor);

        if (!duplicado) {
            int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
            assertEquals(1, resultadoObtenido);
        } else {
            fail("El profesor ya existe en la base de datos.");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorNombreIncorrecto() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("1Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorNombreNulo() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorApellidosIncorrectos() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("1Atzin");
        profesor.setApellidoMaterno("1Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorApellidosNulos() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("");
        profesor.setApellidoMaterno("");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorCorreoInvalido() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzinexamplecom");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorCorreoNulo() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = AssertionError.class)
    public void testRegistrarProfesorIdiomaInexistente() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(10);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }

    @Test(expected = AssertionError.class)
    public void testRegistrarProfesorClaveInexistente() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("Clave Inexistente 123");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = AssertionError.class)
    public void testRegistrarProfesorClaveNula() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarProfesorCaracteresEspeciales() throws Exception {
        System.out.println("Registrando Profesor");

        Profesor profesor = new Profesor();
        profesor.setNombre("***Erick");
        profesor.setApellidoPaterno("#Atzin");
        profesor.setApellidoMaterno("#Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setIdAcceso(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        Acceso acceso = new Acceso();
        acceso.setContrasenia("erick*Atzin1@");
        acceso.setUsuario("atzin@example.com");
        acceso.setTipoUsuario(EnumTipoDeAcceso.Profesor.toString());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.registrarProfesor(profesor, acceso);
        assertEquals(1, resultadoObtenido);
    }
    
    @Test
    public void testVerificarProfesorDuplicadoExitoso() throws Exception {
        System.out.println("Verificando Profesor Duplicado");

        Profesor profesor = new Profesor();
        profesor.setNombre("Erick");
        profesor.setApellidoPaterno("Atzin");
        profesor.setApellidoMaterno("Olarte");
        profesor.setCorreo("atzin@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarProfesorDuplicado(profesor);
        assertTrue(resultadoObtenido); 
    }
    
    @Test
    public void testVerificarProfesorDuplicado() throws Exception {
        System.out.println("Verificando Profesor Duplicado");

        Profesor nuevoProfesor = new Profesor();
        nuevoProfesor.setNombre("Juan");
        nuevoProfesor.setApellidoPaterno("Perez");
        nuevoProfesor.setApellidoMaterno("Garcia");
        nuevoProfesor.setCorreo("juan@example.com");
        nuevoProfesor.setIdIdiomas(2); 
        nuevoProfesor.setEstadoProfesor("Activo");
        nuevoProfesor.setClaveInstitucional("claveficticia");
        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarProfesorDuplicado(nuevoProfesor);
        assertFalse(resultadoObtenido); 
    }

    @Test
    public void testObtenerListaProfesoresPorNombreExitoso() throws SQLException {
        System.out.println("Obteniendo Lista de Profesores por Nombre");
        String nombre = "Erick";
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> listaProfesores = profesorDAO.obtenerListaProfesoresPorNombre(nombre);
        assertNotNull(listaProfesores);
        assertFalse(listaProfesores.isEmpty());
        for (Profesor profesor : listaProfesores) {
            boolean coincideNombre = profesor.getNombre().contains(nombre);
            boolean coincideApellidoPaterno = profesor.getApellidoPaterno().contains(nombre);
            boolean coincideApellidoMaterno = profesor.getApellidoMaterno().contains(nombre);

            assertTrue(coincideNombre || coincideApellidoPaterno || coincideApellidoMaterno);
        }
    }
    
    @Test
    public void testObtenerListaProfesoresPorNombreInexistente() throws SQLException {
        System.out.println("Obteniendo Lista de Profesores por Nombre");
        String criterioBusqueda = "Oscar";
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> listaProfesores = profesorDAO.obtenerListaProfesoresPorNombre(criterioBusqueda);
        assertNotNull(listaProfesores);
        assertTrue(listaProfesores.isEmpty());
    }
    
    @Test(expected = AssertionError.class)
    public void testObtenerListaProfesoresPorNombreInvalido() throws SQLException {
        System.out.println("Obteniendo Lista de Profesores por Nombre");
        String nombre = "1Erick";
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<Profesor> listaProfesores = profesorDAO.obtenerListaProfesoresPorNombre(nombre);
        assertNotNull(listaProfesores);
        assertFalse(listaProfesores.isEmpty());
        for (Profesor profesor : listaProfesores) {
            boolean coincideNombre = profesor.getNombre().contains(nombre);
            boolean coincideApellidoPaterno = profesor.getApellidoPaterno().contains(nombre);
            boolean coincideApellidoMaterno = profesor.getApellidoMaterno().contains(nombre);

            assertTrue(coincideNombre || coincideApellidoPaterno || coincideApellidoMaterno);
        }
    }

    @Test
    public void testVerificarExistenciaProfesorExitoso() throws SQLException {
        System.out.println("Verificando Existencia de Profesor");

        String nombre = "Erick";
        String apellidoPaterno = "Atzin";
        String apellidoMaterno = "Olarte";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarExistenciaProfesor(nombre, apellidoPaterno, apellidoMaterno);
        assertTrue(resultadoObtenido); 
    }

    @Test
    public void testVerificarExistenciaProfesorNoExitoso() throws SQLException {
        System.out.println("Verificando No Existencia de Profesor");

        String nombre = "Juan";
        String apellidoPaterno = "Perez";
        String apellidoMaterno = "Garcia";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarExistenciaProfesor(nombre, apellidoPaterno, apellidoMaterno);
        assertFalse(resultadoObtenido); 
    }
    
    @Test(expected = AssertionError.class)
    public void testVerificarExistenciaProfesorNombreInvalido() throws SQLException {
        System.out.println("Verificando Existencia de Profesor");

        String nombre = "1.Erick";
        String apellidoPaterno = "Atzin";
        String apellidoMaterno = "Olarte";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarExistenciaProfesor(nombre, apellidoPaterno, apellidoMaterno);
        assertTrue(resultadoObtenido); 
    }
    
    @Test(expected = AssertionError.class)
    public void testVerificarExistenciaProfesorApellidoInvalido() throws SQLException {
        System.out.println("Verificando Existencia de Profesor");

        String nombre = "Erick";
        String apellidoPaterno = "1.Atzin";
        String apellidoMaterno = "1.Olarte";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        boolean resultadoObtenido = profesorDAO.verificarExistenciaProfesor(nombre, apellidoPaterno, apellidoMaterno);
        assertTrue(resultadoObtenido); 
    }
    
    @Test
    public void testObtenerProfesorPorCorreoExitoso() throws SQLException {
        System.out.println("Obteniendo Profesor por Correo");
        String correoExistente = "atzin@example.com";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        Profesor profesorObtenido = profesorDAO.obtenerProfesorPorCorreo(correoExistente);

        assertNotNull(profesorObtenido);
        assertEquals(correoExistente, profesorObtenido.getCorreo());
    }

    @Test(expected = AssertionError.class)
    public void testObtenerProfesorPorCorreoInexistente() throws SQLException {
        System.out.println("Obteniendo Profesor por Correo");
        String correoInexistente = "atzin1@example.com";

        ProfesorDAO profesorDAO = new ProfesorDAO();
        Profesor profesorObtenido = profesorDAO.obtenerProfesorPorCorreo(correoInexistente);

        assertNotNull(profesorObtenido);
        assertEquals(correoInexistente, profesorObtenido.getCorreo());
    }
    
    @Test
    public void testObtenerListaTodosLosProfesores() throws SQLException {
        ProfesorDAO profesorDAO = new ProfesorDAO(); 
        List<Profesor> listaProfesores = profesorDAO.obtenerListaTodosLosProfesores();
        assertEquals(1, listaProfesores.size());
        Profesor primerProfesor = listaProfesores.get(0);
        assertEquals(1, primerProfesor.getIdProfesor());
        assertEquals("Erick", primerProfesor.getNombre());
        assertEquals("Atzin", primerProfesor.getApellidoPaterno());
        assertEquals("Olarte", primerProfesor.getApellidoMaterno());
        assertEquals("atzin@example.com", primerProfesor.getCorreo());
        assertEquals(1, primerProfesor.getIdIdiomas());
        assertEquals(1, primerProfesor.getIdAcceso());
        assertEquals("Activo", primerProfesor.getEstadoProfesor());
        assertEquals("UNIVERSIDAD VERACRUZANA", primerProfesor.getClaveInstitucional());
    }
    
    @Test
    public void testObtenerProfesorPorIDExitoso() throws SQLException {
        System.out.println("Obteniendo Profesor por ID");

        String idProfesorExistente = "1";
        ProfesorDAO profesorDAO = new ProfesorDAO();
        Profesor profesorObtenido = profesorDAO.obtenerProfesorPorID(idProfesorExistente);

        assertNotNull(profesorObtenido);
        assertEquals(idProfesorExistente, String.valueOf(profesorObtenido.getIdProfesor()));
    }
    
       @Test
        public void testActualizarInformacionDelProfesorExitoso() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorNombreIncorrecto() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("1ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorNombreNulo() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorApellidosIncorrecto() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("1AtzinActualizado");
        profesor.setApellidoMaterno("1OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorApellidosNulos() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("");
        profesor.setApellidoMaterno("");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorCorreoInvalido() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizadoexample.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testActualizarInformacionDelProfesorCorreoNulo() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = SQLException.class)
    public void testActualizarInformacionDelProfesorIdiomaInexistente() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(10);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = SQLException.class)
    public void testActualizarInformacionDelProfesorClaveInvalida() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("claveficticia321");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = SQLException.class)
    public void testActualizarInformacionDelProfesorClaveNula() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("");
        profesor.setIdProfesor(1);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "1");
        assertEquals(1, resultadoObtenido);
    }
    
    @Test(expected = AssertionError.class)
    public void testActualizarInformacionDelProfesorInexistente() throws Exception {
        System.out.println("Actualizando Información del Profesor");
 
        Profesor profesor = new Profesor();
        profesor.setNombre("ErickActualizado");
        profesor.setApellidoPaterno("AtzinActualizado");
        profesor.setApellidoMaterno("OlarteActualizado");
        profesor.setCorreo("atzin.actualizado@example.com");
        profesor.setIdIdiomas(1);
        profesor.setEstadoProfesor("Activo");
        profesor.setClaveInstitucional("30MSU0940B");
        profesor.setIdProfesor(30);
        
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int resultadoObtenido = profesorDAO.actualizarInformacionDelProfesor(profesor, "30");
        assertEquals(1, resultadoObtenido);
    }

    
    @Test
    public void testEliminarProfesorExitoso() throws SQLException {
        System.out.println("Eliminando Profesor");
        String correo = "atzin.actualizado@example.com"; 
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int columnaAfectada = profesorDAO.eliminarProfesor(correo);
        assertEquals(2, columnaAfectada);
    }
    
    @Test(expected = AssertionError.class)
    public void testEliminarProfesorInexistente() throws SQLException {
        System.out.println("Eliminando Profesor");
        String correo = "oscar@example.com"; 
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int columnaAfectada = profesorDAO.eliminarProfesor(correo);
        assertEquals(2, columnaAfectada);
    }
    
    @Test(expected = AssertionError.class)
    public void testEliminarProfesorCorreoInvalido() throws SQLException {
        System.out.println("Eliminando Profesor");
        String correo = "atzinexamplecom"; 
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int columnaAfectada = profesorDAO.eliminarProfesor(correo);
        assertEquals(2, columnaAfectada);
    }
    
    @Test
    public void testObtenerListaDeIdiomasExitoso() throws SQLException {
        System.out.println("Obteniendo Lista de Idiomas");
        List<List<String>> listaEsperada = new ArrayList<>();
        listaEsperada.add(Arrays.asList("1", "Inglés"));
        listaEsperada.add(Arrays.asList("2", "Español"));
        listaEsperada.add(Arrays.asList("3", "Chino mandarín"));
        listaEsperada.add(Arrays.asList("4", "Aleman"));
        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<List<String>> listaObtenida = profesorDAO.obtenerListaDeIdiomas();
        assertNotNull(listaObtenida);

        assertEquals(listaEsperada.size(), listaObtenida.size());
        for (int i = 0; i < listaEsperada.size(); i++) {
            List<String> idiomaEsperado = listaEsperada.get(i);
            List<String> idiomaObtenido = listaObtenida.get(i);

            assertEquals(idiomaEsperado, idiomaObtenido);
        }
    }
    
    @Test
    public void testObtenerListaDeTodosLosEstadoProfesorExitoso() throws SQLException {
        System.out.println("Obteniendo Lista de Todos los Estados de Profesor");
        List<String> listaEsperada = Arrays.asList("Activo");

        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<String> listaObtenida = profesorDAO.obtenerListaDeTodosLosEstadoProfesor();

        assertNotNull(listaObtenida);
        assertFalse(listaObtenida.isEmpty());
        assertEquals(listaEsperada.size(), listaObtenida.size());
        assertTrue(listaObtenida.containsAll(listaEsperada));
    }

    @Test
    public void testObtenerListaDeNombreInstitucionExitoso() throws SQLException {
        System.out.println("Obteniendo Lista de Nombres de Instituciones");

        List<String> listaEsperada = Arrays.asList("UNIVERSIDAD VERACRUZANA");

        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<String> listaObtenida = profesorDAO.obtenerListaDeNombreInstitucion();

        assertNotNull(listaObtenida);
        assertFalse(listaObtenida.isEmpty());
        assertEquals(listaEsperada.size(), listaObtenida.size());
        assertTrue(listaObtenida.containsAll(listaEsperada));
    }
    
        @Test
    public void testObtenerListaDeInstitucionesExitoso() {
        System.out.println("Obteniendo Lista de Instituciones");
        List<List<String>> listaEsperada = new ArrayList<>();
        listaEsperada.add(Arrays.asList("30MSU0940B", "UNIVERSIDAD VERACRUZANA"));

        ProfesorDAO profesorDAO = new ProfesorDAO();
        List<List<String>> listaObtenida = profesorDAO.obtenerListaDeInstituciones();

        assertNotNull(listaObtenida);
        assertFalse(listaObtenida.isEmpty());
        assertEquals(listaEsperada.size(), listaObtenida.size());
        assertTrue(listaObtenida.containsAll(listaEsperada));
    }

    


}
