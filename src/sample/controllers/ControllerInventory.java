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
import sample.CharacterManager;
import sample.Items;
import java.io.IOException;
import java.util.ArrayList;
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

    private final ArrayList<ArrayList<Button>> buttonInventory = new ArrayList<>(inventoryWidth);
    private final ArrayList<ArrayList<Items>> itemsView = new ArrayList<>(inventoryWidth);
    @FXML private final ArrayList<Button> buttonEquipment = new ArrayList<>(sizeOfCloths);
    @FXML private final ArrayList<Items> equipmentView = new ArrayList<>(sizeOfCloths);

    private static final TemporaryChosenContainer tmp = new TemporaryChosenContainer();

    @FXML public void initialize(){
        if(CharacterManager.instance == null){
            new CharacterManager();
        }
        for(int i=0; i<inventoryWidth; i++){
            ArrayList<Button> buttonToAdd = new ArrayList<>();
            for(int j=0; j<inventoryHeight; j++){
                buttonToAdd.add(new Button());
            }
            buttonInventory.add(buttonToAdd);
            ArrayList<Items> itemsToAdd = new ArrayList<>();
            for(int j=0; j<4; j++){
                itemsToAdd.add(new Items(0, 0));
            }
            itemsView.add(itemsToAdd);
        }
        for(int i=0; i< sizeOfCloths; i++){
            buttonEquipment.add(new Button());
            equipmentView.add(new Items(0, 0));
        }
        ArrayList<Items> tmp = CharacterManager.instance.inventory.getAllItems();
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                itemsView.get(i).set(j, tmp.get(3*i + j));
            }
        }
        tmp = CharacterManager.instance.inventory.getEquippedItemsList();
        for(int i=0; i<7; i++){
            equipmentView.set(i, tmp.get(i));
        }
        getButtonInv();
        updateButtons();
    }

    public void beforeExiting(){
        CharacterManager.instance.inventory.setAllEquippedItems(equipmentView);
        CharacterManager.instance.inventory.setAllItemsList(itemsView);
    }
    public void fillItemView(){
        for(int i=0; i<inventoryHeight; i++){
            for(int j=0; j<inventoryWidth; j++){
                int k = ThreadLocalRandom.current().nextInt(0, 8);
                int l = ThreadLocalRandom.current().nextInt(0, 3);
                itemsView.get(i).set(j, new Items(k, l));
            }
        }
        for(int i=0; i<sizeOfCloths; i++){
            equipmentView.set(i, new Items(0, 0));
        }
    }
    void updateButtons(){
        for(int i=0; i<inventoryWidth; i++){
            for(int j=0; j<inventoryHeight; j++){
                setGraphicButton(buttonInventory.get(i).get(j), itemsView.get(i).get(j));
            }
        }
        for(int i=0; i<sizeOfCloths; i++){
            setGraphicButton(buttonEquipment.get(i), equipmentView.get(i));
        }
        setGraphicButton(chosenButton, tmp.item);
    }
    private void clearHolder(){
        tmp.item = new Items(0, 0);
        tmp.hasItems = false;
    }
    private void setGraphicButton(Button tmpButton, Items tmpItem){
        String string = "/resources/textures/Items/" + tmpItem.toString() + ".png";
        Image img = new Image((Objects.requireNonNull(getClass().getResource(string))).toString());
        ImageView view = new ImageView(img);
        tmpButton.setGraphic(view);
    }
    @FXML
    private void isBoot(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.BOOTS)){
            swapToWear(0);
        } else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isArmor(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.ARMOR)){
            swapToWear(1);
        } else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isHelmet(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.HELMET)){
            swapToWear(2);
        }else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isWeaponOne(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.WEAPON_ONE)){
            swapToWear(3);
        }else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isWeaponTwo(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.WEAPON_TWO)){
            swapToWear(4);
        }else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isTrinketOne(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.TRINKET)){
            swapToWear(5);
        }else{
            clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isTrinketTwo(){
        if(!tmp.hasItems){
            return;
        }
        if(tmp.item.myType.equals(Items.type.TRINKET)){
            swapToWear(6);
        }else{
            clearHolder();
        }
        updateButtons();
    }
    private void swapToWear(int x){
        if(equipmentView.get(x).myType == Items.type.EMPTY){
            itemsView.get(tmp.cordX).set(tmp.cordY, new Items(0, 0));
            CharacterManager.instance.inventory.equipItem();
            equipmentView.set(x, tmp.item);
        } else {
            Items swap = equipmentView.get(x);
            equipmentView.set(x, tmp.item);
            itemsView.get(tmp.cordX).set(tmp.cordY, swap);
        }
        clearHolder();
    }
    private void checkButtonsProperties(int x, int y){
        if(!tmp.hasItems){
            if(itemsView.get(x).get(y).myType != Items.type.EMPTY){
                tmp.cordX = x;
                tmp.cordY = y;
                tmp.item = itemsView.get(x).get(y);
                tmp.hasItems = true;
            }
            updateButtons();
            return;
        }
        if (itemsView.get(x).get(y).myType != Items.type.EMPTY) {
            itemsView.get(tmp.cordX).set(tmp.cordY, itemsView.get(x).get(y));
        }
        else{
            itemsView.get(tmp.cordX).set(tmp.cordY, new Items(0, 0));
        }
        itemsView.get(x).set(y, tmp.item);
        clearHolder();
        updateButtons();
    }
    private static class TemporaryChosenContainer{
        Integer cordX;
        Integer cordY;
        boolean hasItems;
        Items item;
        TemporaryChosenContainer(){
            hasItems = false;
            item = new Items(0, 0);
        }
    }
    public void getButtonInv(){
        buttonInventory.get(0).set(0, aa);
        buttonInventory.get(0).set(1, ab);
        buttonInventory.get(0).set(2, ac);
        buttonInventory.get(0).set(3, ad);
        buttonInventory.get(1).set(0, ba);
        buttonInventory.get(1).set(1, bb);
        buttonInventory.get(1).set(2, bc);
        buttonInventory.get(1).set(3, bd);
        buttonInventory.get(2).set(0, ca);
        buttonInventory.get(2).set(1, cb);
        buttonInventory.get(2).set(2, cc);
        buttonInventory.get(2).set(3, cd);
        buttonInventory.get(3).set(0, da);
        buttonInventory.get(3).set(1, db);
        buttonInventory.get(3).set(2, dc);
        buttonInventory.get(3).set(3, dd);

        buttonEquipment.set(0, bootsButton);
        buttonEquipment.set(1, armorButton);
        buttonEquipment.set(2, helmetButton);
        buttonEquipment.set(3, weapon1Button);
        buttonEquipment.set(4, weapon2Button);
        buttonEquipment.set(5, trinket1Button);
        buttonEquipment.set(6, trinket2Button);
    }

    public void switchToSceneMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/resources/fxml/sceneMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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