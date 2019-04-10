package com.example.geoefficace.produto.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geoefficace.produto.Entity.Produto;
import com.example.geoefficace.produto.Entity.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoefficace on 03/04/2019.
 */

public class ProdutoDataStorage extends DbContext{

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_SUBCATEGORIA = "subcategoria";
    public static final String COLUMN_PRECO = "preco";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_QUANTIDADE = "quantidade";
    public static final String TABLE_NAME = "produto";

    public ProdutoDataStorage(Context context) {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" +
                COLUMN_ID + " VARCHAR PRIMARY KEY NOT NULL," +
                COLUMN_NOME + " VARCHAR NOT NULL," +
                COLUMN_CATEGORIA + " VARCHAR NOT NULL," +
                COLUMN_SUBCATEGORIA + " VARCHAR NOT NULL," +
                COLUMN_PRECO + " VARCHAR NOT NULL," +
                COLUMN_DESCRICAO + " VARCHAR NOT NULL," +
                COLUMN_QUANTIDADE + " INTEGER NOT NULL " +
                ");";
        db.execSQL(sql);
    }

    public void saveOrUpdate(Produto element){
        this.saveOrUpdate(getWritableDatabase(), element);
    }

    private void saveOrUpdate(SQLiteDatabase db, Produto element) {

        int action = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, element.getId());
        contentValues.put(COLUMN_NOME, element.getNome());
        contentValues.put(COLUMN_CATEGORIA, element.getCategoria());
        contentValues.put(COLUMN_SUBCATEGORIA, element.getSubcategoria());
        contentValues.put(COLUMN_PRECO, element.getPreco());
        contentValues.put(COLUMN_DESCRICAO, element.getDescricao());
        contentValues.put(COLUMN_QUANTIDADE, element.getQuantidade());

        int id = (int) db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1)
            db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(element.getId())});

    }

    public Cursor getAllCursor(String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(true, TABLE_NAME, new String[]{"*"}, selection, selectionArgs, null, null, COLUMN_NOME + " ASC", null);
    }

    public Produto findById(String id) {
        Cursor cursor = getAllCursor(COLUMN_ID + " = ? ", new String[]{id});
        if (cursor.moveToFirst())
            return cursorToObject(cursor);
        return null;
    }

    public List<Produto> getAll(String sql, String[] args) {
        Cursor cursor = getAllCursor(sql, args);
        List<Produto> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(cursorToObject(cursor));
        }
        return list;
    }

    public List<String> getAllString(String sql, String[] args) {
        Cursor cursor = getAllCursor(sql, args);
        List<String> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(cursorToObject(cursor).getNome().toUpperCase());
        }
        return list;
    }

    public Produto cursorToObject(Cursor c) {

        Produto produto = new Produto();
        produto.setId(c.getString(c.getColumnIndex(COLUMN_ID)));
        produto.setNome(c.getString(c.getColumnIndex(COLUMN_NOME)));
        produto.setCategoria(c.getString(c.getColumnIndex(COLUMN_CATEGORIA)));
        produto.setSubcategoria(c.getString(c.getColumnIndex(COLUMN_SUBCATEGORIA)));
        produto.setDescricao(c.getString(c.getColumnIndex(COLUMN_DESCRICAO)));
        produto.setPreco(c.getString(c.getColumnIndex(COLUMN_PRECO)));
        produto.setQuantidade(c.getInt(c.getColumnIndex(COLUMN_QUANTIDADE)));

        return produto;

    }


    public void remove(Produto element) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID+" = ?", new String[]{element.getId()});
    }


}
