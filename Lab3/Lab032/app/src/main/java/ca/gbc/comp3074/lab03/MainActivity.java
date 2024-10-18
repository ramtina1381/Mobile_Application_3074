package ca.gbc.comp3074.lab03;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_activity, btn_map, btn_browser, btn_alarm, btn_camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_activity = findViewById(R.id.btn_activity);
        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        btn_browser = findViewById(R.id.btn_browser);
        btn_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser("https://www.georgebrown.ca/");
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });

        btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
    }

    public void openActivity(){
        Intent i = new Intent(MainActivity.this, MyActivity2.class);
        startActivity(i);
    }

    public void openBrowser(String url ){
        Uri page = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, page);
        //if(i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        //} else {
        //    Toast.makeText(MainActivity.this, "No App to perform action", Toast.LENGTH_LONG).show();
        //}
    }

    public void openMap() {
        Uri location = Uri.parse("geo: 0,0?q=thai+restaurants+toronto");
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(location);
        startActivity(i);
    }

    public void setAlarm(){
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up");
        i.putExtra(AlarmClock.EXTRA_HOUR, 9);
        i.putExtra(AlarmClock.EXTRA_MINUTES, 35);
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(i);
    }

    static final int REQUEST_CAPTURE_IMAGE = 1;

    public void captureImage(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.withAppendedPath(location, fileName));
        startActivityForResult(i, REQUEST_CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Log.d("IMAGE", "Got data");
            Bitmap img = data.getParcelableExtra("data");
            ImageView i = findViewById(R.id.imageView);
            i.setImageBitmap(img);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
