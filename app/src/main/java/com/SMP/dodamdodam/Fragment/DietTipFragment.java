package com.SMP.dodamdodam.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.SMP.dodamdodam.R;

public class DietTipFragment extends Fragment {
    ImageView diet1, diet2, diet3;


    @Override
    public void onStart() {
        super.onStart();


        diet1 =getView().findViewById(R.id.diet1);
        diet2 = getView().findViewById(R.id.diet2);
        diet3 =getView().findViewById(R.id.diet3);

        diet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), com.SMP.dodamdodam.Diet.diet1.class);
                startActivity(intent);
            }
        });
        diet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), com.SMP.dodamdodam.Diet.diet2.class);
                startActivity(intent);
            }
        });

        diet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), com.SMP.dodamdodam.Diet.diet3.class);
                startActivity(intent);
            }
        });
    }

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.diet_main, container, false);


        return view;
    }
}
