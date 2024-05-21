package sdgcoilvic.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import org.apache.log4j.Logger;

public class CorreoElectronico {
    private static final Logger LOG = Logger.getLogger(CorreoElectronico.class);

    public static boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        boolean enviadoExitosamente = false;

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.starttls.enable", "true");

        Authenticator autenticador = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("proyectocoilvic", "j w a l z n f t a a d s f d f j");
            }
        };

        Session sesion = Session.getInstance(propiedades, autenticador);

        try {
            Message mensajeCorreo = new MimeMessage(sesion);
            mensajeCorreo.setFrom(new InternetAddress("proyectocoilvic@gmail.com"));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(mensaje);

            Transport transport = sesion.getTransport("smtp");
            transport.connect("smtp.gmail.com", "proyectocoilvic", "j w a l z n f t a a d s f d f j");
            transport.sendMessage(mensajeCorreo, mensajeCorreo.getAllRecipients());
            transport.close();

            enviadoExitosamente = true;
        } catch (MessagingException e) {
            enviadoExitosamente = false;
            LOG.info("Error al enviar el correo: " + e);
        }

        return enviadoExitosamente;
    }


    public static boolean verificarEnvioCorreo(String destinatario, String asunto, String mensaje) {
         boolean correoEnviado = enviarCorreo(destinatario, asunto, mensaje);
         boolean enviadoExitosamente = false;
         if (correoEnviado == false) {
             Alert alert = new Alert(Alert.AlertType.ERROR,
                     "El correo no se pudo enviar. ¿Desea intentarlo de nuevo?",
                     ButtonType.YES, ButtonType.NO);
             alert.setHeaderText("Error al enviar correo, verifique su conexion a internet");
             Optional<ButtonType> result = alert.showAndWait();
             if (result.isPresent() && result.get() == ButtonType.YES) {
                 enviadoExitosamente = verificarEnvioCorreo(destinatario, asunto, mensaje);
             }else {
              enviadoExitosamente = false;
             }  
         } else {
             enviadoExitosamente = true;
         }
         return enviadoExitosamente;
    }
}
