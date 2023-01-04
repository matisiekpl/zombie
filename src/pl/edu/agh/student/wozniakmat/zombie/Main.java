package pl.edu.agh.student.wozniakmat.zombie;

import javax.swing.*;

public class Main {


    Zombie zombie;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Zombie");
        DrawPanel panel = new DrawPanel(Main.class.getResource("/resources/6473205443_7df3397e72_b.jpg"), ZombieFactory.getInstance());
        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}