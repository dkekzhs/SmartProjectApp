package com.SMP.dodamdodam.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NameRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/UserNameChk.php";
    private Map<String, String> map;

    public NameRequest(String Name, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("UserName", Name);
        Log.d(TAG,"리퀘스트 요청 "+map );
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}