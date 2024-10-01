package sk.uniza.fri.accessories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import sk.uniza.fri.ships.PlayerShip;

/**
 * Trieda ShieldEffect
 * reprezentuje efekt (textúru) štítu
 *
 **/
public class ShieldEffect {
    private final Texture shieldEffect = new Texture("Items/ShieldEffect.png");

    /**
     * metóda draw
     * metóda slúžiaca na vykreslenie štítu, ak hráč prevzial buff nedotknuteľnosti
     **/
    public void draw(Batch batch, PlayerShip playerShip) {
        batch.draw(this.shieldEffect, playerShip.getXposition() - 5, playerShip.getYposition());    // 5 je odchylka
    }
}
