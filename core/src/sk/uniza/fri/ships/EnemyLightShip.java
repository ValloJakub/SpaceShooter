package sk.uniza.fri.ships;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Trieda EnemyLightShip
 * reprezentuje nepriateľskú ľahkú loď
 * rozširuje triedu Enemy
 **/
public class EnemyLightShip extends Enemy {

    /**
     * Konštruktor triedy EnemyLightShip
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param hitpoints, positionX, positionY, speed,
     **/
    public EnemyLightShip(int hitpoints, float positionX, float positionY, float speed) {
        super(new Texture("SpaceShips/EnemyLight.png"), hitpoints, positionX, positionY, speed);
    }

    /**
     * metóda fired
     * vráti true na základe podmienky (výstrel)
     **/
    @Override
    public boolean fired() {
        Random random = new Random();
        return random.nextInt(60) == 1;
    }
}
