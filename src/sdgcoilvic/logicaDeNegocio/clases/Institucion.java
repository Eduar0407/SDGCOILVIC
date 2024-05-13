package sdgcoilvic.logicaDeNegocio.clases;

public class Institucion {
    private String claveInstitucional;
    private String nombreInstitucion;
    private String nombrePais;
    private String correo;

    private final static String EXPRESION_REGULAR_CLAVE_INSTITUCIONAL = "^[A-Z0-9]{1,20}$";
    private final static String EXPRESION_REGULAR_NOMBRE_INSTITUCION = "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,200}$";
    private final static String EXPRESION_REGULAR_NOMBRE_PAIS = "^[\\p{L}0-9áéíóúÁÉÍÓÚüÜ\\s]{1,60}$";
    private final static String EXPRESION_REGULAR_CORREO_ELECTRONICO = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z.-]{2,255}\\.[a-zA-Z]{2,}$";



    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        if (!claveInstitucional.matches(EXPRESION_REGULAR_CLAVE_INSTITUCIONAL)) {
            throw new IllegalArgumentException("La clave institucional es inválida.");
        }
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        if (!nombreInstitucion.matches(EXPRESION_REGULAR_NOMBRE_INSTITUCION)) {
            throw new IllegalArgumentException("El nombre de la institución es inválido.");
        }
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        if (!nombrePais.matches(EXPRESION_REGULAR_NOMBRE_PAIS)) {
            throw new IllegalArgumentException("El nombre del país es inválido.");
        }
        this.nombrePais = nombrePais;
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
     @Override
    public String toString() {
        return claveInstitucional + " " + nombreInstitucion + " " + nombrePais + " " + correo;
    }
    
    @Override 
    public boolean equals(Object object) {
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
       final Institucion otraInstitucion = (Institucion) object;
       return (this.claveInstitucional == null ? otraInstitucion.claveInstitucional == null : this.claveInstitucional.equals(otraInstitucion.claveInstitucional))
            && (this.nombreInstitucion == null ? otraInstitucion.nombreInstitucion == null : this.nombreInstitucion.equals(otraInstitucion.nombreInstitucion))
            && (this.nombrePais == null ? otraInstitucion.nombrePais == null : this.nombrePais.equals(otraInstitucion.nombrePais))   
            && (this.correo == null ? otraInstitucion.correo == null : this.correo.equals(otraInstitucion.correo));
 
    }
}