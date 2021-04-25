package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.ControllerCharacter;

public class Main extends Application {
    public static ControllerCharacter controller;
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        stage.setResizable(false);
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/fxml/sceneMenu.fxml"));
        Parent root= loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        KeyPolling.pollScene(scene);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                float deltaTime = (float)((now - lastFrameNanos) /1e9);
                lastFrameNanos = now;
                tick(deltaTime);
            }
        };
        timer.start();
    }
    private float lastFrameNanos;

    private void tick(float deltaTime){
        //Input -> logic -> rendering
        if(controller!=null){
            controller.move(deltaTime);
        }
    }
}
