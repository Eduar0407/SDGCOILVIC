package sdgcoilvic.Utilidades;

public class AccesoSingleton {
    private static AccesoSingleton instance;
    private String accesoId;

    private AccesoSingleton() {
        
    }

    public static AccesoSingleton getInstance() {
        if (instance == null) {
            instance = new AccesoSingleton();
        }
        return instance;
    }
    
    public void borrarInstancia() {
        accesoId = "";
    }

    public String getAccesoId() {
        return accesoId;
    }

    public void setAccesoId(String accesoId) {
        this.accesoId = accesoId;
    }
}
