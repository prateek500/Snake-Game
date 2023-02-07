package snake;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame("snake game");
        frame.setBounds(10,10,910,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GamePanel panel=new GamePanel();
        panel.setBackground(Color.gray);
        frame.add(panel);
        frame.setVisible(true);
    }

}
