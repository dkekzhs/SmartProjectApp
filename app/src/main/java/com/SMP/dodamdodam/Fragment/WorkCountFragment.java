package com.SMP.dodamdodam.Fragment;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.SMP.dodamdodam.R;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */

//가속도 센서 만보기 계산 API
//-->  https://copycoding.tistory.com/5?category=1014356
public class WorkCountFragment extends Fragment {
    Button mReset;
    TextView mwalknum;
    //현재 걸음 수
    private int mSteps = 0;
    //리스너가 등록되고 난 후의 step count
    private int mCounterSteps = 0;


    //센서 연결을 위한 변수
    private SensorManager sensorManager;
    //private Sensor accelerormeterSensor;
    private Sensor stepCountSensor;
    SensorEventListener listener;

    private View view;


    public WorkCountFragment() {
        // Required empty public constructor
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
            //Toast.makeText(this,"No Step Detect Sensor",Toast.LENGTH_SHORT).show();
        }
        mwalknum = view.findViewById(R.id.sensor);

        //초기화 버튼 : 다시 숫자를 0으로 만들어준다.
       /* listener = new SensorEventListener() {
            @Override

                if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

                    //stepcountsenersor는 앱이 꺼지더라도 초기화 되지않는다. 그러므로 우리는 초기값을 가지고 있어야한다.
                    if (mCounterSteps < 1) {
                        // initial value
                        mCounterSteps = (int) event.values[0];
                    }
                    //리셋 안된 값 + 현재값 - 리셋 안된 값
                    mSteps = (int) event.values[0] - mCounterSteps;
                    mwalknum.setText(Integer.toString(mSteps));
                    Log.i("log: ", "New step detected by STEP_COUNTER sensor. Total step count: " + mSteps);
                }
        }

    }
        public void onStart () {
            super.onStart();
            if (stepCountSensor != null) {
                //센서의 속도 설정
                sensorManager.registerListener(listener, stepCountSensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
        public void onStop () {
            super.onStop();
            if (sensorManager != null) {
                sensorManager.unregisterListener(listener);
            }
        }
    }*/
        return view;
    }
}

