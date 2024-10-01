package sk.uniza.fri.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sk.uniza.fri.accessories.ShieldEffect;
import sk.uniza.fri.replenishments.GameObjects;
import sk.uniza.fri.replenishments.Heal;
import sk.uniza.fri.replenishments.Reload;
import sk.uniza.fri.replenishments.Invulnerability;
import sk.uniza.fri.replenishments.OverLoad;
import sk.uniza.fri.replenishments.HyperSpeed;
import sk.uniza.fri.accessories.Meteor;
import sk.uniza.fri.missile.Shot;
import sk.uniza.fri.ships.Enemy;
import sk.uniza.fri.ships.PlayerShip;
import sk.uniza.fri.ships.IMovement;
import sk.uniza.fri.ships.SpaceShip;
import sk.uniza.fri.ships.EnemyBossShip;
import sk.uniza.fri.ships.EnemyMediumShip;
import sk.uniza.fri.ships.EnemyLightShip;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Trieda GameDisplay
 *
 **/
public class GameDisplay extends InputAdapter implements Screen {
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 720;

    private final Camera camera;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final Texture background;

    private final Texture galaxy1;
    private final Texture galaxy2;
    private final Texture galaxy3;
    private final Texture nebula;
    private final Texture comet1;
    private final Texture comet2;

    private float yPositionOfGalaxy2 = 1200;  // prvotna y-ova pozicia
    private float yPositionOfGalaxy1 = 1800;  // prvotna y-ova pozicia
    private float yPositionOfGalaxy3 = 2000;  // prvotna y-ova pozicia
    private float yPositionOfNebula = 3000;   // prvotna y-ova pozicia
    private float yPositionOfComet1 = 2500;   // prvotna y-ova pozicia
    private float yPositionOfComet2 = 2000;   // prvotna y-ova pozicia

    private PlayerShip playerShip;
    private Meteor meteor = new Meteor();

    // cas
    private float time;
    private int seconds;

    // na pocitanie 5 sekund v metode na zrychlenie/spomalenie
    private float countFiveSeconds;

    private float totalTime;

    private int lightShipDestroyed = 0;
    private int mediumShipDestroyed = 0;
    private int bossShipDestroyed = 0;

    private final ShieldEffect shieldEffect;

    private final ArrayList<Enemy> listOfEnemies; // zoznam nepriatelskych lodi
    private final ArrayList<Shot> listOfEnemyMissiles;  // zoznam nepriatelskych striel
    private final ArrayList<Shot> listOfPlayerMissiles;  //  zoznam hracovych striel
    private final ArrayList<GameObjects> listOfGameObjects;   // zoznam hernych objektov (heal, naboje, zrychlenie)

    /**
     * Konštruktor triedy GameDisplay
     * vytvorí všetky inštancie potrebné na spustenie hry
     **/
    public GameDisplay() {
        this.camera = new OrthographicCamera();//
        this.viewport = new StretchViewport(DISPLAY_WIDTH, DISPLAY_HEIGHT, this.camera);
        this.batch = new SpriteBatch();

        this.shieldEffect = new ShieldEffect();

        this.listOfEnemies = new ArrayList<>();
        this.listOfPlayerMissiles = new ArrayList<>();
        this.listOfEnemyMissiles = new ArrayList<>();
        this.listOfGameObjects = new ArrayList<>();

        // cas na zaciatku
        this.time = 0;

        // celkovy herny cas
        this.totalTime = 0;

        // priradenie obrazka pozadia
        this.background = new Texture("space.png");

        // priradenie obrazkov telies
        this.galaxy1 = new Texture("SpaceObjects/galaxy1.png");
        this.galaxy2 = new Texture("SpaceObjects/galaxy2.png");
        this.galaxy3 = new Texture("SpaceObjects/galaxy3.png");
        this.nebula = new Texture("SpaceObjects/nebula.png");
        this.comet1 = new Texture("SpaceObjects/comet1.png");
        this.comet2 = new Texture("SpaceObjects/comet2.png");

        // zavolanie metody na vytvorenie hraca so zasobnikom a zivotmi, ktore ma k dispozicii
        Random random = new Random();
        this.createPlayer(random.nextInt(5) + 4, 6.0f, random.nextInt(30) + 20);
    }

