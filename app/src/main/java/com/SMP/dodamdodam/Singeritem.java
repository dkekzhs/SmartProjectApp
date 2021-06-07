package com.SMP.dodamdodam;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class Singeritem {
    String name;
    String mobile;
    Bitmap resId;

    //생성
    public Singeritem(String name, String mobile, Bitmap resId) {
        this.name = name;
        this.mobile = mobile;
        this.resId = resId;
    }


    //변수에 접근할 때 .OO 접근하기보다는 안전하게 getter, setter를 이용합니다.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Bitmap getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return "SingerItem{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", resId='" + resId + '\'' +
                '}';
    }
}