package sdgcoilvic.logicaDeNegocio.clases;

public class Acceso {
    private int idAcceso;
    private String contrasenia;
    private String usuario;
    private String tipoUsuario;


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
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
        @Override
    public boolean equals(Object obj){
        Acceso accesoTemporal = (Acceso)obj;
        return this.contrasenia.equals(accesoTemporal.getContrasenia())&&
                this.usuario.equals(accesoTemporal.getUsuario())&&
                this.tipoUsuario.equals(accesoTemporal.getTipoUsuario()); 
    }


}
