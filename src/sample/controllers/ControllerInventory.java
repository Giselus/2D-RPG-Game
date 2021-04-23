package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.Items;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ControllerInventory {
    @FXML
    private final int inventoryWidth = 4;
    private final int inventoryHeight = 4;
    @FXML public Button chosenButton;
    @FXML public Button aa;
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
    @FXML Text itemDescription;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private Button[][] buttonInv = new Button[inventoryWidth][inventoryHeight];

    private Items[][] itemView = new Items[inventoryWidth][inventoryHeight];

    public static TemporaryChosenContainer tmp = new TemporaryChosenContainer();

    public void fillItemView(){
        getButtonInv();
        for(int i=0; i<inventoryHeight; i++){
            for(int j=0; j<inventoryWidth; j++){
                int k = ThreadLocalRandom.current().nextInt(0, 16);
                itemView[i][j] = new Items(k);
            }
        }
        updateButtons();
    }
    void updateButtons(){
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                int k = itemView[i][j].id;
                buttonInv[i][j].setText("" + k);
                buttonInv[i][j].setStyle("-fx-border-color:black");
            }
        }
        if(tmp.hasItems){
            chosenButton.setText("" + tmp.itemId);
        }
    }
    
    private void checkButtonsProperties(int x, int y){
        if(!tmp.hasItems){
            if(itemView[x][y].id != 0){
                tmp.cordX = x;
                tmp.cordY = y;
                tmp.itemId = itemView[x][y].id;
                tmp.hasItems = true;
            }
            updateButtons();
            return;
        }
        tmp.hasItems = false;
        if (itemView[x][y].id != 0) {
            itemView[tmp.cordX][tmp.cordY].id = itemView[x][y].id;
        }
        else{
            itemView[tmp.cordX][tmp.cordY].id = 0;
        }
        itemView[x][y].id = tmp.itemId;
        updateButtons();
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

    private static class TemporaryChosenContainer{
        Integer cordX;
        Integer cordY;
        Integer itemId;
        boolean hasItems;
        TemporaryChosenContainer(){
            hasItems = false;
        }
    }
    public void pickItemAA(){
        checkButtonsProperties(0, 0);
    }
    public void pickItemAB(){
        checkButtonsProperties(0, 1);
    }
    public void pickItemAC(){
        checkButtonsProperties(0, 2);
    }
    public void pickItemAD(){
        checkButtonsProperties(0, 3);
    }
    public void pickItemBA(){
        checkButtonsProperties(1, 0);
    }
    public void pickItemBB(){
        checkButtonsProperties(1, 1);
    }
    public void pickItemBC(){
        checkButtonsProperties(1, 2);
    }
    public void pickItemBD(){
        checkButtonsProperties(1, 3);
    }
    public void pickItemCA(){
        checkButtonsProperties(2, 0);
    }
    public void pickItemCB(){
        checkButtonsProperties(2, 1);
    }
    public void pickItemCC(){
        checkButtonsProperties(2, 2);
    }
    public void pickItemCD(){
        checkButtonsProperties(2, 3);
    }
    public void pickItemDA(){
        checkButtonsProperties(3, 0);
    }
    public void pickItemDB(){
        checkButtonsProperties(3, 1);
    }
    public void pickItemDC(){
        checkButtonsProperties(3, 2);
    }
    public void pickItemDD(){
        checkButtonsProperties(3, 3);
    }
}