import java.io.Serializable;

class Main implements Serializable{
    double x = 1.0;
    int y = 2;

    public static void main(String[] args) {
        var m = new Main();
        System.out.println("double: " + m.x + " int: " + m.y);
    }
}
