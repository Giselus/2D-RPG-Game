package sample;

import javafx.scene.image.Image;

public class ImageFrame{
    public int left,down;
    public int xSize,ySize;
    public Image img;

    public ImageFrame(Image img){
        this.img = img;
        left = down = 0;
        xSize = (int)img.getWidth();
        ySize = (int)img.getHeight();
    }

    public ImageFrame(Image img, int left, int down, int xSize, int ySize){
        this.img = img;
        this.left = left;
        this.down = down;
        this.xSize = xSize;
        this.ySize = ySize;
    }
}
