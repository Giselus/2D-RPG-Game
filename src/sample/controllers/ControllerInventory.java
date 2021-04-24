package sample.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private final int sizeOfCloths = 7;
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
    @FXML public Button bootsButton;
    @FXML public Button armorButton;
    @FXML public Button helmetButton;
    @FXML public Button weapon1Button;
    @FXML public Button weapon2Button;
    @FXML public Button trinket1Button;
    @FXML public Button trinket2Button;

    private final Button[][] buttonInv = new Button[inventoryWidth][inventoryHeight];
    private final Items[][] itemView = new Items[inventoryWidth][inventoryHeight];
    @FXML private final Button[] buttonWear = new Button[sizeOfCloths];
    @FXML private final Items[] equipment = new Items[sizeOfCloths];

    private static final TemporaryChosenContainer tmp = new TemporaryChosenContainer();

    public void fillItemView(){
        getButtonInv();
        for(int i=0; i<inventoryHeight; i++){
            for(int j=0; j<inventoryWidth; j++){
                int k = ThreadLocalRandom.current().nextInt(0, 9);
                itemView[i][j] = new Items(k);
            }
        }
        for(int i=0; i<sizeOfCloths; i++){
            equipment[i] = new Items(0);
        }
        updateButtons();
    }

    void updateButtons(){
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                int k = itemView[i][j].id;
                String string = "/resources/textures/Items/ItemId" + k + ".png";
                Image img = new Image((Objects.requireNonNull(getClass().getResource(string))).toString());
                ImageView view = new ImageView(img);
                buttonInv[i][j].setGraphic(view);
            }
        }
        for(int i=0; i<sizeOfCloths; i++){
            int k = equipment[i].id;
            String string = "/resources/textures/Items/ItemId" + k + ".png";
            Image img = new Image((Objects.requireNonNull(getClass().getResource(string))).toString());
            ImageView view = new ImageView(img);
            buttonWear[i].setGraphic(view);
        }
        int k;
        if(tmp.hasItems){
            k = tmp.itemId;
        } else{
            k=0;
        }
        String string = "/resources/textures/Items/ItemId" + k + ".png";
        Image img = new Image((Objects.requireNonNull(getClass().getResource(string))).toString());
        ImageView view = new ImageView(img);
        chosenButton.setGraphic(view);
    }
    @FXML
    private void isBoot(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.BOOTS)){
            swapToWear(0);
        } else{
            tmp.hasItems = false;
        }
        updateButtons();
    }

    @FXML
    private void isArmor(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.ARMOR)){
            swapToWear(1);
        } else{
            tmp.hasItems = false;
        }
        updateButtons();
    }

    @FXML
    private void isHelmet(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.HELMET)){
            swapToWear(2);
        }else{
            tmp.hasItems = false;
        }
        updateButtons();
    }
    @FXML
    private void isWeaponOne(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.WEAPON_ONE)){
            swapToWear(3);
        }else{
            tmp.hasItems = false;
        }
        updateButtons();
    }
    @FXML
    private void isWeaponTwo(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.WEAPON_TWO)){
            swapToWear(4);
        }else{
            tmp.hasItems = false;
        }
        updateButtons();
    }
    @FXML
    private void isTrinketOne(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.TRINKET)){
            swapToWear(5);
        }else{
            tmp.hasItems = false;
        }
        updateButtons();
    }
    @FXML
    private void isTrinketTwo(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.tmpType.equals(Items.type.TRINKET)){
            swapToWear(6);
        }
        tmp.hasItems = false;
        updateButtons();
    }
    private void swapToWear(int x){
        tmp.hasItems = false;
        if(equipment[x].id == 0){
            itemView[tmp.cordX][tmp.cordY].id = 0;
            equipment[x].id = tmp.itemId;
        } else {
            int swap = equipment[x].id;
            equipment[x].id = tmp.itemId;
            itemView[tmp.cordX][tmp.cordY].id = swap;
        }
    }
    private void checkButtonsProperties(int x, int y){
        if(!tmp.hasItems){
            if(itemView[x][y].id != 0){
                tmp.cordX = x;
                tmp.cordY = y;
                tmp.itemId = itemView[x][y].id;
                tmp.tmpType = itemView[x][y].myType;
                System.out.println(tmp.tmpType);
                tmp.hasItems = true;
            }
            updateButtons();
            return;
        }
        tmp.hasItems = false;
        if (itemView[x][y].id != 0) {
            itemView[tmp.cordX][tmp.cordY].id = itemView[x][y].id;
            itemView[tmp.cordX][tmp.cordY].myType = itemView[x][y].myType;
        }
        else{
            itemView[tmp.cordX][tmp.cordY].id = 0;
            itemView[tmp.cordX][tmp.cordY].myType = Items.type.EMPTY;
        }
        itemView[x][y].id = tmp.itemId;
        itemView[x][y].myType = tmp.tmpType;
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

        buttonWear[0] = bootsButton;
        buttonWear[1] = armorButton;
        buttonWear[2] = helmetButton;
        buttonWear[3] = weapon1Button;
        buttonWear[4] = weapon2Button;
        buttonWear[5] = trinket1Button;
        buttonWear[6] = trinket2Button;
    }
    public void switchToSceneMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private static class TemporaryChosenContainer{
        Integer cordX;
        Integer cordY;
        Integer itemId;
        boolean hasItems;
        Items.type tmpType;
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