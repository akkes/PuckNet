package pacman.model;

/**
 * Created by akkes on 31/12/2015.
 */

public class Player {
    protected int oldX;
    protected int oldY;
    protected int posX;
    protected int posY;
    private int movementX;
    private int movementY;
    private int nextDirection;
    private int power;
    private int powerTime;
    private int score;
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
        powerTime = 0;
        score = 0;
    }

    public void resetScore() {
        score = 0;
    }

    public void addScore(int n) {
        score += n;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getDirection() {
        int diffX = posX - oldX;
        int diffY = posY - oldY;

        if (diffY > 0) {
            return 2;
        } else if (diffY < 0){
            return 0;
        } else if (diffX > 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public void setPosX(int posX) {
        this.oldY = this.posY;
        this.oldX = this.posX;
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.oldX = this.posX;
        this.oldY = this.posY;
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

    public int getPowerTime() {
        return powerTime;
    }

    public void setPower(int power) {
        this.power = power;
        if (1 == power) {
            powerTime = 10 * 60;
        }
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
                setPosX(newX);
            }
        } else if (-1 == movementX) {
            if (1 != game.getUnit(newX, newY)
                    && 1 != game.getUnit(newX, newY + Game.unitsPerSquare-1)) {
                setPosX(newX);
            }
        }

        // On the Y axis
        if (1 == movementY) {
            if (1 != game.getUnit(newX, newY + Game.unitsPerSquare-1)
                    && 1 != game.getUnit(newX + Game.unitsPerSquare-1, newY + Game.unitsPerSquare-1)) {
                setPosY(newY);
            }
        } else if (-1 == movementY) {
            if (1 != game.getUnit(newX, newY)
                    && 1 != game.getUnit(newX + Game.unitsPerSquare-1, newY)) {
                setPosY(newY);
            }
        }

        if (1 == getPower()) {
            powerTime--;
        }
    }
}
