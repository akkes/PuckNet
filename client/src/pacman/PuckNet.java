package pacman;

import pacman.controler.KeyboardControler;
import pacman.controler.NetworkControler;
import pacman.model.Game;
import pacman.view.GameDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class PuckNet extends JFrame {
    private NetworkControler networkControler;

    public PuckNet() {

        // créer données
        Game game = new Game();

        // demander IP serveur


        // connection
        String serverAddress = (String)JOptionPane.showInputDialog(this, "Entrez l'adresse du serveur");
        this.networkControler = new NetworkControler(game, serverAddress, 7825);
        game.setNetworkControler(networkControler);
        if (!networkControler.isConnected()) {
            System.exit(0);
        }

        // créer fenêtre
        GameDisplay gameDisplay = new GameDisplay(game);
        gameDisplay.addKeyListener(new KeyboardControler(game, networkControler));
        add(gameDisplay);
        setTitle("PuckNet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(game.getMapWidth() * gameDisplay.getSquareSize(),
                (game.getMapHeight()+1) * gameDisplay.getSquareSize());
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);

        // Jouer
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if(e.getID() == WindowEvent.WINDOW_CLOSING){

            networkControler.closeConnexion();

        }

        super.processWindowEvent(e);
    }

    public static void main(String[] args) {
    	new PuckNet();
    }
}
