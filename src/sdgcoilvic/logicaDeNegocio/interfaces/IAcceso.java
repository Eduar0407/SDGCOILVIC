package sdgcoilvic.logicaDeNegocio.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import sdgcoilvic.logicaDeNegocio.clases.Acceso;

public interface IAcceso {
    int agregarAcceso (Acceso acceso)throws SQLException ;
    int existeAcceso(String usuario, String contrasenia)throws SQLException ; 
    int obtenerTipoUsuario(String usuario)throws SQLException ; 
}
