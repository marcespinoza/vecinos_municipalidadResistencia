package com.muni.resistencia.Vista;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.flashbar.Flashbar;
import com.muni.resistencia.Interfaces.LoginInterface;
import com.muni.resistencia.Presentador.LoginPresentador;
import com.muni.resistencia.R;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginInterface.Vista {

    private TextView bienvenido;
    private Button login;
    private LoginInterface.Presentador lPresentador;
    private AlertDialog dialog;
    private EditText documento, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new SpotsDialog.Builder()
                .setContext(LoginActivity.this)
                .setMessage("Espere por favor..")
                .build();
        documento = findViewById(R.id.documento);
        clave = findViewById(R.id.clave);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarUsuario();
            }
        });
        lPresentador = new LoginPresentador(this);
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
    public void exitoLogin() {
        dialog.dismiss();
        Intent Intent = new Intent(this, Servicios_activity.class);
        startActivity(Intent);
    }
}
