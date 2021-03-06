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
import android.widget.AdapterView;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
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
    String flaskserver;
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
        //???????????? ??????????????? ??????, ????????? ???????????? ???????????????.
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

        // ???????????? ???????????? ???????????? ?????? ??????
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView singerItemView = null;
            // ????????? ???????????? ??? ?????????
            if(convertView == null) {
                singerItemView = new SingerItemView(getApplicationContext());
            } else {
                singerItemView = (SingerItemView)convertView;
            }
            final String base_url = "https://www.youtube.com/watch?v=";
            Singeritem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());

            singerItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(base_url + item.getQuery()));
                    startActivity(browserIntent);
                }
            });

            return singerItemView;
        }
    }

    public String getURLEncode(String content){
        try {
            return URLEncoder.encode(content, "utf-8");   // UTF-8
            // return URLEncoder.encode(content, "euc-kr");  // EUC-KR
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getyoutube(){
        String keyword = getIntent().getStringExtra("weather");
        String encodeKeyword = getURLEncode(keyword);
        Log.d("TAG", "?????? ?????? : " + keyword);
        Log.d("TAG", "?????? ?????? ????????? : " + encodeKeyword);
        flaskserver = getString(R.string.flaskserver);
        String url4 = flaskserver +"/video_list?key=AIzaSyCqEZTPQlS48rUKlRxe2sMvV1UXpK4F3ZM&&query=" + encodeKeyword;
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



                        // ????????????????????? ??????????????? ????????? ????????? ??? ???,
                        // ????????? ?????? Thread??? ?????? ????????? ?????? Thread??? ???????????? ???????????? ??????.
                        Thread mThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    url = new URL(youtubeurl);

                                    // Web?????? ???????????? ????????? ???
                                    // ImageView??? ????????? Bitmap??? ?????????
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true); // ????????? ?????? ?????? ??????
                                    conn.connect();

                                    InputStream is = conn.getInputStream(); // InputStream ??? ????????????
                                    bitmap = BitmapFactory.decodeStream(is); // Bitmap?????? ??????

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        mThread.start(); // Thread ??????

                        try {
                            // ?????? Thread??? ????????? ?????? Thread??? ????????? ????????? ????????? ??????????????????
                            // join()??? ???????????? ????????? ?????? Thread??? ????????? ????????? ?????? Thread??? ???????????? ??????
                            mThread.join();
                            adapter.addItem(new Singeritem(c,d,bitmap,b));
                            // ?????? Thread?????? ???????????? ???????????? ????????? ????????? ???
                            // UI ????????? ??? ??? ?????? ?????? Thread?????? ImageView??? ???????????? ????????????

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