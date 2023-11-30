package assets;

public class Worker {
    private int id;
    private int age;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Worker(int id, String name, int age) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Worker(int id) {
        this.id = id;
    }
}
