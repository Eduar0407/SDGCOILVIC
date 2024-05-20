package sdgcoilvic.logicaDeNegocio.interfaces;

import java.sql.SQLException;
import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.Colaboracion;

public interface IColaboracion {
    
   public int crearColaboracion(Colaboracion colaboracion, int idProfesor)throws SQLException;
   public int finalizarColaboracion(int idColaboracion);
   public int cerrarColaboracion(int idColaboracion);
   public int iniciarColaboracion(int idColaboracion);
   public Colaboracion consultarColaboracion(int idColaboracion);
   public List<Colaboracion> consultarTodasColaboraciones();
   public List<Colaboracion> filtrarColaboraciones(String filtro);
}
