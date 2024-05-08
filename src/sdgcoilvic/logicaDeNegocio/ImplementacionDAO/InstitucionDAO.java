package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.Institucion;
import sdgcoilvic.logicaDeNegocio.interfaces.IInstitucion;

public class InstitucionDAO implements IInstitucion {
    private static final String AGREGAR_INSTITUCION = """
                                                      INSERT INTO institucion (claveInstitucional, nombreInstitucion, Pais_idPais, correo)
                                                      VALUES (?,?, (SELECT idPais FROM pais WHERE nombrePais = ?),?);""";
    private static final String CONSULTA_TODAS_LAS_INSTITUCIONES = "SELECT i.claveInstitucional, i.nombreInstitucion, p.nombre AS nombrePais "
                                                            + "FROM institucion i "
                                                            + "INNER JOIN pais p ON i.Pais_idPais = p.idPais";
    private static final String OBTENER_INSTITUCION_POR_CLAVE = "SELECT i.claveInstitucional, i.nombreInstitucion, p.nombre AS nombrePais "
                                                         + "FROM institucion AS i "
                                                         + "JOIN pais AS p ON i.Pais_idPais = p.idPais "
                                                         + "WHERE i.claveInstitucional = ?";
    private static final String OBTENER_INSTITUCIONES_POR_PAIS = "SELECT institucion.claveInstitucional, institucion.nombreInstitucion "
                                                          + "FROM institucion "
                                                          + "JOIN pais ON institucion.Pais_idPais = pais.idPais "
                                                          + "WHERE pais.nombre = ?";

    @Override
    public int insertarInstitucion(Institucion institucion) throws SQLException{
        int resultado = 0;
        try {
            ManejadorBaseDeDatos.obtenerConexion().setAutoCommit(false);
            PreparedStatement statement = ManejadorBaseDeDatos.obtenerConexion().prepareStatement(AGREGAR_INSTITUCION);
            statement.setString(1, institucion.getClaveInstitucional());
            statement.setString(2, institucion.getNombreInstitucion());
            statement.setString(3, institucion.getNombrePais());
            statement.setString(4, institucion.getCorreo());
            resultado = statement.executeUpdate();
            ManejadorBaseDeDatos.obtenerConexion().commit();
        } catch (SQLException ex) {
            ManejadorBaseDeDatos.obtenerConexion().rollback();
        } finally {
            ManejadorBaseDeDatos.obtenerConexion().close();
        }
        return resultado;
    }
    
    @Override
     public List<Institucion> obtenerTodasLasInstituciones() {
         List<Institucion> listaInstituciones = new ArrayList<>();
         String consulta = CONSULTA_TODAS_LAS_INSTITUCIONES;
         try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
              PreparedStatement statement = conexion.prepareStatement(consulta);
              ResultSet rs = statement.executeQuery()) {
             while (rs.next()) {
                 Institucion institucion = new Institucion();
                 institucion.setClaveInstitucional(rs.getString("claveInstitucional"));
                 institucion.setNombreInstitucion(rs.getString("nombreInstitucion"));
                 institucion.setNombrePais(rs.getString("nombrePais"));
                 listaInstituciones.add(institucion);
             }
         } catch (SQLException ex) {
             Logger.getLogger(InstitucionDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         return listaInstituciones; 
     }

     @Override
    public Institucion obtenerInstitucionPorClave(String claveInstitucional) {
        Institucion institucion = null;
        String consulta = OBTENER_INSTITUCION_POR_CLAVE;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            preparedStatement.setString(1, claveInstitucional);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    institucion = new Institucion();
                    institucion.setClaveInstitucional(rs.getString("claveInstitucional"));
                    institucion.setNombreInstitucion(rs.getString("nombreInstitucion"));
                    institucion.setNombrePais(rs.getString("nombrePais"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return institucion;
    }


     @Override
     public List<Institucion> obtenerListaInstitucionesPorPais(String nombrePais) {
         List<Institucion> listaInstitucionesPorPais = new ArrayList<>();
         String consulta = OBTENER_INSTITUCIONES_POR_PAIS;
         try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
              PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
             preparedStatement.setString(1, nombrePais);
             try (ResultSet rs = preparedStatement.executeQuery()) {
                 while (rs.next()) {
                     Institucion institucion = new Institucion();
                     institucion.setClaveInstitucional(rs.getString("claveInstitucional"));
                     institucion.setNombreInstitucion(rs.getString("nombreInstitucion"));
                     listaInstitucionesPorPais.add(institucion);
                 }
             }
         } catch (SQLException ex) {
             Logger.getLogger(InstitucionDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         return listaInstitucionesPorPais;
     }

}
