package com.rileygrant.anagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }
    public void onClickReplay(View v) {
        Intent mintent = new Intent(Results.this, MainActivity.class);
        startActivity(mintent);
    }
}