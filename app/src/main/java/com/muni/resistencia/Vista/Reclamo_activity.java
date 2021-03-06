package com.muni.resistencia.Vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Presentador.ReclamoPresentador;
import com.muni.resistencia.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import me.toptas.fancyshowcase.FancyShowCaseView;

public class Reclamo_activity extends AppCompatActivity implements OnMapReadyCallback, ReclamoInterface.Vista{

    SupportMapFragment mapFragment;
    TextView ubicacion;
    ConstraintLayout coordinatorLayout;
    Button reclamoBtn;
    Marker marker = null;
    LatLng latLong;
    String idServicio = "";
    String idContravencion = "";
    String tipo, idComision;
    ReclamoInterface.Presentador presentador;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclamo_activity);
        presentador = new ReclamoPresentador(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idServicio = extras.getString("idServicio","");
            idContravencion = extras.getString("idContravencion","");
            idComision = extras.getString("IdComision");
            tipo = extras.getString("tipo");
        }
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Registrando reclamo..")
                .build();
        ubicacion = findViewById(R.id.ubicacion);
        coordinatorLayout =findViewById(R.id.coordinatorLayout);
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("showcase")
                .setUseDefaultSharedPreference(true)
                .build();
        reclamoBtn = findViewById(R.id.reclamo);
        reclamoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ubicacion.getText().equals("")){
                     Snackbar snackbar = Snackbar.make(coordinatorLayout, "Selecciona una ubicación", Snackbar.LENGTH_LONG);
                     snackbar.show();
                }else{
                    dialog.show();
                    presentador.guardarReclamo(idComision, idServicio, idContravencion, ubicacion.getText().toString());
                }
            }
        });
        if(!Prefs.getBoolean("showcasereclamo",false)){
            showcase();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-27.451024, -58.986687), 15));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latLong = latLng;
                getAddressFromLatLng(getApplicationContext(), latLng);
                if(marker!=null)
                marker.remove();
                marker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).position(latLng));
            }
        });
    }

    private void showcase(){
        Prefs.putBoolean("showcasereclamo",true);
        new FancyShowCaseView.Builder(this)
                .focusOn(findViewById(R.id.map))
                .title("Toque sobre el mapa para elegir la ubicación del reclamo")
                .build()
                .show();
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

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }

    @Override
    public void mostrarToast(final String mensaje) {
        dialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(Reclamo_activity.this, mensaje,  Toast.LENGTH_LONG);
                toast.show();
                ubicacion.setText("");
                marker.remove();
            }
        });
    }
}
