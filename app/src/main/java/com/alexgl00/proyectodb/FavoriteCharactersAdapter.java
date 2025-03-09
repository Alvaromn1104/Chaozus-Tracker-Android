package com.alexgl00.proyectodb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCharactersAdapter extends RecyclerView.Adapter<FavoriteCharactersAdapter.ViewHolder> {

    private Context context;
    private List<FavoriteCharacter> favoriteCharacters;

    public FavoriteCharactersAdapter(Context context, List<FavoriteCharacter> favoriteCharacters) {
        this.context = context;
        this.favoriteCharacters = favoriteCharacters;
        Log.d("FavoriteCharactersAdapter", "Constructor llamado, tamaño de favoriteCharacters: " + favoriteCharacters.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el layout para cada elemento de la lista
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_character, parent, false);
        Log.d("Adapter", "Inflando el layout para un nuevo ViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteCharacter favoriteCharacter = favoriteCharacters.get(position);
        Log.d("FavoriteCharactersAdapter", "Mostrando personaje: " + favoriteCharacter.getName() + " con imagen ID: " + favoriteCharacter.getImageResId());

        // Mostrar solo la imagen
        holder.characterImagefav.setImageResource(favoriteCharacter.getImageResId());
        // Para agregar el nombre también
        //holder.characterNamefav.setText(favoriteCharacter.getName());


    }

    @Override
    public int getItemCount() {
        Log.d("FavoriteCharactersAdapter", "Total de personajes favoritos: " + favoriteCharacters.size());
        return favoriteCharacters.size();
    }

    public void updateFavorites(List<FavoriteCharacter> updatedFavorites) {
        if (updatedFavorites != null && !updatedFavorites.isEmpty()) {
            Log.d("FavoriteCharactersAdapter", "Lista de favoritos recibida no vacia. Tamano antes de actualizar: " + favoriteCharacters.size());
            // Crear una nueva lista temporal sin duplicados
            List<FavoriteCharacter> tempFavorites = new ArrayList<>();

            for (FavoriteCharacter newCharacter : updatedFavorites) {
                if (!favoriteCharacters.contains(newCharacter)) {
                    tempFavorites.add(newCharacter);
                }
            }

            // Limpiamos la lista anterior
            favoriteCharacters.addAll(tempFavorites); // Añadimos los elementos sin duplicados
            Log.d("FavoriteCharactersAdapter", "Lista de favoritos actualizada, tamano: " + favoriteCharacters.size());
        } else {
            Log.d("FavoriteCharactersAdapter", "Lista de favoritos recibida vacia.");
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImagefav;
        TextView characterNamefav; // Mostrar el nombre

        public ViewHolder(View itemView) {
            super(itemView);
            characterImagefav = itemView.findViewById(R.id.characterImagefav);
            characterNamefav = itemView.findViewById(R.id.characterNamefav);
        }
    }
}
