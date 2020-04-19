package com.example.projectefinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class Helper  extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;

    // database name
    private static final String database_NAME = "BuidemDataBase";

    String CREATE_MAQUINA =
            "CREATE TABLE maquina ( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom_client TEXT NOT NULL," +
                    "adreca TEXT NOT NULL," +
                    "codi_postal TEXT NOT NULL," +
                    "poblacio TEXT NOT NULL," +
                    "telefon TEXT," +
                    "email TEXT," +
                    "numero_serie INTEGER NOT NULL," +
                    "ultima_revisio TEXT," +
                    "tipus_id INTEGER NOT NULL," +
                    "zones_id INTEGER NOT NULL," +
                    "FOREIGN KEY(tipus_id) REFERENCES tipus_maquina(_id) ," +
                    "FOREIGN KEY(zones_id) REFERENCES zones(_id))";

    private String CREATE_ZONES =
            "CREATE TABLE zones (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL)";

    private String CREATE_TIPUS_MAQUINES =
            "CREATE TABLE tipus_maquina (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL)";



    public Helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ZONES);
        db.execSQL(CREATE_TIPUS_MAQUINES);
        db.execSQL(CREATE_MAQUINA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}