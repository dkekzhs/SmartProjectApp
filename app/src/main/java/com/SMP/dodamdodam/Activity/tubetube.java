package com.SMP.dodamdodam.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.SMP.dodamdodam.SingerItemView;
import com.SMP.dodamdodam.Singeritem;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class tubetube extends AppCompatActivity {
    EditText editText;
    TextView textView;
    String urlStr, stringRequest2;
    Handler handler = new Handler();
    JSONArray language;
    RequestQueue requestQueue;
    Gson gson;
    ListView listTest;
Bitmap bitmap;
URL url;

    public static Bitmap StringToBitmap(String url) {
        try { byte[] encodeByte = Base64.decode(url, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null; }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

    getyoutube();

    }

    class SingerAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<Singeritem> items = new ArrayList<Singeritem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Singeritem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 어댑터가 데이터를 관리하고 뷰도 만듬
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView singerItemView = null;
            // 코드를 재사용할 수 있도록
            if(convertView == null) {
                singerItemView = new SingerItemView(getApplicationContext());
            } else {
                singerItemView = (SingerItemView)convertView;
            }
            Singeritem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());



            return singerItemView;
        }
    }

    public void getyoutube(){

        String url4 = "http://d18f6be8cc89.ngrok.io/video_list?key=AIzaSyCqEZTPQlS48rUKlRxe2sMvV1UXpK4F3ZM&&query=%EC%9A%B4%EB%8F%99";
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST,url4, new Response.Listener<String>() {
            ListView listTest = findViewById(R.id.listTest);
            SingerAdapter adapter = new SingerAdapter();
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(String response) {
                Log.d("TAG",response);
                try {

                    JSONObject  jsonObject = new JSONObject(response);
                    Log.d("TAG", String.valueOf(jsonObject));
                    for(int i =0;i<jsonObject.length();i++){
                        String a = jsonObject.getString(Integer.toString(i));
                        JSONObject jsonobj = new JSONObject(a);
                        String b =  jsonobj.getString("videoId");
                        String c =  jsonobj.getString("title");
                        String d = jsonobj.getString("description");
                        String youtubeurl = jsonobj.getString("url");



                        // 안드로이드에서 네트워크와 관련된 작업을 할 때,
                        // 반드시 메인 Thread가 아닌 별도의 작업 Thread를 생성하여 작업해야 한다.
                        Thread mThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                     url = new URL(youtubeurl);

                                    // Web에서 이미지를 가져온 뒤
                                    // ImageView에 지정할 Bitmap을 만든다
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true); // 서버로 부터 응답 수신
                                    conn.connect();

                                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        mThread.start(); // Thread 실행

                        try {
                            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
                            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
                            mThread.join();
                            adapter.addItem(new Singeritem(c,d,bitmap));
                            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
                            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }




                        Log.d("TAG1", b);
                        Log.d("TAG2", c);
                        Log.d("TAG3", d);
                        Log.d("TAG4", youtubeurl);
                        Log.d("TAG",a);


                        listTest.setAdapter(adapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_LONG).show();
                Log.d("TAG", String.valueOf(error));

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<>();
                return parms;

            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(stringRequest3);
    }


}
