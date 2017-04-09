package nshrapnel.geoinformaticaassignment1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Sensor> sensors;
    private SensorManager sensorManager;
    private Map<String, TextView> textViewMap;
    private SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                TextView textView = textViewMap.get(event.sensor.getName());
                String sensorInforation = String.format("Name: %s\n", event.sensor.getName());
                sensorInforation += String.format("Version: %s\n", event.sensor.getVersion());
                sensorInforation += String.format("Manufacturer: %s\n", event.sensor.getVendor());
                sensorInforation += String.format("Value: %s\n", Arrays.toString(event.values));
                textView.setText(sensorInforation);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        textViewMap = new HashMap<>();
        for (Sensor sensor : sensors) {
            TextView textView = new TextView(this);
            textViewMap.put(sensor.getName(), textView);
            textView.setText(sensor.getName());
            mainLayout.addView(textView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Sensor sensor : sensors) {
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}