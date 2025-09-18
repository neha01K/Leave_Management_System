interface EngineOfBike{
    void start();
}

class HondaEngineOfBike implements EngineOfBike{
    @Override
    public void start(){
        System.out.println("Honda engine started..");
    }
}

class SuzukiEngineOfBike implements EngineOfBike{
    @Override
    public void start(){
        System.out.println("Suzuki Engine started..");
    }
}

class Bike{
    private EngineOfBike engine;

    public Bike(EngineOfBike engine){
        this.engine = engine;
    }

    void drive(){
        engine.start();
        System.out.println("Bike started");
    }
}
public class LooseCouplingExample {
    public static void main(String[] args) {
        Bike bike1 = new Bike(new SuzukiEngineOfBike());
        Bike bike2 = new Bike(new HondaEngineOfBike());
        bike1.drive();
        bike2.drive();
    }
}
