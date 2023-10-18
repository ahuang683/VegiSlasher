package VegiSlasher;

public class MainCharacter extends VSEntity{
    int score;
    MainCharacter(MainGame s){
        lives=1000;
        score = 0;

        panel = s;
        width = 10;
        height = 10;
        imageFile = "baby.jpg";
        speed = 2;
        x=50;
        y=50;
    }
    void Run(){

        if(CD==1){
            x-=speed;
        }
        if(CD==2){
            x+=speed;
        }
    }

}
