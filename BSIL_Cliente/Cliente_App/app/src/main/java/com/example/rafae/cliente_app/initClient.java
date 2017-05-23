package com.example.rafae.cliente_app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class initClient extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    pontSocket pSocket;
    Context contexto;

    initClient(String addr, int port, TextView textResponse, pontSocket s, Context c) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        pSocket = s;
        contexto = c;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        printar("Vai começar");

        try {
            if(InetAddress.getByName(dstAddress).isReachable(5000)){
                socket = new Socket(dstAddress, dstPort);
                printar("Socket criado");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                response = in.readLine();
            }else {
                response+= "O endereço: " + dstAddress + " não pode ser acessado";
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } catch(NetworkOnMainThreadException e){
            e.printStackTrace();
            response = "NetworkOnMainThreadException: " + e.toString();
        } catch(Exception e){
            e.printStackTrace();
            response =  "Exception: " + e.toString();
        }
        printar(response);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(contexto, "|"+response+"|", Toast.LENGTH_SHORT).show();
        if(response.matches("^-?\\d+$")) {
            textResponse.setText(response);
            pSocket.socket = socket;
            pSocket.in = in;
            pSocket.out = out;
            printar("A conexão foi efetuada com sucesso");
            Toast.makeText(contexto, "A conexão foi efetuada com sucesso", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }else{
            pSocket.socket = socket;
            pSocket.in = in;
            pSocket.out = out;
            pSocket.execute();
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
