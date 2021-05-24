package sample.controllers;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import sample.CharacterManager;
import sample.Inventory;
import sample.InventoryPlayer;
import sample.Items;
import sample.Main;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerInventory {

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
    //skilsLabel:
     @FXML public Text attackLabel;
     @FXML public Text defenseLabel;
     @FXML public Text agilityLabel;
     @FXML public Text staminaLabel;
     @FXML public Text luckLabel;
     @FXML public Text manaLabel;
     @FXML public Text nameLabel;
     @FXML public Text goldLabel;
     //endskillsLabel
    //Image
    Image helmetImg;
    Image armorImg;
    Image bootsImg;
    //endImage
    //ImageView
    ImageView helmetImageView;
    ImageView armorImageView;
    ImageView bootsImageView;
    //endImageView
    private final ArrayList<ArrayList<Button>> buttonInventory = new ArrayList<>(inventoryWidth);
    private final ArrayList<ArrayList<Items>> itemsView = new ArrayList<>(inventoryWidth);
    @FXML private final ArrayList<Button> buttonEquipment = new ArrayList<>(sizeOfCloths);
    @FXML private final ArrayList<Items> equipmentView = new ArrayList<>(sizeOfCloths);

    private static final Inventory.TemporaryChosenContainer temporaryChosen = new Inventory.TemporaryChosenContainer();

    //this function is not essential for inventory, it can be changed
    public void addSkils(){
        attackLabel.setText(String.valueOf(CharacterManager.instance.attack));
        defenseLabel.setText(String.valueOf(CharacterManager.instance.defense));
        agilityLabel.setText(String.valueOf(CharacterManager.instance.agility));
        luckLabel.setText(String.valueOf(CharacterManager.instance.luck));
        manaLabel.setText(String.valueOf(CharacterManager.instance.mana));
        staminaLabel.setText(String.valueOf(CharacterManager.instance.stamina));
        //nameLabel.setText(String.valueOf(CharacterManager.instance.name));

    }
    public void addImage(Image image){
        ImageView imageViewSkin=new ImageView(image);
        imageViewSkin.setViewport(new Rectangle2D(64,64*10,64,64));
        imageViewSkin.setX(608);
        imageViewSkin.setY(294);
        anchorPane.getChildren().add(imageViewSkin);
    }
    public void addImageHelemet(Image image){
        helmetImageView=new ImageView(image);
        helmetImageView.setViewport(new Rectangle2D(64,64*10,64,64));
        helmetImageView.setX(608);
        helmetImageView.setY(294);
        anchorPane.getChildren().add(helmetImageView);
    }
    public void addImageArmor(Image image){
        armorImageView=new ImageView(image);
        armorImageView.setViewport(new Rectangle2D(64,64*10,64,64));
        armorImageView.setX(608);
        armorImageView.setY(294);
        anchorPane.getChildren().add(armorImageView);
    }
    public void addImageBoots(Image image){
        bootsImageView=new ImageView(image);
        bootsImageView.setViewport(new Rectangle2D(64,64*10,64,64));
        bootsImageView.setX(608);
        bootsImageView.setY(294);
        anchorPane.getChildren().add(bootsImageView);
    }
    public void deleteItems(){
        anchorPane.getChildren().remove(helmetImageView);
        anchorPane.getChildren().remove(armorImageView);
        anchorPane.getChildren().remove(bootsImageView);
//        if(!CharacterManager.instance.hasHelmet){
//            helmetImageView.imageProperty().set(null);
//            //idAnchorPane.getChildren().remove(imageViewSkin);
//        }
    }
    @FXML public void initialize(){
        if(CharacterManager.instance == null){
            new CharacterManager();
        }
        helmetImageView=new ImageView();
        helmetImageView=new ImageView();
        armorImageView=new ImageView();
        bootsImageView=new ImageView();
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
        initializeButtons();
        updateButtons();
        addSkils();
    }
    @FXML private void isBoot(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(0, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.BOOTS)){
            InventoryPlayer.swapToWear(0, equipmentView, itemsView, temporaryChosen);
        } else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isArmor(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(1, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.ARMOR)){
            InventoryPlayer.swapToWear(1, equipmentView, itemsView, temporaryChosen);
        } else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isHelmet(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(2, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.HELMET)){
            InventoryPlayer.swapToWear(2, equipmentView, itemsView, temporaryChosen);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isWeaponOne(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(3, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.WEAPON_ONE)){
            InventoryPlayer.swapToWear(3, equipmentView, itemsView, temporaryChosen);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isWeaponTwo(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(4, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.WEAPON_TWO)){
            InventoryPlayer.swapToWear(4, equipmentView, itemsView, temporaryChosen);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isTrinketOne(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(5, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.TRINKET)){
            InventoryPlayer.swapToWear(5, equipmentView, itemsView, temporaryChosen);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    @FXML private void isTrinketTwo(){
        if(!temporaryChosen.hasItems){
            InventoryPlayer.pickItemFromEquipment(6, equipmentView, temporaryChosen);
            updateButtons();
            return;
        }
        if(temporaryChosen.item.myType.equals(Items.type.TRINKET)){
            InventoryPlayer.swapToWear(6, equipmentView, itemsView, temporaryChosen);
        }else{
            temporaryChosen.clearHolder();
        }
        updateButtons();
    }
    public void beforeExiting(){
        CharacterManager.instance.inventory.setAllEquippedItems(equipmentView);
        CharacterManager.instance.inventory.setAllItemsList(itemsView);
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
        checkWhatIsWearing();
    }
    private void wearItems(){
        deleteItems();
        helmetImg=new Image(String.valueOf(getClass().getResource(CharacterManager.instance.helmetOn.getPath())));
        armorImg=new Image(String.valueOf(getClass().getResource(CharacterManager.instance.armorOn.getPath())));
        bootsImg=new Image(String.valueOf(getClass().getResource(CharacterManager.instance.bootsOn.getPath())));
        addImageHelemet(helmetImg);
        addImageArmor(armorImg);
        addImageBoots(bootsImg);
        System.out.println(CharacterManager.instance.helmetOn.getPath());
    }
    private void checkWhatIsWearing(){
        CharacterManager.instance.hasBoots = equipmentView.get(0).myType != Items.type.EMPTY;
        CharacterManager.instance.bootsOn = equipmentView.get(0);
        CharacterManager.instance.hasArmor = equipmentView.get(1).myType != Items.type.EMPTY;
        CharacterManager.instance.armorOn = equipmentView.get(1);
        CharacterManager.instance.hasHelmet = equipmentView.get(2).myType != Items.type.EMPTY;
        CharacterManager.instance.helmetOn = equipmentView.get(2);
        wearItems();
    }
    private void setGraphicButton(Button tmpButton, Items tmpItem){
        tmpButton.setGraphic(tmpItem.getImageView());
    }
    private void initializeButtons(){
        buttonInventory.get(0).set(0, aa);
        aa.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(0, 0, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(0).set(1, ab);
        ab.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(0, 1, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(0).set(2, ac);
        ac.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(0, 2, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(0).set(3, ad);
        ad.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemAD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(0, 3, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(1).set(0, ba);
        ba.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(1, 0, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(1).set(1, bb);
        bb.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(1, 1, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(1).set(2, bc);
        bc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(1, 2, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(1).set(3, bd);
        bd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemBD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(1, 3, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(2).set(0, ca);
        ca.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(2, 0, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(2).set(1, cb);
        cb.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(2, 1, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(2).set(2, cc);
        cc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(2, 2, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(2).set(3, cd);
        cd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemCD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(2, 3, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(3).set(0, da);
        da.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDA();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(3, 0, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(3).set(1, db);
        db.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDB();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(3, 1, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(3).set(2, dc);
        dc.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDC();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(3, 2, equipmentView, itemsView, temporaryChosen);
                updateButtons();
            }
        });
        buttonInventory.get(3).set(3, dd);
        dd.setOnMousePressed(e->{
            if(e.getButton()== MouseButton.PRIMARY){
                pickItemDD();
            }
            if(e.getButton() == MouseButton.SECONDARY){
                InventoryPlayer.quickUseOfItem(3, 3, equipmentView, itemsView, temporaryChosen);
                updateButtons();
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
        Main.setScene("/resources/fxml/mainGameScene.fxml");
    }

    public void pickItemAA(){
        InventoryPlayer.checkSlotProperties(0, 0, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemAB(){
        InventoryPlayer.checkSlotProperties(0, 1, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemAC(){
        InventoryPlayer.checkSlotProperties(0, 2, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemAD(){
        InventoryPlayer.checkSlotProperties(0, 3, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemBA(){
        InventoryPlayer.checkSlotProperties(1, 0, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemBB(){
        InventoryPlayer.checkSlotProperties(1, 1, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemBC(){
        InventoryPlayer.checkSlotProperties(1, 2, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemBD(){
        InventoryPlayer.checkSlotProperties(1, 3, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemCA(){
        InventoryPlayer.checkSlotProperties(2, 0, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemCB(){
        InventoryPlayer.checkSlotProperties(2, 1, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemCC(){
        InventoryPlayer.checkSlotProperties(2, 2, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemCD(){
        InventoryPlayer.checkSlotProperties(2, 3, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemDA(){
        InventoryPlayer.checkSlotProperties(3, 0, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemDB(){
        InventoryPlayer.checkSlotProperties(3, 1, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemDC(){
        InventoryPlayer.checkSlotProperties(3, 2, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
    public void pickItemDD(){
        InventoryPlayer.checkSlotProperties(3, 3, itemsView, temporaryChosen, equipmentView);
        updateButtons();
    }
}