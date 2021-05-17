package sample;

import javafx.util.Pair;

import java.util.HashMap;

public class Layer {

    private Tile[][] tiles;
    private boolean [][] blocked;
    public HashMap<Pair<Integer,Integer>, String> events;
    private int width, height;
    Layer(int x, int  y){
        width = x;
        height = y;
        tiles = new Tile[x][y];
        blocked = new boolean[x][y];
        events = new HashMap<>();
    }

    public Tile getTileAtPos(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return tiles[x][y];
    }

    public void setTileAtPos(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public void setCollisionAtPos(int x, int y, boolean coll){
        blocked[x][y] = coll;
    }

    public boolean getCollisionAtPos(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height)
            return true;
        return blocked[x][y];
    }

    public void addEvent(int x, int y, String code){
        events.put(new Pair<>(x,y),code);
    }

    public String getEvent(int x, int y){
        return events.get(new Pair<>(x,y));
    }

    public void deleteEvent(int x, int y){
        events.remove(new Pair<>(x,y));
    }

}
