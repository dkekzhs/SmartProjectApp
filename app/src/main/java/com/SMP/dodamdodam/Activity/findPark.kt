package com.SMP.dodamdodam.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.location.LocationProvider
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.widget.Toast
import com.SMP.dodamdodam.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_maps.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class findPark : AppCompatActivity(){
    val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)

    val REQUEST_PERMISSION_CODE = 1

    val DEFAULT_ZOOM_LEVEL = 17f

    val CITY_HALL = LatLng(37.5662952, 126.97794509999994)

    var googleMap: GoogleMap ?= null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mapView.onCreate(savedInstanceState)

        if(hasPermissions()){
            initMap()
        }else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_CODE)
        }

        myLocationButton.setOnClickListener{ onMyLocationButtonClick() }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        initMap()
    }

    fun hasPermissions() : Boolean{
        for(permission in PERMISSIONS){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun initMap(){
        mapView.getMapAsync {

            googleMap = it

            it.uiSettings.isMyLocationButtonEnabled = false

            when{
                hasPermissions() -> {
                    it.isMyLocationEnabled = true
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
                }
                else -> {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(CITY_HALL, DEFAULT_ZOOM_LEVEL))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation() : LatLng{
        val locationProvider : String = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val lastKnownLocation : Location = locationManager.getLastKnownLocation(locationProvider) !!
        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }

    fun onMyLocationButtonClick(){
        when {
            hasPermissions() -> googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(getMyLocation(), DEFAULT_ZOOM_LEVEL))
            else -> Toast.makeText(applicationContext, "위치사용권한 설정에 동의해주세요", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume(){
        super.onResume()
        mapView.onResume()
    }

    override fun onPause(){
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy(){
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory(){
        super.onLowMemory()
        mapView.onLowMemory()
    }

//    val API_KEY = "626b4f4c4f736862383376696e5161"
//
//    var task : ParkReadTask? = null
//
//    var parks = JSONArray()
//
//    val bitmap by lazy {
//        val drawable = resources.getDrawable(R.drawable.restroom_sign, null) as BitmapDrawable
//        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
//    }
//
//    fun JSONArray.merge(anotherArray : JSONArray){
//        for (i in 0 until anotherArray.length()){
//            this.put(anotherArray.get(i))
//        }
//    }
//
//    fun readData(starIndex : Int, lastIndex : Int) : JSONObject{
//        val url =
//            URL("http://openAPI.seoul.go.kr:8088" + "/${API_KEY}/xml/SearchParkInfoService/${starIndex}/${lastIndex}")
//        val connection = url.openConnection()
//
//        val data = connection.getInputStream().readBytes().toString(charset("UTF-8"))
//        return JSONObject(data)
//    }
//
//    inner class ParkReadTask : AsyncTask<Void, JSONArray, String>(){
//        override fun onPreExecute() {
//            googleMap?.clear()
//            parks = JSONArray()
//        }
//
//        override fun doInBackground(vararg params: Void?): String {
//            val step = 1000
//            var startIndex = 1
//            var lastIndex = step
//            var totalCount = 0
//
//            do {
//                if(isCancelled) break
//
//                if (totalCount != 0){
//                    startIndex += step
//                    lastIndex += step
//                }
//
//                val jsonObject = readData(startIndex, lastIndex)
//
//                totalCount = jsonObject.getJSONObject("SearchParkInfoService").getInt("list_total_count")
//
//                val rows = jsonObject.getJSONObject("SearchParkInfoService").getJSONArray("row")
//
//                parks.merge(rows)
//
//                publishProgress(rows)
//            } while (lastIndex < totalCount)
//                return "complete"
//            }
//
//        override fun onProgressUpdate(vararg values: JSONArray?) {
//            val array = values [0]
//            array?.let {
//                for (i in 0 until array.length()){
//                    addMarkers(array.getJSONObject(i))
//            }
//        }
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        task?.cancel(true)
//        task = ParkReadTask()
//        task?.execute()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        task?.cancel(true)
//        task = null
//    }
//
//    fun addMarkers(park : JSONObject){
//        googleMap?.addMarker(
//            MarkerOptions()
//                .position(LatLng(park.getDouble("LATITUDE"), park.getDouble("LONGITUDE")))
//                .title(park.getString("FNAME"))
//                .snippet(park.getString("ANAME"))
//                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//        )
//    }
}

