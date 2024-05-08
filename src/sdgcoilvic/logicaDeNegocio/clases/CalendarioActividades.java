package sdgcoilvic.logicaDeNegocio.clases;
import java.sql.Date;

public class CalendarioActividades {
    private int idCalendarioActividades;
    private Date fechaInicio;
    private Date fechaFin;
    private String tema;
    private String herramientas;


    public CalendarioActividades(int idCalendario, Date fechaInicio, Date fechaFin, String tema, String herramientas) {
    this.idCalendarioActividades = idCalendario;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.tema = tema;
    this.herramientas = herramientas;
}

    public CalendarioActividades(int idCalendario, java.util.Date fechaInicio, java.util.Date fechaFin, String tema, String herramientas) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   public CalendarioActividades() {
    this.idCalendarioActividades = 0;
    this.fechaInicio = null; 
    this.fechaFin = null; 
    this.tema = ""; 
    this.herramientas = "";
}



    public int getIdCalendarioActividades() {
        return idCalendarioActividades;
    }

    public void setIdCalendarioActividades(int idCalendarioActividades) {
        this.idCalendarioActividades = idCalendarioActividades;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(String herramientas) {
        this.herramientas = herramientas;
    }

}
