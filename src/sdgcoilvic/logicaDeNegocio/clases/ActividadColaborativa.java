package sdgcoilvic.logicaDeNegocio.clases;

public class ActividadColaborativa {
    private int idActividadColaborativa;
    private String nombreActividad;
    private String descripcion;
    private int puntaje;
    private int idCalendarioActividades; 

    public int getIdActividadColaborativa() {
        return idActividadColaborativa;
    }

    public void setIdActividadColaborativa(int idActividadColaborativa) {
        this.idActividadColaborativa = idActividadColaborativa;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getIdCalendarioActividades() {
        return idCalendarioActividades;
    }

    public void setIdCalendarioActividades(int idCalendarioActividades) {
        this.idCalendarioActividades = idCalendarioActividades;
    }
}
