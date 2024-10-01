package sk.uniza.fri.accessories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Trieda Health
 * reprezentuje inštanciu životov
 *
 **/
public class Health {
    private int hitPoints;
    private final Texture healthTexture = new Texture("Health/heart.png");

    /**
     * Konštruktor triedy Health
     * vytvorí inštanciu životov podľa vstupu
     **/
    public Health(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * metóda lowerHp
     * zníži počet životov
     **/
    public void lowerHp() {
        this.hitPoints--;
    }

    /**
     * metóda addHp
     * zvýši počet životov, pričom maximálny počet môže byť 15
     **/
    public void addHp() {
        this.hitPoints++;
        if (this.hitPoints >= 15) {
            this.hitPoints = 15;
        }
    }

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie všetkých životov na hernej ploche vo forme srdiečok (vľavo dole)
     **/
    public void draw(SpriteBatch batch) {
        int n = 0;
        while (n < this.hitPoints) {
            batch.draw(this.healthTexture, 5 + (n * 30), 5); // 30 kvoli medzere medzi texturami
            n++;
        }
    }
}