    /**
     * súkromná metóda renderBackground
     * slúži na vykreslenie a pohyb estetických objektov ako sú galaxie, kométy a pod
     * vykresluje aj herné pozadie - vesmír
     **/
    private void renderBackground() {       // vykreslovanie pozadia (vesmir s objektami)
        this.batch.draw(this.background, 0, 0); // vykreslenie pozadia
        // vykreslenie prvej galaxie
        this.yPositionOfGalaxy1--;
        if (this.yPositionOfGalaxy1 < -200) {
            this.yPositionOfGalaxy1 = 1800;
        }
        this.batch.draw(this.galaxy1, 50, this.yPositionOfGalaxy1, 90, 75);

        // vykreslenie druhej galaxie
        this.yPositionOfGalaxy2 -= 0.6;
        if (this.yPositionOfGalaxy2 < -200) {
            this.yPositionOfGalaxy2 = 1200;
        }
        this.batch.draw(this.galaxy2, 320, this.yPositionOfGalaxy2, 130, 100);

        // vykreslenie tretej galaxie
        this.yPositionOfGalaxy3 -= 0.9;
        if (this.yPositionOfGalaxy3 < -200) {
            this.yPositionOfGalaxy3 = 2000;
        }
        this.batch.draw(this.galaxy3, 800, this.yPositionOfGalaxy3, 100, 80);

        // vykreslenie vesmirnej hmloviny
        this.yPositionOfNebula -= 0.8;
        if (this.yPositionOfNebula < -650) {
            this.yPositionOfNebula = 3000;
        }
        this.batch.draw(this.nebula, 350, this.yPositionOfNebula, 800, 600);

        // vykreslenie prvej komety
        this.yPositionOfComet1 -= 0.5;
        if (this.yPositionOfComet1 < -200) {
            this.yPositionOfComet1 = 2500;
        }
        this.batch.draw(this.comet1, 300, this.yPositionOfComet1, 50, 90);

        // vykreslenie druhej komety
        this.yPositionOfComet2 -= 0.8;
        if (this.yPositionOfComet2 < -200) {
            this.yPositionOfComet2 = 2000;
        }
        this.batch.draw(this.comet2, 1000, this.yPositionOfComet2, 50, 100);
    }

    @Override
    public void show() {
    }

