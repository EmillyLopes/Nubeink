package com.emy.nubeink.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emy.nubeink.R;
import com.emy.nubeink.usuarios.SistemaUsuarios;
import com.emy.nubeink.usuarios.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private SistemaUsuarios sistema;
    private EditText nomeCadastro;
    private EditText emailCadastro;
    private EditText senhaCadastro;
    private TextView cadastro;
    private Button buttonCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializa o sistema de usuários
        sistema = new SistemaUsuarios();

        //Identificando os elementos da tela
        nomeCadastro = findViewById(R.id.editTextPersonName);
        emailCadastro = findViewById(R.id.editTextPersonEmailCadastro);
        senhaCadastro = findViewById(R.id.editTextPersonSenhaCadastro);
        //Identificando botões
        buttonCadastro = findViewById(R.id.buttonCadastrar);
        cadastro = findViewById(R.id.textViewCadastre);

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeCadastro.getText().toString();
                String email = emailCadastro.getText().toString();
                String senha = senhaCadastro.getText().toString();
                Usuario usuario = new Usuario(nome, email, senha);
                validaESalva(usuario);
            }
        });
    }

    private void validaESalva(Usuario usuario) {
        if (usuario != null) {
            if (!usuario.getNome().isEmpty()) {
                sistema.adicionarUsuario(usuario);
                Toast.makeText(getApplicationContext(), "Usuário Salvo: " + usuario.getNome(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Digite um Nome antes de salvar.", Toast.LENGTH_SHORT).show();
            }
            if (!usuario.getEmail().isEmpty()) {
                sistema.adicionarUsuario(usuario);
                Toast.makeText(getApplicationContext(), "Usuário Salvo: " + usuario.getNome(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Digite um e-mail antes de salvar.", Toast.LENGTH_SHORT).show();
            }
            if (!usuario.getSenha().isEmpty()) {
                if (usuario != null) {
                    sistema.adicionarUsuario(usuario);
                    Toast.makeText(getApplicationContext(), "Usuário Salvo: " + usuario.getNome(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Digite uma senha antes de salvar.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
