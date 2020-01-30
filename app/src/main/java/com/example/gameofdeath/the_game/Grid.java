package com.example.gameofdeath.the_game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.gameofdeath.GameCanvas;
import com.example.gameofdeath.MainActivity;

public class Grid {

    Cell[][] cells;

    public Grid(int size){
        cells=new Cell[size][size];

       /* for(int i=0;i<9;i++) {
            String gridInput= MainActivity.player.getInput();
            Log.wtf("a", "grid: "+gridInput);

        }*/

        for(int i=0;i<cells.length;i++){
            for(int ii=0;ii<cells[i].length;ii++){
                String gridInput= MainActivity.player.getInput();
                //Log.wtf("a", "grid: "+gridInput);

                cells[ii][i]=new Cell(ii*Cell.size,i*Cell.size,gridInput);
            }
        }

    }


    public void draw(Canvas g){
        for(int i=0;i<cells.length;i++){
            for(int ii=0;ii<cells[i].length;ii++){
                cells[i][ii].draw(g);
            }
        }
    }

    public boolean intersect(int x,int y,int size){
        if(GameCanvas.player.getMoney()<=0){
            GameCanvas.player.setMoney(0);
        }

        boolean onGrid=false;
        for(int i=0;i<cells.length;i++){
            for(int ii=0;ii<cells[i].length;ii++){

                if(cells[i][ii].intersect(x,y,size).equals("0")){
                    if(GameCanvas.player.getMoney()>=100) {
                        GameCanvas.player.setMoney(GameCanvas.player.getMoney()-100);
                        if(GameCanvas.player.isPlayerOne()) {
                            cells[i][ii].setState("1");
                            cells[i][ii].coolSize=Cell.size/2;
                        }
                        else{
                            cells[i][ii].setState("2");
                            cells[i][ii].coolSize=Cell.size/2;
                        }
                    }
                    onGrid=true;
                }
                else if(cells[i][ii].intersect(x,y,size).equals("1") ){
                    //if(GameCanvas.player.getMoney()>0){
                    if(GameCanvas.player.isPlayerOne()) {
                        GameCanvas.player.setMoney(GameCanvas.player.getMoney() + 100);
                        cells[i][ii].setState("0");
                    }
                    //}
                    onGrid=true;
                }
                else if(cells[i][ii].intersect(x,y,size).equals("2")){
                    //if(GameCanvas.player.getMoney()>0){
                    if(!GameCanvas.player.isPlayerOne()) {
                        GameCanvas.player.setMoney(GameCanvas.player.getMoney() + 100);
                        cells[i][ii].setState("0");
                    }
                    //}
                    onGrid=true;
                }
            }
        }
        return onGrid;
    }

    public String[] getStates() {
        int points=0;
        String[] states = new String[cells.length * cells[0].length];
        int index = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int ii = 0; ii < cells[i].length; ii++) {
                states[index] = cells[ii][i].getState();

                if(GameCanvas.player.isPlayerOne()){
                    if(states[index].equals("1")){
                        points+=20;
                    }
                    else if(states[index].equals("2")){
                        points-=0;
                    }
                }
                else{
                    if(states[index].equals("1")){
                        points-=0;
                    }
                    else if(states[index].equals("2")){
                        points+=20;
                    }
                }

                index++;
            }
        }

        GameCanvas.player.setPoints(points);

        return states;
    }

    public void setStates(){

        String[] states = new String[cells.length * cells[0].length];
        int index = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int ii = 0; ii < cells[i].length; ii++) {
                cells[ii][i].setState(MainActivity.player.getInput());
                cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }

    }

    public void doGameOfLifeStep(){
        int points=0;

        for (int i = 0; i < cells.length; i++) {
            for (int ii = 0; ii < cells[i].length; ii++) {
                int enemy=0;
                int allie=0;


                for (int iii = -1; iii <=1; iii++) {
                    for (int iiii = -1; iiii <=1; iiii++) {
                        if(iii==0 && iiii==0){
                            continue;
                        }
                        if(i==0 && iii==-1){
                            continue;
                        }
                        if(ii==0 && iiii==-1){
                            continue;
                        }
                        if(i==cells.length-1 && iii==1){
                            continue;
                        }
                        if(ii==cells[0].length-1 && iiii==1){
                            continue;
                        }

                        if(cells[ii+iiii][i+iii].getState().equals("1")){
                            allie++;
                        }
                        else if(cells[ii+iiii][i+iii].getState().equals("2")){
                            enemy++;
                        }

                    }
                }

                cells[ii][i].setAllieNeighbour(allie);
                cells[ii][i].setEnemyNeighbour(enemy);

            }
        }


        for (int i = 0; i < cells.length; i++) {
            for (int ii = 0; ii < cells[i].length; ii++) {
                int alive=cells[ii][i].getAllieNeighbour()+cells[ii][i].getEnemyNeighbour();

                if(cells[ii][i].getState().equals("0")){
                    //Log.wtf("a",i+"  "+ii+"    "+alive);
                    if(alive==3){
                        if(cells[ii][i].getAllieNeighbour()>cells[ii][i].getEnemyNeighbour()) {
                            cells[ii][i].setState("1");
                            cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else if(cells[ii][i].getAllieNeighbour()==cells[ii][i].getEnemyNeighbour()){//om det är lika  många röda som gröna.
                            cells[ii][i].setState(""+((int)(Math.random()*2)+1));
                            cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                        else{
                            cells[ii][i].setState("2");
                            cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                }
                else if(cells[ii][i].getState().equals("1")){
                    //Log.wtf("a",i+"  "+ii+"    "+alive);
                    if(alive<2 || alive>3){
                        Log.wtf("a","die!");
                        cells[ii][i].setState("0");
                    }
                    else if(cells[ii][i].getAllieNeighbour()>cells[ii][i].getEnemyNeighbour()){

                    }
                    else if(cells[ii][i].getAllieNeighbour()==cells[ii][i].getEnemyNeighbour()){
                        cells[ii][i].setState(""+((int)(Math.random()*2)+1));
                        cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
                    }
                    else{
                        cells[ii][i].setState("2");
                    }
                }
                else if(cells[ii][i].getState().equals("2")){
                    //Log.wtf("a",i+"  "+ii+"    "+alive);
                    if(alive<2 || alive>3){
                        cells[ii][i].setState("0");
                    }
                    else if(cells[ii][i].getAllieNeighbour()>cells[ii][i].getEnemyNeighbour()){
                        cells[ii][i].setState("1");
                    }
                    else if(cells[ii][i].getAllieNeighbour()==cells[ii][i].getEnemyNeighbour()){
                        cells[ii][i].setState(""+((int)(Math.random()*2)+1));
                        cells[ii][i].coolSize=Cell.size/2;//animation!!!!!!!!!!!!!!!!!!!!!!!!
                    }
                    else{

                    }
                }
            }
        }








    }


}