    /**
     * metóda render
     * zaisťuje a kontroluje priebeh celej hry
     * vykresľuje pozadie s objektami
     * počíta herný čas
     * vykresľuje všetky lode - hráča aj nepriateľa
     * vykresľuje herné objekty, ktoré môže hráč vziať
     * zabezpečuje pohyb všetkých elementov
     **/
    @Override
    public void render(float delta) {   // mozog hry; pohyby hraca a nepriatelov
        this.batch.begin();

        // vykreslenie pozadia s komponentami
        this.renderBackground();

        // pripocitavanie casu a konverzia na sekundy
        this.time += Gdx.graphics.getDeltaTime();
        this.seconds = (int)this.time;

        //pripocitavanie celkoveho casu
        this.totalTime += Gdx.graphics.getDeltaTime();

        // generuj nepriatelov (kazde 2 sekundy)
        this.generateEnemies();

        // vykreslenie hracovej lode
        this.playerShip.draw(batch);

        // vykreslenie nepriatelskych lodi v zozname
        for (SpaceShip enemy : this.listOfEnemies) {
            enemy.draw(batch);
        }

        // vykreslenie striel hraca
        for (Shot missile : this.listOfPlayerMissiles) {
            missile.draw(batch);
        }

        // vykreslenie striel nepriatela
        for (Shot missile : this.listOfEnemyMissiles) {
            missile.draw(batch);
        }

        // vykreslenie hernych objektov
        for (GameObjects gameObject : this.listOfGameObjects) {
            gameObject.draw(batch);
        }

        // vykreslenie hracovych zivotov
        this.playerShip.getHealth().draw(batch);

        // vykreslenie hracovych nabojov
        this.playerShip.getBullets().draw(batch);

        //pohyb hracovej lode
        this.playerShip.moveUp();
        this.playerShip.moveDown();
        this.playerShip.moveLeft();
        this.playerShip.moveRight();

        // ak hrac stlaci medzernik, lod vystreli
        if (this.playerShip.getBulletCount() > 0) {
            if (this.playerShip.didPlayerFire()) {
                this.listOfPlayerMissiles.add(new Shot(true, this.playerShip.getXposition() + this.playerShip.getWidth() / 2, this.playerShip.getYposition() + this.playerShip.getHeight()));
                this.playerShip.getBullets().fireBullet();
            }
        }

        // strielanie nepriatelskych lodi; ak sa sanca vyhodnoti ako true, vytvori novu strelu a prida do zoznamu
        for (Enemy enemy : this.listOfEnemies) {
            if (enemy.fired()) {
                this.listOfEnemyMissiles.add(new Shot(false, enemy.getXposition() + enemy.getWidth() / 2, enemy.getYposition()));
            }
        }

        //pohyb nepriatela
        //vpravo
        for (IMovement enemy : this.listOfEnemies) { // pohyb vsetkych lodi v zozname
            if (enemy.getXposition() < this.playerShip.getXposition()) {
                enemy.moveRight();
            }
            //vlavo
            if (enemy.getXposition() > this.playerShip.getXposition()) {
                enemy.moveLeft();
            }
            //dole
            enemy.moveDown();
        }

        // ked vyjde z hernej plochy, tak resetne meteorit
        if (this.meteor.getPositionY() <= -300) {
            this.meteor = new Meteor();
        }

        // pohyb meteoritu
        this.meteor.fly();
        this.meteor.draw(batch);

        // pohyb hernych objektov
        for (GameObjects gameObject : this.listOfGameObjects) {
            gameObject.move();
        }

        // pohyb striel hraca
        this.movePlayerMissiles();

        //pohyb striel nepriatelov
        this.moveEnemyMissiles();

        // kolizia hracovej lode s meteoritom
        this.playerHitMeteor();

        // kolizia nepriatelskych lodi s meteoritom
        this.enemyHitMeteor();

        // kolizia hraca s nepriatelskou strelou
        this.playerWasHit();

        // kolizia nepriatela s hracovou strelou
        this.enemyWasHit();

        // kolizia striel s meteoritom (hrac aj nepriatel)
        this.missileHitMeteor();

        // kontrola ci hrac vzal herny objekt
        this.gameObjectTaken();

        //nahodna generacia zivotov(heal) na ploche
        this.generateHeal();

        //nahodna generacia nabojov(reload) na ploche
        this.generateAmmunition();

        //nahodna generacia nesmrtelnosti(invulnerability) na ploche
        this.generateInvulnerability();

        //nahodna generacia zrychlenia(hyperSpeed) na ploche
        this.generateHyperSpeed();

        //nahodna generacia spomalenia(overLoadedEngine) na ploche
        this.generateOverloading();

        // ak bol hrac imunny, po vycerpani sa mu opat nastavy zranitelnost
        if (this.playerShip.getHitsToAbsorb() == 0) {
            this.playerShip.setInvulnerability(false);
        }

        // ak je hrac imunny, vykresli stit
        if (this.playerShip.isInvulnerable()) {
            this.shieldEffect.draw(batch, this.playerShip);
        }

        // po 5 sekundach sa hracova rychlost ustali, ak bola zmenena
        if (this.playerShip.isSpeedChanged()) {
            this.countFiveSeconds += Gdx.graphics.getDeltaTime();
            int fiveSeconds = (int)this.countFiveSeconds;

            if (fiveSeconds == 5) {
                this.playerShip.backToNormal();
                this.countFiveSeconds = 0;
            }
        }

        // ukonci hru, ak hrac vycerpa vsetky zivoty
        if (this.playerShip.isDestroyed()) {
            try {
                this.end();
            } catch (IOException e) {
                e.printStackTrace();
                Gdx.app.exit();
            }
        }
        this.batch.end();
    }

    /**
     * súkromná metóda createPlayer
     * vytvorí hráčovú loď podľa vstupných parametrov
     *
     **/
    // vytvorenie lode hraca, priradenie zivotov a priradenie nabojov
    private void createPlayer(int hitpoints, float speed, int bullets) {
        this.playerShip = new PlayerShip(hitpoints, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 6f, speed, bullets);
    }

