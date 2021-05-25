package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.ControllerCharacter;

public class Main extends Application {
    public static Stage mainStage;
    static String styleCss;
    public static Scene mainScene;
    public static ControllerCharacter controller;
    public static void main(String[] args){
        Application.launch(args);
    }
    public static void setScene(String tmp,String styleCss){
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Main.class.getResource(tmp));
            Parent root= loader.load();
            Scene scene = new Scene(root);
           // String css=this.getClass().getResource("/resources/style/styleInventory.css").toExternalForm();
            scene.getStylesheets().add(Main.class.getResource(styleCss).toExternalForm());
            mainScene=scene;
            mainStage.setScene(scene);
            mainStage.show();

            KeyPolling.pollScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    static boolean toClear = false;
    public static void clearUptadables(){
        toClear = true;
    }
    @Override
    public void start(Stage stage) throws Exception {
        mainStage=stage;
        stage.setResizable(false);
        setScene("/resources/fxml/sceneMenu.fxml","/resources/style/styleMenu.css");
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
            if(toClear){
                Updatable.updatableList.clear();
                toClear = false;
                break;
            }
            obj.Update(deltaTime);
        }
        for(Updatable obj: Updatable.updatableList){
            if(toClear){
                Updatable.updatableList.clear();
                toClear = false;
                break;
            }
            obj.LateUpdate(deltaTime);
        }
    }
}
