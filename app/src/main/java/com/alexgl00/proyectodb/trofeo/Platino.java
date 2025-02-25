package com.alexgl00.proyectodb.trofeo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl00.proyectodb.R;

import java.util.ArrayList;

public class Platino extends AppCompatActivity {

    ArrayList<Platino_item> platinoItem = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platino);

        RecyclerView recyclerViewPlatino = findViewById(R.id.recyclerViewPlatino);
        setPlatino();

        PlatinoRVAdapter adapter = new PlatinoRVAdapter(
                this, platinoItem
        );

        recyclerViewPlatino.setAdapter(adapter);
        recyclerViewPlatino.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setPlatino() {
        String[] platinoName = getResources().getStringArray(R.array.platino_name);
        String[] platinoDescription = getResources().getStringArray(R.array.platino_description);

        for(int i = 0; i < platinoName.length; i++) {
            platinoItem.add(new Platino_item(
                    platinoName[i],
                    platinoDescription[i]
            )
            );

        }
    }
}



