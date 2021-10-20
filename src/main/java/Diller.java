import java.util.ArrayList;
import java.util.List;

public class Diller {
    private String modelCar;
    private int countCar = 1;
    final int SLEEP_TIME = 3000;
    private List<Car> cars = new ArrayList<>();

    public synchronized void sell(Buyer buyer) {
        try {
            System.out.println("К нам в салон пришел " + buyer.getName());
            while (cars.isEmpty()) {
                System.out.println("Но машин нет приходите позже...");
                wait();
            }
            Thread.sleep(SLEEP_TIME);
            System.out.println("Продана машина №" + cars.get(0).getNumberCar() + ". Покупатель " + buyer.getName() + " счастлив.");
            cars.remove(0);
            System.out.println("------------------------------");
        } catch (InterruptedException e) {
            System.out.println("Перехват исключения InterruptedException у потока " + Thread.currentThread().getName());
        }
    }

    public synchronized int orderCar() {
        try {
            if (cars.isEmpty()) {
                modelCar = Thread.currentThread().getName();
                System.out.println("Производство машины № " + countCar + " с завода " + modelCar);
                Thread.sleep(SLEEP_TIME);
                cars.add(new Car(countCar));
                countCar++;
                System.out.println("Поставка новой машины произведена успешно");
                notify();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return countCar;
    }

}
