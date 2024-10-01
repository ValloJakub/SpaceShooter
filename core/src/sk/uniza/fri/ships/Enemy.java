package sk.uniza.fri.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * abstraktná trieda Enemy
 * reprezentuje nepriateľa
 * implementuje rozhranie IMovement
 * rozširuje triedu SpaceShip
 *
 **/
public abstract class Enemy extends SpaceShip implements IMovement {
    private static final int LEFT_LIMIT = 0;
    private static final int RIGHT_LIMIT = Gdx.graphics.getWidth() - 80;
    private static final int UPPER_LIMIT = 0;
    private static final int LOWER_LIMIT = Gdx.graphics.getHeight() - 320;

    /**
     * Konštruktor triedy Enemy
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param texture, hitpoints, positionX, positionY, speed,
     **/
    public Enemy(Texture texture, int hitpoints, float positionX, float positionY, float speed) {
        super(texture, hitpoints, positionX, positionY, speed);
    }

    /**
     * metóda moveUp
     * na základe podmienky posunie nepriateľa smerom nahor
     **/
    @Override
    public void moveUp() {
        if (this.getYposition() > UPPER_LIMIT) {
            this.moveTo(0, this.getSpeed());
        }
    }

    /**
     * metóda moveUp
     * na základe podmienky posunie nepriateľa smerom nadol
     **/
    @Override
    public void moveDown() {
        if (this.getYposition() > LOWER_LIMIT) {
            this.moveTo(0, -this.getSpeed());
        }
    }

    /**
     * metóda moveUp
     * na základe podmienky posunie nepriateľa smerom vľavo
     **/
    @Override
    public void moveLeft() {
        if (this.getXposition() > LEFT_LIMIT) {
            this.moveTo(-this.getSpeed(), 0);
        }
    }

    /**
     * metóda moveUp
     * na základe podmienky posunie nepriateľa smerom vpravo
     **/
    @Override
    public void moveRight() {
        if (this.getXposition() < RIGHT_LIMIT) {
            this.moveTo(this.getSpeed(), 0);
        }
    }

    /**
     * abstraktná metóda fired
     **/
    public abstract boolean fired();
}
