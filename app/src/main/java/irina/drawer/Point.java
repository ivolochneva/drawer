package irina.drawer;

public class Point extends Figure {
    private float size;
    private float x;
    private float y;

    public Point(int color, float x, float y, float size) {
        super(color);
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public boolean isCross(Point other) {
        float a = x - other.x;
        float b = y - other.y;
        float c = (float) Math.sqrt(a * a + b * b);
        return size + other.size > c;
    }
}
