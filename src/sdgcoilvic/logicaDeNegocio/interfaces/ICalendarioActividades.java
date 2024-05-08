package sdgcoilvic.logicaDeNegocio.interfaces;

    import java.util.List;
    import sdgcoilvic.logicaDeNegocio.clases.CalendarioActividades;

public interface ICalendarioActividades {
    int agregarCalendarioActividades(CalendarioActividades calendario) ;
    int modificarCalendarioActividades(CalendarioActividades calendario, int idCalendarioActividades) ;
    List<CalendarioActividades> consultarUnCalendarioActividades();
}