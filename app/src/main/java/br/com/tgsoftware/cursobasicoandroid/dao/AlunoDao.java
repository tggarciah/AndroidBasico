package br.com.tgsoftware.cursobasicoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.tgsoftware.cursobasicoandroid.model.Aluno;

public class AlunoDao extends SQLiteOpenHelper {
    public AlunoDao(Context context) {
        super(context, "Agenda", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 2:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql); //indo para a versão 2
        }
    }

    public List<Aluno> buscarAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValues(aluno);
        db.insert("Alunos", null, values);
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("Alunos", "id = ?", new String[]{String.valueOf(aluno.getId())});
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = getContentValues(aluno);
        db.update("Alunos", values, "id = ?", new String[] {String.valueOf(aluno.getId())});
    }

    private ContentValues getContentValues(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public boolean ehAluno(String telefone) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[]{telefone});
        result = cursor.getCount();
        cursor.close();
        return result > 0;
    }
}