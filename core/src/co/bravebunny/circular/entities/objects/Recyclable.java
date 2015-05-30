package co.bravebunny.circular.entities.objects;

/**
 * Created by Ricardo on 30/05/2015.
 */
public interface Recyclable {
    void reset();

    boolean isDead();

    void setDead(boolean dead);
}
