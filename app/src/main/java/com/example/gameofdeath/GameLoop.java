package com.example.gameofdeath;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.gameofdeath.the_game.Grid;

public class GameLoop extends Thread {

    private boolean running;
    private GameCanvas gamec;
    public SurfaceHolder surfaceHolder;
    public Canvas canvas;

    public GameLoop(SurfaceHolder surfaceHolder, GameCanvas gamec){
        super();
        this.surfaceHolder=surfaceHolder;
        this.gamec=gamec;
    }




    static boolean setup=false;

    @Override
    public void run(){


        new Thread(){
            public void run(){
                while(MainActivity.player.run){
                    String input=MainActivity.player.getInput();
                    Log.wtf("a","init "+input);
                    if(input.contains("ginit")) {
                        if(input.contains("creator")) {
                            game.gameStarted=true;
                            game.myTurn=true;
                            GameCanvas.player.setPlayerOne(true);
                        }
                        if(input.contains("player")) {
                            game.gameStarted=true;
                            game.myTurn=false;
                            GameCanvas.player.setMoney(GameCanvas.player.getMoney()-50);
                        }


                        setup=true;

                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){

                        }

                    }

                    if(input.contains("turn")){
                        GameCanvas.timeStart=System.currentTimeMillis();
                        GameCanvas.player.setMoney(GameCanvas.player.getMoney()+50);
                        GameCanvas.grid.setStates();
                        game.myTurn=true;


                    }




                }
            }
        }.start();



        while(running){
            canvas=null;
            try{
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamec.update();

                    gamec.drawG(canvas);




                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            try{
                Thread.sleep(10);
            }catch (InterruptedException e){

            }

        }

    }

    public void setRunning(boolean isRunning){
        running=isRunning;
    }


}
