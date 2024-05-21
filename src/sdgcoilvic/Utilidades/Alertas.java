package sdgcoilvic.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.control.ButtonBar;

public class Alertas {

    public static final ButtonType BOTON_SI = new ButtonType("Sí");
    public static final ButtonType BOTON_NO = new ButtonType("No");
    
    public static void mostrarMensaje(AlertType tipoAlerta, String titulo, String contenido) {
        mostrarAlerta(tipoAlerta, titulo, contenido);
    }

    public static boolean mostrarConfirmacion(String titulo, String contenido) {
        return mostrarAlertaDeConfirmacion(titulo, contenido);
    }
    
    public static void mostrarMensajeProfesorNoSeleccionado() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Debe seleccionar un Profesor");
    }
        
    public static void mostrarMensajeNoPuedesIniciarColaboracion() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Para iniciar la colaboracion debe de aceptar almenos una solicitud de colaboración");
    }
    
    public static void mostrarMensajeErrorColaboracionEnCurso() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Para iniciar una nueva colaboracion no debe de tener niguna colaboracion en curso");
    }
    
    public static void mostrarMensajeSolicitudNoSeleccionado() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Debe seleccionar una solicitud");
    }
    
    
    public static void mostrarMensajePropuestaNoSeleccionado() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "Debe seleccionar una Propuesta");
    }
    
    public static void mostrarMensajeAccesoDenegado() {
        mostrarAlerta(AlertType.INFORMATION, "ACCESO DENEGADO", "Su cuenta se encuentra archivada, si desea ingresar contacte a los administrativos.");
    }
    
    public static void mostrarMensajeInicioSesionFallido() {
        mostrarAlerta(AlertType.ERROR, "AVISO", "Usuario o contraseña incorrectos");
    }
    
        public static void mostrarMensajeNoHayInstitucionesRegistradas() {
       mostrarAlerta(AlertType.ERROR, "Error", "No Hay Instituciones Registradas en la base");
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
    
    public static void mostrarMensajeInformacionNoRegistrada() {
        mostrarAlerta(AlertType.ERROR, "Error", "Hubo un error, no pudimos almacenar su informacion");
    }
    
    public static void mostrarMensajeInstitucionNoEncontrada() {
        mostrarAlerta(AlertType.INFORMATION, "AVISO", "No existe coincidencias");
    }
    
    public static void mostrarMensajeElCorreoNoSePudoEnviar() {
        mostrarAlerta(AlertType.ERROR, "Error al enviar correo", "El correo no se pudo enviar. El movimiento no se puede acompletar.");
    }
    
    public static void mostrarMensajeCamposVacios() {
        mostrarMensaje(AlertType.WARNING, "No se puede dejar ningún campo vacío", 
                "Por favor, ingrese toda la información solicitada");
    }
    
    public static void mostrarMensajeErrorBaseDatos() {
        mostrarMensaje(AlertType.ERROR, "ERROR", 
                "No se pudo conectar con la base de datos.\nInténtelo de nuevo o hágalo más tarde");
    }
    
    public static void mostrarMensajeEmailYaRegistrado() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "El Email ingresado ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeArchivoNoSeleccionado() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", "Seleccione un archivo");
    }
    
    public static void mostrarMensajeExito() {
        mostrarMensaje(AlertType.INFORMATION, "Registro exitoso", 
                "La información se registró correctamente en el sistema");
    }
    
    public static void mostrarMensajeExitoInicioColanboracion() {
        mostrarMensaje(AlertType.INFORMATION, "Registro exitoso", 
                "La información se registró correctamente en el sistema. La Colaboracion se encuentra en curso");
    }
    
    public static void mostrarMensajeErrorRegistro() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "No se pudo registrar la información    .\nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeErrorCambioPantalla() {
        mostrarMensaje(AlertType.ERROR, "ERROR", 
                "Ha ocurrido un error inesperado al cambiar de pantalla.\nInténtelo de nuevo o hágalo más tarde");
    }
    
    public static void mostrarMensajeErrorIniciarColaboracion() {
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
    
    public static void mostrarMensajeCorreoConFormatoInvalido () {
        mostrarMensaje(AlertType.WARNING, "Correo invalido", 
                "El correo electrónico es inválido. "
                                + "Por favor, asegúrate de que tenga el formato "
                                + "correcto, por ejemplo: usuario@dominio.com");    
    }
    
    public static void mostrarMensajeProfesorYaExistente() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "El Profesor ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeNumeroPersonalYaExistente() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "El Numero de personal ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeInstitucionYaExistente() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "El Nombre de la institucion ingresada ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
        
    public static void mostrarMensajeClaveInstitucionalYaRegistrada() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "La clave ingresada ya ha sido registrado previamente. \nPor favor, inténtelo nuevamente");
    }
    
    public static void mostrarMensajeDatosIguales() {
        mostrarMensaje(AlertType.INFORMATION, "AVISO", 
                "No hay cambios para actualizar. Por favor, modifique algún campo antes de intentar actualizar.");
    }

    public static boolean mostrarMensajeCancelar() {
        return mostrarConfirmacion("AVISO", "¿Estás seguro que deseas cancelar?");
    }

    public static boolean mostrarMensajeConfirmacionDesactivar() {
        return mostrarConfirmacion("AVISO", "¿Estás seguro que deseas desactivar?");
    }
    
    public static boolean mostrarMensajeConfirmacionArchivarProfesor() {
        return mostrarConfirmacion ("Confirmar archivado", "¿Está seguro de archivar al profesor?");
    }
    
    public static boolean mostrarMensajeConfirmacionActivarProfesor() {
        return mostrarConfirmacion ("Confirmar activación", "Una vez activado, el profesor estará disponible en el sistema.");
    }
    
    public static boolean mostrarMensajeAgregarEstudiantes() {
        return mostrarConfirmacion ("Agregar Estudiante", "¿No desar agruegar un estudiante a la colaboración?");
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
    
    public static boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
}