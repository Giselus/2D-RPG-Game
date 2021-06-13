package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileDatabase {

    public static ArrayList<TileSet> tileSets = new ArrayList<>();
    public static Map<Integer, Tile> tiles;
    public static Tile getTile(int id){
        return tiles.get(id);
    }

    public static void Init(){
        tiles = new HashMap<Integer, Tile>();
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/BaseChip.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/Dirt.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/Flower.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/[A]Water_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/[A]WaterFall_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/[A]Wall-Up_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/tilesets/LightShadow_pipo.png").toString()));
    }

}
