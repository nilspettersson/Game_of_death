package com.example.gameofdeath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static boolean inGame=false;
    public static Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        player=new Player();
        try {
            Thread.sleep(20);
        }catch (InterruptedException e){

        }


        LinearLayout box = (LinearLayout) findViewById(R.id.box);

        String games=player.getInput();
        String gameNames=player.getInput();

        Log.wtf("a",gameNames);

        Log.wtf("a",games+" ******");
        int count=0;
        int start=0;
        for(int i=0;i<games.length();i++){
            if(games.charAt(i)=='*'){

                String currentName="";
                int start2=0;
                int currentCount=0;
                for(int ii=0;ii<gameNames.length();ii++){
                    if(gameNames.charAt(ii)=='*'){
                        if(currentCount==count) {
                            currentName=gameNames.substring(start2, ii);
                        }
                        start2 = ii + 1;
                        currentCount++;
                    }
                }
                count++;

                Button btn = new Button(MainActivity.this);
                btn.setTag(games.substring(start,i));
                btn.setText(currentName);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.wtf("a",v.getTag().toString());
                        MainActivity.player.getInput();//reads old data that I do not understand.
                        player.sendOutput("join "+v.getTag().toString());

                        gotToGame(v);

                    }
                });

                box.addView(btn);
                start=i+1;
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


                showCreateMatch(MainActivity.this,v);


            }
        });




        Button back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.sendOutput("leave");
                gotToHome(v);
            }
        });

        final Button refresh=(Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.sendOutput("leave");
                refresh(v);
            }
        });






        /*final Handler handler = new Handler();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        MyAsyncTask mAsync = new MyAsyncTask();
                        mAsync.execute();
                    }
                });
            }
        };
        timer.schedule(task, 0, 500); //Every 1 second*/



    }



    private void showCreateMatch(Context c, View v) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Create new game")
                .setMessage("name your match.")
                .setView(taskEditText)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inGame=true;
                        String name = String.valueOf(taskEditText.getText());
                        player.createGameId();
                        player.sendOutput(name);
                        gotToGame(MainActivity.this.getCurrentFocus());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }



    @Override
    public void onBackPressed() {
        player.sendOutput("leave");

        finish();

    }




    String calculatedString;
    MyAsyncTask mAsync = null;
    Timer timer = null;
    TimerTask task = null;

    private class MyAsyncTask extends AsyncTask<String, Void, String> {



        public MyAsyncTask(){

        }

        @Override
        protected String doInBackground(String... params) {
            //Background operation in a separate thread
            //Write here your code to run in the background thread

            //calculate here whatever you like






            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!inGame) {
                Log.wtf("a","gogogo");
                LinearLayout box = (LinearLayout) findViewById(R.id.box);

                String games = player.getInput();
                Log.wtf("a", games + " ******");

                int start = 0;
                for (int i = 0; i < games.length(); i++) {
                    if (games.charAt(i) == '*') {

                        Button btn = new Button(MainActivity.this);
                        btn.setTag(games.substring(start, i));
                        btn.setText("Game " + games.substring(start, i));

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.wtf("a", v.getTag().toString());
                                MainActivity.player.getInput();//reads old data that I do not understand.
                                player.sendOutput("join " + v.getTag().toString());

                                gotToGame(v);

                            }
                        });

                        box.addView(btn);
                        start = i;
                    }
                }

            }



        }

        @Override
        protected void onPreExecute() {
            //Called on Main UI Thread. Executed before the Background operation, allows you to have access to the UI
        }
    }





    public void gotToGame(View view){
        Intent intent=new Intent(this,game.class);
        startActivity(intent);
    }

    public void gotToHome(View view){
        Intent intent=new Intent(this,home.class);
        startActivity(intent);
        //startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
    public void refresh(View view){
        Intent intent=new Intent(this,MainActivity.class);
        //startActivity(intent);
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }



}
