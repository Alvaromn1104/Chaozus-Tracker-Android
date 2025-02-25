package com.alexgl00.proyectodb.character;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DragonBallApiService {

    @GET("characters/{id}")
    Call<Character> getCharacterById(@Path("id") String characterId);
}
