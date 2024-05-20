package sdgcoilvic.controladores;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SDGCOILVIC extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/acceso.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.show();    
    } 
    
     public void mostrarVentanaAcceso (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/Acceso.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrativoMenu(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AdministrativoMenu.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaGestionDeColaboraciones (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/GestionDeColaboraciones.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaEvidenciasDeActividad(Stage stage) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/EvidenciasDeActividad.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        EvidenciasDeActividadControlador controller = loader.getController();
        controller.setStage(stage);

        stageAgregar.show();
    }
   
    public void mostrarVentanaGestionDeOfertasDeColaboracion (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/GestionDeOfertasDeColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaGestionDeProfesores (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/GestionDeProfesores.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAgregarProfesorExterno(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AgregarProfesorExterno.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        AgregarProfesorExternoControlador controller = loader.getController();
        controller.setOnCloseCallback(onCloseCallback);
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaAgregarProfesorUV(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AgregarProfesorUV.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        AgregarProfesorUVControlador controller = loader.getController();
        controller.setOnCloseCallback(onCloseCallback);
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaModificarProfesorUV(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/ModificarProfesorUV.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        ModificarProfesorUVControlador controller = loader.getController();
        controller.setOnCloseCallback(onCloseCallback);
        controller.setStage(stage);

        stageAgregar.show();
    }
    
        public void mostrarVentanaModificarProfesorExterno(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/ModificarProfesorExterno.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        ModificarProfesorExternoControlador controller = loader.getController();
        controller.setOnCloseCallback(onCloseCallback);
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaGestionDePropuestasColaboracion (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/GestionDePropuestasColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaEvaluarPropuestaDeColaboracion(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/EvaluarPropuestaDeColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void mostrarVentanaGestionDeInstituciones(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/GestionDeInstituciones.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaRegistrarInstitucion(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/RegistrarInstitucion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageRegistrar.setScene(scene);

        stageRegistrar.initModality(Modality.APPLICATION_MODAL);
        RegistrarInstitucionControlador controller = loader.getController();
        controller.setStage(stage);
        controller.setOnCloseCallback(onCloseCallback);
        stageRegistrar.show();
    }
    
    public void mostrarVentanaProfesorMenu (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/ProfesorMenu.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrarSolicitudes(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AdministrarSolicitudes.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrarColaboracionesDisponibles(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AdministrarColaboracionesDisponibles.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
        
    public void mostrarVentanaDeclaracionDeProposito(Stage stage) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/DeclaracionDeProposito.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageRegistrar.setScene(scene);

        stageRegistrar.initModality(Modality.APPLICATION_MODAL);
        DeclaracionDePropositoControlador controller = loader.getController();
        controller.setStage(stage);
        stageRegistrar.show();
    }
    
    public void mostrarVentanaAdministrarActividades(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AdministrarActividades.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrarPropuestasDeColaboracion(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AdministrarPropuestasDeColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaNuevaPropuesta(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/NuevaPropuesta.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaActualizarPropuestaPropuesta(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/ActualizarPropuesta.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaIniciarColaboracion(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/interfazDeUsuario/IniciarColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAgregarEstudiante(Stage stage) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AgregarEstudiante.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageRegistrar.setScene(scene);
 
       stageRegistrar.initModality(Modality.APPLICATION_MODAL);
        AgregarEstudianteControlador controller = loader.getController();
        controller.setStage(stage);
        stageRegistrar.show();
    }
    
    public void mostrarVentanaAgregarActividad(Stage stage) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/interfazDeUsuario/AgregarActividad.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageRegistrar.setScene(scene);

        stageRegistrar.initModality(Modality.APPLICATION_MODAL);
        AgregarActividadControlador controller = loader.getController();
        controller.setStage(stage);

        stageRegistrar.show();
    }
    
   
    
    public static void main(String[] args) {
           launch();
       }

}
