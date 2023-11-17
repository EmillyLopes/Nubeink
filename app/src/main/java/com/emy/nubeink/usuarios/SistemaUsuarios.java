package com.emy.nubeink.usuarios;

import java.util.ArrayList;
import java.util.List;

public class SistemaUsuarios {
    private List<Usuario> listaUsuarios;

    public SistemaUsuarios() {
        // Inicializa a lista de usuários
        listaUsuarios = new ArrayList<>();
    }

    // Adiciona um usuário à lista
    public void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    // Busca um usuário pelo e-mail
    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equals(email)) {
                System.out.println("Usuário encontrado: " + usuario.getNome());
            }
        }
        System.out.println("Usuário não encontrado para o e-mail: " + email);
        return null;
    }
}
