import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {
    @Test
    public void twoPlusTwoShouldEqualsFour(){
        var calculator = new SimpleCalculator();
        assertEquals(4,calculator.add(2,2));
    }

    @Test
    public void threePlusFourShouldEqualsSeven(){
        var calculator = new SimpleCalculator();
        assertTrue(calculator.add(3,4)==7);
    }

    @Test
    public void threeMinusSixShouldEqualsMinusThree(){
        var calculator = new SimpleCalculator();
        assertEquals(-3, calculator.subtract(3,6));
    }

    @Test
    public void fiveMultiplyZeroEqualsZero(){
        var calculator = new SimpleCalculator();
        assertEquals(0, calculator.multiply(5,0));
    }

    @Test
    public void divivdeByZeroShouldThrowException(){
        var calculator = new SimpleCalculator();
        assertThrows(IllegalArgumentException.class,
                () ->{
            calculator.divide(3,0);
                });
    }

    @Test
    public void sevenMuliplySevenShouldEqualsFourtyNine(){
        var calculator = new SimpleCalculator();
        assertEquals(49, calculator.multiply(7,7));
    }

}