package com.rileygrant.anagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Hard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
    }
    public void onClickSubmit(View v) {
        Intent mintent = new Intent(Hard.this, Results.class);
        startActivity(mintent);
    }
}
