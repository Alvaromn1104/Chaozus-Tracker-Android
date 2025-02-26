package com.alexgl00.proyectodb.character;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl00.proyectodb.R;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private ImageView characterImage;
    private TextView characterName;
    private RecyclerView recyclerViewTransformations;
    private TransformationAdapter transformationAdapter;
    private DragonBallApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail);

        characterImage = findViewById(R.id.characterImage);
        recyclerViewTransformations = findViewById(R.id.recyclerViewTransformations);
        characterName = findViewById(R.id.characterName);

        recyclerViewTransformations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Obtener el ID del personaje desde el Intent
        String characterId = getIntent().getStringExtra("CHARACTER_ID");

        // Verificar si el ID es válido
        if (characterId == null || characterId.isEmpty()) {
            characterId = "1"; // Cambiar esto por un ID válido de la API
            Log.w("DEBUG", "ID nulo o vacío, asignando ID por defecto: " + characterId);
        }

        Log.d("DEBUG", "ID recibido en Intent: " + characterId);

        // Obtener la instancia de Retrofit
        apiService = ApiClient.getInstance().create(DragonBallApiService.class);

        // Cargar los detalles del personaje
        loadCharacterDetails(characterId);
    }

    public static void openCharacterDetail(Context context, String characterId) {
        Intent intent = new Intent(context, CharacterDetailActivity.class);
        intent.putExtra("CHARACTER_ID", characterId);
        context.startActivity(intent);
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
                    }

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
                Log.e("API_ERROR", "Error en la conexión: " + t.getMessage());
                Toast.makeText(CharacterDetailActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
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
