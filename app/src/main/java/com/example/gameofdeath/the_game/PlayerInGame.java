package com.example.gameofdeath.the_game;

public class PlayerInGame {

    private boolean playerOne;

    private int money;
    private int points;

    public PlayerInGame(){
        playerOne=false;
        points=0;
        money=500;
    }

    public int getMoney(){
        return money;
    }
    public void setMoney(int money){
        this.money=money;
    }

    public int getPoints(){
        return points;
    }
    public void setPoints(int points){
        this.points=points;
    }
    public boolean isPlayerOne(){
        return playerOne;
    }
    public void setPlayerOne(boolean playerOne){
        this.playerOne=playerOne;
    }
}
