package com.SMP.dodamdodam.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.SMP.dodamdodam.Activity.MainActivity;
import com.SMP.dodamdodam.Activity.findPark;
import com.SMP.dodamdodam.Activity.loginActivity;
import com.SMP.dodamdodam.R;

import com.SMP.dodamdodam.Request.WalkRequest;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestFragment extends Fragment {


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;
    private View view;
    private ImageView iv;
    private Button btn_upload;
    Bitmap image;
    String url ="http://116.34.4.118:8080/Ex/upload.php";
    ProgressDialog progressDialog;
    @Override
    public void onStart() {
        super.onStart();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, container, false);

        checkSelfPermission();
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
        btn_upload.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                progressDialog  = new ProgressDialog(getActivity());
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Wait time");
                progressDialog.show();
                StringRequest  stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
                        parms.put("UserName",SharedPreferenceBean.getAttribute(getActivity().getApplication(),"UserName"));
                        return parms;

                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);
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

}
