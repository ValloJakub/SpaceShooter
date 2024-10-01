package sk.uniza.fri.accessories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;


/**
 * Trieda Meteor
 * reprezentuje inštanciu meteoritu
 *
 **/
public class Meteor {
    private int positionX;
    private int positionY;
    private Texture meteorTexture;


    /**
     * Konštruktor triedy Meteor
     * pri vytvorení zavolá metódu createRandomMeteor
     **/
    public Meteor() {
        this.createRandomMeteor();  // vytvori meteorit na nahodne x-ovej suradnici a prideli mu jeden z 3 obrazkov
    }

    /**
     * metóda createRandomMeteor
     * na náhodnej x-ovej pozícii výtvorí meteorit
     **/
    public void createRandomMeteor() {
        Random random = new Random();
        this.positionX = random.nextInt(1080) + 100;
        this.positionY = Gdx.graphics.getHeight() + 100;
        this.meteorTexture = new Texture("Meteors/" + "meteor" + (random.nextInt(6) + 1) + ".png");
    }

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie meteoritu
     **/
    public void draw(Batch batch) {
        batch.draw(this.meteorTexture, this.positionX, this.positionY); // vykreslenie meteoritu
    }

    /**
     * metóda fly
     * zabezpečuje pohyb(let) meteoritu zhora nadol
     **/
    public void fly() {
        this.changePosition(0, -2);  // pohybuje sa smerom dole rychlostou 2
    }

    /**
     * metóda changePosition
     * metóda slúžiaca na zmenu polohy vzhľadom na parametre
     **/
    public void changePosition(float x, float y) {
        this.positionX += x;
        this.positionY += y;
    }

    /**
     * Getter na súradnicu x
     * vráti pozíciu súradnice x
     **/
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Getter na súradnicu y
     * vráti pozíciu súradnice y
     **/
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * Getter na šírku
     * vráti šírku textúry
     **/
    public int getWidth() {
        return this.meteorTexture.getWidth();
    }

    /**
     * Getter na výšku
     * vráti výšku textúry
     **/
    public int getHeight() {
        return this.meteorTexture.getHeight();
    }
}
