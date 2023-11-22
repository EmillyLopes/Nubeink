package com.emy.nubeink.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emy.nubeink.R;
import com.emy.nubeink.model.dto.Usuario;
import com.emy.nubeink.model.service.SistemaUsuarios;

public class MenuActivity extends AppCompatActivity {

    private Usuario usuarioLogado;
    private ImageView imagemPerfil;
    private TextView textViewNome;
    private Button buttonOcultar;
    private Button buttonAjuda;
    private Button buttonNotificacoes;
    private TextView textViewConta;
    private TextView textViewSaldo;
    private Button buttonConta;
    private Button buttonMeusCartoes;
    private ImageButton buttonTransferir;
    private ImageButton buttonPix;
    private ImageButton buttonDeposito;
    private ImageButton buttonSair;

    private SistemaUsuarios sistemaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializa o sistema de usuários
        sistemaUsuarios = new SistemaUsuarios(this);

        // Inicializando os elementos do layout
        imagemPerfil = findViewById(R.id.imagemPerfil);
        textViewNome = findViewById(R.id.textViewNome);
        buttonOcultar = findViewById(R.id.buttonOcultar);
        buttonAjuda = findViewById(R.id.buttonAjuda);
        buttonNotificacoes = findViewById(R.id.buttonNotificacoes);
        textViewConta = findViewById(R.id.textViewConta);
        textViewSaldo = findViewById(R.id.textViewSaldo);
        buttonConta = findViewById(R.id.buttonConta);
        buttonMeusCartoes = findViewById(R.id.buttonMeusCartoes);
        buttonTransferir = findViewById(R.id.buttonTransferir);
        buttonSair = findViewById(R.id.imageButtonSairMenu);
        buttonPix = findViewById(R.id.buttonPix);
        buttonDeposito = findViewById(R.id.buttonDepositar);

        // Obtendo dados do Intent
        String nomeUsuario = getIntent().getStringExtra("NOME_USUARIO");
        String emailUsuario = getIntent().getStringExtra("EMAIL_USUARIO");
        double saldoUsuario = getIntent().getDoubleExtra("SALDO_USUARIO", 0.0);
        long idUsuario = getIntent().getLongExtra("ID_USUARIO", 1);

        usuarioLogado = new Usuario(idUsuario, nomeUsuario, emailUsuario, saldoUsuario);

        textViewNome.setText("Olá, " + usuarioLogado.getNome());
        Double saldoPeloBanco = sistemaUsuarios.buscarSaldoUsuario(usuarioLogado);
        textViewSaldo.setText("R$ " + saldoPeloBanco);

        buttonTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = sistemaUsuarios.criarIntentUsuario(usuarioLogado,MenuActivity.this, TransferirActivity.class);
                startActivity(it);
            }
        });

        buttonPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = sistemaUsuarios.criarIntentUsuario(usuarioLogado,MenuActivity.this, TransferirActivity.class);
                startActivity(it);
            }
        });

        buttonDeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = sistemaUsuarios.criarIntentUsuario(usuarioLogado,MenuActivity.this, DepositarActivity.class);
                startActivity(it);
            }
        });

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = sistemaUsuarios.criarIntentUsuario(usuarioLogado,MenuActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
