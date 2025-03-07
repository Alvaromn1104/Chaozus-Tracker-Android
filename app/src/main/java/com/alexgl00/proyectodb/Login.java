package com.alexgl00.proyectodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private static final String PREFS_NAME = "Usuario";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "userPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        TextInputEditText editEmailTIL = findViewById(R.id.edit_email);
        TextInputEditText editPasswordTIL = findViewById(R.id.edit_password);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView createAccount = findViewById(R.id.DBCreateAccount);
        CardView cardViewLogin = findViewById(R.id.cardViewLogin);

        SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);

        // Configura la animacion incial
        cardViewLogin.setAlpha(0f);
        cardViewLogin.setTranslationY(100f);

        // Retasamos la animacion 1.5 segundos
        new Handler().postDelayed(() ->{
            cardViewLogin.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(1000)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }, 1500);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailTIL.getText().toString();
                String password = editPasswordTIL.getText().toString();

                String registeredEmail = preferences.getString("email", "anonimo");
                String registeredPassword = preferences.getString("password", "anonimo");
                String registeredUsername = preferences.getString("username", "anonimo");

                if (!email.equals(registeredEmail)) {
                    Toast.makeText(getApplicationContext(), "Correo electrónico incorrecto", Toast.LENGTH_LONG).show();
                } else if (!password.equals(registeredPassword)) {
                    Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                } else {

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", registeredUsername);
                    editor.apply();

                    launchMain();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegister();
            }
        });
    }

    private void launchMain() {
        Intent intent = new Intent(Login.this, Cargando.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void launchRegister() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}
