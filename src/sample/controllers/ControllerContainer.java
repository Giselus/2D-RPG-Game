package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.*;
import java.util.ArrayList;

public class ControllerContainer {

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
    @FXML public Button AA;
    @FXML public Button AB;
    @FXML public Button AC;
    @FXML public Button AD;
    @FXML public Button BA;
    @FXML public Button BB;
    @FXML public Button BC;
    @FXML public Button BD;
    @FXML public Button CA;
    @FXML public Button CB;
    @FXML public Button CD;
    @FXML public Button CC;
    @FXML public Button DA;
    @FXML public Button DB;
    @FXML public Button DC;
    @FXML public Button DD;
    @FXML public Button exitButton;
    @FXML public AnchorPane anchorPane;
    public Text amountOfGold;
    @FXML public Button buyButton;
    @FXML public Button sellButton;
    @FXML public Button tmpChosen;
    @FXML public Text priceText;
    int inventoryWidth = 8;
    int inventoryHeight = 4;
    int player_items;
    int shop_items;
    boolean first;
    boolean isShop;
    InventoryPlayer playerInventory;
    ArrayList<ArrayList<Items>> allItems;
    ArrayList<ArrayList<Button>> buttonInventory;
    Items[][] copyShop;
    Inventory.TemporaryChosenContainer temporaryChosen = new Inventory.TemporaryChosenContainer();

