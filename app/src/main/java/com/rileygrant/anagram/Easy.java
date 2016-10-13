package com.rileygrant.anagram;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
//word list http://www-personal.umich.edu/~jlawler/wordlist
public class Easy extends AppCompatActivity {
    //to keep track of answer and what to display
     private String answer;
     private List<String> words;
     private String shuffled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        //need to double check the numbers
        words = pickWords(4020,"easy");
        answer = words.remove(0);
        shuffled = shuffleStr(answer);
        final TextView textView = (TextView)findViewById(R.id.wordToGuess);
        textView.setText(shuffled);
        final EditText guess = (EditText)findViewById(R.id.guess);
        guess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //cool animation for when input is correct
            @Override
            public void afterTextChanged(Editable s) {
                if(guess.getText().toString().toLowerCase().equals(answer)){
                    guess.animate().alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            //clear input and change transparency
                            guess.setText("");
                            guess.animate().alpha(1);
                        }
                    });
                    //get a new word, clear old one
                    textView.animate().alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            answer = words.remove(0);
                            shuffled = shuffleStr(answer);
                            textView.setText("");
                            textView.setText(shuffled);
                            textView.animate().alpha(1);
                        }
                    });
                }
            }
        });
    }

    public void onClickSubmit(View v){
        Intent mintent = new Intent(Easy.this, Results.class);
        startActivity(mintent);
    }
    //http://www.technotalkative.com/android-read-file-from-assets/
    //http://stackoverflow.com/questions/15286182/android-reading-a-text-file-and-storing-to-an-arrayliststring
    //pick words from lists in assets
    List<String> pickWords(int count,String difficulty){
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
        List<String> chosenWords = new ArrayList<>();
        for(int i = 0; i < count; i++){
            randomNum = r.nextInt((words.size()-1) + 1);
            chosenWords.add(words.remove(randomNum));
        }
        return chosenWords;
    }

    //shuffle the string
    String shuffleStr(String str){
        char[] chars = str.toCharArray();
        String scrambled = "";
        Random r = new Random();
        int randomNum = r.nextInt((chars.length-1)-0);
        ArrayList<Character> characters = new ArrayList<>();
        for(char c: chars){
            characters.add(c);
        }
        Collections.shuffle(characters);
        for(char c: characters){
            scrambled = scrambled + c;
        }
        return scrambled;
    }

}
