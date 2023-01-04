package pl.edu.agh.student.wozniakmat.zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    BufferedImage background;
    SpriteFactory factory;
    List<Sprite> sprites = new ArrayList<>();
    CrossHair crossHair = new CrossHair(this);

    DrawPanel(URL backgroundImagageURL, SpriteFactory factory) {
        try {
            background = ImageIO.read(backgroundImagageURL);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.factory = factory;
        new AnimationThread().start();
        addMouseMotionListener(crossHair);
        addMouseListener(crossHair);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        synchronized (sprites) {
            for (Sprite s : sprites) s.draw(g, this);
        }
        crossHair.draw(g);
    }

    class AnimationThread extends Thread {
        public void run() {
            for (int i = 0; ; i++) {
                if (i % 30 == 0) {
                    synchronized (sprites) {
                        sprites.add(factory.newSprite(getWidth(), (int) (0.7 * getHeight())));
                    }
                }
                synchronized (sprites) {
                    for (Sprite s : sprites) s.next();
                }
                repaint();
                try {
                    sleep(1000 / 30);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
