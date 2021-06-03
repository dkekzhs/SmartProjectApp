package com.SMP.dodamdodam.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserGPSRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://116.34.4.118:8080/Ex/userLoc.php";
    private static final String TAG = "testlog?";
    private Map<String, String> map;

    public UserGPSRequest(String LATITUDE, String LONGITUDE) {
        super(Method.POST, URL, null, null);

        map = new HashMap<>();
        map.put("LATITUDE", LATITUDE);
        map.put("LONGITUDE", LONGITUDE);
        Log.d(TAG,"리퀘스트 요청 "+map );
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}