package com.SMP.dodamdodam.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.Request.WalkRequest;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class WalkCountFragment extends Fragment implements SensorEventListener {
    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 2;
    TextView mwalknum;
    //현재 걸음 수
    private int mSteps = 0; // 카운트 수
    private int mSetpsinit=0; //걸음 초기값
    //리스너가 등록되고 난 후의 step count

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

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);  // JSONObject 추출

                            String Date = jObject.getString("Date");
                            String count = jObject.getString("count");
                            entries.add(new BarEntry(i, Float.parseFloat(count)));
                            if (Date.equals(now)) {
                                try {
                                    SharedPreferenceBean.setAttribute(getActivity().getApplication(), "walk", count);
                                    Log.e("TAG4", "세임타임");
                                } catch (Exception e) {

                                    Log.e("TAG4", "화면 이동한 경우");
                                    mSteps =0;

                                }

                            }

                        }

                        // Add bars to a bar set
                        BarDataSet barSet = new BarDataSet(entries, "Tenses");
                        // Create a BarData object and assign it to the chart
                        BarData barData = new BarData(barSet);
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


        if(SharedPreferenceBean.getAttribute(getActivity().getApplication(),"walk")!=null){
            mwalknum.setText(SharedPreferenceBean.getAttribute(getActivity().getApplication(),"walk"));
            mSetpsinit = Integer.parseInt(SharedPreferenceBean.getAttribute(getActivity().getApplication(),"walk"));
        }
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
                                    SharedPreferenceBean.setAttribute(getActivity().getApplication(), "walk", count);
                                    Log.e("TAG4", "세임타임");
                                } catch (Exception e) {

                                    Log.e("TAG4", "화면 이동한 경우");
                                    mSteps =0;

                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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




}