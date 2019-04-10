package com.example.geoefficace.produto.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geoefficace.produto.Entity.Usuario;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoefficace on 03/04/2019.
 */

public class UsuarioDataStorage extends DbContext {

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String TABLE_NAME = "usuario";

    public UsuarioDataStorage(Context context) {
        super(context);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" +
                COLUMN_EMAIL + " VARCHAR PRIMARY KEY NOT NULL," +
                COLUMN_PASSWORD + " VARCHAR NOT NULL " +
                ");";
        db.execSQL(sql);

        Usuario element = new Usuario();
        element.setEmail("admin@upf.br");
        element.setPassword(this.MD5("admin1239"));

        this.saveOrUpdate(db, element);

    }

    public void saveOrUpdate(Usuario element){
        this.saveOrUpdate(getWritableDatabase(), element);
    }

    private void saveOrUpdate(SQLiteDatabase db, Usuario element) {

        int action = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, element.getEmail());
        contentValues.put(COLUMN_PASSWORD, element.getPassword());

        int id = (int) db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1)
            db.update(TABLE_NAME, contentValues, COLUMN_EMAIL + " = ?", new String[]{String.valueOf(element.getEmail())});

    }

    public Cursor getAllCursor(String selection, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(true, TABLE_NAME, new String[]{"*"}, selection, selectionArgs, null, null, COLUMN_EMAIL + " ASC", null);
    }

    public Usuario findByEmailAndPassword(String email, String pass) {
        Cursor cursor = getAllCursor(COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, this.MD5(pass)});
        if (cursor.moveToFirst())
            return cursorToObject(cursor);
        return null;
    }

    public List<Usuario> getAll(String sql, String[] args) {
        Cursor cursor = getAllCursor(sql, args);
        List<Usuario> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(cursorToObject(cursor));
        }
        return list;
    }

    public List<String> getAllString(String sql, String[] args) {
        Cursor cursor = getAllCursor(sql, args);
        List<String> list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(cursorToObject(cursor).getEmail().toUpperCase());
        }
        return list;
    }

    public Usuario cursorToObject(Cursor c) {

        Usuario user = new Usuario();
        user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
        user.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));

        return user;

    }


    public void remove(Usuario element) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_EMAIL + " = ?", new String[]{element.getEmail()});
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }

}
