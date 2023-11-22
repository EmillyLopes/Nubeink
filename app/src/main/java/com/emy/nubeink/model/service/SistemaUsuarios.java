package com.emy.nubeink.model.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.emy.nubeink.model.dto.Usuario;
import com.emy.nubeink.model.repository.UsuarioDbHelper;

import java.util.Optional;

public class SistemaUsuarios {
    private UsuarioDbHelper dbHelper;

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

    public Usuario buscarUsuario(String nomeDestino, long idDestino) {
        if (!nomeDestino.isEmpty() && idDestino != 0) {
            return buscarUsuarioPorNomeEID(nomeDestino, idDestino);
        } else if (!nomeDestino.isEmpty()) {
            return buscarUsuarioPorNome(nomeDestino);
        } else if (idDestino != 0) {
            return buscarUsuarioPorId(idDestino);
        }
        return null;
    }

    public double buscarSaldoUsuario(Usuario usuario) {
        Usuario usuarioEcontrado = buscarUsuario(usuario.getNome(), usuario.getId());
        if (usuarioEcontrado != null) {
            return usuarioEcontrado.getSaldo();
        } else {
            // Você pode retornar um valor padrão ou lançar uma exceção, dependendo da lógica do seu aplicativo.
            return 0.0;
        }
    }

    public Optional<Usuario> obterUsuarioLogado(Usuario usuario) {
        return Optional.ofNullable(buscarUsuarioPorId(usuario.getId()));
    }

    public void atualizarUsuario(Usuario usuarioAtualizado) {
        dbHelper.atualizarUsuario(usuarioAtualizado);
    }

    public void realizarTransferencia(Usuario usuarioLogado, double valor, Usuario usuarioDestino) {
        Optional<Usuario> usuarioOrigem = obterUsuarioLogado(usuarioLogado);

        if (usuarioOrigem.isPresent() && usuarioOrigem.get().getSaldo() >= valor) {
            Usuario usuarioDestinoEncontrado = buscarUsuario(usuarioDestino.getNome(), usuarioDestino.getId());
            if (usuarioDestinoEncontrado != null) {
                transferirSaldo(usuarioOrigem.get(), usuarioDestinoEncontrado, valor);
            } else {
                Log.e("SistemaUsuarios", "Usuário de destino não encontrado.");
            }
        } else {
            Log.e("SistemaUsuarios", "Saldo insuficiente para realizar a transferência ou usuário de origem não encontrado.");
        }
    }

    private void transferirSaldo(Usuario usuarioOrigem, Usuario usuarioDestino, double valor) {
        usuarioOrigem.debitarSaldo(valor);
        usuarioDestino.creditarSaldo(valor);

        atualizarUsuario(usuarioOrigem);
        atualizarUsuario(usuarioDestino);

        Log.i("SistemaUsuarios", "Transferência realizada com sucesso.");
    }

    public Intent criarIntentUsuario(Usuario usuarioEncontrado, Context packageContext, Class<?> classeDestino) {
        Intent intent = new Intent(packageContext, classeDestino);
        intent.putExtra("NOME_USUARIO", usuarioEncontrado.getNome());
        intent.putExtra("EMAIL_USUARIO", usuarioEncontrado.getEmail());
        intent.putExtra("SALDO_USUARIO", usuarioEncontrado.getSaldo());
        intent.putExtra("ID_USUARIO", usuarioEncontrado.getId());
        return intent;
    }
}
