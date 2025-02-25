package com.alexgl00.proyectodb.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.alexgl00.proyectodb.R;

public class Historia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.historia);

        CardView cardGoku = findViewById(R.id.cardViewHistoryGoku);
        CardView cardVegeta = findViewById(R.id.cardViewHistoryVegeta);
        CardView cardGohan = findViewById(R.id.cardViewHistoryGohan);
        CardView cardGokuBlack = findViewById(R.id.cardViewHistoryGokuBlack);


        cardGoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, HistoriaGoku.class);
                startActivity(intent);
            }
        });

        cardVegeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, HistoriaVegeta.class);
                startActivity(intent);
            }
        });

        cardGohan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, HistoriaGohan.class);
                startActivity(intent);
            }
        });

        cardGokuBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, HistoriaGokuBlack.class);
                startActivity(intent);
            }
        });
    }
}
