package sk.uniza.fri.replenishments;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import sk.uniza.fri.ships.PlayerShip;

import java.util.Random;

/**
 * abstraktná trieda GameObjects
 * reprezentuje herné objekty
 *
 **/
public abstract class GameObjects {
    private final Texture texture;

    private final float x;
    private float y;

    /**
     * Konštruktor triedy GameObjects
     * vytvorí herný objekt na náhodnej pozícii s textúrou
     *
     **/
    public GameObjects(Texture texture) {
        this.texture = texture;
        Random random = new Random();
        this.x = random.nextInt(1080) + 100;
        this.y = Gdx.graphics.getHeight();
    }

    /**
     * Getter na šírku
     *@return vráti šírku textúry
     **/
    public float getWidth() {
        return this.texture.getWidth();
    }

    /**
     * Getter na výšku
     *@return vráti výšku textúry
     **/
    public float getHeight() {
        return this.texture.getHeight();
    }


    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie vytvoreného herného objektu
     * vytvorí objekt na danej pozícii s pridelenou textúrou
     *
     **/
    public void draw(Batch batch) {
        batch.draw(this.texture, this.x, this.y, this.texture.getWidth(), this.texture.getHeight());
    }

    /**
     * metóda move
     * metóda slúžiaca na pohyb objektu po hernej ploche
     *
     **/
    public void move() {
        this.y -= 3;
    }

    /**
     * metóda wasPickedUp
     * @return true ak hráč zdvihol (mal kolíziu)  herný objekt
     *
     **/
    public boolean wasPickedUp(PlayerShip playerShip) {
        return this.x <= playerShip.getXposition() + playerShip.getWidth() && this.y <= playerShip.getYposition() + playerShip.getHeight() && this.x + this.getWidth() >= playerShip.getXposition() &&
                this.y + this.getHeight() >= playerShip.getYposition();
    }

    /**
     * abstraktná metóda boostUp
     * priradí hráčovi buff (alebo debuff)
     *
     **/
    public abstract void boostUp(PlayerShip playerShip);
}
