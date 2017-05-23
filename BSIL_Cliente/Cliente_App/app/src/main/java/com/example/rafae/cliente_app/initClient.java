package com.example.rafae.cliente_app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class initClient extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    Socket socket;
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
            if(InetAddress.getByName(dstAddress).isReachable(9000)){
                socket = new Socket(dstAddress, dstPort);
                printar("Socket criado");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();


                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }
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
            printar("A conexão foi efetuada com sucesso");
            Toast.makeText(contexto, "A conexão foi efetuada com sucesso", Toast.LENGTH_SHORT).show();
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
