package com.SMP.dodamdodam.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.SMP.dodamdodam.Fragment.MapsFrag;
import com.SMP.dodamdodam.Fragment.DiteTipFragment;
import com.SMP.dodamdodam.Fragment.UserFragment;
import com.SMP.dodamdodam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private UserFragment frag1;
    private DiteTipFragment frag2;
    private MapsFrag frag3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String Platform = intent.getStringExtra("UserRegister");




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
                    case R.id.place:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        frag1 = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userID",userID);
        bundle.putString("UserRegister",Platform);
        //fragment1로 번들 전달
        frag1.setArguments(bundle);


        frag2 = new DiteTipFragment();
        frag3 = new MapsFrag();
        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

    }




    // 프래그먼트 교체가 일어나는 실행문이다.
    private void setFrag(int n) {
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

        }
    }
}
