package pl.edu.agh.student.wozniakmat.zombie;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;

    CrossHair(DrawPanel parent) {
        this.parent = parent;
    }

    /* x, y to współrzedne celownika
       activated - flaga jest ustawiana po oddaniu strzału (naciśnięciu przyciku myszy)
    */
    int x;
    int y;
    boolean activated = false;

    void draw(Graphics g) {
        if (activated) g.setColor(Color.RED);
        else g.setColor(Color.WHITE);

        Graphics2D g2 = (Graphics2D) g;
        g2.draw(new Line2D.Float(x, y - 50, x, y + 50));
        g2.draw(new Line2D.Float(x - 50, y, x + 50, y));
        g2.drawOval(x - 50, y - 50, 100, 100);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        parent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        activated = true;
        parent.repaint();
        notifyListeners();
        Timer timer = new Timer("Timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated = false;
                parent.repaint();
            }
        }, 300);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    List<CrossHairListener> listeners = new ArrayList<CrossHairListener>();

    void addCrossHairListener(CrossHairListener e) {
        listeners.add(e);
    }

    void notifyListeners() {
        for (var e : listeners)
            e.onShotsFired(x, y);
    }
}
