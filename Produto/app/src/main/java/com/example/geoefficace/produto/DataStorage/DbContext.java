package com.example.geoefficace.produto.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by geoefficace on 03/04/2019.
 */

public class DbContext {
    protected Context context;

    public DbContext(Context context) {
        this.context = context;
    }

    protected SQLiteDatabase getWritableDatabase() {
        try{
            return Manager.getInstance(context).getWritableDatabase();
        } catch (Exception e){
            return null;
        }

    }

    protected SQLiteDatabase getReadableDatabase() {
        try{
            return Manager.getInstance(context).getReadableDatabase();
        } catch (Exception e){
            return null;
        }

    }
}
