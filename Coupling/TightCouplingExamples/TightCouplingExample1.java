class Engine{
    public void start(){
        System.out.println("Engine started..");
    }
}

class HondaEngine{
    public void start(){
        System.out.println("Honda engine started..");
    }
}

class Car{
    public void drive(){
        Engine engine = new Engine(); //direct dependency
        HondaEngine hondaEngine = new HondaEngine();
        engine.start();
        hondaEngine.start();
        System.out.println("Driving the car");
    }
}

public class TightCouplingExample1 {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
    }
}
