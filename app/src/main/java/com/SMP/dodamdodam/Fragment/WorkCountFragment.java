package com.SMP.dodamdodam.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;

public class WorkCountFragment extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    TextView tvStepDetector;
    private int mStepDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pedometer);

        tvStepDetector = (TextView)findViewById(R.id.sensor);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepDetectorSensor == null) {
            Toast.makeText(this, "No Step Detect Sensor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if(event.values[0] == 1.0f) {
                mStepDetector++;
                tvStepDetector.setText("Step Detect : " + String.valueOf(mStepDetector));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
