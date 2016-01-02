package pacman.model;

/**
 * Created by akkes on 02/01/2016.
 */
public class LocalPlayer extends Player {

    public LocalPlayer(Game game) {
        super(game);
    }

    @Override
    public void update() {
        super.update();

        // Eating (gums/dots/players)
        game.eat(posX + Game.unitsPerSquare/2, posY + Game.unitsPerSquare/2);
    }
}
