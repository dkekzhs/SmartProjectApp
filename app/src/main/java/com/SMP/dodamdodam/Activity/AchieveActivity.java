package com.SMP.dodamdodam.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.SMP.dodamdodam.Achieve.VerticalAdapter;
import com.SMP.dodamdodam.Achieve.VerticalData;
import com.SMP.dodamdodam.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AchieveActivity extends AppCompatActivity {
    private RecyclerView mVerticalView;
    private VerticalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int MAX_ITEM_COUNT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        // RecyclerView binding
        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list);

        // init Data
        ArrayList<VerticalData> data = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data.add(new VerticalData(R.mipmap.ic_launcher, i+"번째 데이터"));
            i++;
        }

        // init LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL
        // setLayoutManager
        mVerticalView.setLayoutManager(mLayoutManager);
        // init Adapter
        mAdapter = new VerticalAdapter();
        // set Data
        mAdapter.setData(data);
        // set Adapter
        mVerticalView.setAdapter(mAdapter);

        //date
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/dd");
        String time = mFormat.format(date);
        System.out.println(time);


    }
}