import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReadWriteLock;



public class Car implements Runnable {
    private static int CARS_COUNT;

    public static void setCb(int count) {
        Car.cb =  new CyclicBarrier(count);
    }

    private static CyclicBarrier cb = new CyclicBarrier(4);

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private  String name;
    public static Queue<Integer> readyRacer = new ConcurrentLinkedDeque<>();
    public String getName() {
        return name;
    }



    public int getSpeed() {
        return speed;
    }

    public static CyclicBarrier getCb() {
        return cb;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            cb.await();
            System.out.println(this.name + " готов");
            cb.await();
            MainClass.globalQueue.add(1);
           cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }


    }


}


