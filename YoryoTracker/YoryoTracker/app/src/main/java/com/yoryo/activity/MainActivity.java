package com.yoryo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yoryo.R;
import com.yoryo.base.MyApplicationClass;
import com.yoryo.model.Location;
import com.yoryo.util.AppPreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float zoomLevel = 15f;
    private LatLng deviceLatLang;
    private Marker deviceMarker;

    private NotificationBroadcastReceiver mNotificationBroadcastReceiver = null;
    private IntentFilter mIntentFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mNotificationBroadcastReceiver = new NotificationBroadcastReceiver(this);
        this.mIntentFilter = new IntentFilter("BROADCAST_MESSAGE_NOTIFICATION_RECEIVED");
        FirebaseMessaging.getInstance().subscribeToTopic("Tracking");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        callResetAPIInBackground();
    }

    private void callResetAPIInBackground() {
        String dataUrl = AppPreferenceStorage.getAppUrl();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, dataUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("result", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("MainActivity", "Error: " + error.getMessage());
                    }
                });

        MyApplicationClass.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng india = new LatLng(18.6444261, 73.7643917);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, zoomLevel));
    }

    private void updateMapMarker(String mapData){
        try {
            Location location = new Location();
            JSONObject jsonObject = new JSONObject(mapData);
            location.setLat(Double.parseDouble(jsonObject.optString("lat")));
            location.setLang(Double.parseDouble(jsonObject.optString("lang")));

            plotLatLangOnMap(location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void plotLatLangOnMap(Location location) {
        deviceLatLang = new LatLng(location.getLat(), location.getLang());
        MarkerOptions busMarkerOptions = new MarkerOptions()
                .position(deviceLatLang)
                .title("Yoryo Bus")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp));
        deviceMarker = mMap.addMarker(busMarkerOptions);
        deviceMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLatLang, zoomLevel));
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {

        WeakReference<MainActivity> mMainActivity;

        public NotificationBroadcastReceiver(MainActivity mainActivity) {
            this.mMainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity mainActivity = mMainActivity.get();
            if (mainActivity != null) {
                Bundle extras = intent.getExtras();
                if (extras != null && extras.containsKey("mapData")) {
                    String notificationInfo = extras.getString("mapData");
                    mainActivity.notificationReceived(notificationInfo);
                }
            }
        }
    }

    public void notificationReceived(String notificationInfo) {
        updateMapMarker(notificationInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mNotificationBroadcastReceiver, this.mIntentFilter);
    }

    @Override
    protected void onPause() {
        if (this.mNotificationBroadcastReceiver != null) {
            //unregisterReceiver(mNotificationBroadcastReceiver);
            //this.mNotificationBroadcastReceiver = null;
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationBroadcastReceiver);
        }
        super.onPause();
    }
}
