package pl.edu.agh.student.wozniakmat.zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ZombieFactory implements SpriteFactory {
    private static ZombieFactory instance;

    public static ZombieFactory getInstance() {
        if (instance == null)
            instance = new ZombieFactory();

        return instance;
    }

    BufferedImage tape;

    ZombieFactory() {
        try {
            this.tape = ImageIO.read(getClass().getResource("/resources/walkingdead.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sprite newSprite(int x, int y) {
        double scale = ((Math.random() * (2 - 0.2)) + 0.2);
        Zombie z = new Zombie(x, y, scale, tape);
        return z;
    }
}
