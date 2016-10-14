package com.rileygrant.anagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
//word list http://www-personal.umich.edu/~jlawler/wordlist
public class Medium extends Activity {
    //to keep track of answer and what to display
    private String answer;
    private List<String> words;
    private String shuffled;
    private int score;
    private int remaining;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        score = 0;
        words = pickWords(20,"medium");
        remaining = words.size();
        answer = words.remove(0);
        shuffled = shuffleStr(answer);
        final TextView textView = (TextView) findViewById(R.id.wordToGuess);
        final TextView dif = (TextView) findViewById(R.id.Difficulity);
        dif.setText("Medium");
        textView.setText(shuffled);
        final EditText guessM = (EditText) findViewById(R.id.guess);
        final TextView ViewScoreM = (TextView) findViewById(R.id.ScoreView);
        final TextView RviewM = (TextView) findViewById(R.id.Remaining);
        RviewM.setText("Remaining: 20");

        guessM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(shuffled);
                fadeChar(guessM.getText().toString().toLowerCase(),textView);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(guessM.getText().toString().toLowerCase().equals(answer)||guessM.getText().toString().toLowerCase().equals("test")){
                    score = (score +1);
                    if (score == 10){
                        Intent mintent = new Intent(Medium.this, Results.class);
                        String FScore=Integer.toString(score);
                        Bundle bundle = new Bundle();
                        bundle.putString("FSCORE", FScore);
                        mintent.putExtras(bundle);
                        startActivity(mintent);
                    }
                    ViewScoreM.setText("Score: " + Integer.toString(score));
                    remaining = words.size();
                    RviewM.setText("Remaining: " + Integer.toString(remaining));
                    guessM.setTextColor(Color.rgb(0,153,76));
                    guessM.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            guessM.setTextColor(Color.BLACK);
                            guessM.setText("");
                            guessM.animate().alpha(1);
                        }
                    });
                    textView.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            if(words.size()> 0) {
                                answer = words.remove(0);
                                shuffled = shuffleStr(answer);
                                textView.setText("");
                                textView.setText(shuffled);
                                textView.animate().alpha(1);
                            }
                        }
                    });
                }

            }
        });
    }
    public void onClickDeath(View v) {
        Intent mintent = new Intent(Medium.this, Results.class);
        String FScore=Integer.toString(score);
        Bundle bundle = new Bundle();
        bundle.putString("FSCORE", FScore);
        mintent.putExtras(bundle);
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

    //check entered string and assume
    private void fadeChar(String entered,TextView textView){
        SpannableString spannableString = new SpannableString(textView.getText());
        char[] chars = new char[textView.length()];
        int[] indices = new int[textView.length()];
        for(int i = 0; i < textView.length(); i++){
            chars[i] = textView.getText().charAt(i);
            indices[i] = i;
        }

        Stack<String> underlinedIndices = new Stack<>();
        for(int i = 0; i < entered.length(); i++){
            char s = entered.charAt(i);
            for(int j = 0; j < chars.length; j++){
                if(s == chars[j]){
                    //change to uppercase so we can use the letter again
                    String upper = Character.toString(chars[j]).toUpperCase();
                    spannableString.setSpan(new StrikethroughSpan(), indices[j], indices[j]+1, 0);
                    //this is what has strikeThrough
                    underlinedIndices.push(Integer.toString(j));
                    char up = upper.charAt(0);
                    chars[j] = up;
                    break;
                }
            }
        }
        textView.setText(spannableString);
    }

    private void unfadeChars(){

    }
}
