package sdgcoilvic.logicaDeNegocio.clases;

public class Acceso {
    private int idAcceso;
    private String contrasenia;
    private String usuario;
    private int tipoUsuario;

    private final static String EXPRESION_REGULAR_ACCESO = "^[a-zA-Z0-9]{1,20}$";
    private final static String EXPRESION_REGULAR_CONTRASENYA = "^(?=.*[!@#$%&*()\\\\[\\\\]{}<>\\\\?\\\\/\\\\\\\\|:;,.\\\\-_+=])"
            + "[a-zA-Z0-9!@#$%&*()\\\\[\\\\]{}<>\\\\?\\\\/\\\\\\\\|:;,.\\\\-_+=]{8,12}$";

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.validarContrasenya(contrasenia);
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.validarAcceso(usuario);
        this.usuario = usuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void validarAcceso(String acceso) {
        if (!acceso.matches(EXPRESION_REGULAR_ACCESO )) {
            throw new IllegalArgumentException("""
                                                El usuario es inválido.
                                                1. Debe contener de 1 a 20 caracteres
                                                2. No debe contener caracteres especiales
                                                3. No puede contener espacios en blanco 
                                                """);
        }
    }

    private void validarContrasenya(String contrasenia) {
        if (!contrasenia.matches(EXPRESION_REGULAR_CONTRASENYA)) {
            throw new IllegalArgumentException("""
                                                La contraseña es inválida.
                                                1. Debe contener de 8 a 12 caracteres
                                                2. La contraseña debe contener al menos un caracter especial
                                                3. No puede contener espacios en blanco 
                                                """);
        }
    }
}