    public static ContainerForNpc swapChest = null;
    public void initialize(){
        first = true;
        isShop = swapChest.isShop;
        if(isShop){
            setGoldText();
            player_items = CharacterManager.instance.inventory.countItems();
            shop_items = swapChest.inventory.countItems();
            copyShop = new Items[4][4];
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    copyShop[i][j] = swapChest.inventory.getItem(i, j);
                }
            }
        } else {
            anchorPane.getChildren().remove(buyButton);
            anchorPane.getChildren().remove(sellButton);
            anchorPane.getChildren().remove(tmpChosen);
            anchorPane.getChildren().remove(priceText);
        }
        playerInventory = CharacterManager.instance.inventory;
        allItems = new ArrayList<>(8);
        buttonInventory = new ArrayList<>(8);
        for(int i=0; i<8; i++){
            ArrayList<Items> tmp = new ArrayList<>();
            ArrayList<Button> tmpB = new ArrayList<>();
            for(int j=0; j<4; j++){
                tmp.add(new Items(0,0));
                tmpB.add(new Button());
            }
            allItems.add(tmp);
            buttonInventory.add(tmpB);
        }
        ArrayList<Items> tmpItems = playerInventory.getAllItems();
        ArrayList<Items> tmpChest = swapChest.inventory.getAllItems();
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                allItems.get(i).set(j, tmpItems.get(4*i+j));
                allItems.get(i+4).set(j, tmpChest.get(4*i+j));
            }
        }
        setupButtons();
        updateButtons();
    }

    @FXML private void buyItem(){
        if(player_items >= 16){
            return;
        }
        if(!temporaryChosen.fromShop){
            return;
        }
        if(temporaryChosen.item.myStatistics.gold > CharacterManager.instance.gold){
            return;
        }
        CharacterManager.instance.gold -= temporaryChosen.item.myStatistics.gold;
        player_items++;
        shop_items--;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++) {
                if(allItems.get(i).get(j).myType == Items.type.EMPTY){
                    allItems.get(i).set(j, temporaryChosen.item);
                    allItems.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
                    temporaryChosen.clearHolder();
                    updateButtons();
                    System.out.println("Kupione");
                    return;
                }
            }
        }
    }
    @FXML private void sellItem(){
        if(shop_items >= 16){
            return;
        }
        if(temporaryChosen.fromShop){
            return;
        }
        CharacterManager.instance.gold += temporaryChosen.item.myStatistics.gold;
        player_items--;
        shop_items++;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++) {
                if(allItems.get(i+4).get(j).myType == Items.type.EMPTY){
                    allItems.get(i+4).set(j, temporaryChosen.item);
                    allItems.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
                    temporaryChosen.clearHolder();
                    updateButtons();
                    System.out.println("Sprzedane");
                    return;
                }
            }
        }
    }
    private void setGoldText(){
        if(first){
            amountOfGold = new Text();
        }
        amountOfGold.setText("Gold: " + CharacterManager.instance.gold);
        amountOfGold.setFont(Font.font("Times New Roman", 30));
        double text_width = amountOfGold.getLayoutBounds().getWidth();
        amountOfGold.setX((1280 - text_width)/2);
        amountOfGold.setY(80);
        if(first){
            anchorPane.getChildren().add(amountOfGold);
            first = false;
        }
        if(temporaryChosen.hasItems){
            priceText.setText("Price: " + temporaryChosen.item.myStatistics.gold);
        } else {
            priceText.setText("");
        }
        priceText.setFont(Font.font("Times New Roman", 20));
    }
    private void setGraphicButton(Button tmpButton, Items tmpItem){
        tmpButton.setGraphic(tmpItem.getImageView());
    }
    void updateButtons(){
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                setGraphicButton(buttonInventory.get(i).get(j), allItems.get(i).get(j));
            }
        }
        if(isShop){
            setGraphicButton(tmpChosen, temporaryChosen.item);
            setGoldText();
        }
    }
    public void beforeExiting(){
        ArrayList<ArrayList<Items>> tmpA = new ArrayList<>(4);
        ArrayList<ArrayList<Items>> tmpB = new ArrayList<>(4);
        for(int i=0; i<4; i++){
            ArrayList<Items> A_tmp = new ArrayList<>();
            ArrayList<Items> B_tmp = new ArrayList<>();
            for(int j=0; j<4; j++){
                A_tmp.add(new Items(0, 0));
                B_tmp.add(new Items(0, 0));
            }
            tmpA.add(A_tmp);
            tmpB.add(B_tmp);
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                tmpA.get(i).set(j, allItems.get(i).get(j));
                tmpB.get(i).set(j, allItems.get(i+4).get(j));
            }
        }
        if(isShop){
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    tmpB.get(i).set(j, copyShop[i][j]);
                }
            }
        }
        CharacterManager.instance.inventory.setAllItemsList(tmpA);
        swapChest.inventory.setAllItemsList(tmpB);
    }
    public void switchToSceneMenu(){
        beforeExiting();
        Main.setScene("/resources/fxml/mainGameScene.fxml","");
    }
    private void setupButtons(){
        buttonInventory.get(0).set(0, aa);
        buttonInventory.get(4).set(0, AA);
        buttonInventory.get(0).set(1, ab);
        buttonInventory.get(4).set(1, AB);
        buttonInventory.get(0).set(2, ac);
        buttonInventory.get(4).set(2, AC);
        buttonInventory.get(0).set(3, ad);
        buttonInventory.get(4).set(3, AD);

        buttonInventory.get(1).set(0, ba);
        buttonInventory.get(5).set(0, BA);
        buttonInventory.get(1).set(1, bb);
        buttonInventory.get(5).set(1, BB);
        buttonInventory.get(1).set(2, bc);
        buttonInventory.get(5).set(2, BC);
        buttonInventory.get(1).set(3, bd);
        buttonInventory.get(5).set(3, BD);

        buttonInventory.get(2).set(0, ca);
        buttonInventory.get(6).set(0, CA);
        buttonInventory.get(2).set(1, cb);
        buttonInventory.get(6).set(1, CB);
        buttonInventory.get(2).set(2, cc);
        buttonInventory.get(6).set(2, CC);
        buttonInventory.get(2).set(3, cd);
        buttonInventory.get(6).set(3, CD);

        buttonInventory.get(3).set(0, da);
        buttonInventory.get(7).set(0, DA);
        buttonInventory.get(3).set(1, db);
        buttonInventory.get(7).set(1, DB);
        buttonInventory.get(3).set(2, dc);
        buttonInventory.get(7).set(2, DC);
        buttonInventory.get(3).set(3, dd);
        buttonInventory.get(7).set(3, DD);

    }
    public void pickItemAA(){
        Inventory.checkSlotProperties(0, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_AA(){
        Inventory.checkSlotProperties(4, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemAB(){
        Inventory.checkSlotProperties(0, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_AB(){
        Inventory.checkSlotProperties(4, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemAC(){
        Inventory.checkSlotProperties(0, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_AC(){
        Inventory.checkSlotProperties(4, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemAD(){
        Inventory.checkSlotProperties(0, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_AD(){
        Inventory.checkSlotProperties(4, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemBA(){
        Inventory.checkSlotProperties(1, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_BA(){
        Inventory.checkSlotProperties(5, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemBB(){
        Inventory.checkSlotProperties(1, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_BB(){
        Inventory.checkSlotProperties(5, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemBC(){
        Inventory.checkSlotProperties(1, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_BC(){
        Inventory.checkSlotProperties(5, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemBD(){
        Inventory.checkSlotProperties(1, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_BD(){
        Inventory.checkSlotProperties(5, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemCA(){
        Inventory.checkSlotProperties(2, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_CA(){
        Inventory.checkSlotProperties(6, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemCB(){
        Inventory.checkSlotProperties(2, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_CB(){
        Inventory.checkSlotProperties(6, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemCC(){
        Inventory.checkSlotProperties(2, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_CC(){
        Inventory.checkSlotProperties(6, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemCD(){
        Inventory.checkSlotProperties(2, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_CD(){
        Inventory.checkSlotProperties(6, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemDA(){
        Inventory.checkSlotProperties(3, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_DA(){
        Inventory.checkSlotProperties(7, 0, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemDB(){
        Inventory.checkSlotProperties(3, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_DB(){
        Inventory.checkSlotProperties(7, 1, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemDC(){
        Inventory.checkSlotProperties(3, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_DC(){
        Inventory.checkSlotProperties(7, 2, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItemDD(){
        Inventory.checkSlotProperties(3, 3, allItems, temporaryChosen);
        updateButtons();
    }
    public void pickItem_DD(){
        Inventory.checkSlotProperties(7, 3, allItems, temporaryChosen);
        updateButtons();
    }
}
