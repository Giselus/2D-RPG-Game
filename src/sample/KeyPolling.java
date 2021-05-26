package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class KeyPolling {

    private static Scene scene;
    private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();
    private static final Set<KeyCode> keysLastlyDown = new HashSet<>();

    public KeyPolling(){

    }
    public static void pollScene(Scene scene){
        clearKeys();
        removeCurrentKeyHandlers();
        setScene(scene);
    }

    private static void clearKeys(){
        keysCurrentlyDown.clear();
        keysLastlyDown.clear();
    }

    private static void removeCurrentKeyHandlers(){
        if(scene != null){
            KeyPolling.scene.setOnKeyPressed(null);
            KeyPolling.scene.setOnKeyReleased(null);
        }
    }

    private static void setScene(Scene scene){
        KeyPolling.scene = scene;
        KeyPolling.scene.setOnKeyPressed((keyEvent -> {
            keysCurrentlyDown.add(keyEvent.getCode());
        }));
        KeyPolling.scene.setOnKeyReleased((keyEvent -> {
            keysCurrentlyDown.remove(keyEvent.getCode());
        }));
    }

    public static void refreshInput(){
        keysLastlyDown.clear();
        for(KeyCode code:keysCurrentlyDown)
            keysLastlyDown.add(code);
    }

    public static boolean isDown(KeyCode keyCode){
        return keysCurrentlyDown.contains(keyCode);
    }

    public static boolean pressedDown(KeyCode keyCode){
        return keysCurrentlyDown.contains(keyCode) && !keysLastlyDown.contains(keyCode);
    }
}
