package com.emy.nubeink.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emy.nubeink.R;
import com.emy.nubeink.model.dto.Usuario;
import com.emy.nubeink.model.service.SistemaUsuarios;

public class DepositarActivity extends AppCompatActivity{

    private Usuario usuarioLogado;
    private SistemaUsuarios sistema;
    private TextView TextSaldo;
    private EditText editTextSaldo;
    private EditText idConta;
    private EditText nomeConta;
    private Button buttonDeposito;
    private ImageButton buttonSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositar);

        // Inicializa o sistema de usuários
        sistema = new SistemaUsuarios(this);

        // Identificando os elementos da tela
        TextSaldo = findViewById(R.id.textViewsSaldoRealDeposito);
        editTextSaldo = findViewById(R.id.editTextValorDeposito);
        nomeConta = findViewById(R.id.editTextNomeContaDeposito);
        idConta = findViewById(R.id.editTextContaDestinoDeposito);
        // Identificando botões
        buttonDeposito = findViewById(R.id.buttonRealizarDeposito);
        buttonSair = findViewById(R.id.imageButtonSairDeposito);

        String nomeUsuario = getIntent().getStringExtra("NOME_USUARIO");
        String emailUsuario = getIntent().getStringExtra("EMAIL_USUARIO");
        double saldoUsuario = getIntent().getDoubleExtra("SALDO_USUARIO", 0.0);
        long idUsuario = getIntent().getLongExtra("ID_USUARIO", 1);

        usuarioLogado = new Usuario(idUsuario, nomeUsuario, emailUsuario, saldoUsuario);
        Double saldoPeloBanco = sistema.buscarSaldoUsuario(usuarioLogado);
        TextSaldo.setText("R$ " + saldoPeloBanco);

        buttonDeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saldo = editTextSaldo.getText().toString();
                String nome = nomeConta.getText().toString();
                String idContaText = idConta.getText().toString();

                Double valor = verificaSaldo(saldo);
                buscarUsuario(valor, nome, idContaText);
            }
        });

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelando operação e voltando para o menu principal", Toast.LENGTH_SHORT).show();
                Intent intent = sistema.criarIntentUsuario(usuarioLogado, DepositarActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private Double verificaSaldo(String valor) {
        Double saldoValido = null;
        try {
            saldoValido = Double.parseDouble(valor);
            if (saldoValido <= 0) {
                Toast.makeText(getApplicationContext(), "O valor da transferência deve ser positivo.", Toast.LENGTH_SHORT).show();
                saldoValido = null;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Por favor, insira um valor válido.", Toast.LENGTH_SHORT).show();
        }
        return saldoValido;
    }

    private void buscarUsuario(Double valor, String nome, String idContaText) {
        if (valor != null && valor != 0) {
            if (!nome.isEmpty() || !idContaText.isEmpty()) {
                long id = !idContaText.isEmpty() ? Long.parseLong(idContaText) : 0;
                Usuario usuarioEncontrado = sistema.buscarUsuario(nome, id);

                if (usuarioEncontrado != null) {
                    realizarTransferencia(usuarioLogado, valor, usuarioEncontrado);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Digite um nome ou ID antes de buscar.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void realizarTransferencia(Usuario usuarioLogado, double saldoTransferencia, Usuario usuarioDestino) {
        sistema.realizarTransferencia(usuarioLogado, saldoTransferencia, usuarioDestino);
        Toast.makeText(getApplicationContext(), "Transferência realizada com sucesso.", Toast.LENGTH_SHORT).show();

        Intent intent = sistema.criarIntentUsuario(usuarioLogado, DepositarActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}

