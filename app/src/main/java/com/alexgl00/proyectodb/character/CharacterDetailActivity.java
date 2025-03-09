package com.alexgl00.proyectodb.character;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl00.proyectodb.FavoriteCharacter;
import com.alexgl00.proyectodb.FavoriteCharactersAdapter;
import com.alexgl00.proyectodb.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private ImageView characterImage;
    private TextView characterName;
    private RecyclerView recyclerViewTransformations;
    private TransformationAdapter transformationAdapter;
    private DragonBallApiService apiService;
    private ImageView favoriteIcon;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FavoriteCharactersAdapter favoriteCharactersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail);

        characterImage = findViewById(R.id.characterImage);
        recyclerViewTransformations = findViewById(R.id.recyclerViewTransformations);
        characterName = findViewById(R.id.characterName);
        favoriteIcon = findViewById(R.id.favoriteIcon);
        apiService = ApiClient.getInstance().create(DragonBallApiService.class);

        recyclerViewTransformations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        sharedPreferences = getSharedPreferences("user_favorites", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        favoriteIcon.setEnabled(false); // Desactivamos el botón al inicio


        // Obtener el ID del personaje de la Intent
        String characterId = getIntent().getStringExtra("CHARACTER_ID");
        if (characterId != null) {
            Log.d("CharacterDetail", "Character ID recibido: " + characterId);
            loadCharacterDetails(characterId);
        } else {
            Log.e("CharacterDetail", "Character ID no encontrado");
            Toast.makeText(this, "Error: No se encontró el personaje", Toast.LENGTH_SHORT).show();
        }

        // Configurar el listener para el icono de favorito
        favoriteIcon.setOnClickListener(v -> handleFavoriteIconClick());


    }
    private void handleFavoriteIconClick() {
        try {
            String characterId = getIntent().getStringExtra("CHARACTER_ID");
            String characterNameText = characterName.getText().toString();
            String imageName = "personaje_" + characterNameText.toLowerCase(); // Nombre de la imagen en drawable

            if (characterId == null || characterNameText.isEmpty()) {
                Toast.makeText(this, "Error: Datos del personaje no encontrados", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recuperar los favoritos existentes
            String jsonFavorites = sharedPreferences.getString("favorites", "{}");
            Map<String, String> favoriteCharacters = new Gson().fromJson(jsonFavorites, new TypeToken<Map<String, String>>() {}.getType());

            if (favoriteCharacters == null) {
                favoriteCharacters = new HashMap<>();
            }

            // Agregar o quitar de favoritos
            if (favoriteCharacters.containsKey(characterId)) {
                favoriteCharacters.remove(characterId);
                favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
                Log.d("Favorites", "Personaje removido de favoritos: " + characterNameText);
            } else {
                favoriteCharacters.put(characterId, imageName); // Guardamos solo el ID y la imagen
                favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
                Log.d("Favorites", "Personaje agregado a favoritos: " + characterNameText);
            }

            // Guardar cambios en SharedPreferences
            String updatedFavorites = new Gson().toJson(favoriteCharacters);
            sharedPreferences.edit().putString("favorites", updatedFavorites).apply();

            // Actualizar el icono de favorito
            updateFavoriteIcon(characterId);

        } catch (Exception e) {
            Log.e("FavoritesError", "Error al manejar los favoritos: " + e.getMessage());
            Toast.makeText(this, "Hubo un error al agregar a favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCharacterDetails(String characterId) {
        Log.d("DEBUG", "Llamando a la API con ID: " + characterId);

        Call<Character> call = apiService.getCharacterById(characterId);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Character character = response.body();
                    characterName.setText(character.getName());
                    Glide.with(CharacterDetailActivity.this)
                            .load(character.getImage())
                            .into(characterImage);

                    // Carga las transformaciones
                    List<Transformation> transformations = character.getTransformations();
                    if (transformations != null && !transformations.isEmpty()) {
                        transformationAdapter = new TransformationAdapter(CharacterDetailActivity.this, transformations);
                        recyclerViewTransformations.setAdapter(transformationAdapter);
                        Log.d("RecyclerView", "Transformaciones cargadas: " + transformations.size());
                    } else {
                        Log.d("RecyclerView", "No se encontraron transformaciones");
                    }


                    favoriteIcon.setEnabled(true);
                    updateFavoriteIcon(characterName.getText().toString());
                    setCharacterAbilities(character.getName());
                } else {
                    characterName.setText("Error al obtener datos");
                    Log.e("API_ERROR", "Respuesta no exitosa: " + response.code());
                    Toast.makeText(CharacterDetailActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                characterName.setText("Error al cargar los datos");
                Log.e("API_ERROR", "Error en la conexion: " + t.getMessage());
                Toast.makeText(CharacterDetailActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteIcon(String characterId) {
        // Recuperar los favoritos existentes
        String jsonFavorites = sharedPreferences.getString("favorites", "{}");
        Map<String, String> favoriteCharacters = new Gson().fromJson(jsonFavorites, new TypeToken<Map<String, String>>() {}.getType());

        if (favoriteCharacters == null) {
            favoriteCharacters = new HashMap<>();
        }

        // Comprobar si el personaje está en favoritos
        if (favoriteCharacters.containsKey(characterId)) {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
            Log.d("Favorites", "Personaje esta en favoritos: " + characterName.getText().toString());

        } else {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            Log.d("Favorites", "Personaje no esta en favoritos: " + characterName.getText().toString());
        }
    }



    public static void openCharacterDetail(Context context, String characterId) {
        Intent intent = new Intent(context, CharacterDetailActivity.class);
        intent.putExtra("CHARACTER_ID", characterId);
        context.startActivity(intent);
    }

    // Aquí puedes agregar un método para establecer las transformaciones de los personajes, si es necesario.
    private void setCharacterAbilities(String characterName) {
        Map<String, List<String>> abilitiesMap = new HashMap<>();

        abilitiesMap.put("Goku", Arrays.asList("Teletransportación", "A tope", "SuperOndaVital", "Explosión dragón", "Supergenkidama"));
        abilitiesMap.put("Vegeta", Arrays.asList("Onda Explosiva", "Ya he calentado", "Cañón Galick", "Frenesi Absoluto", "Destello Final"));
        abilitiesMap.put("Gohan", Arrays.asList("Sentido Salvaje", "Carga al máximo", "Masenko", "Golpe Destello Explosivo", "Súper Kamehameha"));
        abilitiesMap.put("Trunks", Arrays.asList("Imagen Reflejada", "Potenciación al limite", "Destrozo Final", "Tormenta Ardiente", "Tajo de Espada de Luz"));
        abilitiesMap.put("Whis", Arrays.asList("Barrera", "Hora del aperitivo", "Preludio de la Destrucción", "Destrucción Sinfónica", "Epílogo de la Destrucción"));
        abilitiesMap.put("Bills", Arrays.asList("Sueño", "Veredicto de la Destrucción", "Esfera de Destrucción", "Hakai a la cabeza", "Esfera de Destrucción Súper"));
        abilitiesMap.put("Broly", Arrays.asList("Carga al máximo", "Perder el control", "Onda de Energía al Máximo", "Choque Gigante", "Impacto Gigante"));
        abilitiesMap.put("Majin Buu", Arrays.asList("Kaikosen", "Sueño", "Goma Bu Bu", "Súper Kamehameha", "Explosión Furiosa"));
        abilitiesMap.put("Freezer", Arrays.asList("Psicoquinesia", "Enardecimiento", "Rayo de la muerte", "Castigador Frenético", "Bola Mortal"));
        abilitiesMap.put("Piccolo", Arrays.asList("Kaikosen", "Al Máximo", "Onda Explosiva de Demonio", "Cañón de Aliento Explosivo", "Cañón de Haz Especial"));

        List<String> abilities = abilitiesMap.getOrDefault(characterName, Arrays.asList("Habilidades Desconocidas", "Habilidades Desconocidas", "Habilidades Desconocidas", "Habilidades Desconocidas", "Habilidades Desconocidas"));

        ((TextView) findViewById(R.id.tv_habilidad1)).setText(abilities.get(0));
        ((TextView) findViewById(R.id.tv_habilidad2)).setText(abilities.get(1));
        ((TextView) findViewById(R.id.tv_habilidad3)).setText(abilities.get(2));
        ((TextView) findViewById(R.id.tv_habilidad4)).setText(abilities.get(3));
        ((TextView) findViewById(R.id.tv_habilidad5)).setText(abilities.get(4));
    }
}
