package spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpelDemo {

    @Value("#{ 500/2 }")
    private int ekNumber;

    @Value("#{ 90>3 ? '43': '87'}")
    private int doNumber;

    //calling the static final value
    @Value("#{T(java.lang.Math).E}")
    private double exponential;

    //calling the static methods
    @Value("#{T(java.lang.Math).sqrt(144)}")
    private int sqrtValue;

    public int getSqrtValue() {
        return sqrtValue;
    }

    public void setSqrtValue(int sqrtValue) {
        this.sqrtValue = sqrtValue;
    }

    public double getExponential() {
        return exponential;
    }

    public void setExponential(double exponential) {
        this.exponential = exponential;
    }

    public int getEkNumber() {
        return ekNumber;
    }

    public void setEkNumber(int ekNumber) {
        this.ekNumber = ekNumber;
    }

    public int getDoNumber() {
        return doNumber;
    }

    public void setDoNumber(int doNumber) {
        this.doNumber = doNumber;
    }

    @Override
    public String toString() {
        return "SpelDemo{" +
                "ekNumber=" + ekNumber +
                ", doNumber=" + doNumber +
                ", exponential=" + exponential +
                ", sqrtValue=" + sqrtValue +
                '}';
    }
}
