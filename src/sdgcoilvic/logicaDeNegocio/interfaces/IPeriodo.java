package sdgcoilvic.logicaDeNegocio.interfaces;


    import java.sql.Date;
    import java.sql.SQLException;
    import java.util.List;
    import sdgcoilvic.logicaDeNegocio.clases.Periodo;

    public interface IPeriodo {
    int agregarPeriodo(Periodo periodo) throws SQLException;
    List<Periodo> consultarTodosPeriodos() throws SQLException;
    List<Periodo> consultarPeriodoPorFecha(Date fecha) throws SQLException;
    List<Periodo> consultarPeriodoPorNombre(String nombrePeriodo) throws SQLException;
}
