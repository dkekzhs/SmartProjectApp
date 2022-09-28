package com.SMP.dodamdodam.Request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ParkRequest extends StringRequest {

    final static private String URL = "localhost/parkRequest2.php";
    private static final String TAG = "testlog?";
    private Map<String, String> map;

    public ParkRequest(String ParkName, String LATITUDE, String LONGITUDE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ParkName", ParkName);
        map.put("Latitude", LATITUDE);
        map.put("Longitude", LONGITUDE);
        Log.d(TAG,"리퀘스트 요청 "+map );
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
