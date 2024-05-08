package sdgcoilvic.logicaDeNegocio.interfaces;
import sdgcoilvic.logicaDeNegocio.clases.ActividadColaborativa;

/**
 *
 * @author abdie
 */
public interface IActividadColaborativa {
    public int agregarActividadColaborativa(ActividadColaborativa actividadColaborativa);
    public int modificarActividadColaborativa(ActividadColaborativa actividadColaborativa, int idActividadColaborativa);
    public ActividadColaborativa consultarActividadColaborativa(int idActividadColaborativa);
}
