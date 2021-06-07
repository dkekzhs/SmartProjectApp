package com.SMP.dodamdodam.Request;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class WalkRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/WalkCount.php";
    private Map<String, String> map;

    public WalkRequest(String UserEmail, String Date, String WalkCount, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("UserEmail", UserEmail);
        map.put("Date", Date);
        map.put("WalkCount", WalkCount);
        Log.d(TAG,"리퀘스트 요청 "+map );
    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}