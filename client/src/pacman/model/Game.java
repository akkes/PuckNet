package pacman.model;

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
    private int mapWidth = 16;
    private int mapHeight = 16;
    private final short donneesCarte[] = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1,
        1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1,
        1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1,
        1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1,
        1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,
        1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1,
        1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1,
        1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 1,
        1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };

    private ArrayList<Point> originalDotsList = new ArrayList<>();
    private ArrayList<Point> originalGumsList = new ArrayList<>();

    private ArrayList<Point> dotsList;
    private ArrayList<Point> gumsList;

    private int localPlayerID = 0;
    private Player[] players = new Player[5];

    public Game() {
        players[0] = new Player(this);

        // Gums list
        originalGumsList.add(new Point(1, 3));

        gumsList = (ArrayList<Point>) originalGumsList.clone();

        // Dots list
        for (int i = 1; i <= 12; i++) {
            originalDotsList.add(new Point(i, 1));
        }
        for (int i = 2; i <= 14; i++) {
            originalDotsList.add(new Point(6, i));
        }
        originalDotsList.add(new Point(1, 2));

        dotsList = (ArrayList<Point>) originalDotsList.clone();
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayer(int id, Player player) {
        players[id] = player;
    }

    public int getLocalPlayerID() {
        return localPlayerID;
    }

    public Player getLocalPlayer() {
        return players[localPlayerID];
    }

    public void setLocalPlayerID(int localPlayerID) {
        this.localPlayerID = localPlayerID;
    }

    public short[] getDonneesCarte() {
        return donneesCarte;
    }

    public ArrayList<Point> getDotsList() {
        return dotsList;
    }

    public ArrayList<Point> getGumsList() {
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

        for (int i = 0; i < dotsList.size(); i++) {
            if (dotsList.get(i).equals(eaten)) {
                dotsList.remove(i);
                //TODO: tell server
            }
        }

        for (int i = 0; i < gumsList.size(); i++) {
            if (gumsList.get(i).equals(eaten)) {
                gumsList.remove(i);
                //TODO: tell server
            }
        }
    }

    public void update() {
        getLocalPlayer().update();

    }
}
