package jp.developer.bbee.wallpaperchanger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String TAG = "WallpaperChanger";

    LinearLayout layoutBack;
    Spinner spinnerColors;
    Button buttonChange;

    Map<String, Integer> colors;
    Integer whiteColor;
    Integer currentColor;
    String colorName;

    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutBack = findViewById(R.id.layoutBack);
        spinnerColors = findViewById(R.id.spinnerColors);
        buttonChange = findViewById(R.id.buttonChange);

        initColors();

        currentColor = colors.get("White");
        if (currentColor == null) {
            currentColor = Color.parseColor("#FFFFFFFF");
        }
        whiteColor = currentColor;
        setColorBackground((int) whiteColor);

        spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = spinnerColors.getSelectedItemPosition();
                colorName = spinnerColors.getItemAtPosition(position).toString();
                currentColor = colors.get(colorName);
                if (currentColor == null) {
                    currentColor = whiteColor;
                }
                setColorBackground((int) currentColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setColorBackground((int) whiteColor);
            }
        });

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallpaperManager = getSystemService(WallpaperManager.class);
                Display d;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    d = getBaseContext().getDisplay();
                } else {
                    d = getWindowManager().getDefaultDisplay();
                }
                int width = d.getMode().getPhysicalWidth();
                int height = d.getMode().getPhysicalHeight();
                Log.d(TAG, "width=" + width + " height=" + height + " color=" + colorName);

                Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                int[] pixels = new int[width*height];
                Arrays.fill(pixels, currentColor);
                b.setPixels(pixels, 0, width, 0, 0, width, height);

                try {
                    wallpaperManager.setBitmap(b);
                    Toast.makeText(MainActivity.this, "Change:" + colorName,
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initColors() {
        colors = new HashMap<>();
        colors.put("White", Color.WHITE);
        colors.put("Black", Color.BLACK);
        colors.put("Red", Color.RED);
        colors.put("Green", Color.GREEN);
        colors.put("Blue", Color.BLUE);
        colors.put("Yellow", Color.YELLOW);
        colors.put("Cyan", Color.CYAN);
        colors.put("Magenta", Color.MAGENTA);
    }

    private void setColorBackground(int color) {
        layoutBack.setBackgroundColor(color);
    }
}