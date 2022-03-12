package jp.developer.bbee.wallpaperchanger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class PaintMode extends AppCompatActivity {

    Button buttonSolidColors;
    Button buttonEraser;
    Button buttonChange2;
    MyCanvas myCanvas;

    WallpaperManager wallpaperManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_mode);

        myCanvas = findViewById(R.id.myCanvas);

        buttonSolidColors = findViewById(R.id.buttonSolidColors);
        buttonSolidColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonEraser = findViewById(R.id.buttonEraser);
        buttonEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.clearCanvas();
            }
        });

        buttonChange2 = findViewById(R.id.buttonChange2);
        buttonChange2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = Bitmap.createBitmap(myCanvas.getWidth(), myCanvas.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                myCanvas.draw(c);

                wallpaperManager2 = getSystemService(WallpaperManager.class);
                try {
                    wallpaperManager2.setBitmap(b);
                    Toast.makeText(PaintMode.this, "Change wallpaper",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}