package sk.uniza.fri.replenishments;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.ships.PlayerShip;

/**
 * Trieda Heal
 * reprezentuje heal na doplnenie hráčových životov
 * rozširuje triedu GameObject
 **/
public class Heal extends GameObjects {

    /**
     * Konštruktor triedy Heal
     * vytvorí heal a priradí mu textúru
     **/
    public Heal() {
        super(new Texture("Health/Heal.png"));
    }

    /**
     * metóda boostUp
     * doplní hráčovi jeden život a pridá na obrazovku srdiečko
     **/
    @Override
    public void boostUp(PlayerShip playerShip) {
        playerShip.addHp(); // zivot
        playerShip.addHeart();  //  srdiecko
    }
}
