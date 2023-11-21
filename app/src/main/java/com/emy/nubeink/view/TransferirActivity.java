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

import java.math.BigDecimal;

public class TransferirActivity extends AppCompatActivity {

    private SistemaUsuarios sistema;
    private TextView TextSaldo;
    private EditText editTextSaldo;
    private EditText idConta;
    private EditText nomeConta;
    private Button buttonTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);

        // Inicializa o sistema de usuários
        sistema = new SistemaUsuarios(this);

        // Identificando os elementos da tela
        TextSaldo = findViewById(R.id.textViewsSaldoReal);
        editTextSaldo = findViewById(R.id.editTextSaldoTransferir);
        nomeConta = findViewById(R.id.editTextPersonNameTransferir);
        idConta = findViewById(R.id.editTextPersonIDTransferir);
        // Identificando botões
        buttonTransferir = findViewById(R.id.buttonTransferirProximo);

        double saldoUsuario = getIntent().getDoubleExtra("SALDO_USUARIO", 0.0);
        TextSaldo.setText("R$ " + saldoUsuario);

        buttonTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saldo = editTextSaldo.getText().toString();
                String nome = nomeConta.getText().toString();
                String idContaText = idConta.getText().toString();

                boolean saldoValido = verificaSaldo(saldo);
                if (saldoValido) {
                    buscarUsuario(saldo,nome, idContaText);
//                    realizarTransferencia(saldo, nome, idContaText);
                }
            }
        });
    }

    private boolean verificaSaldo(String valor) {
        Double saldoValido = Double.valueOf(valor);
        if (saldoValido <= 0) {
            Toast.makeText(getApplicationContext(), "O valor da transferência deve ser positivo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void buscarUsuario(String saldo, String nome, String idContaText) {
        if(saldo.isEmpty()){
            double saldoo = Double.parseDouble(saldo);
            if (!nome.isEmpty() && !idContaText.isEmpty()) {
                long id = Long.parseLong(idContaText);
                Usuario usuarioEncontradoPorNomeEId = sistema.buscarUsuarioPorNomeEID(nome, id);
                if (usuarioEncontradoPorNomeEId != null) {
                    Toast.makeText(getApplicationContext(), "Usuário encontrado por nome e ID: " + usuarioEncontradoPorNomeEId.getNome(), Toast.LENGTH_SHORT).show();
                    sistema.realizarTransferencia(saldoo, nome, id);
                    Toast.makeText(getApplicationContext(), "Transferência realizada com sucesso.", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(TransferirActivity.this, MenuActivity.class);
                    it.putExtra("SALDO_USUARIO", sistema.buscarUsuarioPorNome(nome).getSaldo());
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado para o nome: " + nome + " e ID: " + id, Toast.LENGTH_SHORT).show();
                }
            } else if (!nome.isEmpty()) {
                Usuario usuarioEncontradoPorNome = sistema.buscarUsuarioPorNome(nome);
                if (usuarioEncontradoPorNome != null) {
                    Toast.makeText(getApplicationContext(), "Usuário encontrado por nome: " + usuarioEncontradoPorNome.getNome(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado para o nome: " + nome, Toast.LENGTH_SHORT).show();
                }
            } else if (!idContaText.isEmpty()) {
                long id = Long.parseLong(idContaText);
                Usuario usuarioEncontradoPorId = sistema.buscarUsuarioPorId(id);
                if (usuarioEncontradoPorId != null) {
                    Toast.makeText(getApplicationContext(), "Usuário encontrado por ID: " + usuarioEncontradoPorId.getNome(), Toast.LENGTH_SHORT).show();
                    sistema.realizarTransferencia(saldoo, nome, id);
                    Toast.makeText(getApplicationContext(), "Transferência realizada com sucesso.", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(TransferirActivity.this, MenuActivity.class);
                    it.putExtra("SALDO_USUARIO", sistema.buscarUsuarioPorNome(nome).getSaldo());
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado para o ID: " + id, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Digite um nome ou ID antes de buscar.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
