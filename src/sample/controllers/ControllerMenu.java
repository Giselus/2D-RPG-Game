package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class ControllerMenu {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneCreator(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneCreator.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSceneLoading(ActionEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneInventory.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void exitGame(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}