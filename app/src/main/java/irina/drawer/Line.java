package irina.drawer;

public class Line extends Figure {
    private Point begin;
    private Point end;
    private float width;

    public Line(int color, Point begin, Point end, float width) {
        super(color);
        this.begin = begin;
        this.end = end;
        this.width = width;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public float getWidth() {
        return width;
    }
}
