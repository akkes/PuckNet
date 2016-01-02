package pacman.view;

import pacman.model.Game;
import pacman.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

/**
 * Created by akkes on 31/12/2015.
 */
public class GameDisplay extends JPanel implements ActionListener {
    private Game game;
    private int squareSize = 24;
    private int dotSize = squareSize/4;
    private int gumSize = squareSize/2;

    private Image pacman;

    private Timer timer;

    public GameDisplay(Game game) {
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.black);
        this.game = game;
        pacman = new ImageIcon("./src/images/Pacman.gif").getImage().getScaledInstance(squareSize, squareSize, 0);

        timer = new Timer(16, this);//vitesse du jeu
        timer.start();
    }

    private void drawMap(Graphics2D g2d) {

        short i = 0;
        int x, y;
        short[] map = game.getDonneesCarte();

        for (y = 0; y < game.getMapHeight() * squareSize; y += squareSize) {
            for (x = 0; x < game.getMapWidth() * squareSize; x += squareSize) {
                if(1 == map[i]) {
                    g2d.setColor(Color.blue);
                    g2d.fillRect(x, y, squareSize,  squareSize);
                } else if(2 == map[i]) {
                    g2d.setColor(Color.yellow);
                    //g2d.fillOval(x + (squareSize-dotSize)/2, y + (squareSize-dotSize)/2, dotSize, dotSize);
                } else if(3 == map[i]) {
                    g2d.setColor(Color.yellow);
                    //g2d.fillOval(x + (squareSize-gumSize)/2, y + (squareSize-gumSize)/2, gumSize, gumSize);
                }

                i++;
            }
        }

        // gums display
        for(Point gum : game.getGumsList()) {
            if (null != gum) {
                g2d.setColor(Color.yellow);
                g2d.fillOval((gum.x * squareSize) + (squareSize - gumSize) / 2, (gum.y * squareSize) + (squareSize - gumSize) / 2, gumSize, gumSize);
            }
        }

        for (Point dot : game.getDotsList()) {
            if (null != dot) {
                g2d.setColor(Color.yellow);
                g2d.fillOval((dot.x * squareSize) + (squareSize - dotSize) / 2, (dot.y * squareSize) + (squareSize - dotSize) / 2, dotSize, dotSize);
            }
        }
    }

    private void drawPlayers(Graphics2D g2d) {
        //TODO: On verra après pour un truc beau
        for(Player player : game.getPlayers()) {
            if (null != player) {
                g2d.drawImage(pacman, squareSize * player.getPosX() / Game.unitsPerSquare, squareSize * player.getPosY() / Game.unitsPerSquare, this);
            }
        }
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g); //empeche repetition du pacman
        // on en est sur? pas de problème chez moi

        Graphics2D g2d = (Graphics2D) g;
        drawMap(g2d);
        drawPlayers(g2d);
    }

    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();
    }
}
