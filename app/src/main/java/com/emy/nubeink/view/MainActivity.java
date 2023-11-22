package com.emy.nubeink.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emy.nubeink.R;
import com.emy.nubeink.model.service.SistemaUsuarios;
import com.emy.nubeink.model.dto.Usuario;

public class MainActivity extends AppCompatActivity {

    private SistemaUsuarios sistema;
    private EditText emailLogin;
    private EditText senhaLogin;
    private TextView cadastre;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa o sistema de usuários
        sistema = new SistemaUsuarios(this);

        //Identificando os elementos da tela
        emailLogin = findViewById(R.id.editTextPersonEmailLogin);
        senhaLogin = findViewById(R.id.editTextPersonSenhaLogin);
        //Identificando botões
        buttonLogin = findViewById(R.id.buttonLogin);
        cadastre = findViewById(R.id.textViewCadastre);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String senha = senhaLogin.getText().toString();
                Usuario usuario = new Usuario(email, senha);
                buscarUsuario(usuario);
            }
        });
        cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });
    }

    private void buscarUsuario(Usuario usuario) {
        if (!usuario.getEmail().isEmpty() && !usuario.getSenha().isEmpty()) {
            Usuario usuarioEncontrado = sistema.buscarUsuarioPorEmail(usuario.getEmail());
            if (usuarioEncontrado != null ) {
                if(usuario.getSenha().equals(usuarioEncontrado.getSenha())){
                    Toast.makeText(getApplicationContext(), "Usuário encontrado: " + usuarioEncontrado.getNome(), Toast.LENGTH_SHORT).show();
                    Intent it = sistema.criarIntentUsuario(usuarioEncontrado,MainActivity.this, MenuActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Usuário não encontrado para o e-mail: " + usuario.getEmail(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Digite um e-mail e senha antes de buscar", Toast.LENGTH_SHORT).show();
        }
    }
}
