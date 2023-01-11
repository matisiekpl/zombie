package pl.edu.agh.student.wozniakmat.zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DrawPanel extends JPanel implements CrossHairListener {

    BufferedImage background;
    SpriteFactory factory;
    List<Sprite> sprites = new ArrayList<>();
    CrossHair crossHair = new CrossHair(this);

    int points = 0;

    boolean stopped = false;

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
        crossHair.addCrossHairListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        synchronized (sprites) {
            for (Sprite s : sprites) if (s.isVisble()) s.draw(g, this);
        }
        g.setColor(Color.white);
        g.drawString("Points: " + points, 100, 100);
        crossHair.draw(g);
    }

    @Override
    public void onShotsFired(int x, int y) {
        synchronized (sprites) {
            for (int i = sprites.size() - 1; i >= 0; i--) {
                Zombie z = (Zombie) sprites.get(i);
                if (z.isHit(x, y)) {
                    sprites.remove(sprites.get(i));
                    System.out.println("Shot");
                    points++;
                    return;
                }
            }
        }
    }

    class AnimationThread extends Thread {
        public void run() {
            for (int i = 0; !stopped; i++) {
                if (i % 30 == 7) {
                    synchronized (sprites) {
                        sprites.add(factory.newSprite(getWidth(), (int) (0.7 * getHeight())));
                        sprites.sort((o1, o2) -> o1.isCloser(o2) ? 1 : -1);
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
