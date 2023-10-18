package VegiSlasher;


import java.awt.*;

public class VSEntity {
    int CD;
    int NotMoving = 0;
    int Left=1;
    int Right=2;
    int lives;

    int GridWidth=10;
    int width, height; //pixel
    String imageFile;
    double speed; //vertical speed
    int x, y;

    MainGame panel;

    void show(Graphics g){
        Image img = Toolkit.getDefaultToolkit().getImage(imageFile);
        g.drawImage(img, x*GridWidth, y*GridWidth, width*GridWidth, height*GridWidth, panel);
    }
}

