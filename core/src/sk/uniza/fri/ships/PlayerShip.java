package sk.uniza.fri.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.accessories.Bullets;
import sk.uniza.fri.accessories.Health;

import java.util.Random;

/**
 * Trieda PlayerShip
 * reprezentuje hráčovu vesmírnu loď
 * implementuje rozhranie IMovement
 * rozširuje triedu SpaceShip
 *
 **/
public class PlayerShip extends SpaceShip  implements IMovement {

    private static final int LEFT_LIMIT = 0;
    private static final int RIGHT_LIMIT = Gdx.graphics.getWidth() - 80;
    private static final int UPPER_LIMIT = Gdx.graphics.getHeight()  - 420;
    private static final int LOWER_LIMIT = 0;

    private boolean invulnerable = false;
    private boolean speedChanged = false;
    private int hitsToAbsorb = 0;

    private final Health health;
    private final Bullets bullets;

    /**
     * Konštruktor triedy PlayerShip
     * inicializuje premenné
     * vytvorí vesmírnu loď na danej pozícii s pridelenou textúrou, životmi a rýchlosťou
     * @param hitpoints, positionX, positionY, speed, bullets
     **/
    public PlayerShip(int hitpoints, float positionX, float positionY, float speed, int bullets) {
        super(new Texture("SpaceShips/PlayerShip.png"), hitpoints, positionX, positionY, speed);
        this.bullets = new Bullets(bullets);
        this.health = new Health(hitpoints);
    }

    /**
     * metóda moveUp
     * na základe podmienky posunie hráča smerom nahor
     **/
    @Override
    public void moveUp() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && this.getYposition() <= UPPER_LIMIT) {
            this.moveTo(0, this.getSpeed());
        }
    }

    /**
     * metóda moveDown
     * na základe podmienky posunie hráča smerom nadol
     **/
    @Override
    public void moveDown() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && this.getYposition() >= LOWER_LIMIT) {
            this.moveTo(0, -this.getSpeed());
        }
    }

    /**
     * metóda moveLeft
     * na základe podmienky posunie hráča smerom vľavo
     **/
    @Override
    public void moveLeft() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.getXposition() >= LEFT_LIMIT) {
            this.moveTo(-this.getSpeed(), 0);
        }
    }

    /**
     * metóda moveRight
     * na základe podmienky posunie hráča smerom vpravo
     **/
    @Override
    public void moveRight() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.getXposition() <= RIGHT_LIMIT) {
            this.moveTo(this.getSpeed(), 0);
        }
    }

    /**
     * metóda didPlayerFire
     * vráti true ak hráč stlačil kláves medzerník
     **/
    public boolean didPlayerFire() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);    // true ak stlacim medzernik
    }

    /**
     * metóda isInvulnerable
     * vráti true ak hráč má vlastnosť invulnerable(nedotknuteľnosť)
     **/
    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    /**
     * metóda setInvulnerabilitt
     * na základe podmienky nastaví hráčovi nedotknuteľnosť
     * @param invulnerable
     **/
    public void setInvulnerability(boolean invulnerable) {
        if (invulnerable) {
            this.hitsToAbsorb = 5;
        }
        this.invulnerable = invulnerable;
    }

    /**
     * metóda addBullets
     * prída hráčovi náhodny počet nábojov (od 10 po 30)
     **/
    public void addBullets() {
        Random random = new Random();
        this.bullets.pickedUpAmmo(random.nextInt(20) + 10);    // nahodny pocet od 10 do 30
    }

    /**
     * metóda overLoadEngine
     * preťaženie motora
     * spomalý hráča
     **/
    public void overLoadEngine() {
        this.speedChanged = true;
        this.setSpeed(3.0f); // spomali hraca o polovicu a aj jeho strelu
    }

    /**
     * metóda hyperSpeed
     * hyper rýchlosť
     * zrýchly hráča
     **/
    public void hyperSpeed() {
        this.speedChanged = true;
        this.setSpeed(10.0f); // zrychli hraca o 2 tretiny a aj jeho strelu
    }

    /**
     * metóda backToNormal
     * vráti rýchlosť hráča na pôvodnú hodnotu
     **/
    public void backToNormal() {
        this.speedChanged = false;
        this.setSpeed(6.0f);
    }

    /**
     * metóda isSpeedChanged
     * vráti true ak hráčova rýchlosť bola zmenená
     *
     **/
    public boolean isSpeedChanged() {
        return this.speedChanged;
    }

    /**
     * metóda getBulletCount
     * vráti počet ostávajúcich náboj hráča
     **/
    public int getBulletCount() {
        return this.bullets.getBullets();
    }

    /**
     * metóda getBullets
     * vráti referenciu na triedu Bullets
     **/
    public Bullets getBullets() {
        return this.bullets;
    }

    /**
     * metóda getHitsToAbsorb
     * vráti počet, koľko môže hráč ešte absorbovať striel (výdrž štítu)
     **/
    public int getHitsToAbsorb() {
        return this.hitsToAbsorb;
    }


    /**
     * metóda lowerAbsorbtion
     * zníži počet striel, ktoré môže hráč absorbovať
     **/
    public void lowerAbsorbtion() {
        this.hitsToAbsorb--;
    }

    /**
     * metóda getHealth
     * vráti referenciu na triedu Health
     **/
    public Health getHealth() {
        return this.health;
    }

    /**
     * metóda addHeart
     * pridá srdiečko na obrazovku, reprezentujúce počet ostávajúcich životov
     **/
    public void addHeart() {    // na pridanie srdiecka na vykreslenie
        this.health.addHp();
    }

    /**
     * metóda lowerHeart
     * odoberie srdiečko z obrazovky, reprezentujúce počet ostávajúcich životov
     **/
    public void lowerHeart() {    // na odobratie srdiecka na vykreslenie
        this.health.lowerHp();
    }
}


