package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Mission {
    public int id;
    public int exp;
    public int gold;
    public String name;
    public String description;
    boolean canBeFinished;
    public ArrayList<enemiesToKill> enemiesToKillArrayList;

    public Mission(int id){
        this.id = id;
        canBeFinished= false;
        enemiesToKillArrayList = new ArrayList<>();
        readMissionStats(id);
        description = readLineStats(id, "missionDescription.txt");
    }
    private void readMissionStats(int id){
        String data = readLineStats(id, "missionStats.txt");
        String[] tab = data.split(":", 10);
        this.name = tab[1];
        this.gold = Integer.parseInt(tab[3]);
        this.exp = Integer.parseInt(tab[4]);
        String[] enemyString = tab[2].split("-");
        for(int i=0; i<enemyString.length; i+=3){
            int x, y, z;
            x = Integer.parseInt(enemyString[i]);
            y = Integer.parseInt(enemyString[i+1]);
            z = Integer.parseInt(enemyString[i+2]);
            //enemiesToKillArrayList.add(new enemiesToKill(x, y, z));
        }
    }
    private String readLineStats(int id, String path){
        try {
            System.out.println("resources/TextFiles/" + path);
            //System.out.println("resources/TextFiles/missionStats.txt");
            File myObj = new File("src/resources/TextFiles/" + path);
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            String data = "";
            while(myReader.hasNextLine()){
                data = myReader.nextLine();
                if(counter == id){
                    break;
                }
                counter++;
            }
            return data;
        } catch (Exception e){
            System.out.println("File has not been found");
            e.printStackTrace();
        }
        return "";
    }
    public class enemiesToKill{
        public Enemy enemy;
        public int amountOfEnemies;
        public int enemiesLeft;

        private enemiesToKill(int id, int difficulty, int amountOfEnemies){
            enemy = new Enemy(id, difficulty);
            this.amountOfEnemies = amountOfEnemies;
            enemiesLeft = 0;
        }
        public void enemyKilled(){
            enemiesLeft++;
        }
        public boolean sameEnemy(Enemy e){
            return e.id == enemy.id && e.level.equals(enemy.level);
        }
        public String getName(){
            return enemy.name;
        }
    }
}
