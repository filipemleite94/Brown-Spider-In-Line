package com.example.rafae.cliente_app;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private TextView password;
    private EditText portField;
    private Button checkPosButton;
    private Button askForPasswordButton;
    private Integer senha;
    private pontSocket cliente;
    private PrintStream saida;
    private ObjectInputStream oin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = (TextView) findViewById(R.id.password);
        checkPosButton = (Button) findViewById(R.id.checkPos);
        askForPasswordButton  = (Button) findViewById(R.id.askForPassword);
        portField = (EditText) findViewById(R.id.portNumber);
        portField.setText("12345");
        cliente = null;
    }

    public void checkPos(View v){
        //Teste
        //Toast.makeText(MainActivity.this, "Created a separate function for handling button click and added this function in button xml", Toast.LENGTH_SHORT).show();

        if(cliente == null){
            printar("Você deve pedir uma senha primeiro");
            return;
        }

        String stringFromServer = null;
        senha = Integer.getInteger(password.getText().toString());

        saida.println(senha);

        try {
            stringFromServer = (String) oin.readObject();
        }catch(Exception E){
            printar("Erro no recebimento do dado");
            return;
        }

        AlertDialog.Builder dig = new AlertDialog.Builder(MainActivity.this);
        dig.setMessage("Posição na fila: " + stringFromServer);
    }

    public void askForPassword(View v){
        initClient aux;
        String ipAddress;
        String auxString;
        int port = 0;
        if(cliente == null){
            cliente = new pontSocket();
        }
        printar("Iniciar");
        auxString = portField.getText().toString();
        port = Integer.parseInt(auxString);
        if(port<1000 || port>99999){
            printar("Port inválido |" + auxString + "|");
            return;
        }
        if(cliente.socket == null){
            try {
                printar("Iniciar");
                //ipAddress = "127.0.0.1";
                ipAddress = "192.168.0.165";
                new initClient(ipAddress, port, password, cliente, MainActivity.this).execute();
                printar("thread iniciada " + port);
            }catch(Exception e){
                printar("Não foi possível estabelecer conexão com o servidor, erro: "  + e.getMessage()+ "|" + e.getLocalizedMessage());
                e.printStackTrace();
                return;
            }

        }else{
            try{
                if(cliente.socket.isConnected()) {
                    cliente.socket.close();
                }
                cliente = null;
                askForPassword(v);
            }catch(Exception e){
                printar("Não foi possível encerrar a senha passada, tente de novo ");
                return;
            }
        }
    }

    private void printar(String s){
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
