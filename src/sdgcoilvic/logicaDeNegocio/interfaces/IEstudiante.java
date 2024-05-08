package sdgcoilvic.logicaDeNegocio.interfaces;

import sdgcoilvic.logicaDeNegocio.clases.Estudiante;
import java.util.List;

public interface IEstudiante{

    public int registrarEstudiante(Estudiante estudiante) ;
    public Estudiante obtenerEstudiantePorNombre(String nombre) ;
    public List<Estudiante>obtenerTodosLosEstudiantes() ;
    
}
