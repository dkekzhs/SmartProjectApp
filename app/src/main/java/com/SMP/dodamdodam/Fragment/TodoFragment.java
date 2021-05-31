package com.SMP.dodamdodam.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.SMP.dodamdodam.Activity.MainActivity;
import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.dinuscxj.progressbar.CircleProgressBar;

public class TodoFragment extends Fragment  implements CircleProgressBar.ProgressFormatter{
    CardView cardView1;
    MainActivity activity;
    private static final String DEFAULT_PATTERN = "%d%%";
    int getWork;
    CircleProgressBar circleProgressBar1;
    String ChangeCount;
    @Override
    public void onStart() {
        super.onStart();
        ChangeCount = SharedPreferenceBean.getAttribute(getContext(),"WorkCount");
        if(ChangeCount==null){
            ChangeCount = "0";
        }
        getWork = Integer.parseInt(ChangeCount);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.todolist, container, false);

        circleProgressBar1 = view.findViewById(R.id.cpb_circlebar1);
        circleProgressBar1.setProgress(getWork);

        cardView1 = view.findViewById(R.id.workcount);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(4);
            }
        });

        return view;
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}
