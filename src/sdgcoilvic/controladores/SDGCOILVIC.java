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
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDeInstituciones.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.show();    
    } 
    
     public void mostrarVentanaAcceso (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/Acceso.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrativoMenu(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AdministrativoMenu.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaGestionDeColaboraciones (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDeColaboraciones.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaEvidenciasDeActividad(Stage stage) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/EvidenciasDeActividad.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        EvidenciasDeActividadControlador controller = loader.getController();
        controller.setStage(stage);

        stageAgregar.show();
    }
   
    public void mostrarVentanaGestionDeOfertasDeColaboracion (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDeOfertasDeColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaGestionDeProfesores (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDeProfesores.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAgregarProfesorExterno(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AgregarProfesorExterno.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AgregarProfesorUV.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        AgregarProfesorUVControlador controller = loader.getController();
        controller.setOnCloseCallback(onCloseCallback);
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaModificarProfesorUV(Stage stage) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/ModificarProfesorUV.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        ModificarProfesorUVControlador controller = loader.getController();
        controller.setStage(stage);

        stageAgregar.show();
    }
    
        public void mostrarVentanaModificarProfesorExterno(Stage stage) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/ModificarProfesorExterno.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        ModificarProfesorExternoControlador controller = loader.getController();
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaGestionDePropuestasColaboracion (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDePropuestasColaboracion.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaEvaluarPropuestaDeColaboracion(Stage stage) throws IOException {
        Stage stageAgregar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/EvaluarPropuestaDeColaboracion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAgregar.setScene(scene);

        stageAgregar.initModality(Modality.APPLICATION_MODAL);
        EvaluarPropuestaColaboracionControlador controller = loader.getController();
        controller.setStage(stage);

        stageAgregar.show();
    }
    
    public void mostrarVentanaGestionDeInstituciones(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/GestionDeInstituciones.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaRegistrarInstitucion(Stage stage, Runnable onCloseCallback) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/RegistrarInstitucion.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/ProfesorMenu.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrarSolicitudes(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AdministrarSolicitudes.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void mostrarVentanaAdministrarActividades(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AdministrarActividades.fxml"));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }
    public void mostrarVentanaAgregarActividad(Stage stage) throws IOException {
        Stage stageRegistrar = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sdgcoilvic/InterfazDeUsuario/AgregarActividad.fxml"));
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
