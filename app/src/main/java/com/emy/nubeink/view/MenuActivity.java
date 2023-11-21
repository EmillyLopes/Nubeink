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

        // Obtendo dados do Intent
        String nomeUsuario = getIntent().getStringExtra("NOME_USUARIO");
        double saldoUsuario = getIntent().getDoubleExtra("SALDO_USUARIO", 0.0);

        // Exibindo dados do usuário
        textViewNome.setText("Olá, " + nomeUsuario);
        textViewSaldo.setText("R$ " + saldoUsuario);

        buttonTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuActivity.this, TransferirActivity.class);
                it.putExtra("SALDO_USUARIO", saldoUsuario);
                startActivity(it);
            }
        });
    }
}
