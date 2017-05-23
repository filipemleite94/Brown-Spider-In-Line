package com.example.rafae.cliente_app;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by filip on 23/05/2017.
 */

public class pontSocket extends AsyncTask<Void,Void,Void>{
    public Socket socket;
    public BufferedReader in;
    public PrintWriter out;

    pontSocket(){
        socket = null;
        in = null;
        out = null;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(socket==null){
            return null;
        }
        try{
            out.println("quit");
            out.flush();
        }catch(Exception e){
            System.out.println("Erro ao avisar a saída");
        }
        try{
            in.close();
            out.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Erro ao fechar o socket");
            return null;
        }
        System.out.println("Socket fechado tranquilamente");
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        in = null;
        out =  null;
        socket = null;
    }
}
