package com.example.geoefficace.produto.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by geoefficace on 02/04/2019.
 */

public class Manager extends SQLiteOpenHelper {

    public static final String DB_NAME = "VersionDB.db";
    public static final int DB_VERSION = 40;
    private static Manager mInstance;
    private final Context myContext;


    public Manager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
    }

    public static synchronized Manager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Manager(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new UsuarioDataStorage(myContext).onCreate(db);
        new ProdutoDataStorage(myContext).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean deleteDatabase(){
        if(this.myContext.deleteDatabase(DB_NAME)){
            mInstance = null;
            return true;
        }

        return false;
    }
}
