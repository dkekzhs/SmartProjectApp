package com.SMP.dodamdodam.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class KakaoRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://116.34.4.118:8080/Ex/kakaologin.php";
    private static final String TAG = "testlog?";
    private Map<String, String> map;

    public KakaoRequest(String UserId  ,Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserEmail", UserId);

        Log.d(TAG,"리퀘스트 요청 "+map );
    }
    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
