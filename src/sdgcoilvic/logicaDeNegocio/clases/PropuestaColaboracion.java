package sdgcoilvic.logicaDeNegocio.clases;

public class PropuestaColaboracion {
    private int idPropuestaColaboracion;
    private String tipoColaboracion;
    private String nombre;
    private String objetivoGeneral;
    private String temas;
    private int numeroEstudiante;
    private String informacionAdicional;
    private String perfilDeLosEstudiantes;
    private int Idiomas_idIdiomas;
    private int Periodo_idPeriodo;
    private int Profesor_idProfesor;
    private int Estado_Propuesta_idEstadoPropuesta;

    public PropuestaColaboracion(int idPropuestaColaboracion, String tipoColaboracion, String nombre, String objetivoGeneral, 
            String temas, int numeroEstudiante, String informacionAdicional, String perfilDeLosEstudiantes, 
            int Idiomas_idIdiomas, int Periodo_idPeriodo, int Profesor_idProfesor, int Estado_Propuesta_idEstadoPropuesta) {
        this.idPropuestaColaboracion = idPropuestaColaboracion;
        this.tipoColaboracion = tipoColaboracion;
        this.nombre = nombre;
        this.objetivoGeneral = objetivoGeneral;
        this.temas = temas;
        this.numeroEstudiante = numeroEstudiante;
        this.informacionAdicional = informacionAdicional;
        this.perfilDeLosEstudiantes = perfilDeLosEstudiantes;
        this.Idiomas_idIdiomas = Idiomas_idIdiomas;
        this.Periodo_idPeriodo = Periodo_idPeriodo;
        this.Profesor_idProfesor = Profesor_idProfesor;
        this.Estado_Propuesta_idEstadoPropuesta = Estado_Propuesta_idEstadoPropuesta;
    }

    public PropuestaColaboracion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getIdPropuestaColaboracion() {
        return idPropuestaColaboracion;
    }

    public void setIdPropuestaColaboracion(int idPropuestaColaboracion) {
        this.idPropuestaColaboracion = idPropuestaColaboracion;
    }

    public String getTipoColaboracion() {
        return tipoColaboracion;
    }

    public void setTipoColaboracion(String tipoColaboracion) {
        this.tipoColaboracion = tipoColaboracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getTemas() {
        return temas;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }

    public int getNumeroEstudiante() {
        return numeroEstudiante;
    }

    public void setNumeroEstudiante(int numeroEstudiante) {
        this.numeroEstudiante = numeroEstudiante;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getPerfilDeLosEstudiantes() {
        return perfilDeLosEstudiantes;
    }

    public void setPerfilDeLosEstudiantes(String perfilDeLosEstudiantes) {
        this.perfilDeLosEstudiantes = perfilDeLosEstudiantes;
    }

    public int getIdiomasId() {
        return Idiomas_idIdiomas;
    }

    public void setIdiomasId(int idiomasId) {
        this.Idiomas_idIdiomas = idiomasId;
    }

    public int getPeriodoId() {
        return Periodo_idPeriodo;
    }

    public void setPeriodoId(int periodoId) {
        this.Periodo_idPeriodo = periodoId;
    }

    public int getProfesorId() {
        return Profesor_idProfesor;
    }

    public void setProfesorId(int profesorId) {
        this.Profesor_idProfesor = profesorId;
    }

    public int getEstado_Propuesta_idEstadoPropuesta() {
        return Estado_Propuesta_idEstadoPropuesta;
    }

    public void setEstado_Propuesta_idEstadoPropuesta(int estado_Propuesta_idEstadoPropuesta) {
        this.Estado_Propuesta_idEstadoPropuesta = estado_Propuesta_idEstadoPropuesta;
    }

    public int getEstadoPropuestaId() {
        return Estado_Propuesta_idEstadoPropuesta;
    }

    public String getEstado() {
    String estado;
    switch (Estado_Propuesta_idEstadoPropuesta) {
        case 1:
            estado = "Aceptado";
            break;
        case 2:
            estado = "Rechazado";
            break;
        case 3:
            estado = "Pendiente";
            break;
        default:
            estado = "Estado desconocido";
    }
    return estado;
}

}