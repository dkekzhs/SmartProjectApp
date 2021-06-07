package com.SMP.dodamdodam.Diet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.SMP.dodamdodam.R;

public class diet3 extends AppCompatActivity {

    Context mContext;
    TextView textView2, textView3, textView4, textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_diet3);
        mContext = getApplicationContext();
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);
        textView5 = findViewById(R.id.textview5);

        Spannable span1 = (Spannable) textView2.getText();
        span1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 16, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span1.setSpan(new AbsoluteSizeSpan(80), 0, 16, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span2 = (Spannable) textView3.getText();
        span2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(80), 0, 15, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span3 = (Spannable) textView4.getText();
        span3.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 16, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span3.setSpan(new AbsoluteSizeSpan(80), 0, 16, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span4 = (Spannable) textView5.getText();
        span4.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 21, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span4.setSpan(new AbsoluteSizeSpan(80), 0, 21, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        ImageButton back3 = findViewById(R.id.back3);

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}