package com.SMP.dodamdodam;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.BitSet;

public class SingerItemView extends LinearLayout {

    //어디서든 사용할 수 있게하려면
    TextView textView, textView2;
    ImageView imageView2;
    LinearLayout linearLayout;
    public SingerItemView(Context context) {
        super(context);
        init(context);//인플레이션해서 붙여주는 역
    }

    public SingerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 지금 만든 객체(xml 레이아웃)를 인플레이션화(메모리 객체화)해서 붙여줌
    // LayoutInflater를 써서 시스템 서비스를 참조할 수 있음
    // 단말이 켜졌을 때 기본적으로 백그라운드에서 실행시키는 것을 시스템 서비스라고 함
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_list,this, true);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView2 = findViewById(R.id.imageView2);
        linearLayout = findViewById(R.id.listV);
    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setMobile(String mobile){
        textView2.setText(mobile);
    }
    public void setImage(Bitmap resId){
        imageView2.setImageBitmap(resId);
    }


}