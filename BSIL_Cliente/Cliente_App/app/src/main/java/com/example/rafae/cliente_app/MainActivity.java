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
    private EditText edtText;
    private Button button;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtText = (EditText) findViewById(R.id.edtText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringFromServer = null;
                senha = edtText.getText().toString();
                try {
                    Socket cliente = new Socket("127.0.0.1", 12345);
                    System.out.println("O cliente se conectou ao servidor!");

                    PrintStream saida = new PrintStream(cliente.getOutputStream());

                    saida.println(senha);

                    InputStream in = cliente.getInputStream();
                    ObjectInputStream oin = new ObjectInputStream(in);
                    stringFromServer = (String) oin.readObject();

                    saida.close();
                    cliente.close();

                }catch(Exception ex){
                    System.out.println("Erro: "+ex.getMessage());
                }

                //Mandar requisição da senha para o servidor aqui!

                AlertDialog.Builder dig = new AlertDialog.Builder(MainActivity.this);
                dig.setMessage("Posição na fila: " + stringFromServer);
            }
        });
    }


}
