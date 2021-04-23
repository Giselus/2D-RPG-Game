package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);

        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/resources/fxml/sceneMenu.fxml"))));
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



    }
}
