package com.SMP.dodamdodam.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.SMP.dodamdodam.Activity.MainActivity;
import com.SMP.dodamdodam.Activity.findPark;
import com.SMP.dodamdodam.Activity.loginActivity;
import com.SMP.dodamdodam.R;
import com.SMP.dodamdodam.SharedPreferenceBean;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

@Metadata(
        mv = {1, 4, 2},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u0004H\u0007J\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0007J\u0012\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\b\u0010#\u001a\u00020\u001fH\u0014J\b\u0010$\u001a\u00020\u001fH\u0016J\u0006\u0010%\u001a\u00020\u001fJ\b\u0010&\u001a\u00020\u001fH\u0014J-\u0010'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u00122\u000e\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\f2\u0006\u0010*\u001a\u00020+H\u0016¢\u0006\u0002\u0010,J\b\u0010-\u001a\u00020\u001fH\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u0012X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006."},
        d2 = {"Lcom/SMP/dodamdodam/Activity/findPark;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "CITY_HALL", "Lcom/google/android/gms/maps/model/LatLng;", "getCITY_HALL", "()Lcom/google/android/gms/maps/model/LatLng;", "DEFAULT_ZOOM_LEVEL", "", "getDEFAULT_ZOOM_LEVEL", "()F", "PERMISSIONS", "", "", "getPERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "REQUEST_PERMISSION_CODE", "", "getREQUEST_PERMISSION_CODE", "()I", "googleMap", "Lcom/google/android/gms/maps/GoogleMap;", "getGoogleMap", "()Lcom/google/android/gms/maps/GoogleMap;", "setGoogleMap", "(Lcom/google/android/gms/maps/GoogleMap;)V", "getMyLocation", "hasPermissions", "", "initMap", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLowMemory", "onMyLocationButtonClick", "onPause", "onRequestPermissionsResult", "requestCode", "permissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "app_debug"}
)
public class MapsFrag extends Fragment {


    @Override
    public void onStart() {
        super.onStart();
    }

    protected View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);

        return view;


    }
}
