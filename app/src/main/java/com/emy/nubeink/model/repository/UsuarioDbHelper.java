package com.emy.nubeink.model.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emy.nubeink.model.dto.Usuario;

public class UsuarioDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;

    // Construtor
    public UsuarioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsuarioContract.TABLE_NAME + " ("
                + UsuarioContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsuarioContract.COLUMN_NOME + " TEXT NOT NULL, "
                + UsuarioContract.COLUMN_EMAIL + " TEXT NOT NULL, "
                + UsuarioContract.COLUMN_SENHA + " TEXT NOT NULL, "
                + UsuarioContract.COLUMN_SALDO + " REAL NOT NULL);" ;
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implemente a lógica de atualização se necessário
    }

    // Adiciona um novo usuário ao banco de dados
    public long adicionarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsuarioContract.COLUMN_NOME, usuario.getNome());
        values.put(UsuarioContract.COLUMN_EMAIL, usuario.getEmail());
        values.put(UsuarioContract.COLUMN_SENHA, usuario.getSenha());
        values.put(UsuarioContract.COLUMN_SALDO, usuario.getSaldo());

        long newRowId = db.insert(UsuarioContract.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Log.i("SistemaUsuarios", "Usuário adicionado com sucesso. ID: " + newRowId);
        } else {
            Log.e("SistemaUsuarios", "Erro ao adicionar usuário ao banco de dados.");
        }

        db.close();
        return newRowId;
    }

    // Busca um usuário pelo e-mail
    public Usuario buscarUsuarioPorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UsuarioContract.COLUMN_ID,
                UsuarioContract.COLUMN_NOME,
                UsuarioContract.COLUMN_EMAIL,
                UsuarioContract.COLUMN_SENHA,
                UsuarioContract.COLUMN_SALDO
        };

        String selection = UsuarioContract.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                UsuarioContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToNext()) {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_NOME));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SENHA));
                double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SALDO));

                Usuario usuarioEncontrado = new Usuario(userId, nome, email, senha, saldo);

                Log.i("SistemaUsuarios", "Usuário encontrado no banco de dados: " + usuarioEncontrado.getNome());
                return usuarioEncontrado;
            } else {
                Log.i("SistemaUsuarios", "Usuário não encontrado para o e-mail: " + email);
                return null;
            }
        } finally {
            cursor.close();
            db.close();
        }
    }

    // Busca um usuário pelo ID
    public Usuario buscarUsuarioPorId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UsuarioContract.COLUMN_NOME,
                UsuarioContract.COLUMN_EMAIL,
                UsuarioContract.COLUMN_SENHA,
                UsuarioContract.COLUMN_SALDO
        };

        String selection = UsuarioContract.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(
                UsuarioContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToNext()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_NOME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_EMAIL));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SENHA));
                double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SALDO));

                Usuario usuarioEncontrado = new Usuario(userId, nome, email, senha, saldo);

                Log.i("SistemaUsuarios", "Usuário encontrado no banco de dados: " + usuarioEncontrado.getNome());
                return usuarioEncontrado;
            } else {
                Log.i("SistemaUsuarios", "Usuário não encontrado para o ID: " + userId);
                return null;
            }
        } finally {
            cursor.close();
            db.close();
        }
    }

    // Busca um usuário pelo nome
    public Usuario buscarUsuarioPorNome(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UsuarioContract.COLUMN_ID,
                UsuarioContract.COLUMN_EMAIL,
                UsuarioContract.COLUMN_SENHA,
                UsuarioContract.COLUMN_SALDO
        };

        String selection = UsuarioContract.COLUMN_NOME + " = ?";
        String[] selectionArgs = { nome };

        Cursor cursor = db.query(
                UsuarioContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToNext()) {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_ID));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_EMAIL));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SENHA));
                double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SALDO));

                Usuario usuarioEncontrado = new Usuario(userId, nome, email, senha, saldo);

                Log.i("SistemaUsuarios", "Usuário encontrado no banco de dados: " + usuarioEncontrado.getNome());
                return usuarioEncontrado;
            } else {
                Log.i("SistemaUsuarios", "Usuário não encontrado para o nome: " + nome);
                return null;
            }
        } finally {
            cursor.close();
            db.close();
        }
    }

    // Busca um usuário pelo nome e/ou ID no banco de dados
    public Usuario buscarUsuarioPorNomeEId(String nome, long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                UsuarioContract.COLUMN_ID,
                UsuarioContract.COLUMN_NOME,
                UsuarioContract.COLUMN_EMAIL,
                UsuarioContract.COLUMN_SENHA,
                UsuarioContract.COLUMN_SALDO
        };

        String selection;
        String[] selectionArgs;

        if (!nome.isEmpty() && id != 0) {
            // Busca pelo nome e ID
            selection = UsuarioContract.COLUMN_NOME + " = ? AND " + UsuarioContract.COLUMN_ID + " = ?";
            selectionArgs = new String[]{nome, String.valueOf(id)};
        } else if (!nome.isEmpty()) {
            // Busca apenas pelo nome
            selection = UsuarioContract.COLUMN_NOME + " = ?";
            selectionArgs = new String[]{nome};
        } else if (id != 0) {
            // Busca apenas pelo ID
            selection = UsuarioContract.COLUMN_ID + " = ?";
            selectionArgs = new String[]{String.valueOf(id)};
        } else {
            // Caso nenhum critério seja fornecido
            Log.i("SistemaUsuarios", "Nenhum critério de busca fornecido");
            return null;
        }

        Cursor cursor = db.query(
                UsuarioContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        try {
            if (cursor.moveToNext()) {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_ID));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_EMAIL));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SENHA));
                double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuarioContract.COLUMN_SALDO));

                Usuario usuarioEncontrado = new Usuario(userId, nome, email, senha, saldo);

                Log.i("SistemaUsuarios", "Usuário encontrado no banco de dados: " + usuarioEncontrado.getNome());
                return usuarioEncontrado;
            } else {
                Log.i("SistemaUsuarios", "Usuário não encontrado para o nome: " + nome + " e ID: " + id);
                return null;
            }
        } finally {
            cursor.close();
            db.close();
        }
    }

    public void atualizarUsuario(Usuario usuarioAtualizado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsuarioContract.COLUMN_NOME, usuarioAtualizado.getNome());
        values.put(UsuarioContract.COLUMN_EMAIL, usuarioAtualizado.getEmail());
        values.put(UsuarioContract.COLUMN_SENHA, usuarioAtualizado.getSenha());
        values.put(UsuarioContract.COLUMN_SALDO, usuarioAtualizado.getSaldo());

        String selection = UsuarioContract.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(usuarioAtualizado.getId())};

        int count = db.update(
                UsuarioContract.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Log.i("UsuarioDbHelper", "Usuário atualizado com sucesso. ID: " + usuarioAtualizado.getId());
        } else {
            Log.e("UsuarioDbHelper", "Erro ao atualizar usuário no banco de dados. ID: " + usuarioAtualizado.getId());
        }
        db.close();
    }
}