    /**
     * súkromná metóda movePlayerMissiles
     * pohýňa všetkými strelami hráča
     * pri výjdeni z hernej plochy maže
     *
     **/
    private void movePlayerMissiles() {
        ArrayList<Shot> playersMissilesToRemove = new ArrayList<>();
        for (Shot shot : this.listOfPlayerMissiles) {
            shot.fire(this.playerShip.getSpeed());
            if (shot.getPositionY() >= Gdx.graphics.getHeight() + 60) {
                playersMissilesToRemove.add(shot);
            }
        }
        this.listOfPlayerMissiles.removeAll(playersMissilesToRemove);
        // hlavny zoznam odstrani vsetky nepotrebne strely
    }

    /**
     * súkromná metóda moveEnemyMissiles
     * pohýňa všetkými strelami nepriateľov
     * pri výjdeni z hernej plochy maže
     *
     **/
    private void moveEnemyMissiles() {
        ArrayList<Shot> enemyMissilesToRemove = new ArrayList<>();
        for (Shot shot : this.listOfEnemyMissiles) {
            shot.fire(-5);    // 5*2=10, nepriatelske strely sa pohybuju rychlostou 8
            if (shot.getPositionY() <= -60) {
                enemyMissilesToRemove.add(shot);
            }
        }
        this.listOfEnemyMissiles.removeAll(enemyMissilesToRemove);
        // hlavny zoznam odstrani vsetky nepotrebne strely
    }

    /**
     * súkromná metóda generateEnemies
     * generuje nepriateľov
     * každé 2 sekundy vygeneruje ľahkú loď
     * po zničení 7 ľahkých lodí, vygeneruje strednú loď
     * po zničení 5 stredných lodí, vygeneruje bossa
     *
     **/
    private void generateEnemies() {
        // kazde (vstup podla parametra) sekund pridaj nepriatelsku lod (lahku)
        Random randomXSpawn = new Random();
        if (this.seconds == 2) {    // kazde 2 sekundy generuje nepriatelsku lahku lod
            this.listOfEnemies.add(new EnemyLightShip(2, randomXSpawn.nextInt(1080) + 100, Gdx.graphics.getHeight(), 3.0f));
            this.time = 0;
        }

        // po zniceni 7 lahkych lodi(light ships), pridaj medium ship
        if (this.lightShipDestroyed % 8 == 7) {
            this.listOfEnemies.add(new EnemyMediumShip(8, randomXSpawn.nextInt(1080) + 100, Gdx.graphics.getHeight(), 2.5f));
            this.lightShipDestroyed++;  // musi tam byt, lebo sa tam prida viac ako jedna lod
        }

        // po zniceni 5 strednych lodi(medium ships), pridaj boss ship
        if (this.mediumShipDestroyed % 6 == 5) {
            this.listOfEnemies.add(new EnemyBossShip(35, randomXSpawn.nextInt(1080) + 100, Gdx.graphics.getHeight(), 2.0f));
            this.mediumShipDestroyed++; // musi tam byt, lebo sa tam prida viac ako jedna lod
        }
    }

    /**
     * súkromná metóda generateHeal
     * generuje heal, ktorý zvýši životy hráčovi
     *
     **/
    private void generateHeal() {
        Random random = new Random();
        if (random.nextInt(800) == 1) {
            this.listOfGameObjects.add(new Heal());
        }
    }

    /**
     * súkromná metóda generateAmmunition
     * generuje ammunition, ktoré zvýšia počet náboj hráčovi
     *
     **/
    private void generateAmmunition() {
        Random random = new Random();
        if (random.nextInt(400) == 1) {
            this.listOfGameObjects.add(new Reload());
        }
    }

    /**
     * súkromná metóda generateInvulnerability
     * generuje invulnerability, ktoré učinia hráča nezraniteľným
     *
     **/
    private void generateInvulnerability() {
        Random random = new Random();
        if (random.nextInt(1750) == 1) {
            this.listOfGameObjects.add(new Invulnerability());
        }
    }

    /**
     * súkromná metóda generateOverloading
     * generuje overloading, ktorý spomalí hráča na 5 sekúnd
     *
     **/
    private void generateOverloading() {
        Random random = new Random();
        if (random.nextInt(1250) == 1) {
            this.listOfGameObjects.add(new OverLoad());
        }
    }

