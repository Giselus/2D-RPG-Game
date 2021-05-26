package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class CharacterManager extends GameObject{

    public static CharacterManager instance;
    public String name;
    public int attack;
    public int defense;
    public int luck;
    public int agility;
    public int hp;
    public int current_hp;
    public int mana;
    public int current_mana;
    public int stamina;
    public int current_stamina;
    public ArrayList<Image> basicImages;
    //addValue
    public int attackItems;
    public int defenseItems;
    public int luckItems;
    public int agilityItems;
    public int manaItems;
    public int staminaItems;
    public int hpItems;
    public Image skin;
    public Image legs;
    public Image body;
    public Image hair;
    public Image helmet;
    public Image armor;
    public Image boots;
    public Items bootsOn;
    public Items armorOn;
    public Items helmetOn;
    public InventoryPlayer inventory;
    public ArrayList<Skills> skills;
    public boolean hasHelmet;
    public boolean hasArmor;
    public boolean hasBoots;

    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Direction lookingDirection;

    //test_line
    public ContainerForNpc interactiveChest;
    public CharacterManager(){
        instance = this;
    }

    public CharacterManager(String name,int attack,int defense,int luck,
            int agility,int mana,int stamina,int hp,Image skin,Image legs,Image body,Image hair,
                            int xPos, int yPos, int zPos){
        super(xPos * 32,yPos * 32,zPos,new ImageFrame(skin,0,640,64,64),
                new ImageFrame(legs,0,640,64,64),
                new ImageFrame(body,0,640,64,64),
                new ImageFrame(hair,0,640,64,64));
        if(skin == null){
            System.out.println("PROBLEM");
        }
        lastX = x = xPos;
        lastY = y = yPos;
        instance = this;
        this.name=name;
        this.attack=attack;
        this.defense=defense;
        this.luck=luck;
        this.agility=agility;
        this.hp=hp;
        this.mana=mana;
        this.stamina=stamina;
        this.skin=skin;
        this.legs=legs;
        this.body=body;
        this.hair=hair;
        this.current_hp=hp;
        inventory = new InventoryPlayer(4, 4);
        skills = new ArrayList<>(4);
        hasArmor = false;
        hasHelmet = false;
        hasBoots = false;

        basicImages = new ArrayList<>();
        basicImages.add(skin);
        basicImages.add(legs);
        basicImages.add(body);
        basicImages.add(hair);

        GenerateAnimations();

        //testing lines, this functions are essential for testing inventory and battle
        interactiveChest = new ContainerForNpc(0, 0, 4, 4);
        interactiveChest.inventory.addItem(new Items(1, 2));
        interactiveChest.inventory.addItem(new Items(3, 0));
        inventory.addItem(new Items(1,0));
        inventory.addItem(new Items(2,0));
        inventory.addItem(new Items(3,2));
        inventory.addItem(new Items(4,2));
        inventory.addItem(new Items(1,0));
        inventory.addItem(new Items(7,0));
        skills.add(new Skills(1, 0));
        skills.add(new Skills(2, 0));
        skills.add(new Skills(1, 1));
        skills.add(new Skills(1, 2));
        this.hp=150;
        current_hp=150;
        //this.stamina=100;
        current_stamina=this.stamina*10 + 100;
        //this.mana = 100;
        current_mana=this.mana*10 + 100;
        //end of testing lines
    }

    Animation left,right,down,up;

    public void GenerateAnimations(){
        float duration = 0.2f;
        left = new Animation(duration, -32, 0, CreateAnimation(9));
        left.imageDuration = duration * 2f;
        left.continuous = true;
        right = new Animation(duration, 32, 0, CreateAnimation(11));
        right.imageDuration = duration * 2f;
        right.continuous = true;
        down = new Animation(duration, 0, 32, CreateAnimation(10));
        down.imageDuration = duration * 2f;
        down.continuous = true;
        up = new Animation(duration, 0, -32, CreateAnimation(8));
        up.imageDuration = duration * 2f;
        up.continuous = true;
    }

    public void RefreshImages(){
        basicImages.clear();
        basicImages.add(skin);
        basicImages.add(legs);
        basicImages.add(body);
        basicImages.add(hair);
        if(helmet != null)
            basicImages.add(helmet);
        if(armor != null)
            basicImages.add(armor);
        if(boots != null)
            basicImages.add(boots);
        GenerateAnimations();
        DrawBasicPosition();
    }

    public void DrawBasicPosition(){
        images.clear();
        for(Image img: basicImages){
            if(lookingDirection == Direction.LEFT){
                images.add(new ImageFrame(img,0,9*64,64,64));
            }else if(lookingDirection == Direction.RIGHT){
                images.add(new ImageFrame(img,0,11*64,64,64));
            }else if(lookingDirection == Direction.UP){
                images.add(new ImageFrame(img,0,8*64,64,64));
            }else{
                images.add(new ImageFrame(img,0,10*64,64,64));
            }
        }
    }

    public ArrayList<ImageFrame>[] CreateAnimation(int row){
        ArrayList<ImageFrame>[] T = new ArrayList[basicImages.size()];
        int id = 0;
        for(Image img: basicImages){
            ArrayList<ImageFrame> temp = new ArrayList<>();
            for(int i = 0; i < 9;i++){
                temp.add(new ImageFrame(img,i*64, row*64,64,64));
            }
            T[id] = temp;
            id++;
        }
        return T;
    }
    private void ResetExcept(Animation animation){
        if(left != animation)
            left.Reset();
        if(right != animation)
            right.Reset();
        if(down != animation)
            down.Reset();
        if(up != animation)
            up.Reset();
    }
    int lastX, lastY;
    public void setCameraPosition(){
        float zoom = Camera.instance.zoom;
        Camera.instance.setPosition(xPos-Camera.instance.getWidth()/2/zoom,yPos-Camera.instance.getHeight()/2/zoom);
    }

    private boolean occupied(int x, int y, int z){
        if(mapHandler.getCurrentMap().getLayer(z).getCollisionAtPos(x,y))
            return true;
        if(EnemyManager.instance.getEnemy(x,y,z) != null)
            return true;
        return false;
    }

    @Override
    public void Update(float deltaTime){
        super.Update(deltaTime);
        setCameraPosition();
        if(animation == null || !animation.isRunning()) {
            Map map = mapHandler.getCurrentMap();
            //Handling events
            String eventCode = null;
            String tmp = null;
            if(lastX != x || lastY != y){
                if(map.getLayer(z).getEvent(x,y) != null) {
                    tmp = map.getLayer(z).getEvent(x, y);
                    if(map.events.get(tmp).getKey() == Map.EventType.STEP)
                        eventCode = tmp;
                }
                lastX = x;
                lastY = y;
            }else {
                if(KeyPolling.pressedDown(KeyCode.SPACE)){
                    if(map.getLayer(z).getEvent(x,y) != null) {
                        tmp = map.getLayer(z).getEvent(x, y);
                        if(map.events.get(tmp).getKey() == Map.EventType.PICK)
                            eventCode = tmp;
                    }
                    switch(lookingDirection){
                        case LEFT:
                            if(EnemyManager.instance.getEnemy(x-1,y,z) != null){
                                EnemyManager.instance.getEnemy(x-1,y,z).Fight();
                            }
                            if(map.getLayer(z).getEvent(x-1,y) != null) {
                                tmp = map.getLayer(z).getEvent(x-1, y);
                                if(map.events.get(tmp).getKey() == Map.EventType.DISTANCE_PICK)
                                    eventCode = tmp;
                            }
                            break;
                        case RIGHT:
                            if(EnemyManager.instance.getEnemy(x+1,y,z) != null){
                                EnemyManager.instance.getEnemy(x+1,y,z).Fight();
                            }
                            if(map.getLayer(z).getEvent(x+1,y) != null) {
                                tmp = map.getLayer(z).getEvent(x+1, y);
                                if(map.events.get(tmp).getKey() == Map.EventType.DISTANCE_PICK)
                                    eventCode = tmp;
                            }
                            break;
                        case UP:
                            if(EnemyManager.instance.getEnemy(x,y-1,z) != null){
                                EnemyManager.instance.getEnemy(x,y-1,z).Fight();
                            }
                            if(map.getLayer(z).getEvent(x,y-1) != null) {
                                tmp = map.getLayer(z).getEvent(x, y-1);
                                if(map.events.get(tmp).getKey() == Map.EventType.DISTANCE_PICK)
                                    eventCode = tmp;
                            }
                            break;
                        case DOWN:
                            if(EnemyManager.instance.getEnemy(x,y+1,z) != null){
                                EnemyManager.instance.getEnemy(x,y+1,z).Fight();
                            }
                            if(map.getLayer(z).getEvent(x,y+1) != null) {
                                tmp = map.getLayer(z).getEvent(x, y+1);
                                if(map.events.get(tmp).getKey() == Map.EventType.DISTANCE_PICK)
                                    eventCode = tmp;
                            }
                            break;
                    }
                }
            }
            if(eventCode != null){
                map.events.get(eventCode).getValue().apply();
                return;
            }
            if(KeyPolling.isDown(KeyCode.I)){
                Main.clearUptadables();
                Main.setScene("/resources/fxml/sceneInventory.fxml","/resources/style/styleInventory.css");
                return;
            }
            float duration = 3f;
            if (KeyPolling.isDown(KeyCode.A)) {
                if(!occupied(x-1,y,z)) {
                    animation = left;
                    ResetExcept(animation);
                    animation.Play(this);
                    x--;
                    lookingDirection = Direction.LEFT;
                }else{
                    lookingDirection = Direction.LEFT;
                    DrawBasicPosition();
                }
            } else if (KeyPolling.isDown(KeyCode.D)) {
                if(!occupied(x+1,y,z)) {
                    animation = right;
                    ResetExcept(animation);
                    animation.Play(this);
                    x++;
                    lookingDirection = Direction.RIGHT;
                }else{
                    lookingDirection = Direction.RIGHT;
                    DrawBasicPosition();
                }
            } else if (KeyPolling.isDown(KeyCode.S)) {
                if(!occupied(x,y+1,z)) {
                    animation = down;
                    ResetExcept(animation);
                    animation.Play(this);
                    y++;
                    lookingDirection = Direction.DOWN;
                }else{
                    lookingDirection = Direction.DOWN;
                    DrawBasicPosition();
                }
            } else if (KeyPolling.isDown(KeyCode.W)) {
                if(!occupied(x,y-1,z)) {
                    animation = up;
                    ResetExcept(animation);
                    animation.Play(this);
                    y--;
                    lookingDirection = Direction.UP;
                }else{
                    lookingDirection = Direction.UP;
                    DrawBasicPosition();
                }
            }
        }
    }
}
