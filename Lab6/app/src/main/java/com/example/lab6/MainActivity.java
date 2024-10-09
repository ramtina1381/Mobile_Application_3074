package com.example.lab6;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // First
    private SensorManager sensorManager;
    ListView list;
    ArrayList<String> sensors;

    // Third
    TextView output;
    SensorEventListener listener;
    Sensor sensor;
    LinearLayout mainLayout;  // Add LinearLayout here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle window insets
        mainLayout = findViewById(R.id.main);  // Get LinearLayout reference once
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Second: Initialize the sensor manager and list view
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        list = findViewById(R.id.list);

        // Fourth: Initialize output text view
        output = findViewById(R.id.output);

        // Get the list of all sensors and display them in a ListView
        sensors = new ArrayList<>();
        List<Sensor> l = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : l) {
            sensors.add(s.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensors);
        list.setAdapter(adapter);

        // Initialize sensor and listener
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values.length < 3) {
                    output.setText(event.values[0] + "°C");
                    if(event.values[0] < -5){
                        mainLayout.setBackgroundColor(Color.rgb(0, 0, 255));  // Use the initialized mainLayout
                    } else {
                        mainLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                } else {
                    output.setText(event.values[0] + "°C " + event.values[1] + "°C " + event.values[2] + "°C");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // Handle accuracy changes if necessary
            }
        };
    }

    // Moved outside onCreate
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                listener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }
}
