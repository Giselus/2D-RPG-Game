package sample;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class mapHandler {

    private static HashMap<String, File> mapFiles;
    private static Map currentMap;

    public static void Init(){
        mapFiles = new HashMap<>();

        File directory = null;

        try {
            directory = new File(mapHandler.class.getResource("/resources/maps").getPath());
        }catch(NullPointerException e){
            System.out.println("Cannot read directory with maps, it's probably empty");
            return;
        }
        if(!directory.isDirectory()){
            System.out.println("Cannot read directory with maps, given path probably lead to file");
            return;
        }
        File [] files = directory.listFiles();
        for(File file: files) {
            if(!file.getName().endsWith(".map"))
                continue;
            try{
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String name = bufferedReader.readLine();

                mapFiles.put(name,file);

                reader.close();
                bufferedReader.close();
            }catch(Exception e){
                System.out.println("Problem with reading file " + e);
            }
        }
    }

    public static void setCurrentMap(String name){
        currentMap = new Map(mapFiles.get(name));
    }

    public static Map getCurrentMap(){
        return currentMap;
    }
}
