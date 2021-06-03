package com.SMP.dodamdodam.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class tubetube extends AppCompatActivity {
    EditText editText;
    TextView textView;
    String urlStr, stringRequest2;
    Handler handler = new Handler();
    JSONArray language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                String url = "http://my-json-feed";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                textView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//                urlStr = editText.getText().toString();

//                String url = "http://76dea33515d9.ngrok.io/video_list?key=AIzaSyCqEZTPQlS48rUKlRxe2sMvV1UXpK4F3ZM";
//
//                final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("TAG", String.valueOf(response));
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//                jsonRequest.setTag("TAG");
//                RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
//                requestQueue2.add(jsonRequest);


//                JSONObject jObject = new JSONObject();
//                try {
//                    JSONArray jArray = jObject.getJSONArray("urlStr");
//                    for (int i = 0; i < jArray.length(); i++){
//                        JSONObject obj = jArray.getJSONObject(i);
//                        String videoId = obj.getString("videoId");
//                        String  title = obj.getString("title");
//                        String description = obj.getString("description");
//                        String url = obj.getString("url");
//                        if(i == 0){
//                            System.out.println("videoId(" + i + "): " + videoId);
//                            System.out.println("title(" + i + "): " + title);
//                            System.out.println("description(" + i + "): " + description);
//                            System.out.println("url(" + i + "): " + url);}
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                JSONObject jObject = new JSONObject();
//                try {
//                    jObject.get("0");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//               thread.start();

            }
        });
    }

    //웹으로 요청을 보내고 응답을 받는 스레드
//    class RequestThread extends Thread{
//        public void run(){
//
//
//            try{
//                URL url = new URL(urlStr);
//                //사용자가 입력한 URL 정보를 이용해 URL 객체를 만들고 openConnection 메소드를 호출하면 HttpURLConnection 객체가 반환
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                if(conn != null){
//                    //10초 동안 응답을 기다려서 응답이 없으면 끝냄
//                    conn.setConnectTimeout(10000);
//                    conn.setRequestMethod("GET");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//
//                    //getResponseCode 메소드를 호출하면 웹서버에 연결하고 응답을 받아줌
//                    int resCode = conn.getResponseCode();
//
//                    //들어오는 데이터를 받을 수 있는 통로 생성, BufferedReader 객체는 한 줄씩 읽어 들일 때 사용
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    String line = null;
//
//                    while (true){
//                        line = reader.readLine();
//                        if(line == null){
//                            break;
//                        }
//                        println(line);
//                    }
//                    reader.close();
//                    conn.disconnect();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    public void println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}
