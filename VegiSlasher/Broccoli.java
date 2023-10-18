package VegiSlasher;

public class Broccoli extends VSEntity{
    Broccoli(MainGame s){

        width = 8;
        height = 8;
        imageFile = "Mushroom.png";
        speed = 1;
        x=(int)(Math.random()*100);
        y=(int)(Math.random()*60);
    }
    void move(){
        int r = (int)(Math.random()*2);
        if (r==0)
            y+=2;
        else
            y-=2;
    }
}
