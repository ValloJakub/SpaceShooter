package sk.uniza.fri.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import sk.uniza.fri.accessories.Meteor;
import sk.uniza.fri.missile.Shot;

import java.util.Random;

/*
 * @author Jakub Vallo
 *
 * @version 4.2.2
 **/

/**
 * abstraktná trieda SpaceShip
 * reprezentuje vesmírnu loď
 *
 **/
public abstract class SpaceShip {
    private final Texture texture;

    private int hp;
    private float speed;

    private float positionX;
    private float positionY;

    /**
     * Konštruktor triedy SpaceShip
     * inicializuje premenné
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param texture, hitpoints, positionX, positionY, speed
     **/
    public SpaceShip(Texture texture, int hitpoints, float positionX, float positionY, float speed) {
        this.hp = hitpoints;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.texture = texture;
    }

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie vytvorenej vesmírnej lode
     **/
    public void draw(Batch batch) {
        batch.draw(this.texture, this.positionX, this.positionY);    // vykreslenie lode
    }

    /**
     * Getter na súradnicu X
     *@return vráti pozíciu súradnice X
     **/
    public float getXposition() {
        return this.positionX;
    }

    /**
     * Getter na súradnicu y
     *@return vráti pozíciu súradnice y
     **/
    public float getYposition() {
        return this.positionY;
    }

    /**
     * Getter na výšku
     *@return vráti výšku textúry
     **/
    public float getHeight() {
        return this.texture.getHeight();
    }

    /**
     * Getter na šírku
     *@return vráti šírku textúry
     **/
    public float getWidth() {
        return this.texture.getWidth();
    }

    /**
     * Getter na rýchlosť
     *@return vráti rýchlosť lode
     **/
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Getter na rýchlosť
     * vráti rýchlosť lode
     **/
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * metóda resetPlayersPosition
     * náhodne vygeneruje novú pozíciu pre hráča
     **/
    public void resetPlayersPosition() {
        Random random = new Random();
        this.positionX = random.nextInt(880) + 200;
        this.positionY = Gdx.graphics.getHeight() / 6f;
    }

    /**
     * metóda resetEnemyPosition
     * náhodne vygeneruje novú pozíciu pre nepriateľa
     **/
    public void resetEnemyPosition() {
        Random random = new Random();
        this.positionX = random.nextInt(1080) + 100;
        this.positionY = Gdx.graphics.getHeight();
    }

    /**
     * metóda lowerHp
     * uberie lodi jeden život
     **/
    public void lowerHp() {
        this.hp--;
    }

    /**
     * metóda addHp
     * pridá lodi jeden život
     **/
    public void addHp() {
        this.hp++;
    }

    /**
     * metóda moveTo
     * posunie loď daným smerom podľa parametrov
     * @param xChange, yChange
     **/
    public void moveTo(float xChange, float yChange) {
        this.positionY += yChange;
        this.positionX += xChange;
    }

    /**
     * metóda collidedWithMeteor
     * kontroluje kolíziu s meteoritom
     **/
    public boolean collidedWithMeteor(Meteor meteor) {
        return this.positionX <= meteor.getPositionX() + meteor.getWidth() && this.positionY <= meteor.getPositionY() + meteor.getHeight() && this.positionX + this.getWidth() >= meteor.getPositionX() &&
                this.getYposition() + this.getHeight() >= meteor.getPositionY();
    }

    /**
     * metóda collidedWithMissile
     * kontroluje kolíziu so strelou
     **/
    public boolean collidedWithMissile(Shot missile) {
        return this.positionX <= missile.getPositionX() + missile.getWidth() && this.positionY <= missile.getPositionY() + missile.getHeight() && this.positionX + this.getWidth() >= missile.getPositionX() &&
                this.getYposition() + this.getHeight() >= missile.getPositionY();
    }

    /**
     * metóda isDestroyed
     * kontroluje, či loď bola zničená
     *
     **/
    public boolean isDestroyed() {
        return this.hp == 0;
    }
}
