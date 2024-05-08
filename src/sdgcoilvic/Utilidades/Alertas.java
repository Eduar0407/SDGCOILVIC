package sdgcoilvic.Utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class Alertas {

    public static final ButtonType BOTON_SI = new ButtonType("Sí");
    public static final ButtonType BOTON_NO = new ButtonType("No");
    
    public static void mostrarMensaje(AlertType tipoAlerta, String titulo, String contenido) {
        mostrarAlerta(tipoAlerta, titulo, contenido);
    }

    public static boolean mostrarConfirmacion(String titulo, String contenido) {
        return mostrarAlertaDeConfirmacion(titulo, contenido);
    }
    
    public static void mostrarMensajeInicioSesionFallido() {
        mostrarAlerta(AlertType.ERROR, "AVISO", "Usuario o contraseña incorrectos");
    }
    
    public static void mostrarMensajeCamposVacios() {
        mostrarMensaje(AlertType.WARNING, "No se puede dejar ningún campo vacío", 
                "Por favor, ingrese toda la información solicitada");
    }
    
    public static void mostrarMensajeErrorBaseDatos() {
        mostrarMensaje(AlertType.ERROR, "ERROR", 
                "No se pudo conectar con la base de datos.\nInténtelo de nuevo o hágalo más tarde");
    }
    
    public static void mostrarMensajeSinResultados() {
        mostrarAlerta(AlertType.ERROR, "Sin resultados", "No se hallaron resultados");
    }
    
    public static void mostrarMensajeCarpetaNoSeleccionada() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Debe seleccionar una carpeta");
    }
    
    public static void mostrarMensajeEvidenciaDescargado() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "La evidencia fue descargada correctamente");
    }
    
    public static void mostrarMensajeEmailYaRegistrado() {
           mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "El Email ingresado ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeArchivoNoSeleccionado() {
           mostrarMensaje(AlertType.INFORMATION, "AVISO", "Seleccione un archivo");
    }
   
    public static void mostrarMensajeInformacionNoRegistrada() {
        mostrarAlerta(AlertType.ERROR, "Error", "Hubo un error, no pudimos almacenar su informacion");
    }
    
    public static void mostrarMensajeExito() {
        mostrarMensaje(AlertType.INFORMATION, "Registro exitoso", 
                "La información se registró correctamente en el sistema");
    }
    
    public static void mostrarMensajeErrorRegistro() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "No se pudo registrar la información    .\nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeErrorCambioPantalla() {
        mostrarMensaje(AlertType.ERROR, "ERROR", 
                "Ha ocurrido un error inesperado al cambiar de pantalla.\nInténtelo de nuevo o hágalo más tarde");
    }
 
    public static void mostrarMensajeInformacionInvalida() {
        mostrarMensaje(AlertType.WARNING, "Los datos ingresados son inválidos", 
                "Por favor, compruebe la información e inténtelo nuevamente");
    }
    
    public static void mostrarMensajeNingunElementoSeleccionado () {
    mostrarMensaje(AlertType.WARNING, "Ningún elemento seleccionado", 
                "Por favor, seleccione un elemento de la tabla");    
    }

    public static boolean mostrarMensajeCancelar() {
        return mostrarConfirmacion("AVISO", "¿Estás seguro que deseas cancelar?");
    }

    public static boolean mostrarMensajeConfirmacionDesactivar() {
        return mostrarConfirmacion("AVISO", "¿Estás seguro que deseas desactivar?");
    }

    private static void mostrarAlerta(AlertType tipoAlerta, String titulo, String contenido) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private static boolean mostrarAlertaDeConfirmacion(String titulo, String contenido) {
        Alert alerta = new Alert(AlertType.CONFIRMATION, contenido, BOTON_SI, BOTON_NO);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == BOTON_SI;
    }
}