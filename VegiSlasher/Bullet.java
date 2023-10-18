package VegiSlasher;

public class Bullet extends VSEntity{
    //true = bullet is less than target
    //false = bullet is more than target
    //"y" true = bullet is above target
    //"y" false = bullet is below target
    boolean dirx;
    boolean diry;
    Bullet(MainGame s) {
        panel = s;
        width = 5;
        height = 2;
        imageFile = "Mushroom.png";
        speed = 1;
        x = 0;
        y = 0;
    }
    void move(){
        if (dirx){
            x = x + (int) speed;
        }
        else{
            x = x - (int) speed;
        }
        if (diry){
            y = y + (int) speed;
        }
        else{
            y = y - (int) speed;
        }

    }
}
