package sample;

import javafx.util.Pair;
import sample.controllers.mainGameController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RenderingManager extends Updatable {


    public static RenderingManager instance;

    public ArrayList<GameObject> renderQueue = new ArrayList<>();
    public void addToRenderQueue(GameObject obj){
        renderQueue.add(obj);
    }

    public RenderingManager(){
        instance = this;
    }

    public void Update(float deltaTime){
        ArrayList<Pair<Integer,GameObject>> T = new ArrayList<>();
        for(GameObject obj : renderQueue){
            T.add(new Pair<>(obj.zPos,obj));
        }
        Collections.sort(T, new Comparator<Pair<Integer, GameObject>>() {
            @Override
            public int compare(Pair<Integer, GameObject> o1, Pair<Integer, GameObject> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        mainGameController.instance.clearCanvas();
        Integer lastLayer = 0;
        for(Pair<Integer,GameObject> pair: T){
            for(int i = lastLayer+1; i <= pair.getKey();i++){
                mapHandler.getCurrentMap().RenderLayer(i);
            }
            lastLayer = pair.getKey();
            pair.getValue().Draw();
        }
        for(int i = lastLayer+1; i <= mapHandler.getCurrentMap().getLayersNo();i++){
            mapHandler.getCurrentMap().RenderLayer(i);
        }
    }

}
