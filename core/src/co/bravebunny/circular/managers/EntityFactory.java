package co.bravebunny.circular.managers;

import com.badlogic.gdx.utils.Array;

import co.bravebunny.circular.entities.objects.Coin;
import co.bravebunny.circular.entities.objects.Enemy;

/**
 * Class that creates game objects.
 * It automatically recycles old objects. Neat.
 */
public class EntityFactory {

    //singleton
    private static EntityFactory instance;
    //Enemies
    private Array<Enemy> enemies = new Array<Enemy>();
    //Coins
    private Array<Coin> coins = new Array<Coin>();

    public static EntityFactory getInstance() {
        if (instance == null) instance = new EntityFactory();
        return instance;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy createEnemy() {
        for (Enemy e : enemies) {
            if (e.isDead()) {
                e.reset();
                return e;
            }
        }
        Enemy e = new Enemy();
        enemies.add(e);
        return e;
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    public Coin createCoin() {
        for (Coin c : coins) {
            if (c.isDead()) {
                c.reset();
                return c;
            }
        }
        Coin c = new Coin();
        coins.add(c);
        return c;
    }

    public void dispose() {
        coins.clear();
        enemies.clear();

    }


}
