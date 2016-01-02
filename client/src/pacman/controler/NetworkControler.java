package pacman.controler;

import pacman.model.Game;
import pacman.model.Player;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by akkes on 31/12/2015.
 */
public class NetworkControler {

    private Game game;
    private DatagramSocket socket;
    private int port;
    private InetAddress ip;
    private int lastReceivedState;

    public NetworkControler(Game game, String address, int port) {
        this.game = game;
        // Create needed tools
        try {
            this.socket = new DatagramSocket () ;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            this.ip = InetAddress.getByName (address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        this.lastReceivedState = 0;

        // Connect
        sendCommand("JOIN Akkes FF0000");

        String serverAnswer = null;
        try {
            serverAnswer = receiveCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] commandContent = serverAnswer.split("[ |\u0000]+");

        // define local playerID
        if (commandContent[0].matches("ACCEPT")) {
            game.setLocalPlayerID(new Integer(commandContent[1]));
        }

        // send first ACK to start communication
        sendCommand("ACK 0");

        // timer to sync with serveur
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                syncWithServer();
            }
        }, 1000/60, 1000/60);
    }

    void sendCommand(String command){
        //TODO: code de la prof, faire la classe en vrai
        byte[] buf = command.getBytes() ;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
        System.out.println ("On envoi au serveur: " + command) ;
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String receiveCommand() throws IOException {
        byte[] buf = new byte[300];
        DatagramPacket packet = new DatagramPacket (buf, buf.length) ;
        socket.receive (packet);

        // Affiche la reponse
        String received = new String (packet.getData ()) ;
        System.out.println ("Le serveur à répondu: " + received) ;
        return received;
    }

    void syncWithServer() {
        String serverAnswer = null;
        try {
            serverAnswer = receiveCommand();
            String[] serverCommand = serverAnswer.split("[ |\u0000]+");

            // Interpret Delta
            if (serverCommand[0].matches("DELTA")) {
                int index = 2;

                while (index < serverCommand.length) {
                    if (serverCommand[index].matches("X")) {
                        int playerID = new Integer(serverCommand[index+1]);
                        int posX = new Integer(serverCommand[index+2]);
                        if (playerID != game.getLocalPlayerID()) {
                            game.getPlayers()[playerID].setPosX(posX);
                        }

                        index += 3;
                    } else if (serverCommand[index].matches("Y")) {
                        int playerID = new Integer(serverCommand[index+1]);
                        int posY = new Integer(serverCommand[index+2]);
                        if (playerID != game.getLocalPlayerID()) {
                            game.getPlayers()[playerID].setPosY(posY);
                        }

                        index += 3;
                    } else if (serverCommand[index].matches("NewPlayer")) {
                        int playerID = new Integer(serverCommand[index+1]);
                        game.setPlayer(playerID, new Player(game));

                        index += 4;
                    }
                }

                int receivedState = new Integer(serverCommand[1]);
                if (receivedState > lastReceivedState) {
                    lastReceivedState = receivedState;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send State
        String acknowledge = "ACK ";
        acknowledge += lastReceivedState;

        // Xpos
        acknowledge += " X " + game.getLocalPlayer().getPosX();
        acknowledge += " Y " + game.getLocalPlayer().getPosY();

        sendCommand(acknowledge);
    }

    public void closeConnexion() {
        socket.close ();
    }
}