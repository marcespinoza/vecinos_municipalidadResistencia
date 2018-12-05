package com.muni.resistencia.Vista;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.muni.resistencia.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Reclamo_activity extends AppCompatActivity implements OnMapReadyCallback{

    SupportMapFragment mapFragment;
    TextView ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclamo_activity);
        ubicacion = findViewById(R.id.ubicacion);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(-58.968950509040759,-27.444941694444466), new LatLng(-58.967193610150908,-27.446500676156219), new LatLng(-58.972186226736987,-27.450979087011429),new LatLng( -58.973943129909856,-27.449420133564697),new LatLng( -58.968950509040759,-27.444941694444466))
                .strokeColor(Color.CYAN).fillColor(Color.TRANSPARENT));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-27.451024, -58.986687), 15));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                getAddressFromLatLng(getApplicationContext(), latLng);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).position(latLng));
            }
        });
    }

    private void getAddressFromLatLng(Context context, LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ubicacion.setText(addresses.get(0).getAddressLine(0));

    }
    
}
