package sdgcoilvic.logicaDeNegocio.clases;

public class Institucion {
    private String claveInstitucional;
    private String nombreInstitucion;
    private String nombrePais;
    private String correo;

    private final static String EXPRESION_REGULAR_CLAVE_INSTITUCIONAL = "^[a-zA-Z0-9]{1,20}$";
    private final static String EXPRESION_REGULAR_NOMBRE_INSTITUCION = "^[a-zA-Z]+$";
    private final static String EXPRESION_REGULAR_NOMBRE_PAIS = "^[a-zA-ZáéíóúÁÉÍÓÚüÜ\\s]+$";
    private final static String EXPRESION_REGULAR_CORREO_ELECTRONICO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,}$";

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
            throw new IllegalArgumentException("El correo electrónico es inválido.");
        }
        this.correo = correo;
    }
}