package com.example.rafae.cliente_app;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

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

            senha = edtText.getText().toString();

                //Mandar requisição da senha para o servidor aqui!

                AlertDialog.Builder dig = new AlertDialog.Builder(MainActivity.this);
                dig.setMessage("Aqui o que retornar do servidor!!");
            }
        });
    }


}
