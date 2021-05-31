package com.SMP.dodamdodam.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.SMP.dodamdodam.Fragment.CalendarFragment;
import com.SMP.dodamdodam.Fragment.DietTipFragment;
import com.SMP.dodamdodam.Fragment.MapsFragment;
import com.SMP.dodamdodam.Fragment.TodoFragment;
import com.SMP.dodamdodam.Fragment.UserFragment;
import com.SMP.dodamdodam.Fragment.WalkCountFragment;
import com.SMP.dodamdodam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private long backKeyPressedTime = 0;
    private Toast toast;
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
                    case R.id.calendar:
                        setFrag(3);
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
        }
    }
}
