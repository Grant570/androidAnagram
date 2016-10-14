package com.rileygrant.anagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickStart(View v){
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        float layoutHeight = layout.getHeight();
        float layoutWidth = layout.getWidth();

        Button b = (Button)v;
        Button about = (Button)findViewById(R.id.about);
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.ALIGN_PARENT_TOP
        );
        if(b.getText().equals("Start")){
            b.animate().x(-1.0f*layoutWidth/45.0f).y(-1.0f*layoutHeight/25.0f);
            about.animate().x(-1.0f*layoutWidth/45.0f);
            b.animate().scaleX(0.4f).scaleY(0.4f);
            about.animate().scaleX(0.4f).scaleY(0.4f);
            b.setText("Level");
            String[] levels = new String[3];
            levels[0] = "Easy";
            levels[1] = "Medium";
            levels[2] = "Hard";
            for(int i = 0; i< 3; i++){
                Button nb = new Button(this);
                nb.setId(i);
                final Context context = this;
                //click event
                nb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((Button)v).getText().equals("Easy")){
                            Button easy = (Button)findViewById(0);
                            easy.animate().alpha(0.25f).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    resetButtons("Easy");
                                }
                            });

                        }
                        else if(((Button)v).getText().equals("Medium")){
                            Button medium = (Button)findViewById(1);
                            medium.animate().alpha(0.25f).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    resetButtons("Medium");
                                }
                            });
                        }
                        else {
                            Button hard = (Button)findViewById(2);
                            hard.animate().alpha(0.25f).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    resetButtons("Hard");
                                }
                            });
                        }
                    }
                });
                String uri = "@drawable/round_button";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                float ratio;
                if(layoutHeight/layoutWidth > layoutWidth/layoutHeight){
                    ratio = layoutWidth/layoutHeight;
                }
                else{
                    ratio = layoutHeight/layoutWidth;
                }
                nb.setText(levels[i]);
                nb.setTextSize(25);
                nb.setTextColor(Color.WHITE);
                nb.setBackground(res);
                nb.setX(b.getX());
                layout.addView(nb);
                nb.animate().scaleX(ratio).scaleY(ratio);
                nb.animate().y((i)*(layoutHeight/4.0f));
            }
        }
        else{
            resetButtons("Level");
        }
    }
    public void onClickAbout(View v){
        final String info = "Creators:\nRiley Shelton\nGrant Swenson\n2016";
        final Button about = (Button)findViewById(R.id.about);
        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_main);

        if(v.getScaleX() < 1){
            resetButtons("");
            about.animate().alpha(0.06f).withEndAction(new Runnable() {
                @Override
                public void run() {

                    about.setTextSize(12);
                    about.setText(info);
                    about.animate().scaleX(1.5f).scaleY(1.5f);
                    about.animate().alpha(1f);
                }
            });
        }

        else if (v.getScaleX()== 1){
            about.animate().alpha(0.06f).withEndAction(new Runnable() {
                @Override
                public void run() {
//                    if(about.getText().equals("About")){
                        about.setTextSize(12);
                        about.setText(info);
                        about.animate().scaleX(1.5f).scaleY(1.5f);
                        about.animate().alpha(1f);
                }
            });
        }
        else if(v.getScaleX() > 1){
            final int fontSize;
                        if(layout.getHeight()> layout.getWidth()){
                            fontSize = 30;
                        }
                        else{
                            fontSize = 20;
                        }
                        about.animate().alpha(0.06f).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                about.animate().scaleX(1).scaleY(1);
                                about.setTextSize(fontSize);
                                about.setText("About");
                                about.animate().alpha(1f);
                            }
                        });
        }
    }


    private void resetButtons(final String activity){
        Button b = (Button)findViewById(R.id.start);
        Button about = (Button)findViewById(R.id.about);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);
        float layoutHeight = layout.getHeight();
        float layoutWidth = layout.getWidth();

        if(null!=layout) {
            for (int i = 0; i < 3; i++) {
                final Button remButton = (Button) findViewById(i);
                remButton.animate().x(layoutWidth).y(500).scaleX(0.1f).scaleY(0.1f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        layout.removeView(remButton);
                    }
                });
            }
        }
        b.animate().x(layoutWidth/3.0f).y(layoutHeight/10.0f);
        about.animate().x(layoutWidth/3.0f);
        b.animate().scaleX(1).scaleY(1);
        final Context context = this;
        about.animate().scaleX(1).scaleY(1).withEndAction(new Runnable() {
                                                              @Override
                                                              public void run() {
        switch(activity){
            case "Easy":
                Intent intent = new Intent(context,Easy.class);
                startActivity(intent);

                break;
            case "Medium":
                intent = new Intent(context,Medium.class);
                startActivity(intent);
                break;
            case "Hard":
                intent = new Intent(context,Hard.class);
                startActivity(intent);
                break;
            default:
                break;
        }
  }
});
                b.setText("Start");

    }
}
