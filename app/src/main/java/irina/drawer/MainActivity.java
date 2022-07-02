package irina.drawer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;
import com.rarepebble.colorpicker.ColorPickerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_COLOR = "color";
    private static final String PREF_WIDTH = "width";

    private final List<Figure> figures = new ArrayList<>();
    private Point lastPoint = null;
    private int currentColor = Color.RED;
    private float currentWidth = 10;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_drawer_view);

        DrawerView view = findViewById(R.id.drawer);
        view.setFigures(figures);
        view.setOnTouchListener((v, event) -> {
            Point point = new Point(currentColor, event.getX(), event.getY(), currentWidth / 2);
            if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                figures.add(point);
                if (lastPoint != null) {
                    Line line = new Line(currentColor, lastPoint, point, currentWidth);
                    figures.add(line);
                }
            } else {
                figures.add(point);
            }
            lastPoint = point;
            view.invalidate();
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.select_color_menu) {
            final ColorPickerView picker = new ColorPickerView(this);
            picker.setColor(currentColor);
            new AlertDialog.Builder(this)
                    .setTitle("Select color")
                    .setView(picker)
                    .setPositiveButton("Select", (dialog, which) -> {
                        currentColor = picker.getColor();
                        saveState();
                    })
                    .create()
                    .show();
        } else if (item.getItemId() == R.id.select_width_menu) {
            Slider slider = new Slider(this);
            slider.setValueFrom(2);
            slider.setValueTo(100);
            slider.setValue(currentWidth);
            new AlertDialog.Builder(this)
                    .setTitle("Select width")
                    .setView(slider)
                    .setPositiveButton("Select", (dialog, which) -> {
                        currentWidth = slider.getValue();
                        saveState();
                    })
                    .create()
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveState() {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_COLOR, currentColor);
        editor.putFloat(PREF_WIDTH, currentWidth);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        currentColor = pref.getInt(PREF_COLOR, Color.RED);
        currentWidth = pref.getFloat(PREF_WIDTH, 10);
    }
}