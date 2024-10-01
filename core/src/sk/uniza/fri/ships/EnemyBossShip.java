package sk.uniza.fri.ships;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Trieda EnemyBossShip
 * reprezentuje nepriateľského bossa
 * rozširuje triedu Enemy
 **/
public class EnemyBossShip extends Enemy {

    /**
     * Konštruktor triedy EnemyBossShip
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param hitpoints, positionX, positionY, speed,
     **/
    public EnemyBossShip(int hitpoints, float positionX, float positionY, float speed) {
        super(new Texture("SpaceShips/EnemyBoss.png"), hitpoints, positionX, positionY, speed);
    }

    /**
     * metóda fired
     * vráti true na základe podmienky (výstrel)
     **/
    @Override
    public boolean fired() {
        Random random = new Random();
        return random.nextInt(35) == 1;
    }
}
