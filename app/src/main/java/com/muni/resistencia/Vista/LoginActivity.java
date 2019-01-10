package com.muni.resistencia.Vista;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.flashbar.Flashbar;
import com.muni.resistencia.Interfaces.LoginInterface;
import com.muni.resistencia.Presentador.LoginPresentador;
import com.muni.resistencia.R;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginInterface.Vista {

    @BindView(R.id.login_button) Button login;
    private LoginInterface.Presentador lPresentador;
    private AlertDialog dialog;
    @BindView(R.id.documento) EditText documento;
    @BindView(R.id.clave) EditText clave;
    @BindView(R.id.checksesion) CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initPrefs();
        if(Prefs.getBoolean("login",false)) {
            Intent intent = new Intent(LoginActivity.this, Servicios_activity.class);
            intent.putExtra("idComision",Prefs.getString("idComision",""));
            startActivity(intent);
            finish();
        }
        dialog = new SpotsDialog.Builder()
                .setContext(LoginActivity.this)
                .setMessage("Espere por favor..")
                .build();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    enviarUsuario();

            }
        });
        lPresentador = new LoginPresentador(this);
    }

    void initPrefs(){
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @Override
    public void enviarUsuario() {
        dialog.show();
        lPresentador.enviarUsuario(documento.getText().toString(), clave.getText().toString());
    }

    @Override
    public void campoVacio() {
        dialog.dismiss();
        new Flashbar.Builder(LoginActivity.this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .duration(3000)
                .message("Rellene todos los campos")
                .build()
        .show();
    }

    @Override
    public void errorUsuario() {
        dialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Flashbar.Builder(LoginActivity.this)
                        .gravity(Flashbar.Gravity.TOP)
                        .duration(3000)
                        .backgroundColorRes(R.color.red_400)
                        .message("Usuario no encontrado")
                        .build().show();
            }
        });
    }

    @Override
    public void exitoLogin(String idComision) {
        dialog.dismiss();
        if(check.isChecked()){
            Prefs.putBoolean("login",true);
        }else{
            Prefs.putBoolean("login",false);
        }
        Prefs.putString("idComision", idComision);
        Intent intent = new Intent(this, Servicios_activity.class);
        intent.putExtra("idComision",idComision);
        startActivity(intent);
        finish();
    }
}
