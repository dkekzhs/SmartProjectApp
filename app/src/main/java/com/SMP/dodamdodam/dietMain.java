package com.SMP.dodamdodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class dietMain extends AppCompatActivity {
    ImageView diet1, diet2, diet3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_main);

        diet1 = findViewById(R.id.diet1);
        diet2 = findViewById(R.id.diet2);
        diet3 = findViewById(R.id.diet3);

        diet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.diet1.class);
                startActivity(intent);
            }
        });
        diet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.diet2.class);
                startActivity(intent);
            }
        });

        diet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.diet3.class);
                startActivity(intent);
            }
        });
    }
}