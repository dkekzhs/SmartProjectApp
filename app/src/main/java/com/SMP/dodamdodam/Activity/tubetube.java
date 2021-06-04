package com.SMP.dodamdodam.Activity;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class tubetube extends AppCompatActivity {
    EditText editText;
    TextView textView;
    ImageView imageView;
    String urlStr, stringRequest2;
    Handler handler = new Handler();
    JSONArray language;
    RequestQueue requestQueue;
    Gson gson;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

    getyoutube();


        ListView listView = findViewById(R.id.listTest);




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
            singerItemView.setVideoId(item.getVideoId());
            singerItemView.setTitle(item.getTitle());
            singerItemView.setDescription(item.getDescription());
            singerItemView.setUrl(item.getUrl());

            return singerItemView;
        }
    }


    public void getyoutube(){
        String url4 = "http://fb583a7e89f0.ngrok.io/video_list?key=AIzaSyCqEZTPQlS48rUKlRxe2sMvV1UXpK4F3ZM";
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST,url4, new Response.Listener<String>() {
            SingerAdapter adapter = new SingerAdapter();
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
                        String e = jsonobj.getString("url");
                        Log.d("TAG1", b);
                        Log.d("TAG2", c);
                        Log.d("TAG3", d);
                        Log.d("TAG4", e);
                        Log.d("TAG",a);
                        adapter.addItem(new Singeritem(b,c,d,e));


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listView.setAdapter(adapter);


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
