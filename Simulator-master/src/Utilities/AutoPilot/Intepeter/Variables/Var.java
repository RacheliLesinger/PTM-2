package Utilities.AutoPilot.Intepeter.Variables;

import Utilities.AutoPilot.Expression.Expression;

public abstract class Var implements Expression {
    protected final String name;

    public Var(String name) {
        this.name = name;
    }

    public abstract void set(Double value) throws Exception;
}
