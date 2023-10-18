package VegiSlasher;

import lesson14_inclass.Entity;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class MainGame extends JPanel implements Runnable {

//    Mario2 m;
//    ArrayList<Coin2> coins; //declaration
//    ArrayList<Koopa> koopas; //declaration
//    ArrayList<MushroomBlock> MB;
//    ArrayList<NormalBlocks> NB;
    MainCharacter m;
    ArrayList<Broccoli> b;
    ArrayList<Bullet> bullet;
    boolean BabyJumped;
    boolean onBlock;
    boolean HitBroccoli=false;
    int whichB;
    int clickx, clicky;
    int babyx,babyy;
    Platform Platform1 = new Platform(this);
    MainGame() {

        System.out.println("Here! this function is being called.");
        //create a mouse event listener
        MouseAdapter myMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //you define what you want to happen when a mouse is being clicked
                int x = e.getX();
                int y = e.getY();
                System.out.println("A mouse click happened at " + x + "," + y);
                clickx = x;
                clicky = y;
                for(Broccoli B: b){
                    if(clickx>=B.x*10&&clickx<=(B.x+B.width)*10&&clicky>=B.y*10&&clicky<=(B.y+B.height)*10){
                        whichB=b.indexOf(B);
                       HitBroccoli=true;
                    }
                }

                repaint(); //refresh the panel with the latest data, paint again
            }
        };
        // add the listener to the panel
        addMouseListener(myMouseAdapter);


//        coins = new ArrayList<Coin2>(); //initialize it to empty
        m = new MainCharacter(this);
        b = new ArrayList<Broccoli>();
        bullet = new ArrayList<Bullet>();

        setFocusable(true);
        KeyAdapter myKeyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_D){
                    m.CD=2;
                }
                else if(e.getKeyCode()==KeyEvent.VK_A){
                    m.CD=1;
                }
                //Jump for Baby
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    if((m.y+m.height)*10>=getHeight()||onBlock){
                        BabyJumped=true;

                    }
                }

            }
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_D)
                    m.CD = 0;
            }


        };
        addKeyListener(myKeyAdapter);

    }


    public void paintComponent (Graphics g){
        super.paintComponent(g);

        Image img = Toolkit.getDefaultToolkit().getImage("Background.GIF");
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g.drawString(m.lives+"",20,20);
        m.show(g);
        Platform1.show(g);
        for(Broccoli b: b){
            b.show(g);
        }
        for(Bullet b1: bullet){
            b1.show(g);
        }

    }

    //play sound function
    void playSound(String soundFileName){
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(soundFileName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();   // create clip reference
            clip.open(audioInputStream);    // open audioInputStream to the clip
            clip.loop(0);
        } catch (Exception exception ) { }

    }
    static boolean checkOverlapping(VSEntity e1, VSEntity e2){
        if (e1.x > e2.x + e2.width) return false;
        if (e1.x + e1.width < e2.x) return false;
        if (e1.y + e1.height < e2.y) return false;
        if (e2.y+ e2.height < e1.y) return false;
        return true;
    }
    int randomPercent(){
        int result = (int) (Math.random()*100);
        return result;
    }


    public void run(){
        int counter = 0;

        Broccoli B3 = new Broccoli(this);
        Broccoli B4 = new Broccoli(this);
        Broccoli B5 = new Broccoli(this);
        Broccoli B6 = new Broccoli(this);
        b.add(B3);
        b.add(B4);
        b.add(B5);
        b.add(B6);
        while(true){

            if (BabyJumped) {
                m.y-=m.speed*10;

                System.out.println("It jumped");
                BabyJumped=false;
            }

            counter++;
            if((((m.x*10>=Platform1.x*10&&m.x*10<=Platform1.x*10+Platform1.width*10)||(m.x*10+m.width*10>=Platform1.x*10&&m.x*10+m.width*10<=Platform1.x*10+Platform1.width*10))&&((m.y+m.height>=Platform1.y&&m.y+m.height<=Platform1.y+Platform1.height)))){
                onBlock=true;
                m.y=Platform1.y-m.height;
            }
            else{
                onBlock=false;
            }
            m.Run();
            if(10*(m.y+m.height)<600&&!onBlock){
                m.y++;
            }

            for(int i = 0; i<b.size(); i++){
                Broccoli B = b.get(i);
                if(checkOverlapping(B,m)){
                    m.lives-=10;
                }
                if((B.y+B.height)*10<getHeight()&&counter%10==0){
                    B.move();
                }
                else if((B.y+B.height)*10>=getHeight()&&counter%10==0){
                    B.y-=2;}
                if(HitBroccoli&&i==whichB) {
//                    m.score+=10;
                    b.remove(i);
                    Broccoli B2 = new Broccoli(this);
                    b.add(B2);
                    HitBroccoli = false;
                    i--;
                }
                for (Bullet b1: bullet){
                    b1.move();
                    if(checkOverlapping(b1,m)){
                        m.lives--;
                    }
                }
                if(m.score>=30){
                    m.score-=30;
                    m.lives+=100;
                }
            }



            //adding everything #annoying
            if(counter%10==0){
                babyx = m.x;
                babyy = m.y;
                for(int i = 0; i<b.size(); i++) {
                    Bullet b1 = new Bullet(this);
                    b1.x = b.get(i).x;
                    b1.y = b.get(i).y;
                    if (b1.x < m.x) {
                        b1.dirx = true;
                    } else {
                        b1.dirx = false;
                    }
                    if (b1.y < m.y) {
                        b1.diry = true;
                    } else {
                        b1.diry = false;
                    }
                    bullet.add(b1);
                }

            }



//            if(counter%1000==0)
//                m.Run();

            //tell the computer to pause (sleep)

            repaint();
            try {
                Thread.sleep(50); //milliseconds
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main (String argv[]) {
        JFrame myFrame = new JFrame("VegiSlasher");
        MainGame myPanel = new MainGame();
        myPanel.setPreferredSize(new Dimension(1000, 600));
        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        new Thread(myPanel).start();
    }
}

