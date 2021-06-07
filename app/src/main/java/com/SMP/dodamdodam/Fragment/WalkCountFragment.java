package com.SMP.dodamdodam.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.Request.WalkRequest;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class WalkCountFragment extends Fragment implements SensorEventListener {
    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 2;
    TextView mwalknum;
    //현재 걸음 수
    private int mSteps = 0; // 카운트 수
    private int mSetpsinit=0; //걸음 초기값
    //리스너가 등록되고 난 후의 step count
    private int perco=0;
    //센서 연결을 위한 변수
    private SensorManager sensorManager;
    //private Sensor accelerormeterSensor;
    private Sensor stepCountSensor;
    private View view;
    private String now;
    BarChart barChart;

    @Override
    public void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
        }
        if (stepCountSensor != null) {
            //센서의 속도 설정
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        mSteps =0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pedometer, container, false);
     //값 가져오기
        barChart = view.findViewById(R.id.barChart);
        //BarEntry를 담는 리스트



        now = Now(System.currentTimeMillis());
            Response.Listener<String> responseListener = new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("WalkCount");


                        ArrayList<BarEntry> entries = new ArrayList<>();
                        String[] arr = new String[jsonArray.length()];
                        Log.d("TAG", String.valueOf(arr.length));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);  // JSONObject 추출

                            String Date = jObject.getString("Date");
                            String count = jObject.getString("count");
                            entries.add(new BarEntry(i, Float.parseFloat(count)));

                            arr[i] = Date;




                            if (Date.equals(now)) {
                                try {
                                    mSetpsinit =returnNum(count);
                                    perco = Integer.parseInt(count);
                                    Log.e("TAG4", "세임타임" + perco);
                                    mwalknum.setText(Integer.toString(perco));

                                } catch (Exception e) {

                                    Log.e("TAG4", "화면 이동한 경우");
                                    mSteps =0;

                                }

                            }

                        }






                        // Add bars to a bar set
                        BarDataSet barSet = new BarDataSet(entries, "걸음 수");
                        BarData barData = new BarData(barSet);
                        XAxis xAxisBottom = barChart.getXAxis();
                        xAxisBottom.setPosition(XAxis.XAxisPosition.BOTTOM);

                        xAxisBottom.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                switch ((int)value){
                                    //write your logic here
                                    case 0:
                                        return arr[0];
                                    case 1:
                                        return arr[1];
                                    case 2:
                                        return arr[2];
                                    case 3:
                                        return arr[3];
                                    default:
                                        return arr[4];
                                }
                            }

                        });


                        xAxisBottom.setDrawGridLines(false);
                        xAxisBottom.setGridLineWidth(0.01f);
                        xAxisBottom.setGranularity(0.01f); // only intervals of 1 day
                        int size = new Float(entries.get(entries.size()-1).getX() - entries.get(0).getX()).intValue();
                        Log.d("TAG", String.valueOf(size));
                        xAxisBottom.setLabelCount(size);

                        // Display it as a percentage
                        barSet.setDrawValues(true);
                        barChart.getAxisLeft().setDrawGridLines(false);
                        barChart.getXAxis().setDrawGridLines(false);

                        barChart.setDrawBarShadow(false);
                        barChart.setTouchEnabled(false);
                        barChart.setData(barData);
                        barChart.setFitBars(false);
                        barChart.animateY(1000);
                        barChart.invalidate();


                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "신체 활동 동의 후 걸어보세요", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };
            WalkRequest walkRequest = new WalkRequest(SharedPreferenceBean.getAttribute(getActivity().getApplication(), "UserEmail"), now, Integer.toString(mSteps), responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(walkRequest);



        //센서 연결[걸음수 센서를 이용한 흔듬 감지]
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepCountSensor == null) {
            Toast.makeText(getActivity(), "센서가 존재하지 않아요", Toast.LENGTH_SHORT).show();
        }
        mwalknum = view.findViewById(R.id.sensor);

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            if(event.values[0] == 1.0f){
                mSteps += event.values[0];
                mwalknum.setText(Integer.toString(mSteps + mSetpsinit));
                Log.e("TAG",""+mSteps);
            }


        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStop() {
        super.onStop();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);

        mSteps =0;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        now = Now(System.currentTimeMillis());

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("WalkCount");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);  // JSONObject 추출
                            Log.d("TAG2", "onResponse:" + jObject);

                            String Date = jObject.getString("Date");
                            String count = jObject.getString("count");

                            if (Date.equals(now)) {
                                try {
                                    mSetpsinit =returnNum(count);
                                    perco = Integer.parseInt(count);
                                    Log.e("TAG4", "세임타임" + perco);
                                    mwalknum.setText(Integer.toString(perco));
                                    barChart.invalidate();
                                } catch (Exception e) {

                                    Log.e("TAG4", "화면 이동한 경우");
                                    mSteps =0;

                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "신체 활동 동의 후 걸어보세요", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            WalkRequest walkRequest = new WalkRequest(SharedPreferenceBean.getAttribute(getActivity().getApplication(), "UserEmail"), now, Integer.toString(mSteps), responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(walkRequest);

        }

    public String Now(long now){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String DateTime = sdf.format(now);
        return DateTime;
    }

public int returnNum(String num){
       int num1 = Integer.parseInt(num);
        return num1;

}


}