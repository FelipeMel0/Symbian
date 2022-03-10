package com.example.symbian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private EditText txtCep;
    private EditText txtNumero;
    private EditText txtComplemento;
    private Button btnCadastrarEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtCep = findViewById(R.id.txtCep);
        txtNumero = findViewById(R.id.txtNumero);
        txtComplemento = findViewById(R.id.txtComplemento);
        btnCadastrarEndereco = findViewById(R.id.btnCadastrarEndereco);

        btnCadastrarEndereco.setOnClickListener(view -> {

            if (!validate()){
                Toast.makeText(this, "Preencha todos os campos, brother", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Cadastro de Endereço")
                    .setMessage("Você está cadastrando um endereço")
                    .setPositiveButton("Salvar", (dialog1, which)-> {
                        //Ação do PositiveButton
                        String cep = txtCep.getText().toString();
                        String numero = txtNumero.getText().toString();
                        String complemento = txtComplemento.getText().toString();


                        int cod_cliente;

                        if (getIntent().hasExtra("COD_CLIENTE")) {
                            Bundle extras = getIntent().getExtras();
                            cod_cliente = extras.getInt("COD_CLIENTE");
                        }

                        boolean cadastroEndereco = SQLHelper.getINSTANCE(CadastroEnderecoActivity.this).addAddress(cod_cliente, cep, numero, complemento);

                        //Opções de diálogo

                        if (cadastroEndereco){
                            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                       }else{
                           Toast.makeText(this, "Houve um erro ao realizar o cadastro", Toast.LENGTH_LONG).show(); }

                    })
                    .setNegativeButton("Cancelar", (dialog1, which)->{}).create();

            dialog.show();

        });

    }

    private boolean validate() {

        return (
                !txtCep.getText().toString().isEmpty() &&
                        !txtNumero.getText().toString().isEmpty() &&
                        !txtComplemento.getText().toString().isEmpty()
        );

    }

}