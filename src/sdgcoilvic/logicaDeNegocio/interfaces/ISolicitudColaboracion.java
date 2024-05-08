package sdgcoilvic.logicaDeNegocio.interfaces;

import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.SolicitudColaboracion;

public interface ISolicitudColaboracion {
    int elaborarSolicitudColaboracion(SolicitudColaboracion solicitud) ;
    int evaluarSolicitudColaboracion(SolicitudColaboracion solicitud, boolean aprobada);
    List<SolicitudColaboracion> consultarSolicitudesColaboracion();
    List<SolicitudColaboracion> consultarSolicitudesColaboracionPorProfesor(int idProfesor);
    List<SolicitudColaboracion> consultarSolicitudesColaboracionPorEstado(boolean aprobada);
}
