package com.SMP.dodamdodam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
            if(SharedPreferenceBean.getAttribute(getApplication(),"UserEmail")!=null && SharedPreferenceBean.getAttribute(getApplication(),"UserPlatform")!=null
                    && SharedPreferenceBean.getAttribute(getApplication(),"UserName")!="") {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(SplashActivity.this, loginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            finish();

        }
    }

