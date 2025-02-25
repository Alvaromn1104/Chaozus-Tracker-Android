package com.alexgl00.proyectodb.news;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.alexgl00.proyectodb.R;

public class News1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.news1);

        WebView webViewNews1 = findViewById(R.id.webViewNews1);

        webViewNews1.getSettings().setJavaScriptEnabled(true);

        String videoHtml = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' " +
                "src='https://www.youtube.com/embed/E2NOsM9u7wE?autoplay=1&rel=0' " +
                "frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe></body></html>";

        webViewNews1.loadDataWithBaseURL(null, videoHtml, "text/html", "utf-8", null );
    }
}
