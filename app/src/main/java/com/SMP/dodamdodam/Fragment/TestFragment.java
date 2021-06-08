package com.SMP.dodamdodam.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestFragment extends Fragment {


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;
    private View view;
    private ImageView iv;
    private Button btn_upload;
    private TextView txttest,txttest2;
    private String getImageName;
    Bitmap image;
    String flaskserver;
    String uploadurl ="http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/upload.php";
    String getInfourl ="http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/getInfoImage.php";
    ProgressDialog progressDialog;
    String Url; //플라스크 서버 URL
    @Override
    public void onStart() {
        super.onStart();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, container, false);

        checkSelfPermission();
        txttest = view.findViewById(R.id.txtTest);
        txttest2 = view.findViewById(R.id.txtTest2);
        btn_upload = view.findViewById(R.id.btn_upload_image);
        iv = view.findViewById(R.id.iv_upload_image);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });
        btn_upload.setEnabled(false);
        btn_upload.setBackgroundColor(Color.parseColor("#868e96"));
        btn_upload.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                progressDialog  = new ProgressDialog(getActivity());
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Wait time");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,uploadurl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String a= jsonObject.getString("Message");
                            flaskserver = getString(R.string.flaskserver);


                            Url = flaskserver +"predict?url="+a;
                            mThread  mthread =new mThread();
                            mthread.start();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parms = new HashMap<>();
                        String imageDate = imageToString(image);
                        parms.put("image",imageDate);
                        parms.put("UserName", SharedPreferenceBean.getAttribute(getActivity().getApplication(),"UserName"));
                        return parms;

                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);
                //끝 -------------------------------서버에서 정보 받기---------------------------------------
                //========================이미지 정보 검색 시작=====================================================

            }





        });



        return view;

    }

    public void checkSelfPermission() {
        String temp = "";
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) {
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "), 1);
        } else {
            Toast.makeText(getActivity(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TAG", "" + permissions[i]);
                }

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == getActivity().RESULT_OK) {
            Uri dataUri = data.getData();
            iv.setImageURI(dataUri);


            try {
 InputStream in = getActivity().getContentResolver().openInputStream(dataUri);
     image = BitmapFactory.decodeStream(in);
     iv.setImageBitmap(image); in.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            btn_upload.setEnabled(true);
            btn_upload.setBackgroundColor(Color.parseColor("#FFE694"));

        } else if (requestCode == 101 && resultCode == getActivity().RESULT_CANCELED) {
            Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
        }
    }

    private  String imageToString (Bitmap bitmap){
        ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }
    class mThread extends Thread{
        @Override
        public void run() {
            try {
                URL url = new URL(Url);
                Log.d("TAG", String.valueOf(url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);
                    urlConnection.disconnect();

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                txttest.setText("서버가 닫혀있는 거같아요");
                e.printStackTrace();
            }

        }
    }

    public void readStream(InputStream in){
        final String data = readData(in);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                txttest.setText(data);
                Log.d("TAG",data);
                getImageName = data;
                requestImage(getImageName);

            }
        });
    }
    public String readData(InputStream is){
        String data = "";
        Scanner s = new Scanner(is);
        while(s.hasNext()) data += s.nextLine() + "\n";
        s.close();
        return data;
    }

    Handler mHandler = new Handler();

    public void requestImage(String Data){
    StringRequest stringRequest2 = new StringRequest(Request.Method.POST,getInfourl, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

            try {
               JSONObject jsonObject = new JSONObject(response);
                String a= jsonObject.getString("a");
                String b= jsonObject.getString("b");
                String c= jsonObject.getString("c");
                String d= jsonObject.getString("d");
                String e= jsonObject.getString("e");
                txttest2.setText("100G : " +b +"\n 지방 : " + c + "\n 단백질 : " +d + "\n 탄수화물 : " + e
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(getActivity().getApplicationContext(), "error : " + error.toString(), Toast.LENGTH_LONG).show();
            Log.d("TAG", String.valueOf(error));
        }
    }
    ){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> parms = new HashMap<>();
            parms.put("Image",Data);
            return parms;

        }
    };
    RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue2.add(stringRequest2);
    }


}
