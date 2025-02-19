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

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);

        TextInputEditText editNameTIL = findViewById(R.id.edit_name);
        TextInputEditText editLastnameTIL = findViewById(R.id.edit_lastname);
        TextInputEditText editUserTIL = findViewById(R.id.edit_user);
        TextInputEditText editEmailRegisterTIL = findViewById(R.id.edit_emailRegister);
        TextInputEditText editPasswordRegisterTIL = findViewById(R.id.edit_passwordRegister);
        TextInputEditText editRepeatPasswordTIL = findViewById(R.id.edit_repeatPassword);
        Button btnCreate = findViewById(R.id.btnCreate);
        TextView DBreturn = findViewById(R.id.DBreturn);
        CardView cardViewRegister = findViewById(R.id.cardViewRegister);

        cardViewRegister.setAlpha(0f);
        cardViewRegister.setTranslationY(100f);

        new Handler().postDelayed(()->{
            cardViewRegister.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(1000)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }, 1500);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =editNameTIL.getText().toString().trim();
                String lastname = editLastnameTIL.getText().toString().trim();
                String user = editUserTIL.getText().toString().trim();
                String email = editEmailRegisterTIL.getText().toString().trim();
                String password = editPasswordRegisterTIL.getText().toString().trim();
                String repeatPassword = editRepeatPasswordTIL.getText().toString().trim();

                if (name.isEmpty() || lastname.isEmpty() || user.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_LONG).show();
                } else if (!password.equals(repeatPassword)) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", user);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "¡Registro exitoso!", Toast.LENGTH_LONG).show();
                    launchLogin();
                }
            }
        });

        DBreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLogin();
            }
        });
    }

    public void launchLogin() {
        Intent intent = new Intent(Register.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
