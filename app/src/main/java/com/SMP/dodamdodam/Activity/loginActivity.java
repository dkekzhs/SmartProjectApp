package com.SMP.dodamdodam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.Request.KakaoRequest;
import com.SMP.dodamdodam.Request.LoginRequest;
import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SessionCallback;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;


public class loginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Button btn_login, btn_register;
    private EditText et_id, et_pass;
    private String kakaoId;

    private static final String TAG = "logError"; //에러 확인

    // 카카오 로그인 세션
    private SessionCallback sessionCallback = new SessionCallback() {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                        }
                        @Override
                        public void onSuccess(MeV2Response result) { //로그인 성공시
                            kakaoId = String.valueOf(result.getId());
                            String userID = kakaoId;

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) { // 로그인 성공시
                                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                            intent.putExtra("userID", kakaoId);
                                            intent.putExtra("UserRegister","kakao"); //픽스 예정 intent로 넘겨줄지 or 세션을 통해서 로그인 관리할지

                                            startActivity(intent);
                                        } else { // 로그인 실패
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            KakaoRequest kakaoRequest = new KakaoRequest(userID,  responseListener);
                            RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                            queue.add(kakaoRequest);
                        }
                        });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 카카오 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            // 세션 콜백 등록
            Session.getCurrentSession().addCallback(sessionCallback);

        btn_login = (Button)findViewById(R.id.btn_login); //로그인 버튼
        btn_register = (Button)findViewById(R.id.btn_register); //회원가입 버튼

        et_id = (EditText)findViewById( R.id.et_id ); //아이디 텍스트
        et_pass = (EditText)findViewById( R.id.et_pass ); //비밀번호 텍스트

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //회원가입시
                Intent intent = new Intent( loginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 입력값 아이디 비밀번호 가져옴
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String UserEmail = jsonObject.getString("UserEmail");
                                String UserRegister = jsonObject.getString("UserRegister");

                                AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                                dialog = builder.setMessage("로그인에 성공하였습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                intent.putExtra("userID", UserEmail);
                                intent.putExtra("UserRegister",UserRegister);

                                startActivity(intent);
                            } else { // 로그인에 실패한 경우
                                AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                                dialog = builder.setMessage("아이디와 비밀번호를 확인해주세요.").setPositiveButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
