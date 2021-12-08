package com.example.websave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class rattig_bar extends AppCompatActivity {
    RatingBar rating_bar;
    Button rating_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rattig_bar);
        rating_bar=findViewById(R.id.rating_bar);
        rating_button=findViewById(R.id.rating_button);

        rating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(rating_bar.getRating());
                Toast.makeText(getApplicationContext(),s+ "Star", Toast.LENGTH_SHORT).show();
            }
        });
    }
}