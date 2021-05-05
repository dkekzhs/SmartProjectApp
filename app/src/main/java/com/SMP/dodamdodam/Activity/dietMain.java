package com.SMP.dodamdodam.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.SMP.dodamdodam.R;

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
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.Diet.diet1.class);
                startActivity(intent);
            }
        });
        diet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.Diet.diet2.class);
                startActivity(intent);
            }
        });

        diet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dietMain.this, com.SMP.dodamdodam.Diet.diet3.class);
                startActivity(intent);
            }
        });
    }
}