package sample;

public class Items {
    public int id;
    public int type;
    public Items(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "My name is " + this.id;
    }
}
