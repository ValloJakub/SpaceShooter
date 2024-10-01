package sk.uniza.fri.replenishments;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.ships.PlayerShip;

/**
 * Trieda Reload
 * reprezentuje počet disponibilných nábooj hráča
 * rozširuje triedu GameObject
 **/
public class Reload extends GameObjects {

    /**
     * Konštruktor triedy Reload
     * vytvorí reload a priradí mu textúru
     **/
    public Reload() {
        super(new Texture("Items/Ammunition.png"));
    }

    /**
     * metóda boostUp
     * doplní hráčovi náhodný počet nábojov
     **/
    @Override
    public void boostUp(PlayerShip playerShip) {
        playerShip.addBullets();
    }
}
