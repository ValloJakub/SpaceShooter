package sk.uniza.fri.replenishments;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.ships.PlayerShip;

/**
 * Trieda OverLoad
 * reprezentuje overLoad na spomalenie hráča
 * rozširuje triedu GameObject
 **/
public class OverLoad extends GameObjects {


    /**
     * Konštruktor triedy OverLoad
     * vytvorí overLoad a priradí mu textúru
     **/
    public OverLoad() {
        super(new Texture("Items/overLoadEngine.png"));
    }

    /**
     * metóda boostUp
     * preťaži hráčovi motor, čím ho spomalí
     **/
    @Override
    public void boostUp(PlayerShip playerShip) {
        playerShip.overLoadEngine();
    }
}
