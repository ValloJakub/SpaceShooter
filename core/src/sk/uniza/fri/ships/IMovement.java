package sk.uniza.fri.ships;

/**
 * rozhranie IMovement
 * interface s definovanými správami
 *
 **/
public interface IMovement {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();

    float getXposition();
}
