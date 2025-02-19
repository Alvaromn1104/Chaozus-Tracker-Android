package com.alexgl00.proyectodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccount extends AppCompatActivity {

    private TextView txtUsername, txtEmail, txtAboutMe;
    private EditText editAboutMe;
    private Button btnEditAboutMe, btnRegister, btnLogout;
    private SharedPreferences preferences;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtAboutMe = findViewById(R.id.txtAboutMe);
        editAboutMe = findViewById(R.id.editAboutMe);
        btnEditAboutMe = findViewById(R.id.btnEditAboutMe);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogout = findViewById(R.id.btnLogout);

        preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String aboutMe = preferences.getString("aboutMe", "");
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn" , false);

        if (username != null && email != null && isLoggedIn) {
            // Usuario registrado
            txtUsername.setText("Usuario: " + username);
            txtEmail.setText("Email: " + email);
            txtAboutMe.setText(aboutMe.isEmpty() ? "No has escrito nada sobre ti" : aboutMe);
            findViewById(R.id.linearLayoutAboutMe).setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            // Usuario no registrado
            txtUsername.setText("No has inciado sesión");
            txtEmail.setText("");
            btnRegister.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            findViewById(R.id.linearLayoutAboutMe).setVisibility(View.GONE);
        }

        btnEditAboutMe.setOnClickListener(view -> {
            if (!isEditing) {
                // Habilitar edición
                txtAboutMe.setVisibility(View.GONE);
                editAboutMe.setVisibility(View.VISIBLE);
                editAboutMe.setText(txtAboutMe.getText().toString());
                btnEditAboutMe.setText("Guardar");
                isEditing = true;
            } else {
                // Guardar cambios
                String newText = editAboutMe.getText().toString().trim();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("aboutMe", newText);
                editor.apply();

                txtAboutMe.setText(newText.isEmpty() ? "No has escrito nada sobre ti" : newText);
                txtAboutMe.setVisibility(View.VISIBLE);
                editAboutMe.setVisibility(View.GONE);
                btnEditAboutMe.setText("Editar");
                isEditing = false;
            }
        });

        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(MyAccount.this, Login.class));
        });
        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(MyAccount.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
