package sdgcoilvic.logicaDeNegocio.interfaces;

import java.sql.SQLException;
import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public interface IProfesor {
    int registrarProfesor(Profesor profesor, Acceso accesor) throws SQLException;
    public boolean verificarExistenciaProfesor(String nombre, String apellidoPaterno, String apellidoMaterno) throws SQLException;
    public boolean verificarSiExisteElCorreo(String correo) throws SQLException ;
    public Profesor obtenerProfesor(int idProfesor) throws SQLException;
    
    int actualizarProfesor(Profesor profesor, int idProfesor)throws SQLException;
    public Profesor obtenerProfesorPorNombre(String nombreProfesor)throws SQLException;
    public List<List<String>> obtenerListaDeIdiomas() throws SQLException;
     public List<List<String>> obtenerListaDeInstituciones() throws SQLException;
    public List<Profesor> obtenerListaTodosLosProfesores()throws SQLException ;
}
