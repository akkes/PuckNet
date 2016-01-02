package pacman;

import pacman.controler.KeyboardControler;
import pacman.controler.NetworkControler;
import pacman.model.Game;
import pacman.view.GameDisplay;

import javax.swing.*;
import java.awt.*;

public class PuckNet extends JFrame {

    public PuckNet() {

        // créer données
        Game game = new Game();

        // demander IP serveur


        // connection
        String serverAddress = (String)JOptionPane.showInputDialog(this, "Entrez l'adresse du serveur");
        NetworkControler networkControler = new NetworkControler(game, serverAddress, 7825);
        game.setNetworkControler(networkControler);
        if (!networkControler.isConnected()) {
            System.exit(0);
        }

        // créer fenêtre
        GameDisplay gameDisplay = new GameDisplay(game);
        gameDisplay.addKeyListener(new KeyboardControler(game));
        add(gameDisplay);
        setTitle("PuckNet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);

        // Jouer
    }
    
   
    public static void main(String[] args) {
    	new PuckNet();
    }
}
