package VegiSlasher;

public class Platform extends VSEntity{
    Platform(MainGame s) {
        panel = s;
        width = 30;
        height = 5;
        imageFile = "Platform.GIF";
        speed = 2;
        x = 35;
        y = 45;
    }
}
