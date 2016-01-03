package pacman.controler;

import pacman.model.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by akkes on 31/12/2015.
 *
 * Controler of player keyboards input
 */
public class KeyboardControler extends KeyAdapter {

    private Game game;
    private NetworkControler networkControler;

    public KeyboardControler(Game game, NetworkControler networkControler) {
        this.game = game;
        this.networkControler = networkControler;
    }

    public void keyPressed(KeyEvent e) { //on indique a quoi servent les touches du clavier

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            game.getPlayers()[game.getLocalPlayerID()].setDirection(3);
        } else if (key == KeyEvent.VK_RIGHT) {
            game.getPlayers()[game.getLocalPlayerID()].setDirection(1);
        } else if (key == KeyEvent.VK_UP) {
            game.getPlayers()[game.getLocalPlayerID()].setDirection(0);
        } else if (key == KeyEvent.VK_DOWN) {
            game.getPlayers()[game.getLocalPlayerID()].setDirection(2);
        } else if (key == KeyEvent.VK_R) {
            networkControler.reset();
        }

    }
}
