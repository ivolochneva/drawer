package irina.drawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class DrawerView extends View {
    private int color = Color.RED;
    private Paint paint;

    private List<Figure> figures;

    public DrawerView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DrawerView, defStyle, 0);

        color = a.getColor(
                R.styleable.DrawerView_color,
                color);

        a.recycle();

        paint = new Paint();

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    private void invalidateTextPaintAndMeasurements() {
        paint.setColor(getColor());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (figures != null) {
            for (Figure figure: figures) {
                paint.setColor(figure.getColor());
                if (figure instanceof Point) {
                    Point point = (Point) figure;
                    canvas.drawCircle(point.getX(), point.getY(), point.getSize(), paint);
                } else if (figure instanceof Line) {
                    Line line = (Line) figure;
                    Point begin = line.getBegin();
                    Point end = line.getEnd();
                    paint.setStrokeWidth(line.getWidth());
                    canvas.drawLine(begin.getX(), begin.getY(), end.getX(), end.getY(), paint);
                }
            }
        }
    }

    /**
     * Gets the color attribute value.
     *
     * @return The color attribute value.
     */
    public int getColor() {
        return color;
    }

    public int getBackgroundColor() {
        return ((ColorDrawable)getBackground()).getColor();
    }

//    /**
//     * Sets the view"s color attribute value. In the view,
//     * this color is the font color.
//     *
//     * @param color The color attribute value to use.
//     */
//    public void setColor(int color) {
//        this.color = color;
//        invalidateTextPaintAndMeasurements();
//    }
}