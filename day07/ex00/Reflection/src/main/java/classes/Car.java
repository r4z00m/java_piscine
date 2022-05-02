package classes;

public class Car {

    private String model;
    private String type;
    private int fuelVolume;
    private int consumption;

    public Car() {
        this.model = "default model";
        this.type = "default type";
        this.fuelVolume = 40;
        this.consumption = 8;
    }

    public Car(String model, String type, int fuelVolume, int consumption) {
        this.model = model;
        this.type = type;
        this.fuelVolume = fuelVolume;
        this.consumption = consumption;
    }

    public int distance() {
        return fuelVolume / consumption * 100;
    }

    public void ha(int i, int j) {
        System.out.println(i * j);
    }

    @Override
    public String toString() {
        return "Car[" +
                "model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", fuelVolume=" + fuelVolume +
                ", consumption=" + consumption +
                ']';
    }
}
