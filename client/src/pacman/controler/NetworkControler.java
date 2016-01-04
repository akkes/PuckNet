package pacman.controler;

import pacman.model.Game;
import pacman.model.Player;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
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
    private ArrayList<Integer> eatenDotsList = new ArrayList<>();
    private ArrayList<Integer> eatenGumsList = new ArrayList<>();
    private ArrayList<Integer> eatenPlayersList = new ArrayList<>();
    private Boolean connected;
    private int lostPackets;
    private Timer timer;

    public NetworkControler(Game game, String address, int port) {
        this.game = game;
        // Create needed tools
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(3000);
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter au serveur. :-(");
            System.exit(0);
        }
        try {
            this.ip = InetAddress.getByName (address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        this.lastReceivedState = 0;
        this.lostPackets = 0;

        // Connect
        sendCommand("JOIN Akkes FF0000");

        String serverAnswer = null;
        try {
            serverAnswer = receiveCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] commandContent = serverAnswer.split("[ |\u0000|\n]+");

        // define local playerID
        if (commandContent[0].matches("ACCEPT")) {
            game.setLocalPlayerID(new Integer(commandContent[1]));
            connected = true;
        } else {
            JOptionPane.showMessageDialog(null, "Le serveur n'a plus de place");
            connected = false;

        }

        // send first ACK to start communication
        sendCommand("ACK 0");

        try {
            this.socket.setSoTimeout(1000/30);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // timer to sync with serveur
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                syncWithServer();
            }
        }, 1000/30, 1000/30);
    }
    
    public Boolean isConnected() {
        return this.connected;
    }

    void sendCommand(String command){
        byte[] buf = command.getBytes() ;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
        System.out.println ("-> " + command) ;
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String receiveCommand() throws IOException {
        byte[] buf = new byte[4000];
        DatagramPacket packet = new DatagramPacket (buf, buf.length) ;
        socket.receive (packet);

        // Affiche la reponse
        String received = new String (packet.getData()) ;
        System.out.println ("<- " + received) ;
        return received;
    }

    void syncWithServer() {
        String serverAnswer = null;
        try {
            serverAnswer = receiveCommand();
            this.lostPackets = 0;
            String[] serverCommand = serverAnswer.split("[ |\u0000|\n]+");

            // Interpret Delta
            if (serverCommand[0].matches("DELTA")) {
                int index = 2;
                int receivedState = new Integer(serverCommand[1]);
                if (receivedState >= this.lastReceivedState) {
                    this.lastReceivedState = receivedState;

                    while (index < serverCommand.length) {
                        if (serverCommand[index].matches("X")
                                && serverCommand.length >= index+2) {
                            int playerID = new Integer(serverCommand[index + 1]);
                            int posX = new Integer(serverCommand[index + 2]);
                            if (playerID != game.getLocalPlayerID()) {
                                game.getPlayers()[playerID].setPosX(posX);
                            }

                            index += 3;
                        } else if (serverCommand[index].matches("Y")
                                && serverCommand.length >= index+2) {
                            int playerID = new Integer(serverCommand[index + 1]);
                            int posY = new Integer(serverCommand[index + 2]);
                            if (playerID != game.getLocalPlayerID()) {
                                game.getPlayers()[playerID].setPosY(posY);
                            }

                            index += 3;
                        } else if (serverCommand[index].matches("Score")
                                && serverCommand.length >= index+2) {
                            int playerID = new Integer(serverCommand[index + 1]);
                            int score = new Integer(serverCommand[index + 2]);
                            game.getPlayers()[playerID].setScore(score);

                            index += 3;
                        } else if (serverCommand[index].matches("NewPlayer")
                                && serverCommand.length >= index+3) {
                            int playerID = new Integer(serverCommand[index + 1]);
                            int posX = new Integer(serverCommand[index + 2]);
                            int posY = new Integer(serverCommand[index + 3]);
                            game.setPlayer(playerID, new Player(game));
                            game.getPlayers()[playerID].setPosX(posX);
                            game.getPlayers()[playerID].setPosY(posY);

                            index += 6;
                        } else if (serverCommand[index].matches("Dot")
                                && serverCommand.length >= index+1) {
                            game.setDotEated(new Integer(serverCommand[index + 1]));

                            index += 2;
                        } else if (serverCommand[index].matches("Gum")
                                && serverCommand.length >= index+1) {
                            game.setGumEated(new Integer(serverCommand[index + 1]));

                            index += 2;
                        } else if (serverCommand[index].matches("Super")
                                && serverCommand.length >= index+1) {
                            game.getPlayers()[new Integer(serverCommand[index + 1])].setPower(1);

                            index += 2;
                        } else if (serverCommand[index].matches("Normal")
                                && serverCommand.length >= index+1) {
                            game.getPlayers()[new Integer(serverCommand[index + 1])].setPower(0);

                            index += 2;
                        } else if (serverCommand[index].matches("Leave")
                                && serverCommand.length >= index+1) {
                            game.setPlayer(new Integer(serverCommand[index + 1]), null);

                            index += 2;
                        } else if (serverCommand[index].matches("Eaten")
                                && serverCommand.length >= index+1) {
                            int playerID = new Integer(serverCommand[index + 1]);
                            game.getPlayers()[playerID].setPosX(game.getSpawnX());
                            game.getPlayers()[playerID].setPosY(game.getSpawnY());

                            index += 2;
                        } else if (serverCommand[index].matches("NewDot")
                                && serverCommand.length >= index+1) {
                            int dotID = new Integer(serverCommand[index + 1]);
                            game.addNewDot(dotID);
                            index += 2;
                        } else if (serverCommand[index].matches("NewGum")
                                && serverCommand.length >= index+1) {
                            int gumID = new Integer(serverCommand[index + 1]);
                            game.addNewGum(gumID);
                            index += 2;
                        } else {
                            System.out.println("Erreur dans la commande du serveur: " + serverCommand[index]);
                            index++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            this.lostPackets++;

            if (lostPackets > 30*5) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Le serveur est déconnecté");
                System.exit(0);
            }
        }

        // Send State
        String acknowledge = "ACK ";
        acknowledge += lastReceivedState;

        // Position
        acknowledge += " X " + game.getLocalPlayer().getPosX();
        acknowledge += " Y " + game.getLocalPlayer().getPosY();

        // Eaten things
        // Gums
        for(Integer gumID : eatenGumsList) {
            acknowledge += " Gum " + gumID;
        }
        eatenGumsList = new ArrayList<>();

        // Dots
        for(Integer dotID : eatenDotsList) {
            acknowledge += " Dot " + dotID;
        }
        eatenDotsList = new ArrayList<>();

        // Players
        for(Integer playerID : eatenPlayersList) {
            acknowledge += " Eat " + playerID;
        }
        eatenPlayersList = new ArrayList<>();

        sendCommand(acknowledge);
    }

    public void setDotEated(int dotID) {
        eatenDotsList.add(dotID);
    }

    public void setGumEated(int gumID) {
        eatenGumsList.add(gumID);
    }

    public void setplayerEated(int playerID) {
        eatenPlayersList.add(playerID);
    }

    public void reset() {
        sendCommand("RESET");
    }

    public void closeConnexion() {
        timer.cancel();
        sendCommand("LEAVE");

        socket.close ();
    }
}
