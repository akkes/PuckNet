package pacman.model;

import pacman.controler.NetworkControler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by akkes on 31/12/2015.
 *
 * Class for game data
 */
public class Game {

    // speed of pacman in square/seconds times cycle/seconds
    public static int unitsPerSquare = 60/4;

    // Map data
    private int mapWidth = 28;
    private int mapHeight = 31;
    private final short donneesCarte[] = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1,
            1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1,
            1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };

    private final Point[] originalDotsList = new Point[80];
    private final Point[] originalGumsList = new Point[5];

    private Point[] dotsList = new Point[80];
    private Point[] gumsList = new Point[5];

    private int localPlayerID = 0;
    private Player[] players = new Player[5];

    private NetworkControler networkControler;

    public Game() {
        // Gums list
        originalGumsList[0] = new Point(1, 3);

        gumsList = originalGumsList.clone();

        // Dots list
        int index = 0;
        for (int i = 1; i <= 12; i++) {
            originalDotsList[index] = new Point(i, 1);
            index++;
        }
        for (int i = 2; i <= 14; i++) {
            originalDotsList[index] = new Point(6, i);
            index++;
        }
        originalDotsList[index] = new Point(1, 2);

        dotsList = originalDotsList.clone();
    }

    public void setNetworkControler(NetworkControler networkControler) {
        this.networkControler = networkControler;
    }

    public int getSpawnX() {
        return 14 * Game.unitsPerSquare;
    }

    public int getSpawnY() {
        return 17 * Game.unitsPerSquare;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayer(int id, Player player) {
        if (id != localPlayerID) {
            players[id] = player;
        }
    }

    public int getLocalPlayerID() {
        return localPlayerID;
    }

    public Player getLocalPlayer() {
        return players[localPlayerID];
    }

    public void setLocalPlayerID(int localPlayerID) {
        this.localPlayerID = localPlayerID;
        this.players[localPlayerID] = new LocalPlayer(this);
    }

    public short[] getDonneesCarte() {
        return donneesCarte;
    }

    public Point[] getDotsList() {
        return dotsList;
    }

    public Point[] getGumsList() {
        return gumsList;
    }

    public short getSquare(int x, int y) {
        return donneesCarte[y * mapWidth + x];
    }

    public short getUnit(int x, int y) {
        return donneesCarte[(y/unitsPerSquare) * mapWidth + (x/unitsPerSquare)];
    }

    private void setUnit(int x, int y, short value) {
        donneesCarte[(y/unitsPerSquare) * mapWidth + (x/unitsPerSquare)] = value;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void eat(int x, int y) {
        Point eaten = new Point(x/unitsPerSquare,y/unitsPerSquare);

        for (int i = 0; i < dotsList.length; i++) {
            if (null != dotsList[i] && dotsList[i].equals(eaten)) {
                dotsList[i] = null;
                networkControler.setDotEated(i);
            }
        }

        for (int i = 0; i < gumsList.length; i++) {
            if (null != gumsList[i] && gumsList[i].equals(eaten)) {
                gumsList[i] = null;
                networkControler.setGumEated(i);
            }
        }

    }

    public void superEat(int x, int y) {
        Point eaten = new Point(x/unitsPerSquare,y/unitsPerSquare);
        for (int i = 0; i < players.length; i++) {
            if (null != players[i]
                    && getLocalPlayerID() != i
                    && players[i].getPosX()/unitsPerSquare == eaten.x
                    && players[i].getPosY()/unitsPerSquare == eaten.y) {
                networkControler.setplayerEated(i);
            }
        }

    }

    public void setDotEated(int dotID) {
        dotsList[dotID] = null;
    }

    public void setGumEated(int gumID) {
        gumsList[gumID] = null;
    }

    public void update() {
        getLocalPlayer().update();

    }
}
