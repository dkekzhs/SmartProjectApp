package com.SMP.dodamdodam.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.SMP.dodamdodam.Fragment.CalendarFragment;
import com.SMP.dodamdodam.Fragment.DietTipFragment;
import com.SMP.dodamdodam.Fragment.MapsFragment;
import com.SMP.dodamdodam.Fragment.TestFragment;
import com.SMP.dodamdodam.Fragment.TodoFragment;
import com.SMP.dodamdodam.Fragment.UserFragment;
import com.SMP.dodamdodam.Fragment.WalkCountFragment;
import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private UserFragment frag1;
    private DietTipFragment frag2;
    private TodoFragment frag3;
    private CalendarFragment frag4;
    private WalkCountFragment frag5;
    private MapsFragment frag6;
    private TestFragment frag7;
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Dialog dialog;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAch();




        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main:
                        setFrag(0);
                        break;
                    case R.id.tipbook:
                        setFrag(1);
                        break;
                    case R.id.todo:
                        setFrag(2);
                        break;
                    case R.id.calendar:
                        setFrag(3);
                        break;
                    case R.id.Test:
                        setFrag(6);
                        break;
                }
                return true;
            }
        });
        frag1 = new UserFragment();
        //fragment1로 번들 전달
        frag2 = new DietTipFragment();
        frag3 = new TodoFragment();
        frag4 = new CalendarFragment();
        frag5 = new WalkCountFragment();
        frag6 = new MapsFragment();
        frag7 = new TestFragment();
        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

    }


    // 프래그먼트 교체가 일어나는 실행문이다.
    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.MainFrame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.MainFrame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.MainFrame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.MainFrame, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.MainFrame, frag5);
                ft.commit();
                break;
            case 5:
                ft.replace(R.id.MainFrame,frag6);
                ft.commit();
                break;
            case 6:
                ft.replace(R.id.MainFrame,frag7);
                ft.commit();
                break;
        }
    }




    public void getAch(){
        String url3 = "http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/firstlogin.php";
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST,url3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject  jsonObject = new JSONObject(response);
                   String  sucess = jsonObject.getString("Message");
                   String onetime = jsonObject.getString("stauts");
                   if(onetime.equals("ssucess")) {
                       AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                       dialog = builder.setMessage(sucess).setPositiveButton("확인", null).create();
                       dialog.show();
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
