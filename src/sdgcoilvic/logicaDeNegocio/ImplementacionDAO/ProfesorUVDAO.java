package sdgcoilvic.logicaDeNegocio.ImplementacionDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdgcoilvic.accesoADatos.ManejadorBaseDeDatos;
import sdgcoilvic.logicaDeNegocio.clases.ProfesorUV;
import sdgcoilvic.logicaDeNegocio.interfaces.IProfesorUV;

public class ProfesorUVDAO implements IProfesorUV {
    private final String INSERTAR_PROFESOR_UV = "INSERT INTO profesor_UV (noPersonal, disciplina, "
            + "Profesor_idProfesor, region_idRegion, "
            + "Categoria_Contratacion_UV_idCategoriaContratacionUV, "
            + "Tipo_Contratacion_UV_idTipoContratacionUV, Area_Académica_idAreaAcadémica) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALIZAR_PROFESOR_UV = "UPDATE profesor_UV SET noPersonal = ?, disciplina = ?, "
            + "Profesor_idProfesor = ?, region_idRegion = ?, "
            + "Categoria_Contratacion_UV_idCategoriaContratacionUV = ?, "
            + "Tipo_Contratacion_UV_idTipoContratacionUV = ?, Area_Académica_idAreaAcadémica = ? "
            + "WHERE idProfesor_UV = ?";

    private static final String OBTENER_PROFESOR_POR_NOMBRE_UV = "SELECT noPersonal, disciplina, "
            + "Profesor_idProfesor, region_idRegion, "
            + "Categoria_Contratacion_UV_idCategoriaContratacionUV, "
            + "Tipo_Contratacion_UV_idTipoContratacionUV, Area_Académica_idAreaAcadémica "
            + "FROM profesor_UV WHERE noPersonal = ?";

    private static final String OBTENER_TODOS_LOS_PROFESORES_UV = "SELECT * FROM profesor_UV";
    
    @Override
    public int registrarProfesorUV(ProfesorUV profesorUV) {
        int columnaAfectada = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(INSERTAR_PROFESOR_UV, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, profesorUV.getNoPersonal());
            preparedStatement.setString(2, profesorUV.getDisciplina());
            preparedStatement.setInt(3, profesorUV.getIdProfesor());
            preparedStatement.setInt(4, profesorUV.getIdRegion());
            preparedStatement.setInt(5, profesorUV.getIdCategoriaContratacionUV());
            preparedStatement.setInt(6, profesorUV.getIdTipoContratacionUV());
            preparedStatement.setInt(7, profesorUV.getIdAreaAcademica());
            columnaAfectada = preparedStatement.executeUpdate();
            ResultSet generatedKeysProfesorUV = preparedStatement.getGeneratedKeys();
            if (generatedKeysProfesorUV.next()) {
                profesorUV.setIdProfesorUV(generatedKeysProfesorUV.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorUVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return columnaAfectada;
    }

    @Override
    public int actualizarProfesor(ProfesorUV profesorUV, int idProfesorUV) {
        int columnaAfectada = 0;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(ACTUALIZAR_PROFESOR_UV)) {
            
            preparedStatement.setString(1, profesorUV.getNoPersonal());
            preparedStatement.setString(2, profesorUV.getDisciplina());
            preparedStatement.setInt(3, profesorUV.getIdProfesor());
            preparedStatement.setInt(4, profesorUV.getIdRegion());
            preparedStatement.setInt(5, profesorUV.getIdCategoriaContratacionUV());
            preparedStatement.setInt(6, profesorUV.getIdTipoContratacionUV());
            preparedStatement.setInt(7, profesorUV.getIdAreaAcademica());
            preparedStatement.setInt(8, idProfesorUV);
            columnaAfectada = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorUVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnaAfectada;
    }

    @Override
    public ProfesorUV obtenerProfesorPorNumeroPersonal(String NumeroPersonal) {
        ProfesorUV profesorUV = null;
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(OBTENER_PROFESOR_POR_NOMBRE_UV)) {
            
            preparedStatement.setString(1, NumeroPersonal);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    profesorUV = new ProfesorUV();
                    profesorUV.setNoPersonal(resultSet.getString("noPersonal"));
                    profesorUV.setDisciplina(resultSet.getString("disciplina"));
                    profesorUV.setIdProfesor(resultSet.getInt("Profesor_idProfesor"));
                    profesorUV.setIdRegion(resultSet.getInt("region_idRegion"));
                    profesorUV.setIdCategoriaContratacionUV(resultSet.getInt("Categoria_Contratacion_UV_idCategoriaContratacionUV"));
                    profesorUV.setIdTipoContratacionUV(resultSet.getInt("Tipo_Contratacion_UV_idTipoContratacionUV"));
                    profesorUV.setIdAreaAcademica(resultSet.getInt("Area_Académica_idAreaAcadémica"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorUVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profesorUV;
    }

    @Override
    public List<ProfesorUV> obtenerListaTodosLosProfesores() {
        List<ProfesorUV> listaProfesores = new ArrayList<>();
        try (Connection conexion = ManejadorBaseDeDatos.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(OBTENER_TODOS_LOS_PROFESORES_UV);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                ProfesorUV profesorUV = new ProfesorUV();
                profesorUV.setNoPersonal(resultSet.getString("noPersonal"));
                profesorUV.setDisciplina(resultSet.getString("disciplina"));
                profesorUV.setIdProfesor(resultSet.getInt("Profesor_idProfesor"));
                profesorUV.setIdRegion(resultSet.getInt("region_idRegion"));
                profesorUV.setIdCategoriaContratacionUV(resultSet.getInt("Categoria_Contratacion_UV_idCategoriaContratacionUV"));
                profesorUV.setIdTipoContratacionUV(resultSet.getInt("Tipo_Contratacion_UV_idTipoContratacionUV"));
                profesorUV.setIdAreaAcademica(resultSet.getInt("Area_Académica_idAreaAcadémica"));
                listaProfesores.add(profesorUV);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorUVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProfesores;
    }
}
