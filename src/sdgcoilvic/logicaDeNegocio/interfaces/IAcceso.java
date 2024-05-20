package sdgcoilvic.logicaDeNegocio.interfaces;


import java.sql.SQLException;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public interface IAcceso {
   public int agregarAcceso (Acceso acceso)throws SQLException ;
   public int verificarExistenciaAcceso(String usuario, String contrasenia)throws SQLException ; 
   public String obtenerTipoUsuario(String usuario)throws SQLException ; 
   public int obtenerIdProfesor (String usuario);
   public Profesor obtenerProfesorPorID(int idProfesor) throws SQLException;
}
