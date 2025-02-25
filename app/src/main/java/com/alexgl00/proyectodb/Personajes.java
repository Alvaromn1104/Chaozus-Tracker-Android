package com.alexgl00.proyectodb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.alexgl00.proyectodb.character.CharacterDetailActivity;

public class Personajes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.personajes);


        ImageView imageViewGoku = findViewById(R.id.imageViewGoku);
        ImageView imageViewVegeta = findViewById(R.id.imageViewVegeta);
        ImageView imageViewWis = findViewById(R.id.imageViewWis);
        ImageView imageViewBills = findViewById(R.id.imageViewBills);
        ImageView imageViewTrunks = findViewById(R.id.imageViewTrunksZ);
        ImageView imageViewGohan = findViewById(R.id.imageViewGohan);


        imageViewGoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "1");
            }
        });

        imageViewVegeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "2");
            }
        });

        imageViewGohan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "10");
            }
        });

        imageViewTrunks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "16");
            }
        });

        imageViewBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "33");
            }
        });

        imageViewWis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDetailActivity.openCharacterDetail(Personajes.this, "34");
            }
        });


    }
}
