package com.example.symbian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnTelaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTelaCadastro = findViewById(R.id.btnTelaCadastro);

        btnTelaCadastro.setOnClickListener(view -> {
            Intent telaCadastro = new Intent(MainActivity.this, CadastroClienteActivity.class);
            startActivity(telaCadastro);
        });

    }
}