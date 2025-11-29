package com.example;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        scene = new Scene(loadFXML("AbaInicial"));
        stage.setResizable(true);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    public static Object setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);

        if (primaryStage != null) {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen(); // Opcional, mas útil para centralizar
        }

        return fxmlLoader.getController(); // Retorna o Controller
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void abrirLogin() throws IOException {
        
        Stage newStage = new Stage();
        primaryStage = newStage; 
        newStage.initStyle(StageStyle.UNDECORATED); 
        Parent root = loadFXML("Login_tela");
        
        if (scene == null) {
            scene = new Scene(root);
        } else {
            scene.setRoot(root); 
        }
        
        newStage.setResizable(true);
        newStage.setScene(scene); 
        newStage.sizeToScene();
        newStage.centerOnScreen();
        newStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}   