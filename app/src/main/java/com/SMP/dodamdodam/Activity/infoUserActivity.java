package com.SMP.dodamdodam.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.Request.NameInputRequest;
import com.SMP.dodamdodam.Request.NameRequest;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class infoUserActivity extends AppCompatActivity {
        TextView txtinfo2;
        EditText editName;
        Button btnStart;
        Button btnNameChk;
        String NameChk1,NameChk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
       txtinfo2 = findViewById(R.id.txtInfo2);
       editName = findViewById(R.id.EdtName);
       btnNameChk = findViewById(R.id.btnInfoChk);
       btnStart = findViewById(R.id.btnInfoSub);

       btnNameChk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String Name = editName.getText().toString();

               if(Name.equals("")){

                   txtinfo2.setText("닉네임을 설정해주세요");
                   txtinfo2.setTextColor(Color.parseColor("#FF0000"));
                   txtinfo2.setTypeface(null, Typeface.BOLD);
                   return;
               }
                if(Name.length()>=10){
                    txtinfo2.setText("10글자 아래로 입력해주세요.");
                    txtinfo2.setTextColor(Color.parseColor("#FF0000"));
                    txtinfo2.setTypeface(null, Typeface.BOLD);
                    return;
                }
               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           boolean success = jsonObject.getBoolean("success");

                           if (success) {
                               txtinfo2.setText("사용 가능한 아이디입니다.");
                               txtinfo2.setTextColor(Color.parseColor("#05EA0F"));
                               txtinfo2.setTypeface(null, Typeface.BOLD);

                               NameChk1 = editName.getText().toString();
                           }
                           else {
                               txtinfo2.setText("이미 사용중인 아이디입니다.");
                               txtinfo2.setTextColor(Color.parseColor("#FF0000"));
                               txtinfo2.setTypeface(null, Typeface.BOLD);
                               NameChk1 = "Null";
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();

                       }
                   }
               };
               NameRequest nameRequest = new NameRequest(Name,responseListener);
               RequestQueue queue = Volley.newRequestQueue(infoUserActivity.this);
               queue.add(nameRequest);
           }
       });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameChk2 = editName.getText().toString();
                if(NameChk2.equals("")){
                    txtinfo2.setText("닉네임을 설정해주세요");
                    txtinfo2.setTextColor(Color.parseColor("#FF0000"));
                    txtinfo2.setTypeface(null, Typeface.BOLD);
                    return;
                }
                else{
                    if(NameChk1.equals(NameChk2)){//true문

                        String UserEmail = SharedPreferenceBean.getAttribute(getApplication(),"UserEmail");
                        String UserPlatform = SharedPreferenceBean.getAttribute(getApplication(),"UserPlatform");
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) { // 로그인에 성공한 경우
                                        System.out.print(success);
                                        String getUserName = jsonObject.getString("UserName");
                                        String getUserEmail = jsonObject.getString("UserEmail");
                                        String getUserRegister = jsonObject.getString("UserRegister");


                                        SharedPreferenceBean.setAttribute(getApplication(),"UserEmail",getUserEmail);
                                        SharedPreferenceBean.setAttribute(getApplication(),"UserPlatform",getUserRegister);
                                        SharedPreferenceBean.setAttribute(getApplication(),"UserName",getUserName);
                                        Intent intent = new Intent(infoUserActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);


                                    } else { // 로그인에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "닉네임 설정 중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        NameInputRequest nameInputRequest = new NameInputRequest(NameChk1,UserEmail,UserPlatform,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(infoUserActivity.this);
                        queue.add(nameInputRequest);

                    }
                else{
                        txtinfo2.setText("닉네임을 제대로 입력해주세요.");
                        txtinfo2.setTextColor(Color.parseColor("#FF0000"));
                        txtinfo2.setTypeface(null, Typeface.BOLD);
                    }
                }



            }
        });
    }
}