package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    private Random random=new Random();
    private int enemyX,enemyY;
    private int[] snakeXlength=new int[750];
    private int[] snakeYlength=new int[750];
    private int lengthOfSnake=3;

    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    private int moves=0;
    private Timer timer;
    private int delay=100;
    private int score=0;
    private boolean gameover=false;

    private ImageIcon SNAKETITLE =new ImageIcon(getClass().getResource("SNAKETITLE.png"));
    private ImageIcon UPHEAD =new ImageIcon(getClass().getResource("uphead.jpg"));
    private ImageIcon DOWNHEAD =new ImageIcon(getClass().getResource("downhead.jpg"));
    private ImageIcon LEFTHEAD =new ImageIcon(getClass().getResource("lefthead.jpg"));
    private ImageIcon RIGHTHEAD =new ImageIcon(getClass().getResource("righthead.jpg"));
    private ImageIcon SNAKEIMAGE =new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon ENEMY =new ImageIcon(getClass().getResource("enemy.png"));
    GamePanel(){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer=new Timer(delay,this);
        timer.start();
        newEnemy();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        g.setColor(Color.BLACK);

        SNAKETITLE.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,575);
        if(moves==0){
            snakeXlength[0]=100;
            snakeXlength[1]=75;
            snakeXlength[2]=50;

            snakeYlength[0]=100;
            snakeYlength[1]=100;
            snakeYlength[2]=100;

        }
        if(left){
            LEFTHEAD.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(right) {
            RIGHTHEAD.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if(up){
            UPHEAD.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }
        if(down){
            DOWNHEAD.paintIcon(this,g,snakeXlength[0],snakeYlength[0]);
        }

        for(int i=1;i<lengthOfSnake;i++){
            SNAKEIMAGE.paintIcon(this,g,snakeXlength[i],snakeYlength[i]);
        }

        ENEMY.paintIcon(this,g,enemyX,enemyY);

        if(gameover){
            g.setColor(Color.white);
            g.setFont(new Font("Aerial",Font.BOLD,50));
            g.drawString("GAME OVER",300,300);
            g.setFont(new Font("Aerial",Font.BOLD,20));
            g.drawString("Press Space to Restart",320,350);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Aerial",Font.PLAIN,14));
        g.drawString("score: "+score,750,30);
        g.drawString("Length: "+lengthOfSnake, 750,50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--){
            snakeXlength[i]=snakeXlength[i-1];
            snakeYlength[i]=snakeYlength[i-1];
        }
        if(left){
            snakeXlength[0]=snakeXlength[0]-25;
        }
        if(right){
            snakeXlength[0]=snakeXlength[0]+25;
        }
        if(up){
            snakeYlength[0]=snakeYlength[0]-25;
        }
        if(down){
            snakeYlength[0]=snakeYlength[0]+25;
        }
        if(snakeXlength[0]>850)
            snakeXlength[0]=25;
        if(snakeXlength[0]<25)
            snakeXlength[0]=850;
        if(snakeYlength[0]>625)
            snakeYlength[0]=75;
        if(snakeYlength[0]<75)
            snakeYlength[0]=625;
        collisionWithEnemy();
        collisionWithBody();
        repaint();
    }

    private void collisionWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--) {
            if ((snakeXlength[i] == snakeXlength[0]) && (snakeYlength[i] == snakeYlength[0])) {
                timer.stop();
                gameover = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT &&(!right)){
            left=true;
            right=false;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT &&(!left)){
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP &&(!down)){
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN &&(!up)){
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void newEnemy() {
        enemyX=xpos[random.nextInt(34)];
        enemyY=ypos[random.nextInt(23)];
        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakeXlength[i]==enemyX && snakeYlength[i]==enemyY)
                newEnemy();
        }
    }
    private void collisionWithEnemy(){
        if(snakeXlength[0]==enemyX && snakeYlength[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }
    private void restart(){
        gameover=false;
        left=false;
        right=true;
        up=false;
        down=false;
        score=0;
        moves=0;
        lengthOfSnake=3;
        timer.start();
        repaint();
    }
}
