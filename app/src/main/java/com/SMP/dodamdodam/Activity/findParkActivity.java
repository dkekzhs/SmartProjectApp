package com.SMP.dodamdodam.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.R.id;
import com.SMP.dodamdodam.Request.LoginRequest;
import com.SMP.dodamdodam.Request.ParkRequest;
import com.SMP.dodamdodam.Request.UserGPSRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u0004H\u0007J\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0007J\u0012\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\b\u0010#\u001a\u00020\u001fH\u0014J\b\u0010$\u001a\u00020\u001fH\u0016J\u0006\u0010%\u001a\u00020\u001fJ\b\u0010&\u001a\u00020\u001fH\u0014J-\u0010'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u00122\u000e\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\f2\u0006\u0010*\u001a\u00020+H\u0016¢\u0006\u0002\u0010,J\b\u0010-\u001a\u00020\u001fH\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u0012X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006."},
        d2 = {"Lcom/SMP/dodamdodam/Activity/findPark;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "CITY_HALL", "Lcom/google/android/gms/maps/model/LatLng;", "getCITY_HALL", "()Lcom/google/android/gms/maps/model/LatLng;", "DEFAULT_ZOOM_LEVEL", "", "getDEFAULT_ZOOM_LEVEL", "()F", "PERMISSIONS", "", "", "getPERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "REQUEST_PERMISSION_CODE", "", "getREQUEST_PERMISSION_CODE", "()I", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "getGoogleMap", "()Lcom/google/android/gms/maps/GoogleMap;", "setGoogleMap", "(Lcom/google/android/gms/maps/GoogleMap;)V", "getMyLocation", "hasPermissions", "", "initMap", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLowMemory", "onMyLocationButtonClick", "onPause", "onRequestPermissionsResult", "requestCode", "permissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "DoDamDoDam.app"}
)
public final class findParkActivity extends AppCompatActivity {
    @NotNull
    private final String[] PERMISSIONS = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    private final int REQUEST_PERMISSION_CODE = 1;
    private final float DEFAULT_ZOOM_LEVEL = 17.0F;
    @NotNull
    private final LatLng CITY_HALL = new LatLng(37.5662952D, 126.97794509999994D);
    @Nullable
    private GoogleMap googleMap;
    private HashMap _$_findViewCache;
    private Switch findpark ;
    ProgressDialog progressDialog;
    @NotNull
    public final String[] getPERMISSIONS() {
        return this.PERMISSIONS;
    }

    public final int getREQUEST_PERMISSION_CODE() {
        return this.REQUEST_PERMISSION_CODE;
    }

    public final float getDEFAULT_ZOOM_LEVEL() {
        return this.DEFAULT_ZOOM_LEVEL;
    }

    @NotNull
    public final LatLng getCITY_HALL() {
        return this.CITY_HALL;
    }

    @Nullable
    public final GoogleMap getGoogleMap() {
        return this.googleMap;
    }

    public final void setGoogleMap(@Nullable GoogleMap var1) {
        this.googleMap = var1;
    }

    static RequestQueue requestQueue;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ((MapView) this._$_findCachedViewById(id.mapView)).onCreate(savedInstanceState);
        if (this.hasPermissions()) {
            this.initMap();
        } else {
            ActivityCompat.requestPermissions((Activity) this, this.PERMISSIONS, this.REQUEST_PERMISSION_CODE);
        }

