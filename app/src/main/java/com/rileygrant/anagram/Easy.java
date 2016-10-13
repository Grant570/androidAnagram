package com.rileygrant.anagram;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Easy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

    }

    public void onClickSubmit(View v){
//testing pickWords- disregard
//        TextView textView = (TextView)findViewById(R.id.UserInput);
//        textView.setText(pickWords(1,"hard")[0]);
        Intent mintent = new Intent(Easy.this, Results.class);
        startActivity(mintent);
    }
    //http://www.technotalkative.com/android-read-file-from-assets/
    //http://stackoverflow.com/questions/15286182/android-reading-a-text-file-and-storing-to-an-arrayliststring
    String[] pickWords(int count,String difficulty){
        AssetManager assetManager = getAssets();
        ArrayList<String> words = new ArrayList<String>();
        InputStream inputStream;
        String inputFile;
        if(difficulty.toLowerCase().equals("easy")){
            inputFile = "easy.txt";
        }
        else if(difficulty.toLowerCase().equals("medium")){
            inputFile = "medium.txt";
        }
        else{
            inputFile = "hard.txt";
        }
        try{
            inputStream = assetManager.open(inputFile);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(streamReader);
            String word; //= br.readLine();
            while((word = br.readLine())!= null){
                words.add(word);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Random r = new Random();
        int randomNum;
        String[] chosenWords = new String[count];
        for(int i = 0; i < count; i++){
            randomNum = r.nextInt((words.size()-1) + 1);
            chosenWords[i] = words.remove(randomNum);
        }
        return chosenWords;
    }

}
