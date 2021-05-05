package com.SMP.dodamdodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class diet1 extends AppCompatActivity {

    Context mContext;
    TextView textView2, textView3, textView4, textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diet1);
        mContext = getApplicationContext();
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);
        textView5 = findViewById(R.id.textview5);

        Spannable span1 = (Spannable) textView2.getText();
        span1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 11, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span1.setSpan(new AbsoluteSizeSpan(80), 0, 11, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span2 = (Spannable) textView3.getText();
        span2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 13, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span2.setSpan(new AbsoluteSizeSpan(80), 0, 13, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span3 = (Spannable) textView4.getText();
        span3.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 14, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span3.setSpan(new AbsoluteSizeSpan(80), 0, 14, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Spannable span4 = (Spannable) textView5.getText();
        span4.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 18, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span4.setSpan(new AbsoluteSizeSpan(80), 0, 18, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);





        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}