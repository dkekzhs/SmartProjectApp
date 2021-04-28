package com.SMP.dodamdodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(getApplicationContext(),loginActivity.class); //로그인 액티비티 볼려고 설정해논거
        startActivity(intent);

    }
}