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
    private Button checkPosButton;
    private Button askForPasswordButton;
    private Integer senha;
    private Socket cliente;
    private PrintStream saida;
    private ObjectInputStream oin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = (TextView) findViewById(R.id.password);
        checkPosButton = (Button) findViewById(R.id.checkPos);
        askForPasswordButton  = (Button) findViewById(R.id.askForPassword);
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
        String aux;
        if(cliente == null){
            try {
                cliente = new Socket("127.0.0.1", 12345);
                printar("O cliente se conectou com o servidor");
                saida = new PrintStream(cliente.getOutputStream());
                oin = new ObjectInputStream(cliente.getInputStream());
            }catch(Exception e){
                printar("Não foi possível estabelecer conexão com o servidor");
                return;
            }
            try {
                aux = oin.readObject().toString();
                while (!aux.matches("^-?\\d+$")) {
                    aux = oin.readObject().toString();
                }
            }catch(Exception E){
                printar("Erro ao ler o envio");
                return;
            }
            password.setText(aux);
        }else{
            try{
                if(cliente.isConnected()) {
                    cliente.close();
                }
                cliente = null;
                askForPassword(v);
            }catch(Exception e){
                printar("Não foi possível encerrar a senha passada, tente de novo");
                return;
            }
        }
    }

    private void printar(String s){
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }
}
