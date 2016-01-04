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
            1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1,
            1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1,
            1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1,
            1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1,
            1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1,
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

    private final Point[] originalDotsList = new Point[334];
    private final Point[] originalGumsList = new Point[5];

    private Point[] dotsList = new Point[80];
    private Point[] gumsList = new Point[5];

    private int localPlayerID = 0;
    private Player[] players = new Player[5];

    private NetworkControler networkControler;

    public Game() {
        createGums();
        createDots();
    }

    private void createDots() {
        int index = 0;
        // first line
        for (int i = 1; i <= 12; i++) {
            originalDotsList[index] = new Point(i, 1);
            index++;
        }
        for (int i = 15; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 1);
            index++;
        }

        // second line
        originalDotsList[index] = new Point(1, 2);
        originalDotsList[index+1] = new Point(6, 2);
        originalDotsList[index+2] = new Point(12, 2);
        originalDotsList[index+3] = new Point(15, 2);
        originalDotsList[index+4] = new Point(21, 2);
        originalDotsList[index+5] = new Point(26, 2);
        index += 6;

        // Third line
        originalDotsList[index] = new Point(6, 3);
        originalDotsList[index+1] = new Point(12, 3);
        originalDotsList[index+2] = new Point(15, 3);
        originalDotsList[index+3] = new Point(21, 3);
        index += 4;

        // Fourth line
        originalDotsList[index] = new Point(1, 4);
        originalDotsList[index+1] = new Point(6, 4);
        originalDotsList[index+2] = new Point(12, 4);
        originalDotsList[index+3] = new Point(15, 4);
        originalDotsList[index+4] = new Point(21, 4);
        originalDotsList[index+5] = new Point(26, 4);
        index += 6;

        // fifth line
        for (int i = 1; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 5);
            index++;
        }

        // 6
        originalDotsList[index] = new Point(1, 6);
        originalDotsList[index+1] = new Point(6, 6);
        originalDotsList[index+2] = new Point(9, 6);
        originalDotsList[index+3] = new Point(18, 6);
        originalDotsList[index+4] = new Point(21, 6);
        originalDotsList[index+5] = new Point(26, 6);
        index += 6;

        // 7
        originalDotsList[index] = new Point(1, 7);
        originalDotsList[index+1] = new Point(6, 7);
        originalDotsList[index+2] = new Point(9, 7);
        originalDotsList[index+3] = new Point(18, 7);
        originalDotsList[index+4] = new Point(21, 7);
        originalDotsList[index+5] = new Point(26, 7);
        index += 6;

        // 8
        for (int i = 1; i <= 6; i++) {
            originalDotsList[index] = new Point(i, 8);
            index++;
        }
        for (int i = 9; i <= 12; i++) {
            originalDotsList[index] = new Point(i, 8);
            index++;
        }
        for (int i = 15; i <= 18; i++) {
            originalDotsList[index] = new Point(i, 8);
            index++;
        }
        for (int i = 21; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 8);
            index++;
        }

        // 9
        originalDotsList[index] = new Point(3, 9);
        originalDotsList[index+1] = new Point(6, 9);
        originalDotsList[index+2] = new Point(12, 9);
        originalDotsList[index+3] = new Point(15, 9);
        originalDotsList[index+4] = new Point(21, 9);
        originalDotsList[index+5] = new Point(24, 9);
        index += 6;

        // 10
        originalDotsList[index] = new Point(3, 10);
        originalDotsList[index+1] = new Point(6, 10);
        originalDotsList[index+2] = new Point(12, 10);
        originalDotsList[index+3] = new Point(15, 10);
        originalDotsList[index+4] = new Point(21, 10);
        originalDotsList[index+5] = new Point(24, 10);
        index += 6;

        // 11
        for (int i = 1; i <= 3; i++) {
            originalDotsList[index] = new Point(i, 11);
            index++;
        }
        originalDotsList[index] = new Point(6, 11);
        index++;
        for (int i = 9; i <= 18; i++) {
            originalDotsList[index] = new Point(i, 11);
            index++;
        }
        originalDotsList[index] = new Point(21, 11);
        index++;
        for (int i = 24; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 11);
            index++;
        }

        // 12
        originalDotsList[index] = new Point(1, 12);
        originalDotsList[index+1] = new Point(6, 12);
        originalDotsList[index+2] = new Point(9, 12);
        originalDotsList[index+3] = new Point(15, 12);
        originalDotsList[index+4] = new Point(18, 12);
        originalDotsList[index+5] = new Point(21, 12);
        originalDotsList[index+6] = new Point(26, 12);
        index += 7;

        // 13
        originalDotsList[index] = new Point(1, 13);
        originalDotsList[index+1] = new Point(6, 13);
        originalDotsList[index+2] = new Point(9, 13);
        originalDotsList[index+3] = new Point(15, 13);
        originalDotsList[index+4] = new Point(18, 13);
        originalDotsList[index+5] = new Point(21, 13);
        originalDotsList[index+6] = new Point(26, 13);
        index += 7;

        // 14
        for (int i = 1; i <= 9; i++) {
            originalDotsList[index] = new Point(i, 14);
            index++;
        }
        for (int i = 12; i <= 15; i++) {
            originalDotsList[index] = new Point(i, 14);
            index++;
        }
        for (int i = 18; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 14);
            index++;
        }

        // 15
        originalDotsList[index] = new Point(1, 15);
        originalDotsList[index+1] = new Point(6, 15);
        originalDotsList[index+2] = new Point(9, 15);
        originalDotsList[index+3] = new Point(12, 15);
        originalDotsList[index+4] = new Point(18, 15);
        originalDotsList[index+5] = new Point(21, 15);
        originalDotsList[index+6] = new Point(26, 15);
        index += 7;

        // 16
        originalDotsList[index] = new Point(1, 16);
        originalDotsList[index+1] = new Point(6, 16);
        originalDotsList[index+2] = new Point(9, 16);
        originalDotsList[index+3] = new Point(12, 16);
        originalDotsList[index+4] = new Point(18, 16);
        originalDotsList[index+5] = new Point(21, 16);
        originalDotsList[index+6] = new Point(26, 16);
        index += 7;

        // 17
        for (int i = 1; i <= 3; i++) {
            originalDotsList[index] = new Point(i, 17);
            index++;
        }
        originalDotsList[index] = new Point(6, 17);
        index++;
        for (int i = 9; i <= 18; i++) {
            originalDotsList[index] = new Point(i, 17);
            index++;
        }
        originalDotsList[index] = new Point(21, 17);
        index++;
        for (int i = 24; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 17);
            index++;
        }

        // 18
        originalDotsList[index] = new Point(3, 18);
        originalDotsList[index+1] = new Point(6, 18);
        originalDotsList[index+2] = new Point(9, 18);
        originalDotsList[index+3] = new Point(18, 18);
        originalDotsList[index+4] = new Point(21, 18);
        originalDotsList[index+5] = new Point(24, 18);
        index += 6;

        // 19
        originalDotsList[index] = new Point(3, 19);
        originalDotsList[index+1] = new Point(6, 19);
        originalDotsList[index+2] = new Point(9, 19);
        originalDotsList[index+3] = new Point(18, 19);
        originalDotsList[index+4] = new Point(21, 19);
        originalDotsList[index+5] = new Point(24, 19);
        index += 6;

        // 20
        for (int i = 1; i <= 12; i++) {
            originalDotsList[index] = new Point(i, 20);
            index++;
        }
        for (int i = 15; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 20);
            index++;
        }

        // 21
        originalDotsList[index] = new Point(1, 21);
        originalDotsList[index+1] = new Point(6, 21);
        originalDotsList[index+2] = new Point(12, 21);
        originalDotsList[index+3] = new Point(15, 21);
        originalDotsList[index+4] = new Point(21, 21);
        originalDotsList[index+5] = new Point(26, 21);
        index += 6;

        // 22
        originalDotsList[index] = new Point(6, 22);
        originalDotsList[index+1] = new Point(12, 22);
        originalDotsList[index+2] = new Point(15, 22);
        originalDotsList[index+3] = new Point(21, 22);
        index += 4;

        // 23
        for (int i = 1; i <= 3; i++) {
            originalDotsList[index] = new Point(i, 23);
            index++;
        }
        for (int i = 6; i <= 21; i++) {
            originalDotsList[index] = new Point(i, 23);
            index++;
        }
        for (int i = 24; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 23);
            index++;
        }

        // 24
        originalDotsList[index] = new Point(3, 24);
        originalDotsList[index+1] = new Point(6, 24);
        originalDotsList[index+2] = new Point(9, 24);
        originalDotsList[index+3] = new Point(18, 24);
        originalDotsList[index+4] = new Point(21, 24);
        originalDotsList[index+5] = new Point(24, 24);
        index += 6;

        // 25
        originalDotsList[index] = new Point(3, 25);
        originalDotsList[index+1] = new Point(6, 25);
        originalDotsList[index+2] = new Point(9, 25);
        originalDotsList[index+3] = new Point(18, 25);
        originalDotsList[index+4] = new Point(21, 25);
        originalDotsList[index+5] = new Point(24, 25);
        index += 6;

        // 26
        for (int i = 1; i <= 6; i++) {
            originalDotsList[index] = new Point(i, 26);
            index++;
        }
        for (int i = 9; i <= 12; i++) {
            originalDotsList[index] = new Point(i, 26);
            index++;
        }
        for (int i = 15; i <= 18; i++) {
            originalDotsList[index] = new Point(i, 26);
            index++;
        }
        for (int i = 21; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 26);
            index++;
        }

        // 27
        originalDotsList[index] = new Point(1, 27);
        originalDotsList[index+1] = new Point(12, 27);
        originalDotsList[index+2] = new Point(15, 27);
        originalDotsList[index+3] = new Point(26, 27);
        index += 4;

        // 28
        originalDotsList[index] = new Point(1, 28);
        originalDotsList[index+1] = new Point(12, 28);
        originalDotsList[index+2] = new Point(15, 28);
        originalDotsList[index+3] = new Point(26, 28);
        index += 4;

        // 29
        for (int i = 1; i <= 26; i++) {
            originalDotsList[index] = new Point(i, 29);
            index++;
        }

        dotsList = originalDotsList.clone();
    }

    private void createGums() {
        originalGumsList[0] = new Point(1, 3);
        originalGumsList[1] = new Point(26, 3);
        originalGumsList[2] = new Point(1, 22);
        originalGumsList[3] = new Point(26, 22);

        gumsList = originalGumsList.clone();
    }

    public void addNewDot(int id) {
        if (0 < id && id < dotsList.length) {
            dotsList[id] = originalDotsList[id];
        }
    }

    public void addNewGum(int id) {
        if (0 < id && id < gumsList.length) {
            gumsList[id] = originalGumsList[id];
        }
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
        if (x < 0 || x >= mapWidth
                || y < 0 || y >= mapHeight) {
            return 1;
        }
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
        if (0 < dotID && dotID < dotsList.length) {
            dotsList[dotID] = null;
        }
    }

    public void setGumEated(int gumID) {
        if (0 < gumID && gumID < dotsList.length) {
            gumsList[gumID] = null;
        }
    }

    public void update() {
        getLocalPlayer().update();

    }
}
