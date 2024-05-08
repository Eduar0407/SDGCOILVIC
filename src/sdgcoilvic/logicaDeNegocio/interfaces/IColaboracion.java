package sdgcoilvic.logicaDeNegocio.interfaces;

import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.Colaboracion;

public interface IColaboracion {
    
    int crearColaboracion(Colaboracion colaboracion);
    int finalizarColaboracion(int idColaboracion);
    int cerrarColaboracion(int idColaboracion);
    int iniciarColaboracion(int idColaboracion);
    int modificarColaboracion(Colaboracion colaboracion);
    Colaboracion consultarColaboracion(int idColaboracion);
    List<Colaboracion> consultarTodasColaboraciones();
    List<Colaboracion> filtrarColaboraciones(String filtro);
}
