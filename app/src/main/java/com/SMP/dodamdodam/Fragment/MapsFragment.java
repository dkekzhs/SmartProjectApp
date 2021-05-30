package com.SMP.dodamdodam.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;

import java.util.EventListener;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class MapsFragment extends Fragment implements SensorEventListener {
    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 2;
    TextView mwalknum;
    //현재 걸음 수
    private int mSteps = 0;
    //리스너가 등록되고 난 후의 step count
    private int mCounterSteps = 0;

    //센서 연결을 위한 변수
    private SensorManager sensorManager;
    //private Sensor accelerormeterSensor;
    private Sensor stepCountSensor;
    private View view;

    @Override
    public void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
            System.out.print("gg"+MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
        }
        if(stepCountSensor !=null){
            //센서의 속도 설정
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_GAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedometer, container, false);
        //센서 연결[걸음수 센서를 이용한 흔듬 감지]
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCountSensor == null) {
            Toast.makeText(getActivity(),"센서가 존재하지 않아요",Toast.LENGTH_SHORT).show();
        }
        mwalknum = view.findViewById(R.id.sensor);
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){

            //stepcountsenersor는 앱이 꺼지더라도 초기화 되지않는다. 그러므로 우리는 초기값을 가지고 있어야한다.
            if (mCounterSteps < 1) {
                // initial value
                mCounterSteps = (int) event.values[0];
            }
            //리셋 안된 값 + 현재값 - 리셋 안된 값
            mSteps = (int) event.values[0] - mCounterSteps;
            mwalknum.setText(Integer.toString(mSteps));

            SharedPreferenceBean.setAttribute(getContext(),"WorkCount",Integer.toString(mSteps));
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onStop(){
        super.onStop();
        if(sensorManager!=null){
            sensorManager.unregisterListener(this);
        }
    }
}