        ((FloatingActionButton) this._$_findCachedViewById(id.myLocationButton)).setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                findParkActivity.this.onMyLocationButtonClick();
            }
        }));
        findpark = findViewById(id.btnfindPark);
        findpark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findpark.isChecked() == true) {
                    findParkActivity.this.addMarker();
                    progressDialog  = new ProgressDialog(findParkActivity.this);
                    progressDialog.setTitle("공원 찾는 중");
                    progressDialog.setMessage("잠시만 기다려 주세요");
                    progressDialog.show();
                }

                else{
                    googleMap.clear();
                    Toast.makeText(findParkActivity.this,"체크가 해제되어 주변공원이 뜨지 않습니다.",Toast.LENGTH_LONG);
                }
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.initMap();
    }

    public final boolean hasPermissions() {
        String[] var3 = this.PERMISSIONS;
        int var4 = var3.length;

        for (int var2 = 0; var2 < var4; ++var2) {
            String permission = var3[var2];
            if (ActivityCompat.checkSelfPermission((Context) this, permission) != 0) {
                return false;
            }
        }

        return true;
    }

    @SuppressLint({"MissingPermission"})
    public final void initMap() {
        ((MapView) this._$_findCachedViewById(id.mapView)).getMapAsync((OnMapReadyCallback) (new OnMapReadyCallback() {
            public final void onMapReady(GoogleMap it) {
                findParkActivity.this.setGoogleMap(it);
                Intrinsics.checkNotNullExpressionValue(it, "it");
                UiSettings var10000 = it.getUiSettings();
                Intrinsics.checkNotNullExpressionValue(var10000, "it.uiSettings");
                var10000.setMyLocationButtonEnabled(false);
                if (findParkActivity.this.hasPermissions()) {
                    it.setMyLocationEnabled(true);
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(findParkActivity.this.getMyLocation(), findParkActivity.this.getDEFAULT_ZOOM_LEVEL()));
                } else {
                    Toast.makeText(findParkActivity.this, "위치정보를 확인 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @SuppressLint({"MissingPermission"})
    @NotNull
    public final LatLng getMyLocation() {
        /* GPS_PROVIDER 쓰면 AVD에서는 실행이 되나 폰을 연결하고는 실행이 중단됨 */
//            String locationProvider = LocationManager.GPS_PROVIDER;
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Object locationManage = this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManage == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.location.LocationManager");
        } else {
            LocationManager locationManager = (LocationManager) locationManage;
            Location location = locationManager.getLastKnownLocation(locationProvider);
            Location lastKnownLocation = location;
            return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
    }

    public final void onMyLocationButtonClick() {
        if (this.hasPermissions()) {
            GoogleMap var10000 = this.googleMap;
            if (var10000 != null) {
                var10000.moveCamera(CameraUpdateFactory.newLatLngZoom(this.getMyLocation(), this.DEFAULT_ZOOM_LEVEL));
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "위치사용권한 설정에 동의해주세요", Toast.LENGTH_SHORT).show();
        }

    }

    public static int calcDistance(double latUser, double lonUser, double latPark, double lonPark) {
        double theDistance = (Math.sin(Math.toRadians(latUser)) *
                Math.sin(Math.toRadians(latPark)) +
                Math.cos(Math.toRadians(latUser)) *
                        Math.cos(Math.toRadians(latPark)) *
                        Math.cos(Math.toRadians(lonUser - lonPark)));

        return new Double((Math.toDegrees(Math.acos(theDistance))) * 69.09*1.6093).intValue();
    }

    public final void addMarker() {
        googleMap.clear();

        String url = "http://ec2-52-79-44-86.ap-northeast-2.compute.amazonaws.com/parkRequest.php";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("TAG", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    Log.d("TAG", "jsonObject complete");
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                    Log.d("TAG", "jsonArray complete");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
//                        Log.d("TAG", "onResponse : " + jObject);

                        String ParkName = jObject.getString("ParkName");
                        String LATITUDE = jObject.getString("Latitude");
                        String LONGITUDE = jObject.getString("Longitude");

                        double lat = Double.parseDouble(LATITUDE);
                        double lon = Double.parseDouble(LONGITUDE);

                        double checkDistance = calcDistance(getMyLocation().latitude, getMyLocation().longitude, lat, lon);

                        if(checkDistance <= 3) { // 3km 이내 공원
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(lat, lon));
                            markerOptions.title(ParkName);
                            Log.d("TAG", "markerOption complete");

                            googleMap.addMarker(markerOptions);
                            progressDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", String.valueOf(error));
            }
        }) {
            @androidx.annotation.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    protected void onResume() {
        super.onResume();
        ((MapView)this._$_findCachedViewById(id.mapView)).onResume();
    }

    protected void onPause() {
        super.onPause();
        ((MapView)this._$_findCachedViewById(id.mapView)).onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        ((MapView)this._$_findCachedViewById(id.mapView)).onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        ((MapView)this._$_findCachedViewById(id.mapView)).onLowMemory();
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
}
