package com.alexgl00.proyectodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alexgl00.proyectodb.history.Historia;
import com.alexgl00.proyectodb.news.News;
import com.alexgl00.proyectodb.trofeo.Platino;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonDLC = findViewById(R.id.Dlcs);
        Button buttonNews = findViewById(R.id.News);
        Button buttonMyAccount = findViewById(R.id.MyAccount);
        Button buttonAcceder = findViewById(R.id.acceder);
        ImageButton imageButtonPersonajes = findViewById(R.id.Personajes);
        ImageButton imageButtonControles = findViewById(R.id.controles);
        ImageButton imageButtonHistoria = findViewById(R.id.historia);
        ImageButton imageButtonPlatino = findViewById(R.id.platino);

        SharedPreferences sharedPreferences= getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", null);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn && userName != null) {
            buttonAcceder.setText(userName);
        }


        buttonAcceder.setOnClickListener(v -> {
            if (!isLoggedIn) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                startActivity(intent);
            }
        });





        buttonDLC.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Dlcs.class);
            startActivity(intent);
        });

        buttonNews.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, News.class);
            startActivity(intent);
        });

        buttonMyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyAccount.class);
            startActivity(intent);
        });

        imageButtonPersonajes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Personajes.class);
            startActivity(intent);
        });

        imageButtonControles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Controles.class);
            startActivity(intent);
        });

        imageButtonHistoria.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Historia.class);
            startActivity(intent);
        });

        imageButtonPlatino.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Platino.class);
            startActivity(intent);
        });

    }
}