package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ControllerInventory {
    @FXML
    private final int inventoryWidth = 4;
    private final int inventoryHeight = 4;
    @FXML private Button aa;
    @FXML public Button ab;
    @FXML public Button ac;
    @FXML public Button ad;
    @FXML public Button ba;
    @FXML public Button bb;
    @FXML public Button bc;
    @FXML public Button bd;
    @FXML public Button ca;
    @FXML public Button cb;
    @FXML public Button cd;
    @FXML public Button cc;
    @FXML public Button da;
    @FXML public Button db;
    @FXML public Button dc;
    @FXML public Button dd;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private Button[][] buttonInv = new Button[inventoryWidth][inventoryHeight];

    private int[][] itemView = new int[inventoryWidth][inventoryHeight];

    public static TemporaryContainer tmp = new TemporaryContainer();

    public void fillItemView(){
        getButtonInv();
        for(int i=0; i<inventoryHeight; i++){
            for(int j=0; j<inventoryWidth; j++){
                int k = ThreadLocalRandom.current().nextInt(0, 3);
                itemView[i][j] = k;
            }
        }
        changeColorOfButton();
    }
    void changeColorOfButton(){
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                if(itemView[i][j] == 0){
                    buttonInv[i][j].setStyle("-fx-background-color: #ff0000");
                }
                else if(itemView[i][j] == 1){
                    buttonInv[i][j].setStyle("-fx-background-color: #00ff00");
                }
                else{
                    buttonInv[i][j].setStyle("-fx-background-color: #0000ff");
                }
            }
        }
    }
    private void checkProperties(int x, int y){
        if(!tmp.hasItems){
            if(itemView[x][y] != 0){
                tmp.cordX = x;
                tmp.cordY = y;
                tmp.colorId = itemView[x][y];
                tmp.hasItems = true;
            }
            return;
        }
        tmp.hasItems = false;
        if (itemView[x][y] != 0) {
            itemView[tmp.cordX][tmp.cordY] = itemView[x][y];
        }
        else{
            itemView[tmp.cordX][tmp.cordY] = 0;
        }
        itemView[x][y] = tmp.colorId;
        changeColorOfButton();
    }
    public void getButtonInv(){
        buttonInv[0][0] = aa;
        buttonInv[0][1] = ab;
        buttonInv[0][2] = ac;
        buttonInv[0][3] = ad;
        buttonInv[1][0] = ba;
        buttonInv[1][1] = bb;
        buttonInv[1][2] = bc;
        buttonInv[1][3] = bd;
        buttonInv[2][0] = ca;
        buttonInv[2][1] = cb;
        buttonInv[2][2] = cc;
        buttonInv[2][3] = cd;
        buttonInv[3][0] = da;
        buttonInv[3][1] = db;
        buttonInv[3][2] = dc;
        buttonInv[3][3] = dd;
    }
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static class TemporaryContainer{
        Integer cordX;
        Integer cordY;
        Integer colorId;
        boolean hasItems;
        TemporaryContainer(){
            hasItems = false;
        }
    }
    public void pickItemAA(){
        checkProperties(0, 0);
    }
    public void pickItemAB(){
        checkProperties(0, 1);
    }
    public void pickItemAC(){
        checkProperties(0, 2);
    }
    public void pickItemAD(){
        checkProperties(0, 3);
    }
    public void pickItemBA(){
        checkProperties(1, 0);
    }
    public void pickItemBB(){
        checkProperties(1, 1);
    }
    public void pickItemBC(){
        checkProperties(1, 2);
    }
    public void pickItemBD(){
        checkProperties(1, 3);
    }
    public void pickItemCA(){
        checkProperties(2, 0);
    }
    public void pickItemCB(){
        checkProperties(2, 1);
    }
    public void pickItemCC(){
        checkProperties(2, 2);
    }
    public void pickItemCD(){
        checkProperties(2, 3);
    }
    public void pickItemDA(){
        checkProperties(3, 0);
    }
    public void pickItemDB(){
        checkProperties(3, 1);
    }
    public void pickItemDC(){
        checkProperties(3, 2);
    }
    public void pickItemDD(){
        checkProperties(3, 3);
    }
}