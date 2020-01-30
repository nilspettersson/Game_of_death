package com.example.gameofdeath.the_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    public static int size;

    private int allieNeighbour=0;
    private int enemyNeighbour=0;

    private int x;
    private int y;
    private String state;
    private Paint color;

    public Cell(int x,int y,String state){
        this.x=x;
        this.y=y;
        this.state=state;

        color=new Paint();

    }

    public String intersect(int x,int y,int size2){


        if(x<this.x+size && y<this.y+size && x+size2>this.x && y+size2>this.y){
            return state;
        }

        return "-1";

    }

    public int coolSize=0;

    int red=0;
    int green=0;
    int blue=0;

    public void draw(Canvas g){
        /*if(state.equals("0")){
            color.setColor(Color.BLACK);
        }
        else if(state.equals("1")){
            color.setColor(Color.GREEN);
        }
        else if(state.equals("2")){
            color.setColor(Color.RED);//red
        }*/

        if(coolSize<size-3){
            coolSize+=3;
        }



        if(state.equals("0")){
            if(red>9){
                red-=10;
            }
            if(green>9){
                green-=10;
            }
            if(blue>9){
                blue-=10;
            }
            color.setColor(Color.rgb(red,green,blue));
        }
        else if(state.equals("1")){
            if(red>9){
                red-=10;
            }
            if(green<246){
                green+=10;
            }
            if(blue>9){
                blue-=10;
            }
            color.setColor(Color.rgb(red,green,blue));
        }
        else if(state.equals("2")){
            if(red<246){
                red+=10;
            }
            if(green>9){
                green-=10;
            }
            if(blue>9){
                blue-=10;
            }
            color.setColor(Color.rgb(red,green,blue));
        }



        color.setStyle(Paint.Style.FILL);
        g.drawRect(x+(size-coolSize),y+(size-coolSize),x+coolSize,y+coolSize,color);
        color.setStyle(Paint.Style.STROKE);
        color.setColor(Color.DKGRAY);
        g.drawRect(x,y,x+size,y+size,color);
    }


    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state=state;
    }

    public int getAllieNeighbour(){
        return allieNeighbour;
    }
    public void setAllieNeighbour(int allieNeighbour){
        this.allieNeighbour=allieNeighbour;
    }
    public int getEnemyNeighbour(){
        return enemyNeighbour;
    }
    public void setEnemyNeighbour(int enemyNeighbour){
        this.enemyNeighbour=enemyNeighbour;
    }


}
