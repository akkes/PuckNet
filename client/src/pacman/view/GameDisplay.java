package pacman.view;

import pacman.model.Game;
import pacman.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 * Created by akkes on 31/12/2015.
 */
public class GameDisplay extends JPanel implements ActionListener {
    private Game game;
    private int squareSize = 24;
    private int dotSize = squareSize/4;
    private int gumSize = squareSize/2;
    private int margin = squareSize/4 +1;

    private Image pacman;
    private Image superPacman;

    private Timer timer;

    public GameDisplay(Game game) {
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.black);
        this.game = game;
        pacman = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Pacman.gif"));

        pacman = pacman.getScaledInstance(squareSize, squareSize, 0);
        superPacman = pacman.getScaledInstance(squareSize + 2*margin, squareSize + 2*margin, 0 );

        timer = new Timer(16, this);//vitesse du jeu
        timer.start();
    }

    public int getSquareSize() {
        return squareSize;
    }

    private void drawMap(Graphics2D g2d) {

        short i = 0;
        int x, y;
        short[] map = game.getDonneesCarte();

        for (y = 0; y < game.getMapHeight(); y++) {
            for (x = 0; x < game.getMapWidth(); x++) {
                if(1 == map[i]) {
                    g2d.setColor(Color.blue);
                    g2d.setStroke(new BasicStroke(2));
                    // Corners
                    // Top-right
                    if (1 == game.getSquare(x-1,y)
                            && 0 == game.getSquare(x+1,y)
                            && 0 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize, y*squareSize + margin, (x+1)*squareSize - margin, y*squareSize + margin);
                        g2d.drawLine((x + 1)*squareSize - margin, y*squareSize + margin, (x + 1)*squareSize - margin, (y+1)*squareSize);
                        // Down-right
                    } else if (1 == game.getSquare(x-1,y)
                            && 0 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 0 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize, (y+1)*squareSize - margin, (x+1)*squareSize - margin, (y+1)*squareSize - margin);
                        g2d.drawLine((x + 1)*squareSize - margin, y*squareSize, (x + 1)*squareSize - margin, (y+1)*squareSize - margin);
                        // Down-left
                    } else if (0 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 0 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize + margin, (y+1)*squareSize - margin, (x+1)*squareSize, (y+1)*squareSize - margin);
                        g2d.drawLine(x*squareSize + margin, y*squareSize, x*squareSize + margin, (y+1)*squareSize - margin);
                        // Top-left
                    } else if (0 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 0 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize + margin, y*squareSize + margin, (x+1)*squareSize, y*squareSize + margin);
                        g2d.drawLine(x*squareSize + margin, y*squareSize + margin, x*squareSize + margin, (y+1)*squareSize);
                        // Borders
                        // Right
                    } else if (1 == game.getSquare(x-1,y)
                            && 0 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)) {
                        g2d.drawLine((x + 1)*squareSize - margin, y*squareSize, (x + 1)*squareSize - margin, (y+1)*squareSize);
                        // Left
                    } else if (0 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize + margin, y*squareSize, x*squareSize + margin, (y+1)*squareSize);
                        // Up
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 0 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize, y*squareSize + margin, (x+1)*squareSize, y*squareSize + margin);
                        // Down
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 0 == game.getSquare(x,y+1)) {
                        g2d.drawLine(x*squareSize, (y+1)*squareSize - margin, (x+1)*squareSize, (y+1)*squareSize - margin);
                        // Inside corners
                        // Top right
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)
                            && 0 == game.getSquare(x+1,y-1)) {
                        g2d.drawLine((x+1)*squareSize - margin, (y)*squareSize + margin, (x+1)*squareSize, (y)*squareSize + margin);
                        g2d.drawLine((x+1)*squareSize - margin, (y)*squareSize, (x+1)*squareSize - margin, (y)*squareSize + margin);
                        // Down right
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)
                            && 0 == game.getSquare(x+1,y+1)) {
                        g2d.drawLine((x+1)*squareSize - margin, (y+1)*squareSize - margin, (x+1)*squareSize, (y+1)*squareSize - margin);
                        g2d.drawLine((x+1)*squareSize - margin, (y+1)*squareSize - margin, (x+1)*squareSize - margin, (y+1)*squareSize);
                        // Down left
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)
                            && 0 == game.getSquare(x-1,y+1)) {
                        g2d.drawLine(x*squareSize, (y+1)*squareSize - margin, x*squareSize + margin, (y+1)*squareSize - margin);
                        g2d.drawLine(x*squareSize + margin, (y+1)*squareSize - margin, x*squareSize + margin, (y+1)*squareSize);
                        // Up left
                    } else if (1 == game.getSquare(x-1,y)
                            && 1 == game.getSquare(x+1,y)
                            && 1 == game.getSquare(x,y-1)
                            && 1 == game.getSquare(x,y+1)
                            && 0 == game.getSquare(x-1,y-1)) {
                        g2d.drawLine(x*squareSize, y*squareSize + margin, x*squareSize + margin, y*squareSize + margin);
                        g2d.drawLine(x*squareSize + margin, y*squareSize, x*squareSize + margin, y*squareSize + margin);
                    } else {
                        // black square => do nothing
                    }
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
                if (1 == player.getPower()) {
                    // TODO: timeout animation
                    g2d.drawImage(superPacman, squareSize * player.getPosX() / Game.unitsPerSquare - margin,
                            squareSize * player.getPosY() / Game.unitsPerSquare - margin, this);
                } else {
                    g2d.drawImage(pacman, squareSize * player.getPosX() / Game.unitsPerSquare, squareSize * player.getPosY() / Game.unitsPerSquare, this);
                }
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
