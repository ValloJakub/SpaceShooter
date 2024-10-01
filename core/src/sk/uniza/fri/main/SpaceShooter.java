package sk.uniza.fri.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Trieda SpaceShooter
 * zabezpečuje vytvorenie inštancie hernej plochy a ozvučenie
 **/
public class SpaceShooter extends Game {
    private GameDisplay display;
    private Music music;

    /**
     * metóda create
     * spúšťa hudbu, inicializuje herný displej
     **/
    // hlavna trieda - spustac
    @Override
    public void create() {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("Audio/DuelOfTheFates.mp3"));
        this.music.setLooping(true);
        this.music.setVolume(0.1f);
        this.music.play();

        this.display = new GameDisplay();
        setScreen(this.display);
    }

    /**
     * metóda render
     * zabezpečuje priebeh hry
     **/
    @Override
    public void render () {
        super.render();
    }

    /**
     * metóda dispose
     * ukončí hru a zruší hudbu
     **/
    @Override
    public void dispose () {
        this.display.dispose();
        this.music.stop();
    }
}