    /**
     * súkromná metóda generateHyperSpeed
     * generuje hyperspeed, ktorý zrýchli hráča na 5 sekúnd
     *
     **/
    private void generateHyperSpeed() {
        Random random = new Random();
        if (random.nextInt(1250) == 1) {
            this.listOfGameObjects.add(new HyperSpeed());
        }
    }

    /**
     * súkromná metóda gameObjectTaken
     * kontroluje, či hráč vzial nejaký objekt, ktorý je v zozname
     *
     **/
    private void gameObjectTaken() {
        ArrayList<GameObjects> listOfObjectsToRemove = new ArrayList<>();
        for (GameObjects gameObject : this.listOfGameObjects) {
            if (gameObject.wasPickedUp(this.playerShip)) {
                gameObject.boostUp(this.playerShip);
                listOfObjectsToRemove.add(gameObject);
            }
        }
        this.listOfGameObjects.removeAll(listOfObjectsToRemove);
    }

    /**
     * súkromná metóda playerHitMeteor
     * kontroluje, či hráč narazil do meteoritu
     *
     **/
    private void playerHitMeteor() {
        if (this.playerShip.collidedWithMeteor(this.meteor)) {
            if (this.playerShip.isInvulnerable()) {   // ak nie je pravda, ze je zranitelny (je nezranitelny)
                this.playerShip.resetPlayersPosition(); // umiestni hraca na pociatocnu poziciu
                this.playerShip.lowerAbsorbtion();
            } else {
                this.playerShip.lowerHeart();       // uberie a schova srdiecko
                this.playerShip.lowerHp();   // uberie zivot hracovi
                this.playerShip.resetPlayersPosition(); // umiestni hraca na pociatocnu poziciu
            }
        }
    }

