package com.alexgl00.proyectodb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class Cargando extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargando);

        VideoView videoView = findViewById(R.id.videoView);

        // Ajustar el tamaño del VideoView a pantalla completa
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        videoView.setLayoutParams(params);

        // Cargar el video desde res/raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.load;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(false); // No repetir el video
            videoView.start();
        });

        // FORZAR el cambio a MainActivity después de 4 segundos
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Cargando.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 4000);
    }
}
