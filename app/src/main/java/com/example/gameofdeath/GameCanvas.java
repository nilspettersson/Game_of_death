package com.example.gameofdeath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.gameofdeath.the_game.Cell;
import com.example.gameofdeath.the_game.Grid;
import com.example.gameofdeath.the_game.PlayerInGame;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private GameLoop loop;

    public GameCanvas(Context context){
        super(context);

        color=new Paint();

        getHolder().addCallback(this);

        loop=new GameLoop(getHolder(),this);

        setOnTouchListener(this);



        setFocusable(true);
    }



    boolean noTouchSceen=true;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            noTouchSceen=true;
        }

        if(game.myTurn && noTouchSceen){
            noTouchSceen=false;

            if(grid.intersect((int)event.getX(),(int)event.getY(),4)==false){
                game.myTurn=false;

                grid.doGameOfLifeStep();

                MainActivity.player.sendOutput("turnSwitch");

                String[] send=grid.getStates();
                for(int i=0;i<send.length;i++){
                    MainActivity.player.sendOutput(send[i]);
                }


            }
            else {


            }

        }


        return true;
    }

    public static Grid grid;

    public static PlayerInGame player=new PlayerInGame();

    Paint color;
    public void drawG(Canvas g){
        Cell.size=getWidth()/16;
        color.setColor(Color.BLACK);
        g.drawRect(0,0,getWidth(),getHeight(),color);

        if(game.gameStarted){
            if(GameLoop.setup){
                GameCanvas.timeStart=System.currentTimeMillis();
                grid=new Grid(16);
                GameLoop.setup=false;
            }


            grid.draw(g);


            color.setColor(Color.WHITE);
            color.setTextSize(60);
            if(game.myTurn){


                if(timeStart!=0) {

                    timeLeft = System.currentTimeMillis() - timeStart;
                    timeLeft /= 1000;
                    timeLeft = limit - timeLeft;
                    if (timeLeft < 0) {
                        game.myTurn = false;
                        timeStart=System.currentTimeMillis();
                        grid.doGameOfLifeStep();

                        MainActivity.player.sendOutput("turnSwitch");

                        String[] send = grid.getStates();
                        for (int i = 0; i < send.length; i++) {
                            MainActivity.player.sendOutput(send[i]);
                        }
                    }

                }
                g.drawText("time left: "+timeLeft+"",60,getHeight()-600,color);


                g.drawText("your turn!",60,getHeight()-80-120-120,color);
            }
            g.drawText("Points: "+player.getPoints(),60,getHeight()-80-120,color);
            g.drawText("Money: "+player.getMoney(),60,getHeight()-80,color);




            if(player.getPoints()>=500){
                g.drawText("you win!!!!!!! ",300,getHeight()-580,color);
                //MainActivity.player.sendOutput("leave");
            }



        }
        else{
            color.setColor(Color.WHITE);
            color.setTextSize(79);
            if(temp<20){
                g.drawText("waiting for opponent",200,getHeight()-1200,color);
            }
            else if(temp<40){
                g.drawText("waiting for opponent.",200,getHeight()-1200,color);
            }
            else if(temp<60){
                g.drawText("waiting for opponent..",200,getHeight()-1200,color);
            }
            else if(temp<80){
                g.drawText("waiting for opponent...",200,getHeight()-1200,color);
            }
            else if(temp<100){
                g.drawText("waiting for opponent...",200,getHeight()-1200,color);
                temp=-1;
            }

            temp++;
        }


    }

    Long timeLeft=-1L;
    Long limit=30l;
    static Long timeStart=0L;

    int temp=0;


    @Override
    public void surfaceCreated(SurfaceHolder holder){
        loop.setRunning(true);
        loop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry=true;
        while(retry){
            try {
                loop.setRunning(false);
                loop.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry=false;
        }
    }

    public void update(){

    }


}
