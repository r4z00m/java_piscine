package classes;

public class Animal {

    private String name;
    private Integer numberOfLegs;
    private Double weight;
    private boolean alive;
    private Long id;

    public Integer greeting(String name){
        System.out.println(name);
        return (5454545);
    }

    public Animal() {
    }

    public Animal(String name, Integer numberOfLegs, Double weight, boolean alive, Long id) {
        this.name = name;
        this.numberOfLegs = numberOfLegs;
        this.weight = weight;
        this.alive = alive;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", numberOfLegs=" + numberOfLegs +
                ", weight=" + weight +
                ", alive=" + alive +
                ", id=" + id +
                '}';
    }
}