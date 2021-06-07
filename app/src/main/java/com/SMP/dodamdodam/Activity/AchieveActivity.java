package com.SMP.dodamdodam.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AchieveActivity extends AppCompatActivity
{
    private TextView txtAchSmall1,txtAchSmall2,txtAchSmall3;
    private ImageView Ach1,Ach2,Ach3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        txtAchSmall1 = findViewById(R.id.txtAchSmall1);
        txtAchSmall2 = findViewById(R.id.txtAchSmall2);
        txtAchSmall3 = findViewById(R.id.txtAchSmall3);
        Ach1 = findViewById(R.id.Ach1);
        Ach2 = findViewById(R.id.Ach2);
        Ach3 = findViewById(R.id.Ach3);
        getAch();


    }


    public void getAch(){
        String achurl = "http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/achieve.php";
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST,achurl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //업적 상태
                    String ach0 = jsonObject.getString("ach0");
                    String ach1 = jsonObject.getString("ach1");
                    String ach2 = jsonObject.getString("ach2");

                    if(ach0.equals("0")){
                        txtAchSmall1.setText("미완료 업적");
                        Ach1.setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_IN);
                    }
                    if(ach1.equals("0")){
                        txtAchSmall2.setText("미완료 업적");
                        Ach2.setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_IN);
                    }
                    if(ach2.equals("0")){
                        txtAchSmall3.setText("미완료 업적");
                        Ach3.setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_IN);
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
                parms.put("UserEmail", SharedPreferenceBean.getAttribute(getApplication(),"UserEmail"));
                return parms;

            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(stringRequest3);
    }
}
