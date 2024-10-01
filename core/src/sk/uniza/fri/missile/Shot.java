package sk.uniza.fri.missile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.accessories.Meteor;


/**
 * Trieda Shot
 * reprezentuje inštanciu strely
 **/
public class Shot {
    private final Texture missileTexture;
    private final float positionX;
    private float positionY;

    /** Konštruktor triedy Shot
     * vytvorí strelu na danej pozícii a priradí mu textúru podľa toho, kto vystrelil
     *
     **/
    public Shot(boolean whoShot, float x, float y) {   // enemy false, hrac true
        Music music;
        if (whoShot) {
            this.missileTexture = new Texture("Missiles/AllyBeam.png");
            music = Gdx.audio.newMusic(Gdx.files.internal("Audio/playerShot.wav"));
            music.setLooping(false);
            music.setVolume(0.4f);
        } else {
            this.missileTexture = new Texture("Missiles/EnemyBeam.png");
            music = Gdx.audio.newMusic(Gdx.files.internal("Audio/playerShot.mp3"));
            music.setLooping(false);
            music.setVolume(0.4f);
        }
        music.play();

        this.positionX = x;
        this.positionY = y;
    }

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie náboja
     **/
    public void draw(SpriteBatch batch) {
        batch.draw(this.missileTexture, this.positionX, this.positionY);    // vykreslenie strely
    }

    /**
     * Getter na súradnicu X
     *@return vráti pozíciu súradnice X
     **/
    public float getPositionX() {
        return this.positionX;
    }

    /**
     * Getter na súradnicu Y
     *@return vráti pozíciu súradnice Y
     **/
    public float getPositionY() {
        return this.positionY;
    }

    /**
     * Getter na šírku
     *@return vráti šírku textúry
     **/
    public float getWidth() {
        return this.missileTexture.getWidth();
    }

    /**
     * Getter na výšku
     *@return vráti výšku textúry
     **/
    public float getHeight() {
        return this.missileTexture.getHeight();
    }

    /**
     * metóda fire
     * posunie strelu daným smerom podľa vstupu
     **/
    public void fire(float yChange) {  // na zmenu suradnice (pohyb)
        this.positionY += yChange * 2;
    }

    /**
     * metóda collidedWithMeteor
     * kontroluje, či nastala kolízia strely s meteoritom
     **/
    public boolean collidedWithMeteor(Meteor meteor) {
        return this.getPositionX() <= meteor.getPositionX() + meteor.getWidth() && this.getPositionY() <= meteor.getPositionY() + meteor.getHeight() && this.getPositionX() + this.getWidth() >= meteor.getPositionX() &&
                this.getPositionY() + this.getHeight() >= meteor.getPositionY();
    }
}

