package Utilities.Math;

public class ColoredRange {
    private final double start;
    private final double end;
    private final String color;

    public ColoredRange(double start, double end, String color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public double getStart(){ return start; }
    public double getEnd(){ return end; }
    public boolean inRange(double d) { return d>=start && d<=end; }
    public String getColor() { return color; }
}
