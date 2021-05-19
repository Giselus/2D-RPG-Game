package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.ControllerCharacter;

public class Main extends Application {
    static Stage mainStage;
    public static ControllerCharacter controller;
    public static void main(String[] args){
        Application.launch(args);
    }
    public static void setScene(String tmp){
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Main.class.getResource(tmp));
            Parent root= loader.load();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
            KeyPolling.pollScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void start(Stage stage) throws Exception {
        mainStage=stage;
        stage.setResizable(false);
        setScene("/resources/fxml/sceneMenu.fxml");
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
        for(Updatable obj: Updatable.updatableList){
            obj.Update(deltaTime);
        }
    }
}
