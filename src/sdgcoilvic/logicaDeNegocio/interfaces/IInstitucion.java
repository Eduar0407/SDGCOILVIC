package sdgcoilvic.logicaDeNegocio.interfaces;

import java.sql.SQLException;
import java.util.List;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;

public interface IInstitucion {
    int insertarInstitucion(Institucion institucion)throws SQLException;
    public List<Institucion>obtenerTodasLasInstituciones() throws SQLException;
    public Institucion obtenerInstitucionPorClave(String claveInstitucional) throws SQLException;
    public List<Institucion>obtenerListaInstitucionesPorPais(String nombrePais) throws SQLException;
}
