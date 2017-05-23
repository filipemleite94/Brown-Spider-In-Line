package com.example.rafae.cliente_app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by filip on 23/05/2017.
 */

public class askPosition extends AsyncTask<Void, Void, Void> {
    String response = "";
    int pass;
    pontSocket pSocket;
    Context contexto;

    askPosition(int pass, pontSocket s, Context c) {
        this.pass = pass;
        pSocket = s;
        contexto = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            pSocket.out.println(pass + "");
            pSocket.out.flush();
            response = pSocket.in.readLine();
        }catch(Exception e){
            printar("Erro na tentativa de obter a a posição");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(contexto, "|"+response+"|", Toast.LENGTH_SHORT).show();
        if(response.matches("^-?\\d+$")) {
            printar("A conexão foi efetuada com sucesso");
            Toast.makeText(contexto, "A conexão foi efetuada com sucesso, faltam: "+response, Toast.LENGTH_LONG).show();
            super.onPostExecute(result);
        }else{
            printar("O envio: " + response + " não é um número");
            Toast.makeText(contexto, "O envio: " + response + " não é um número", Toast.LENGTH_SHORT).show();
        }
        printar("Thread terminada tranquilamente");
    }

    private void printar(String s){
        System.out.println(s);
        //Toast.makeText(contexto, s, Toast.LENGTH_SHORT).show();
    }
}
