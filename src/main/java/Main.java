import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int sellCars = 11;
        final int AWAIT_TIME = 4;
        final Diller diller = new Diller();
        Runnable buyer1 = () -> diller.sell(new Buyer("Антон"));
        Runnable buyer2 = () -> diller.sell(new Buyer("Руслан"));
        Runnable buyer3 = () -> diller.sell(new Buyer("Кирил"));
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        Callable orderCar = diller::orderCar;

        for(int i=0; i<sellCars;) {
            threadPool.submit(buyer1);
            threadPool.submit(buyer2);
            threadPool.submit(buyer3);

            FutureTask<Integer> future = new FutureTask<>(orderCar);
            new Thread(future, "ЛАДА").start();
            i = future.get();

        }
        threadPool.shutdown();
        if (!threadPool.awaitTermination(AWAIT_TIME, TimeUnit.SECONDS)) {
            System.out.println("Подождал 4 сек. и заврешил программу. Подскажите как можно сделать лучше?");
            System.exit(0);
        }

    }
}
