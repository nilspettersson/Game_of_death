package com.example.gameofdeath;

import android.util.Log;

import java.io.IOException;

public class Player {
    Client client;
    int id;

    public Player(){
        client=new Client("192.168.216.121",9090);
        Log.wtf("a","player create!");
        new Thread(){
            public void run(){



                while(true){

                    checkConnection();
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                }

            }
        }.start();







        new Thread(){
            public void run(){


                Player.this.getId();

                while(true){


                    //Log.wtf("a",getGameId());
                    try {
                        Thread.sleep(0);
                    }catch (InterruptedException e){

                    }
                }

            }
        }.start();






    }

    public void createGameId(){
        try {
            client.getOutput().writeUTF("cg");//create game
        }catch (IOException e){

        }
    }

    public String getGameId(){
        String input="";
        try {
            while(client.getInput().available()>0) {

                input+= client.getInput().readUTF();
            }
            return input;
        }catch (IOException e){

        }
        return input;
    }

    public String getInput(){
        try {
            String input=client.getInput().readUTF();
            return input;
        }catch (IOException e){
            Log.wtf("a","error");
        }
        return null;
    }


    public void sendOutput(String output){
        try {
            client.getOutput().writeUTF(output);
        }catch (IOException e){

        }
    }



    public void getId(){
        try {
            id = client.getInput().readInt();
        }catch (IOException e){
            id=-1;
        }
    }

    public void checkConnection(){
        try {
            client.getOutput().writeUTF("client id:"+id+" connected");
        }catch (IOException e){

        }
    }


}
