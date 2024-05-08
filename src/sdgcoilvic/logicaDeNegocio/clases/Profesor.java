package sdgcoilvic.logicaDeNegocio.clases;

import com.mysql.cj.jdbc.Blob;
import java.util.Objects;

public class Profesor {
    private int idProfesor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private String claveInstitucional;
    private int idIdiomas;
    private Blob foto;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public int getIdIdiomas() {
        return idIdiomas;
    }

    public void setIdIdiomas(int idIdiomas) {
        this.idIdiomas = idIdiomas;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;         }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Profesor otroProfesor = (Profesor) obj;
        return idProfesor == otroProfesor.idProfesor &&
                Objects.equals(nombre, otroProfesor.nombre) &&
                Objects.equals(apellidoPaterno, otroProfesor.apellidoPaterno) &&
                Objects.equals(apellidoMaterno, otroProfesor.apellidoMaterno) &&
                Objects.equals(correo, otroProfesor.correo) &&
                Objects.equals(telefono, otroProfesor.telefono) &&
                Objects.equals(claveInstitucional, otroProfesor.claveInstitucional) &&
                idIdiomas == otroProfesor.idIdiomas &&
                Objects.equals(foto, otroProfesor.foto);
    }
}
