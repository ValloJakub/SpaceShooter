package sk.uniza.fri.ships;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Trieda EnemyMediumShip
 * reprezentuje nepriateľskú strednú loď
 * rozširuje triedu Enemy
 **/
public class EnemyMediumShip extends Enemy {

    /**
     * Konštruktor triedy EnemyMediumShip
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param hitpoints, positionX, positionY, speed,
     **/
    public EnemyMediumShip(int hitpoints, float positionX, float positionY, float speed) {
        super(new Texture("SpaceShips/EnemyMedium.png"), hitpoints, positionX, positionY, speed);
    }

    /**
     * metóda fired
     * vráti true na základe podmienky (výstrel)
     **/
    @Override
    public boolean fired() {
        Random random = new Random();
        return random.nextInt(50) == 1;
    }
}
