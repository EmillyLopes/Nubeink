package com.emy.nubeink.model.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.emy.nubeink.model.dto.Usuario;
import com.emy.nubeink.model.repository.UsuarioDbHelper;

import java.util.Optional;

public class SistemaUsuarios {
    private static final String PREF_USUARIO_LOGADO = "usuario_logado";
    private UsuarioDbHelper dbHelper;
    private Context context;

    public SistemaUsuarios(Context context) {
        dbHelper = new UsuarioDbHelper(context);
    }

    public void adicionarUsuario(Usuario usuario) {
        dbHelper.adicionarUsuario(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return dbHelper.buscarUsuarioPorEmail(email);
    }

    public Usuario buscarUsuarioPorId(long id) {
        return dbHelper.buscarUsuarioPorId(id);
    }

    public Usuario buscarUsuarioPorNome(String nome) {
        return dbHelper.buscarUsuarioPorNome(nome);
    }

    public Usuario buscarUsuarioPorNomeEID(String nome, long id) {
        return dbHelper.buscarUsuarioPorNomeEId(nome, id);
    }

    public void realizarTransferencia(double valor, String nomeDestino, long idDestino) {
        // Busca o usuário de origem (suponhamos que você já tenha o usuário logado)
        Optional<Usuario> usuarioOrigem = obterUsuarioLogado();

        // Verifica se o usuário de origem possui saldo suficiente
        if (usuarioOrigem.isPresent() && usuarioOrigem.get().getSaldo() >= valor) {
            // Busca o usuário de destino
            Usuario usuarioDestino = null;
            if (!nomeDestino.isEmpty() && idDestino != 0) {
                usuarioDestino = buscarUsuarioPorNomeEID(nomeDestino, idDestino);
            } else if (!nomeDestino.isEmpty()) {
                usuarioDestino = buscarUsuarioPorNome(nomeDestino);
            } else if (idDestino != 0) {
                usuarioDestino = buscarUsuarioPorId(idDestino);
            }

            // Verifica se o usuário de destino foi encontrado
            if (usuarioDestino != null) {
                // Realiza a transferência
                usuarioOrigem.get().debitarSaldo(valor);
                usuarioDestino.creditarSaldo(valor);

                // Atualiza os dados no banco de dados
                atualizarUsuario(usuarioOrigem.get());
                atualizarUsuario(usuarioDestino);

                Log.i("SistemaUsuarios", "Transferência realizada com sucesso.");
            } else {
                Log.e("SistemaUsuarios", "Usuário de destino não encontrado.");
            }
        } else {
            Log.e("SistemaUsuarios", "Saldo insuficiente para realizar a transferência ou usuário de origem não encontrado.");
        }
    }

    public Optional<Usuario> obterUsuarioLogado() {
        Optional<Long> idUsuarioLogado = obterIdUsuarioLogado();
        // Verifica se o ID do usuário logado está presente
        return idUsuarioLogado.map(id -> buscarUsuarioPorId(id));
    }

    private Optional<Long> obterIdUsuarioLogado() {
        // Obtém as preferências compartilhadas
        SharedPreferences preferences = context.getSharedPreferences("MeuAppPrefs", Context.MODE_PRIVATE);

        // Obtém o ID do usuário logado (ou retorna vazio se não estiver presente)
        return Optional.of(preferences.getLong(PREF_USUARIO_LOGADO, -1L));
    }

    public void atualizarUsuario(Usuario usuarioAtualizado) {
        obterIdUsuarioLogado().ifPresent(id -> {
            usuarioAtualizado.setId(id);
            dbHelper.atualizarUsuario(usuarioAtualizado);
        });
    }
}
