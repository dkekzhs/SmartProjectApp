package com.SMP.dodamdodam.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.SMP.dodamdodam.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {
    ImageView weatherView;
    ImageButton TouchMe;
    TextView temView, weatherTextView, TouchMeText;
    Button btn_tube;
    String keyword;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherView = findViewById(R.id.weatherView);
        weatherTextView = findViewById(R.id.weatherTextView);
        temView = findViewById(R.id.temView);
        TouchMe = findViewById(R.id.TouchMe);
        TouchMeText = findViewById(R.id.TouchMeText);
        btn_tube = findViewById(R.id.btn_tube);

        weatherView.setVisibility(View.GONE);
        weatherTextView.setVisibility(View.GONE);
        temView.setVisibility(View.GONE);
        btn_tube.setVisibility(View.GONE);

        TouchMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentWeatherCall();
                weatherView.setVisibility(View.VISIBLE);
                weatherTextView.setVisibility(View.VISIBLE);
                temView.setVisibility(View.VISIBLE);
                btn_tube.setVisibility(View.VISIBLE);
                TouchMeText.setVisibility(View.GONE);
            }
        });


        btn_tube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, tubetube.class);
                intent.putExtra("weather", keyword);
                Log.d("TAG", "운동 추천 : " + keyword);
                startActivity(intent);
            }
        });
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    @SuppressLint({"MissingPermission"})
    @NotNull
    public final LatLng getMyLocation() {
        /* GPS_PROVIDER 쓰면 AVD에서는 실행이 되나 폰을 연결하고는 실행이 중단됨 */
//            String locationProvider = LocationManager.GPS_PROVIDER;
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Object locationManage = this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManage == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.location.LocationManager");
        } else {
            LocationManager locationManager = (LocationManager) locationManage;
            Location location = locationManager.getLastKnownLocation(locationProvider);
            Location lastKnownLocation = location;

            return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
    }

    private void CurrentWeatherCall() {
        String lat = Double.toString(getMyLocation().latitude);
        String lon = Double.toString(getMyLocation().longitude);
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=e5684e67f5cda17d9c49b43ec08464c4\n";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //날씨 키값 받기
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);

                    String weather_main = weatherObj.getString("main");
                    String weather = weatherObj.getString("description");

                    String weather_icon = weatherObj.getString("icon");
                    String imageUrl = "http://openweathermap.org/img/wn/" + weather_icon + "@2x.png";
                    Picasso.get().load(imageUrl).resize(500, 500).into(weatherView);
                    weatherTextView.setText(weather);

                    //기온 키값 받기
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));

                    //기온 받고 켈빈 온도를 섭씨 온도로 변경
                    double tempDo = (Math.round((tempK.getDouble("temp") - 273.15) * 100) / 100.0);
                    temView.setText(tempDo + "°C");
                    Log.d("TAG", "날씨 : " + url);

                    //운동 판단
                    String in = "실내운동";
                    String out = "야외운동";
                    switch (weather_main){
                        case "Thunderstorm" : // 뇌우
                            keyword = in;
                        case "Drizzle" : // 이슬비
                            keyword = in;
                        case "Rain" : // 비
                            keyword = in;
                        case "Snow" : // 눈
                            keyword = in;
                        case "Clear" : // 맑음
                            keyword = out;
                        case "Clouds" : // 흐림
                            keyword = out;
                        default: // 기본 설정
                            keyword = in;
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
}