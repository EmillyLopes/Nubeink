package com.emy.nubeink.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emy.nubeink.R;
import com.emy.nubeink.model.service.SistemaUsuarios;
import com.emy.nubeink.model.dto.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private SistemaUsuarios sistema;
    private EditText nomeCadastro;
    private EditText emailCadastro;
    private EditText senhaCadastro;
    private EditText saldoCadastro;
    private TextView cadastro;
    private Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializa o sistema de usuários
        sistema = new SistemaUsuarios(this);

        //Identificando os elementos da tela
        nomeCadastro = findViewById(R.id.editTextPersonName);
        emailCadastro = findViewById(R.id.editTextPersonEmailCadastro);
        senhaCadastro = findViewById(R.id.editTextPersonSenhaCadastro);
        saldoCadastro = findViewById(R.id.editTextSaldoCadastro);
        //Identificando botões
        buttonCadastro = findViewById(R.id.buttonCadastrar);
        cadastro = findViewById(R.id.textViewCadastre);

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeCadastro.getText().toString();
                String email = emailCadastro.getText().toString();
                String senha = senhaCadastro.getText().toString();
                String saldoText = saldoCadastro.getText().toString();

                if (validaEntradas(nome, email, senha, saldoText)) {
                    double saldo = Double.parseDouble(saldoText);
                    Usuario usuario = new Usuario(nome, email, senha, saldo);
                    validaESalva(usuario);
                }
            }
        });
    }

    private boolean validaEntradas(String nome, String email, String senha, String saldo) {
        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(getApplicationContext(), "Digite um Nome antes de salvar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Digite um e-mail antes de salvar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(getApplicationContext(), "Digite uma senha antes de salvar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(saldo)) {
            Toast.makeText(getApplicationContext(), "Digite um Saldo antes de salvar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void validaESalva(Usuario usuario) {
        sistema.adicionarUsuario(usuario);
        Toast.makeText(getApplicationContext(), "Usuário Salvo: " + usuario.getNome(), Toast.LENGTH_SHORT).show();
        Intent it = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(it);
    }
}
