package sk.uniza.fri.accessories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Trieda Bullets
 * reprezentuje inštanciu nábojov
 **/
public class Bullets {
    private int bullets;
    private final Texture ammunitionTexture = new Texture("Items/Bullet.png");

    /**
     * Konštruktor triedy Bullets
     * vytvorí inštanciu nábojov podľa vstupu
     **/
    public Bullets(int bullets) {
        this.bullets = bullets;
    }

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie všetkých náboj na hernej ploche vo forme strely (vpravo dole)
     **/
    public void draw(SpriteBatch batch) {
        int n = 0;
        while (n < this.bullets) {
            batch.draw(this.ammunitionTexture, 1270 - (n * 10), 5);     // 10 kvoli medzere medzi texturami
            n++;
        }
    }

    /**
     * Getter na náboje
     *@return vráti počet disponibilných nábojov
     **/
    public int getBullets() {
        return this.bullets;
    }

    /**
     * metóda pickedUpAmmo
     * zvyši počet nábojov hráča podľa vstupu, pričom maximálny počet je 82
     **/
    public void pickedUpAmmo(int howMuch) {
        this.bullets += howMuch;
        if (this.bullets >= 82) {
            this.bullets = 82;
        }
    }

    /**
     * metóda fireBullet
     * po výstrele zníži počet nábojov o jeden
     **/
    public void fireBullet() {
        this.bullets--;
    }
}


