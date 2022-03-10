package com.example.symbian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastrarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobrenome);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        btnCadastrarCliente = findViewById(R.id.btnCadastrarCliente);

        btnCadastrarCliente.setOnClickListener(view -> { //O corpo do Lambda trata a ação que será executado após o clique
            if (!validate()){
                Toast.makeText(this, "Preencha todos os campos, brother", Toast.LENGTH_SHORT).show();
                return;
            }

            //Processo de gravação do usuário
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.cadastroClienteTitulo))
                    .setMessage("Você está cadastrando um usuário")
                    .setPositiveButton("Salvar", (dialog1, which)->{
                        //Ação do PositiveButton
                        String nome = txtNome.getText().toString();
                        String sobrenome = txtSobrenome.getText().toString();
                        String login = txtLogin.getText().toString();
                        String senha = txtSenha.getText().toString();

                        int cod_cliente = SQLHelper.getINSTANCE(this).addUser(nome, sobrenome, login, senha);

                        //Opções de diálogo

                        if (cod_cliente != 0){
                            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();

                            Intent telaCadastro = new Intent(CadastroClienteActivity.this, CadastroEnderecoActivity.class);
                            telaCadastro.putExtra("COD_CLIENTE", cod_cliente);
                            startActivity(telaCadastro);

                        }else{
                            Toast.makeText(this, "Houve um erro ao realizar o cadastro", Toast.LENGTH_LONG).show();
                        }

                    })
                   .setNegativeButton("Cancelar", (dialog1, which)->{}).create();

            dialog.show();

        });

    }

    private boolean validate() {

        return (
                !txtNome.getText().toString().isEmpty() &&
                        !txtSobrenome.getText().toString().isEmpty() &&
                        !txtLogin.getText().toString().isEmpty() &&
                        !txtSenha.getText().toString().isEmpty()
        );

    }

}