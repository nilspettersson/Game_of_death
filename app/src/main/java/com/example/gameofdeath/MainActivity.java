package com.example.gameofdeath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        player=new Player();
        try {
            Thread.sleep(10);
        }catch (InterruptedException e){

        }


        LinearLayout box = (LinearLayout) findViewById(R.id.box);

        String games=player.getInput();
        Log.wtf("a",games+" ******");

        int start=0;
        for(int i=0;i<games.length();i++){
            if(games.charAt(i)=='*'){

                Button btn = new Button(MainActivity.this);
                btn.setTag(games.substring(start,i));
                btn.setText("Game "+games.substring(start,i));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.wtf("a",v.getTag().toString());
                        player.sendOutput("join "+v.getTag().toString());
                    }
                });

                box.addView(btn);
                start=i;
            }
        }


        /*if(!games.equals("")) {
            LinearLayout box = (LinearLayout) findViewById(R.id.box);
            Button btn = new Button(MainActivity.this);
            btn.setText(games);
            box.addView(btn);
        }*/


        Button create=(Button)findViewById(R.id.button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.createGameId();
                /*LinearLayout box=(LinearLayout)findViewById(R.id.box);
                Button btn=new Button(MainActivity.this);
                btn.setText("Game id:");
                box.addView(btn);*/
            }
        });

    }
}
