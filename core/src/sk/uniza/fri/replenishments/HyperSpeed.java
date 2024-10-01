package sk.uniza.fri.replenishments;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.ships.PlayerShip;

/**
 * Trieda HyperSpeed
 * reprezentuje hyperSpeed na zrýchlenie hráča
 * rozširuje triedu GameObject
 **/
public class HyperSpeed extends GameObjects {

    /**
     * Konštruktor triedy HyperSpeed
     * vytvorí hyperSpeed a priradí mu textúru
     **/
    public HyperSpeed() {
        super(new Texture("Items/hyperSpeed.png"));
    }

    /**
     * metóda boostUp
     * zrýchli hráča
     **/
    @Override
    public void boostUp(PlayerShip playerShip) {
        playerShip.hyperSpeed();
    }
}
