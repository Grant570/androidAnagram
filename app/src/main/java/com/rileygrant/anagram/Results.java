package com.rileygrant.anagram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Results extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle bundle = getIntent().getExtras();
        String FinalScore = bundle.getString("FSCORE");
        final TextView FScore = (TextView)findViewById(R.id.ScoreView);
        FScore.setText("Score: " + FinalScore);

    }

    public void onClickReplay(View v) {
        Intent mintent = new Intent(Results.this, MainActivity.class);
        startActivity(mintent);
    }
}