package pl.edu.agh.student.wozniakmat.zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie implements Sprite {
    BufferedImage tape;
    int x = 500;
    int y = 500;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 2000 / 10;  // z rysunku;

    Zombie(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        try {
            this.tape = ImageIO.read(getClass().getResource("/resources/walkingdead.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Zombie(int x, int y, double scale, BufferedImage tape) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = tape;
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */

    public void draw(Graphics g, JPanel parent) {
        Image img = tape.getSubimage(index * WIDTH, 0, WIDTH, HEIGHT); // pobierz klatkę
        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next() {
        x -= 20 * scale;
        index = (index + 1) % 10;
    }

    @Override
    public boolean isVisble() {
        return x > -400;
    }

    @Override
    public boolean isHit(int _x, int _y) {
        int x1 = x;
        int y1 = y - (int) (HEIGHT * scale) / 2;
        int x2 = x1 + (int) (WIDTH * scale);
        int y2 = y1 + (int) (HEIGHT * scale);

        if (_x < x1) return false;
        if (_x > x2) return false;
        if (_y < y1) return false;
        if (_y > y2) return false;
        return true;
    }

    @Override
    public boolean isCloser(Sprite other) {
        return scale > ((Zombie) other).scale;
    }
}