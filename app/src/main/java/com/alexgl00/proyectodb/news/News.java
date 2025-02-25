package com.alexgl00.proyectodb.news;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.alexgl00.proyectodb.R;

public class News extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.news);

        WebView webView = findViewById(R.id.webView);

        Button buttonSaberMas = findViewById(R.id.buttonSaberMas);
        Button buttonSaberMas2 = findViewById(R.id.buttonSaberMas2);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());

        String videoHtml = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/E2NOsM9u7wE?autoplay=1&rel=0' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe></body></html>";

        webView.loadDataWithBaseURL(null, videoHtml, "text/html", "utf-8", null);

        //---------------------------------------------------------------


        buttonSaberMas.setOnClickListener(v -> {
            Intent intent = new Intent(News.this, News1.class);
            startActivity(intent);
        });
        buttonSaberMas2.setOnClickListener(v -> {
            Intent intent = new Intent( News.this, News2.class);
            startActivity(intent);
        });

    }
}
