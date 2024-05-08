package sdgcoilvic.logicaDeNegocio.interfaces;

import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.Profesor;

public interface IProfesor {
    int registrarProfesor(Profesor profesor);
    int actualizarProfesor(Profesor profesor, int idProfesor);
    public Profesor obtenerProfesorPorNombre(String nombreProfesor);
    public List<Profesor> obtenerListaTodosLosProfesores() ;
}
