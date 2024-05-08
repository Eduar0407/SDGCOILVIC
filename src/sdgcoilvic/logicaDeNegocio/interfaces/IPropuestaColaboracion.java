package sdgcoilvic.logicaDeNegocio.interfaces;

import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.PropuestaColaboracion;

public interface IPropuestaColaboracion {
    int someterPropuestaColaboracion(PropuestaColaboracion propuesta);
    void evaluarPropuestaColaboracion(PropuestaColaboracion propuesta, boolean aprobada);
    List<PropuestaColaboracion> consultarPropuestasColaboracion();
    List<PropuestaColaboracion> consultarPropuestasColaboracionPorProfesor(int idProfesor);
    List<PropuestaColaboracion> consultarPropuestasColaboracionPorEstado(boolean aprobada);
}

