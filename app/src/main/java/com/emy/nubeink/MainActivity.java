package com.emy.nubeink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emy.nubeink.cadastro.CadastroActivity;
import com.emy.nubeink.usuarios.SistemaUsuarios;
import com.emy.nubeink.usuarios.Usuario;

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
        sistema = new SistemaUsuarios();

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
                it.putExtra("mgs", "ola");
                startActivity(it);
            }
        });
    }


    private void buscarUsuario(Usuario usuario) {
        // Verifica se o e-mail está vazio
        if (!usuario.getEmail().isEmpty()) {
            // Busca o usuário pelo e-mail
            Usuario usuarioEncontrado = sistema.buscarUsuarioPorEmail(usuario.getEmail());

            // Exibe o resultado na TextView
            if (usuarioEncontrado != null) {
                Toast.makeText(getApplicationContext(),"Usuário encontrado: " + usuarioEncontrado.getNome(),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Usuário não encontrado para o e-mail: " + usuarioEncontrado.getEmail(),Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Digite um e-mail antes de buscar.",Toast.LENGTH_SHORT).show();
        }
    }
}
