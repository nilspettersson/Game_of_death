package com.example.gameofdeath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class game extends AppCompatActivity {

    static boolean gameStarted=false;
    static boolean myTurn;

    static TextView textP1;
    static TextView textP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);

        setContentView(new GameCanvas(this));


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        /*String p1=MainActivity.player.getInput();

        String p2=MainActivity.player.getInput();
        Log.wtf("a","player "+p1+" vs player "+p2);*/


        /*textP1=(TextView)findViewById(R.id.p1);
        textP1.setText("player ");

        textP2=(TextView)findViewById(R.id.p2);
        textP2.setText("player ");*/



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


    @Override
    public void onBackPressed() {
        MainActivity.player.sendOutput("leave");

        finish();

    }




    static int v=0;

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
            //Called on Main UI Thread. Executed after the Background operation, allows you to have access to the UI
            /*game.textP1.setText("Player "+v);
            v++;*/

            String input=MainActivity.player.getInput();
            //Log.wtf("a",input);
            if(input.contains("ginit")) {
                if(input.contains("creator")) {
                    game.gameStarted=true;
                    game.myTurn=true;
                }
                if(input.contains("player")) {
                    game.gameStarted=true;
                    game.myTurn=true;
                }
            }
        }

        @Override
        protected void onPreExecute() {
            //Called on Main UI Thread. Executed before the Background operation, allows you to have access to the UI
        }
    }







}
