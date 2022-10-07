import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static final int ASSEMBLY_TIME = 400;
    public static final int BUYER_TIMEOUT = 300;
    public static final int CAR_LIMIT = 10;

    public static void main(String[] args) {
        ReentrantLock locker = new ReentrantLock(true);
        List<Car> carsAvailable = new ArrayList<>();
        Condition condition = locker.newCondition();

        new Thread(() -> {
            for (int i = 0; i < CAR_LIMIT; i++) {
                locker.lock();
                try {
                    Thread.sleep(ASSEMBLY_TIME);
                } catch (InterruptedException e) {
                    return;
                }
                Car car = new Car(Brand.values()[new Random().nextInt(CAR_LIMIT / 2)].name(),
                        Colors.values()[new Random().nextInt(CAR_LIMIT / 2)].name());
                System.out.println("В салон привезли " + car);
                carsAvailable.add(car);
                condition.signal();
                locker.unlock();
            }
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("Гоголь");
            for (int i = 0; i < CAR_LIMIT; i++) {
                System.out.println("Покупатель " + Thread.currentThread().getName() + " зашёл в салон");

                if (carsAvailable.isEmpty()) {
                    System.out.println("Машин нет");
                    if (!Thread.currentThread().isInterrupted()) {
                        locker.lock();
                    }
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " уехал на новенькой " + carsAvailable.remove(0));
                try {
                    Thread.sleep(BUYER_TIMEOUT);
                } catch (InterruptedException e) {
                    return;
                }
                if (!Thread.currentThread().isInterrupted()) {
                    locker.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("Достоевский");
            for (int i = 0; i < CAR_LIMIT; i++) {
                System.out.println("Покупатель " + Thread.currentThread().getName() + " зашёл в салон");

                if (carsAvailable.isEmpty()) {
                    System.out.println("Машин нет");
                    if (!Thread.currentThread().isInterrupted()) {
                        locker.lock();
                    }
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " уехал на новенькой " + carsAvailable.remove(0));
                try {
                    Thread.sleep(BUYER_TIMEOUT);
                } catch (InterruptedException e) {
                    return;
                }
                if (!Thread.currentThread().isInterrupted()) {
                    locker.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            locker.lock();
            Thread.currentThread().setName("Ремарк");
            for (int i = 0; i < CAR_LIMIT; i++) {
                System.out.println("Покупатель " + Thread.currentThread().getName() + " зашёл в салон");
                if (carsAvailable.isEmpty()) {
                    System.out.println("Машин нет");
                    if (!Thread.currentThread().isInterrupted()) {
                        locker.lock();
                    }
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " уехал на новенькой " + carsAvailable.remove(0));
                try {
                    Thread.sleep(BUYER_TIMEOUT);
                } catch (InterruptedException e) {
                    return;
                }
                if (!Thread.currentThread().isInterrupted()) {
                    locker.unlock();
                }
            }
        }).start();
    }
}

