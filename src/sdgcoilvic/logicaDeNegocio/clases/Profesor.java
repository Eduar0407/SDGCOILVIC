package sdgcoilvic.logicaDeNegocio.clases;


public class Profesor {
    private int idProfesor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String claveInstitucional;
    private int idIdiomas;
    private int idEstado;
    private int idAcceso;

    private final static String EXPRESION_REGULAR = "^[\\p{L}áéíóúÁÉÍÓÚüÜ\\s]{1,60}$";
    private final static String EXPRESION_REGULAR_CORREO_ELECTRONICO = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z.-]{2,255}\\.[a-zA-Z]{2,}$";

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
        if (!nombre.matches(EXPRESION_REGULAR)) {
            throw new IllegalArgumentException();
        }
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        if (!apellidoPaterno.matches(EXPRESION_REGULAR)) {
            throw new IllegalArgumentException();
        }
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        if (!apellidoMaterno.matches(EXPRESION_REGULAR)) {
            throw new IllegalArgumentException();
        }
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (!correo.matches(EXPRESION_REGULAR_CORREO_ELECTRONICO)) {
            throw new IllegalArgumentException();
        }
        this.correo = correo;
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
    
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno + " " + correo;
    }
    
    @Override
    public boolean equals(Object object) {
        if ((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
        final Profesor otroProfesor = (Profesor) object;
        return (this.idProfesor == otroProfesor.idProfesor
            && this.nombre == null ? otroProfesor.nombre == null : this.nombre.equals(otroProfesor.nombre))
            && (this.apellidoPaterno == null ? otroProfesor.apellidoPaterno == null : this.apellidoPaterno.equals(otroProfesor.apellidoPaterno))   
            && (this.apellidoMaterno == null ? otroProfesor.apellidoMaterno == null : this.apellidoMaterno.equals(otroProfesor.apellidoMaterno))  
            && (this.correo == null ? otroProfesor.correo == null : this.correo.equals(otroProfesor.correo))
            && (this.claveInstitucional == null ? otroProfesor.claveInstitucional == null : this.claveInstitucional.equals(otroProfesor.claveInstitucional))
            && this.idIdiomas == otroProfesor.idIdiomas
            && this.idEstado == otroProfesor.idEstado
            && this.idAcceso == otroProfesor.idAcceso;
    }

}
