package com.alexgl00.proyectodb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAccount extends AppCompatActivity {

    private TextView txtUsername, txtEmail, txtAboutMe;
    private EditText editAboutMe;
    private Button btnEditAboutMe, btnRegister, btnLogout;
    private SharedPreferences preferences;
    private boolean isEditing = false;
    private RecyclerView recyclerViewFavorites;
    private FavoriteCharactersAdapter adapter;
    private List<FavoriteCharacter> favoriteCharacters = new ArrayList<>();
    private Map<String, String> characterToDrawableMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        // Inicialización de vistas
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtAboutMe = findViewById(R.id.txtAboutMe);
        editAboutMe = findViewById(R.id.editAboutMe);
        btnEditAboutMe = findViewById(R.id.btnEditAboutMe);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogout = findViewById(R.id.btnLogout);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Configurar el Adapter
        adapter = new FavoriteCharactersAdapter(this, favoriteCharacters);
        recyclerViewFavorites.setAdapter(adapter);



        preferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String aboutMe = preferences.getString("aboutMe", "");
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (username != null && email != null && isLoggedIn) {
            // Usuario registrado
            txtUsername.setText("Usuario: " + username);
            txtEmail.setText("Email: " + email);
            txtAboutMe.setText(aboutMe.isEmpty() ? "No has escrito nada sobre ti" : aboutMe);
            findViewById(R.id.linearLayoutAboutMe).setVisibility(View.VISIBLE);
            // Cargar personajes favoritos
            loadFavoriteCharacters();
            btnRegister.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            // Usuario no registrado
            txtUsername.setText("No has iniciado sesión");
            txtEmail.setText("");
            btnRegister.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            findViewById(R.id.linearLayoutAboutMe).setVisibility(View.GONE);
        }

        // Editar sobre mí
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

        // Ir a la pantalla de registro
        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(MyAccount.this, Login.class));
        });

        // Cerrar sesión
        btnLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(MyAccount.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void initCharacterToDrawableMap() {
        characterToDrawableMap = new HashMap<>();
        characterToDrawableMap.put("Bills", "personaje_bills");
        characterToDrawableMap.put("33", "personaje_bills");
        characterToDrawableMap.put("Goku", "personaje_goku");
        characterToDrawableMap.put("1", "personaje_goku");
        characterToDrawableMap.put("Vegeta", "personaje_vegeta");
        characterToDrawableMap.put("2", "personaje_vegeta");
        characterToDrawableMap.put("Gohan", "personaje_gohan");
        characterToDrawableMap.put("10", "personaje_gohan");
        characterToDrawableMap.put("trunks", "personaje_trunks");
        characterToDrawableMap.put("16", "personaje_trunks");
        characterToDrawableMap.put("Whis", "personaje_whis");
        characterToDrawableMap.put("34", "personaje_whis");
        characterToDrawableMap.put("Piccolo", "personaje_piccolo");
        characterToDrawableMap.put("3", "personaje_piccolo");
        characterToDrawableMap.put("Broly", "personaje_broly");
        characterToDrawableMap.put("68", "personaje_broly");
        characterToDrawableMap.put("Majin buu", "personaje_majinbuu");
        characterToDrawableMap.put("32", "personaje_broly");
        characterToDrawableMap.put("Freezer", "personaje_freezer");
        characterToDrawableMap.put("5", "personaje_broly");
    }

    private void loadFavoriteCharacters() {
        initCharacterToDrawableMap();
        SharedPreferences favoritePreferences = getSharedPreferences("user_favorites", Context.MODE_PRIVATE);
        String jsonFavorites = favoritePreferences.getString("favorites", "");
        Log.d("MyAccount", "Cargando favoritos: " + jsonFavorites);

        if (jsonFavorites.isEmpty()) {
            favoriteCharacters.clear();
            adapter.updateFavorites(favoriteCharacters);
            Log.d("MyAccount", "No se encontraron favoritos.");
            return;
        }

        Map<String, String> favoriteMap;
        try {
            favoriteMap = new Gson().fromJson(jsonFavorites, new TypeToken<Map<String, String>>() {}.getType());
            Log.d("MyAccount", "Mapa de favoritos deserializado: " + favoriteMap.toString());
        } catch (JsonSyntaxException e) {
            favoriteCharacters.clear();
            adapter.updateFavorites(favoriteCharacters);
            Log.e("MyAccount", "Error al analizar JSON: " + e.getMessage());
            return;
        }

        favoriteCharacters.clear();
        for (String characterId : favoriteMap.keySet()) {
            Log.d("MyAccount", "Procesando characterId: " + characterId);
            String imageName = characterToDrawableMap.get(characterId);
            if (imageName != null) {
                int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imageResId != 0) {
                    // Cargar imagen con Glide
                    Glide.with(this)
                            .load(imageResId)
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    Log.d("MyAccount", "Imagen cargada para characterId: " + characterId);
                                    favoriteCharacters.add(new FavoriteCharacter(characterId, imageName, imageResId));
                                    adapter.updateFavorites(favoriteCharacters);  // Actualizar el adaptador después de agregar todos los personajes
                                }
                            });
                } else {
                    Log.d("MyAccount", "ID de recurso no encontrado para: " + imageName);
                }
            } else {
                Log.d("MyAccount", "Nombre de imagen no encontrado en el mapa para characterId: " + characterId);
            }
        }

        // Cuando se han agregado todos los personajes, se actualiza el adaptador
        if (!favoriteCharacters.isEmpty()) {
            Log.d("MyAccount", "Personajes favoritos cargados.");
            adapter.updateFavorites(favoriteCharacters);
        }
    }

}
