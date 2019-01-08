package com.muni.resistencia.Vista;

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
import com.muni.resistencia.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.toptas.fancyshowcase.FancyShowCaseView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Reclamo_activity extends AppCompatActivity implements OnMapReadyCallback{

    SupportMapFragment mapFragment;
    TextView ubicacion;
    ConstraintLayout coordinatorLayout;
    Button reclamoBtn;
    Marker marker = null;
    LatLng latLong;
    String IdServicio, IdComision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclamo_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            IdServicio = extras.getString("IdServicio");
            IdComision = extras.getString("IdComision");
        }
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
                    guardarReclamo();
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
        googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(-27.444860305988339,-58.993571074209129), new LatLng(-27.438544438076047,-58.986453427127174), new LatLng(-27.444910561872952,-58.979515227145235),new LatLng( -27.451112212282002,-58.986513341637689),new LatLng( -27.444860305988339,-58.993571074209129))
                .strokeColor(Color.CYAN).fillColor(Color.TRANSPARENT));
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

    private void guardarReclamo(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());


        post( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Reclamo_activity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ubicacion.setText("");
                        Toast.makeText(Reclamo_activity.this, "Error al registrar el reclamo", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Reclamo_activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ubicacion.setText("");
                            Toast.makeText(Reclamo_activity.this, "Reclamo registrado", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Reclamo_activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Reclamo_activity.this, "Error al registrar reclamo", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }

    Call post(Callback callback) {
        OkHttpClient client = new OkHttpClient();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = df.format(Calendar.getInstance().getTime());
        RequestBody formBody = new FormBody.Builder()
                .add("idServicio", "20")
                .add("idComision", "20")
                .add("ubicacion", ubicacion.getText().toString())
                .add("estado", "Your message")
                .add("fecha", fechaActual)
                .build();
        Request request = new Request.Builder()
                .url("http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/reclamo")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private void showcase(){
        Prefs.putBoolean("showcasereclamo",true);
        new FancyShowCaseView.Builder(this)
                .focusOn(findViewById(R.id.map))
                .title("Mantenga presionado sobre el mapa para elegir la ubicación del reclamo")
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
    
}
