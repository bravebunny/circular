package co.bravebunny.circular.managers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

import java.lang.reflect.InvocationTargetException;

import co.bravebunny.circular.entities.objects.Coin;
import co.bravebunny.circular.entities.objects.Enemy;
import co.bravebunny.circular.entities.objects.Recyclable;

/**
 * Class that creates game objects.
 * It automatically recycles old objects. Neat.
 */
public class EntityFactory {

    //singleton
    private static EntityFactory instance;

    //Entities
    private ArrayMap<Class, Array<Recyclable>> entities = new ArrayMap<Class, Array<Recyclable>>();

    public static EntityFactory getInstance() {
        if (instance == null) instance = new EntityFactory();
        return instance;
    }

    public Array<Recyclable> getArray(Class c) {
        if (!entities.containsKey(c)) {
            entities.put(c, new Array<Recyclable>());
        }
        return entities.get(c);
    }

    public Recyclable create(Class c) {
        Array<Recyclable> array = getArray(c);

        for (Recyclable r : array) {
            if (r.isDead()) {
                r.reset();
                return r;
            }
        }
        Recyclable r = null;
        try {
            r = (Recyclable) c.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        array.add(r);
        return r;
    }

    public Array<Recyclable> getEnemies() {
        return getArray(Enemy.class);
    }

    public Enemy createEnemy() {
        System.out.println("---Enemies: " + getArray(Enemy.class).size);
        return (Enemy) create(Enemy.class);
    }

    public Array<Recyclable> getCoins() {
        return getArray(Coin.class);
    }

    public Coin createCoin() {
        System.out.println("---Coins: " + getArray(Coin.class).size);
        return (Coin) create(Coin.class);
    }

    public void dispose() {
        getArray(Coin.class).clear();
        getArray(Enemy.class).clear();
        //TODO dispose them all

    }

    public void resetAll() {
        for (ObjectMap.Entry<Class, Array<Recyclable>> a : entities) {
            for (Recyclable r : a.value) {
                r.reset();
                r.setDead(true);
            }
        }
    }


}
