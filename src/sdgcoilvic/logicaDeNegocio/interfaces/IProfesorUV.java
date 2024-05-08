package sdgcoilvic.logicaDeNegocio.interfaces;

import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;

public interface IProfesorUV {
    int registrarProfesorUV( ProfesorUV profesorUV);
    int actualizarProfesor(ProfesorUV profesorUV,int idProfesorUV);
    public ProfesorUV obtenerProfesorPorNumeroPersonal(String NumeroPersonal);
    public List<ProfesorUV> obtenerListaTodosLosProfesores() ;
}
