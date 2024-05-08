package sdgcoilvic.logicaDeNegocio.clases;

public class SolicitudColaboracion {
    private int idSolicitudColaboracion;
    private String fechaCreacion;
    private String mensaje;
    private int idProfesor;
    private int idEstadoSolicitud;

    public SolicitudColaboracion() {
    }

    public SolicitudColaboracion(int idSolicitudColaboracion, String fechaCreacion, String mensaje, int idProfesor, int idEstadoSolicitud) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
        this.fechaCreacion = fechaCreacion;
        this.mensaje = mensaje;
        this.idProfesor = idProfesor;
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public int getIdSolicitudColaboracion() {
        return idSolicitudColaboracion;
    }

    public void setIdSolicitudColaboracion(int idSolicitudColaboracion) {
        this.idSolicitudColaboracion = idSolicitudColaboracion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }
}
