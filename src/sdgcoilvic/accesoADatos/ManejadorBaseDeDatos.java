package sdgcoilvic.accesoADatos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ManejadorBaseDeDatos {
    private static final Logger LOG = Logger.getLogger(ManejadorBaseDeDatos.class);
    private static Connection conexion;

    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = conectar();
        }

        return conexion;
    }

    private static Connection conectar() throws SQLException {
        Connection nuevaConexion = null;
        Properties propiedades = new ManejadorBaseDeDatos().obtenerArchivoPropiedades();

        if (propiedades != null) {
            String direccion = propiedades.getProperty("direccion");
            String usuario = propiedades.getProperty("usuario");
            String contraseña = propiedades.getProperty("contrasenya");
            nuevaConexion = DriverManager.getConnection(direccion, usuario, contraseña);
        } else {
            throw new SQLDataException("No fue posible encontrar las credenciales de la base de datos");
        }
        return nuevaConexion;
    }

    private Properties obtenerArchivoPropiedades() {
        Properties propiedades = null;

        try (FileInputStream archivoConfiguracion = new FileInputStream(new File("src\\sdgcoilvic\\accesoADatos\\Logger.txt"))) {
            if (archivoConfiguracion != null) {
                propiedades = new Properties();
                propiedades.load(archivoConfiguracion);
            }
        } catch (FileNotFoundException excepcionArchivoNoEncontrado) {
            System.out.println(excepcionArchivoNoEncontrado.getMessage());
        } catch (IOException excepcionIO) {
            System.out.println(excepcionIO.getMessage());
        }
        return propiedades;
    }

    public static boolean cerrarConexion() {
        boolean estaCerrada = false;

        try {
            if (conexion != null) {
                conexion.close();
            }
            estaCerrada = true;
        } catch (SQLException excepcionSQL) {
            LOG.fatal(excepcionSQL);
        }
        return estaCerrada;
    }

    public static boolean rollback() throws SQLException {
        boolean isReversed = false;

        try {
            if (conexion != null) {
                conexion.rollback();
            }
            isReversed = true;
        } catch (SQLException sQLException) {
            throw sQLException;
        }
        return isReversed;
    }
}

