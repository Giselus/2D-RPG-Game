package sample.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
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
    @FXML public AnchorPane anchorPane;


    private final ArrayList<ArrayList<Button>> buttonInventory = new ArrayList<>(inventoryWidth);
    private final ArrayList<ArrayList<Items>> itemsView = new ArrayList<>(inventoryWidth);
    @FXML private final ArrayList<Button> buttonEquipment = new ArrayList<>(sizeOfCloths);
    @FXML private final ArrayList<Items> equipmentView = new ArrayList<>(sizeOfCloths);

    private static final TemporaryChosenContainer temporaryChosen = new TemporaryChosenContainer();

    public void addImage(Image image){
        ImageView imageViewSkin=new ImageView(image);
        imageViewSkin.setViewport(new Rectangle2D(64,64*10,64,64));
        imageViewSkin.setX(608);
        imageViewSkin.setY(294);
        anchorPane.getChildren().add(imageViewSkin);
    }
    @FXML public void initialize(){
        if(CharacterManager.instance == null){
            new CharacterManager();
        }
        addImage(CharacterManager.instance.skin);
        addImage(CharacterManager.instance.legs);
        addImage(CharacterManager.instance.body);
        addImage(CharacterManager.instance.hair);
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
                itemsView.get(i).set(j, tmp.get(inventoryWidth*i + j));
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
        setGraphicButton(chosenButton, temporaryChosen.item);
    }

    private void setGraphicButton(Button tmpButton, Items tmpItem){
        String string = tmpItem.getPath();
        Image img = new Image((Objects.requireNonNull(getClass().getResource(string))).toString());
        ImageView view = new ImageView(img);
        tmpButton.setGraphic(view);
    }
    @FXML
    private void isBoot(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(0);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.BOOTS)){
            swapToWear(0);
        } else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isArmor(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(1);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.ARMOR)){
            swapToWear(1);
        } else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isHelmet(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(2);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.HELMET)){
            swapToWear(2);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isWeaponOne(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(3);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.WEAPON_ONE)){
            swapToWear(3);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isWeaponTwo(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(4);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.WEAPON_TWO)){
            swapToWear(4);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isTrinketOne(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(5);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.TRINKET)){
            swapToWear(5);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML
    private void isTrinketTwo(){
        if(!temporaryChosen.hasItems){
            pickItemFromEquipment(6);
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.TRINKET)){
            swapToWear(6);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    private void swapToWear(int x){
        if(equipmentView.get(x).myType == Items.type.EMPTY){
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
            CharacterManager.instance.inventory.equipItem();
            equipmentView.set(x, temporaryChosen.item);
        } else {
            Items swap = equipmentView.get(x);
            equipmentView.set(x, temporaryChosen.item);
            itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, swap);
        }
        temporaryChosen.clearHolder();
    }
    private void pickItemFromEquipment(int x){
        if(equipmentView.get(x).myType == Items.type.EMPTY){
            return;
        }
        temporaryChosen.pickWearing(x, equipmentView);
        updateButtons();
    }
    private void quickUseOfItem(int x, int y){
        if(temporaryChosen.hasItems){
            temporaryChosen.clearHolder();
            updateButtons();
            return;
        }
        Items tempItem = itemsView.get(x).get(y);
        int tmpValue = -1;
        if(tempItem.myType == Items.type.EMPTY){
            return;
        }
        if(tempItem.myType == Items.type.BOOTS){
            tmpValue = 0;
        } else if(tempItem.myType == Items.type.ARMOR){
            tmpValue = 1;
        } else if(tempItem.myType == Items.type.HELMET){
            tmpValue = 2;
        } else if(tempItem.myType == Items.type.WEAPON_ONE){
            tmpValue = 3;
        } else if(tempItem.myType == Items.type.WEAPON_TWO){
            tmpValue = 4;
        } else if (tempItem.myType == Items.type.TRINKET){
            if(equipmentView.get(5).myType == Items.type.EMPTY){
                tmpValue = 5;
            } else {
                tmpValue = 6;
            }
        } else {
            return;
        }
        if(equipmentView.get(tmpValue).myType == Items.type.EMPTY){
            itemsView.get(x).set(y, new Items(0, 0));
        } else {
            itemsView.get(x).set(y, equipmentView.get(tmpValue));
        }
        equipmentView.set(tmpValue, tempItem);
        updateButtons();
    }
    private void checkButtonsProperties(int x, int y){
        if(!temporaryChosen.hasItems){
            if(itemsView.get(x).get(y).myType != Items.type.EMPTY){
                temporaryChosen.pickEquipment(x, y, itemsView);
            }
            updateButtons();
            return;
        }
        if(temporaryChosen.isWearing){
            if(itemsView.get(x).get(y).myType == temporaryChosen.item.myType){
                equipmentView.set(temporaryChosen.equipmentId, itemsView.get(x).get(y));
                itemsView.get(x).set(y, temporaryChosen.item);
            } else if(itemsView.get(x).get(y).myType == Items.type.EMPTY){
                equipmentView.set(temporaryChosen.equipmentId, new Items(0, 0));
                itemsView.get(x).set(y, temporaryChosen.item);
            }
        } else {
            if (itemsView.get(x).get(y).myType != Items.type.EMPTY) {
                itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, itemsView.get(x).get(y));
            } else {
                itemsView.get(temporaryChosen.cordX).set(temporaryChosen.cordY, new Items(0, 0));
            }
            itemsView.get(x).set(y, temporaryChosen.item);
        }
        temporaryChosen.clearHolder();
        updateButtons();
    }
    private static class TemporaryChosenContainer{
        Integer cordX;
        Integer cordY;
        boolean hasItems;
        boolean isWearing;
        Integer equipmentId;
        Items item;
        TemporaryChosenContainer(){
            hasItems = false;
            isWearing = false;
            item = new Items(0, 0);
        }
        void pickWearing(int x, ArrayList<Items> arg){
            hasItems = true;
            isWearing = true;
            equipmentId = x;
            item = arg.get(x);
        }
        void pickEquipment(int x, int y, ArrayList<ArrayList<Items>> arg){
            hasItems = true;
            isWearing = false;
            cordX = x;
            cordY = y;
            item = arg.get(x).get(y);
        }
        void clearHolder(){
            hasItems = false;
            isWearing = false;
            item = new Items(0, 0);
        }
    }
    public void getButtonInv(){
        buttonInventory.get(0).set(0, aa);
        aa.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(0, 0);
            }
        });
        buttonInventory.get(0).set(1, ab);
        ab.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(0, 1);
            }
        });
        buttonInventory.get(0).set(2, ac);
        ac.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(0, 2);
            }
        });
        buttonInventory.get(0).set(3, ad);
        ad.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(0, 3);
            }
        });
        buttonInventory.get(1).set(0, ba);
        ba.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(1, 0);
            }
        });
        buttonInventory.get(1).set(1, bb);
        bb.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(1, 1);
            }
        });
        buttonInventory.get(1).set(2, bc);
        bc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(1, 2);
            }
        });
        buttonInventory.get(1).set(3, bd);
        bd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(1, 3);
            }
        });
        buttonInventory.get(2).set(0, ca);
        ca.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(2, 0);
            }
        });
        buttonInventory.get(2).set(1, cb);
        cb.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(2, 1);
            }
        });
        buttonInventory.get(2).set(2, cc);
        cc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(2, 2);
            }
        });
        buttonInventory.get(2).set(3, cd);
        cd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(2, 3);
            }
        });
        buttonInventory.get(3).set(0, da);
        da.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(3, 0);
            }
        });
        buttonInventory.get(3).set(1, db);
        db.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(3, 1);
            }
        });
        buttonInventory.get(3).set(2, dc);
        dc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(3, 2);
            }
        });
        buttonInventory.get(3).set(3, dd);
        dd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                quickUseOfItem(3, 3);
            }
        });

        buttonEquipment.set(0, bootsButton);
        buttonEquipment.set(1, armorButton);
        buttonEquipment.set(2, helmetButton);
        buttonEquipment.set(3, weapon1Button);
        buttonEquipment.set(4, weapon2Button);
        buttonEquipment.set(5, trinket1Button);
        buttonEquipment.set(6, trinket2Button);
    }

    public void switchToSceneMenu(ActionEvent event) throws IOException {
        beforeExiting();
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