package pacman.model;

/**
 * Created by akkes on 31/12/2015.
 */

public class Player {
    protected int posX;
    protected int posY;
    private int movementX;
    private int movementY;
    private int nextDirection;
    private int power;
    protected Game game;

    public Player(Game game) {
        this.game = game;
        reset();
    }

    public void reset() {
        posX = game.getSpawnX();
        posY = game.getSpawnY();
        movementX = 1;
        movementY = 0;
        nextDirection = -1;
        power = 0;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getMovementX() {
        return movementX;
    }

    public int getMovementY() {
        return movementY;
    }

    public void setDirection(int direction) {
        nextDirection = direction;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void update() {
        // change direction if possible
        switch (nextDirection) {
            case 0:
                if (1 != game.getUnit(posX, posY - 1)
                        && 1 != game.getUnit(posX + Game.unitsPerSquare-1, posY - 1)) {
                    movementX = 0;
                    movementY = -1;
                }
                break;
            case 1:
                if (1 != game.getUnit(posX + Game.unitsPerSquare-1 + 1, posY)
                        && 1 != game.getUnit(posX + Game.unitsPerSquare-1 + 1, posY + Game.unitsPerSquare-1)) {
                    movementX = 1;
                    movementY = 0;
                }
                break;
            case 2:
                if (1 != game.getUnit(posX, posY + Game.unitsPerSquare-1 + 1)
                        && 1 != game.getUnit(posX + Game.unitsPerSquare-1, posY + Game.unitsPerSquare-1 + 1)) {
                    movementX = 0;
                    movementY = 1;
                }
                break;
            case 3:
                if (1 != game.getUnit(posX - 1, posY)
                        && 1 != game.getUnit(posX - 1, posY + Game.unitsPerSquare-1)) {
                    movementX = -1;
                    movementY = 0;
                }
                break;
        }

        // Calculate possible new position
        int newX = posX + movementX;
        int newY = posY + movementY;

        // is the player authorized to move here?
        // On the X axis
        if (1 == movementX) {
            if (1 != game.getUnit(newX + Game.unitsPerSquare-1, newY)
                    && 1 != game.getUnit(newX + Game.unitsPerSquare-1, newY + Game.unitsPerSquare-1)) {
                posX = newX;
            }
        } else if (-1 == movementX) {
            if (1 != game.getUnit(newX, newY)
                    && 1 != game.getUnit(newX, newY + Game.unitsPerSquare-1)) {
                posX = newX;
            }
        }

        // On the Y axis
        if (1 == movementY) {
            if (1 != game.getUnit(newX, newY + Game.unitsPerSquare-1)
                    && 1 != game.getUnit(newX + Game.unitsPerSquare-1, newY + Game.unitsPerSquare-1)) {
                posY = newY;
            }
        } else if (-1 == movementY) {
            if (1 != game.getUnit(newX, newY)
                    && 1 != game.getUnit(newX + Game.unitsPerSquare-1, newY)) {
                posY = newY;
            }
        }
    }
}