    /**
     * súkromná metóda enemyHitMeteor
     * kontroluje, či nepriateľ narazil do meteoritu
     *
     **/
    private void enemyHitMeteor() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();   // lokalna premenna zoznamu, kde sa ukladaju nepriatelia na odstranenie
        for (Enemy enemy : this.listOfEnemies) {
            if (enemy.collidedWithMeteor(this.meteor)) {
                enemy.lowerHp();
                enemy.resetEnemyPosition();
            }
            // ak ma nepriatel 0 zivotov, prida ho do zoznamu na odstranenie
            if (enemy.isDestroyed()) {
                enemiesToRemove.add(enemy);
            }
        }
        this.listOfEnemies.removeAll(enemiesToRemove);  // hlavny zoznam odstrani vsetkych nevhodnych nepriatelov
    }

    /**
     * súkromná metóda missileHitMeteor
     * kontroluje, či hociktorá strela trafila meteorit
     * ak áno, odstráni strelu
     *
     **/
    private void missileHitMeteor() {
        ArrayList<Shot> enemyShotsToRemove = new ArrayList<>();
        ArrayList<Shot> playerShotsToRemove = new ArrayList<>();
        for (Shot enemyShot : this.listOfEnemyMissiles) {
            if (enemyShot.collidedWithMeteor(this.meteor)) {
                enemyShotsToRemove.add(enemyShot);
            }
        }
        this.listOfEnemyMissiles.removeAll(enemyShotsToRemove);

        for (Shot playerShot : this.listOfPlayerMissiles) {
            if (playerShot.collidedWithMeteor(this.meteor)) {
                playerShotsToRemove.add(playerShot);
            }
        }
        this.listOfPlayerMissiles.removeAll(playerShotsToRemove);
    }

    /**
     * súkromná metóda enemyWasHit
     * kontroluje, či bol nepriateľ zasiahnutý hráčovou strelou
     * ak áno, uberie život a strelu vymaže
     *
     **/
    private void enemyWasHit() {
        ArrayList<Shot> playerMissilesToRemoveAfterHit = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Shot shot : this.listOfPlayerMissiles) {
            for (Enemy enemy : this.listOfEnemies) {
                if (enemy.collidedWithMissile(shot)) {
                    playerMissilesToRemoveAfterHit.add(shot);
                    enemy.lowerHp();
                }
                if (enemy.isDestroyed()) {
                    if (enemy instanceof EnemyBossShip) {
                        this.bossShipDestroyed++;
                    }
                    if (enemy instanceof EnemyMediumShip) {
                        this.mediumShipDestroyed++;
                    }
                    this.lightShipDestroyed++;
                    enemiesToRemove.add(enemy);
                }
            }
        }
        this.listOfEnemies.removeAll(enemiesToRemove);
        this.listOfPlayerMissiles.removeAll(playerMissilesToRemoveAfterHit);
    }

    /**
     * súkromná metóda playerWasHit
     * kontroluje, či bol hráč zasiahnutý nepriateľskou strelou
     * ak áno, uberie život a strelu vymaže
     *
     **/
    private void playerWasHit() {
        ArrayList<Shot> enemyMissilesToRemove = new ArrayList<>();
        for (Shot shot : this.listOfEnemyMissiles) {
            if (this.playerShip.collidedWithMissile(shot)) {
                enemyMissilesToRemove.add(shot);
                if (this.playerShip.isInvulnerable()) {   // ak je nezranitelny, uberie absorpciu
                    this.playerShip.lowerAbsorbtion();
                } else {
                    this.playerShip.lowerHeart();       // uberie a schova srdiecko
                    this.playerShip.lowerHp();   // uberie zivot hracovi
                }
            }
        }
        this.listOfEnemyMissiles.removeAll(enemyMissilesToRemove);
    }

    /**
     * súkromná metóda getLightShipsCount
     * vracia počet zničených ľahkých lodí
     *
     **/
    private int getLightShipsCount() {
        return this.lightShipDestroyed - this.mediumShipDestroyed;
        // zakazdym ked sa zobrazi medium ship, pripocita sa jedna light ship ako znicena,
        // preto treba na konci odpocitat od znicenych light ship pocet znicenych medium ship
    }

    /**
     * súkromná metóda getMediumShipsCount
     * vracia počet zničených stredných lodí
     *
     **/
    private int getMediumShipsCount() {
        return this.mediumShipDestroyed - this.bossShipDestroyed;
        // zakazdym ked sa zobrazi boss ship, pripocita sa jedna medium ship ako znicena,
        // preto treba na konci odpocitat od znicenych medium ship pocet znicenych boss ship
    }

    /**
     * súkromná metóda getBossShipsCount
     * vracia počet zničených bossov
     *
     **/
    private int getBossShipsCount() {
        return this.bossShipDestroyed;
    }

    /**
     * súkromná metóda getTotalTimePlayed
     * vracia celkový herný čas v sekundách
     *
     **/
    private int getTotalTimePlayed() {
        return (int)this.totalTime;
    }

    /**
     * súkromná metóda endingScore
     * po skončení hry, vypíše hráčove skóre
     *
     **/
    private void endingScore() {
        JFrame jFrame = new JFrame();

        jFrame.setVisible(true);
        jFrame.setSize(400, 200);
        jFrame.setLocationRelativeTo(null); // zobrazi do stredu obrazovky

        JLabel jLabel = new JLabel("Enemy light ships destroyed: " + this.getLightShipsCount());
        JLabel jLabel1 = new JLabel("Enemy medium ships destroyed: " + this.getMediumShipsCount());
        JLabel jLabel2 = new JLabel("Enemy boss ships destroyed: " + this.getBossShipsCount());

        jFrame.setLayout(new GridLayout(3, 1));

        jFrame.add(jLabel);
        jFrame.add(jLabel1);
        jFrame.add(jLabel2);
    }

    /**
     * súkromná metóda endingScore
     * ukončuje hru a vypisuje skóre, ktoré následne zapíše do súboru
     *
     **/
    private void end() throws IOException {
        Gdx.app.exit();
        this.endingScore();
        this.saveScore();
    }

    /**
     * súkromná metóda saveScore
     * slúži na zápis skóre do súboru
     *
     **/
    private void saveScore() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat();
        Date date = new Date();
        FileWriter fileWriter = new FileWriter("Score.txt", true);
        fileWriter.write("____________________________________________________");
        fileWriter.write("\n" + dateFormat.format(date));
        fileWriter.write("\nTime played: " + this.getTotalTimePlayed() + " seconds");
        fileWriter.write("\nEnemy light ships destroyed: " + this.getLightShipsCount());
        fileWriter.write("\nEnemy medium ships destroyed: " + this.getMediumShipsCount());
        fileWriter.write("\nEnemy boss ships destroyed: " + this.getBossShipsCount());
        fileWriter.write("\n____________________________________________________");
        fileWriter.close();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
        this.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
