package com.SMP.dodamdodam.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.SMP.dodamdodam.Activity.MainActivity;
import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dinuscxj.progressbar.CircleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TodoFragment extends Fragment implements CircleProgressBar.ProgressFormatter{
    private CardView cardView1;
    private MainActivity activity;
    private TextView txtWork;
    private static final String DEFAULT_PATTERN = "%d%%";

    CircleProgressBar circleProgressBar1;

    String countString;
    int allcount,count;
    int messageCountInt;
    private int achwork=1000;
    private String getWorkCount,MessageCount;

    private AlertDialog dialog;
    @Override
    public void onStart() {
        super.onStart();

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

        String url ="http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/gettodo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    getWorkCount= jsonObject.getString("Message");
                    countString = jsonObject.getString("Count");
                    MessageCount = jsonObject.getString("test");

                    Log.d("TAG",response);
                    messageCountInt = Integer.parseInt(countString);
                    if(messageCountInt ==1 && MessageCount.equals("1") ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        dialog = builder.setMessage("업적 클리어 업적리스트에 확인하세요.").setPositiveButton("확인", null).create();
                        dialog.show();

                    }
                    if(MessageCount.equals("2")){
                        allcount = 100;
                        circleProgressBar1.setProgress((int)allcount);
                    }
                    if(MessageCount.equals("0")){
                        count = Integer.parseInt(getWorkCount);
                        allcount = (count * 100  /achwork);
                        circleProgressBar1.setProgress((int)allcount);
                    }



                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "걸음수를 측정하게 되면 값이 표시됩니다!", Toast.LENGTH_LONG).show();
                }
        //토스트 메시지 response 시 Josn 내용 다 볼 수 있음 여기에
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity().getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<>();
                parms.put("UserEmail", SharedPreferenceBean.getAttribute(getActivity().getApplication(),"UserEmail"));
                return parms;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);


        txtWork = view.findViewById(R.id.txtWork);
        circleProgressBar1 = view.findViewById(R.id.cpb_circlebar1);





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
