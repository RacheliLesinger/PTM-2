package Utilities.AutoPilot.Expression;

public class Number implements Expression {
    final double value;

    public Number(double value) {
        this.value = value;
    }
    @Override
    public double calculate() {
        return value;
    }
}
