package com.example.projectefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

    public static final String TABLE_MAQUINA="maquina";
    public static final String MAQUINA_ID="_id";
    public static final String MAQUINA_NOM_CLIENT="nom_client";
    public static final String MAQUINA_ADRECA="adreca";
    public static final String MAQUINA_CODI_POSTAL="codi_postal";
    public static final String MAQUINA_POBLACIO="poblacio";
    public static final String MAQUINA_TELEFON="telefon";
    public static final String MAQUINA_EMAIL="email";
    public static final String MAQUINA_NUMERO_SERIE="numero_serie";
    public static final String MAQUINA_ULTIMA_REVISIO="ultima_revisio";
    public static final String MAQUINA_TIPUS_ID="tipus_id";
    public static final String MAQUINA_ZONA_ID="zones_id";

    public static final String TABLE_ZONES="zones";
    public static final String ZONES_ID="_id";
    public static final String ZONES_NOM="nom";

    public static final String TABLE_TIPUS_MAQUINES="tipus_maquina";
    public static final String TIPUS_MAQUINES_ID="_id";
    public static final String TIPUS_MAQUINES_NOM="nom";

    private Helper dbHelper;
    private SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public DataSource(Context ctx) {
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new Helper(ctx);

        // amés també construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    // DESTRUCTOR
    protected void finalize () {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    //***********************
    // CRUD MAQUINES
    //***********************

    public Cursor maquines() {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUMERO_SERIE,MAQUINA_ULTIMA_REVISIO,MAQUINA_TIPUS_ID,MAQUINA_ZONA_ID},
                null, null,
                null, null, MAQUINA_ID);
    }

    public Cursor maquina(long id) {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUMERO_SERIE,MAQUINA_ULTIMA_REVISIO,MAQUINA_TIPUS_ID,MAQUINA_ZONA_ID},
                MAQUINA_ID+"=?", new String[]{String.valueOf(id)},
                null, null, null);
    }

    public long addMaquina(String maquina_nom_client,String maquina_adreca,String maquina_codi_postal,String maquina_poblacio,String maquina_telefon,String maquina_email,String maquina_numero_serie,String maquina_ultima_revisio,String maquina_tipus_id,String maquina_zona_id) {
        ContentValues val = new ContentValues();
        val.put(MAQUINA_NOM_CLIENT,maquina_nom_client);
        val.put(MAQUINA_ADRECA,maquina_adreca);
        val.put(MAQUINA_CODI_POSTAL,maquina_codi_postal);
        val.put(MAQUINA_POBLACIO,maquina_poblacio);
        val.put(MAQUINA_TELEFON,maquina_telefon);
        val.put(MAQUINA_EMAIL,maquina_email);
        val.put(MAQUINA_NUMERO_SERIE,maquina_numero_serie);
        val.put(MAQUINA_ULTIMA_REVISIO,maquina_ultima_revisio);
        val.put(MAQUINA_TIPUS_ID,maquina_tipus_id);
        val.put(MAQUINA_ZONA_ID,maquina_zona_id);
        return dbW.insert(TABLE_MAQUINA,null,val);

    }

    public void updateMaquina(long id,String maquina_nom_client,String maquina_adreca,String maquina_codi_postal,String maquina_poblacio,String maquina_telefon,String maquina_email,String maquina_numero_serie,String maquina_ultima_revisio,String maquina_tipus_id,String maquina_zona_id) {
        ContentValues val = new ContentValues();
        val.put(MAQUINA_NOM_CLIENT,maquina_nom_client);
        val.put(MAQUINA_ADRECA,maquina_adreca);
        val.put(MAQUINA_CODI_POSTAL,maquina_codi_postal);
        val.put(MAQUINA_POBLACIO,maquina_poblacio);
        val.put(MAQUINA_TELEFON,maquina_telefon);
        val.put(MAQUINA_EMAIL,maquina_email);
        val.put(MAQUINA_NUMERO_SERIE,maquina_numero_serie);
        val.put(MAQUINA_ULTIMA_REVISIO,maquina_ultima_revisio);
        val.put(MAQUINA_TIPUS_ID,maquina_tipus_id);
        val.put(MAQUINA_ZONA_ID,maquina_zona_id);
        dbW.update(TABLE_MAQUINA,val,MAQUINA_ID+"=?",new String[]{String.valueOf(id)});
    }

    public void deleteMaquina(long id) {
        dbW.delete(TABLE_MAQUINA,MAQUINA_ID+"=?",new String[]{String.valueOf(id)});
    }

    //Wheres****
    public Cursor maquinaWhere(String camp, int valor){
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUMERO_SERIE,MAQUINA_ULTIMA_REVISIO,MAQUINA_TIPUS_ID,MAQUINA_ZONA_ID},
                camp+"=?", new String[]{String.valueOf(valor)},
                null, null, MAQUINA_ID);
    }
    //OrderBy
    public Cursor maquinaOrderBy(String camp) {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUMERO_SERIE,MAQUINA_ULTIMA_REVISIO,MAQUINA_TIPUS_ID,MAQUINA_ZONA_ID},
                null, null,
                null, null, camp);
    }


    //***********************
    // CRUD TIPUS DE MAQUINES
    //***********************

    public Cursor tipusMaquines() {
        return dbR.query(TABLE_TIPUS_MAQUINES, new String[]{TIPUS_MAQUINES_ID,TIPUS_MAQUINES_NOM},
                null, null,
                null, null, TIPUS_MAQUINES_ID);
    }

    public Cursor tipusMaquina(long id) {
        return dbR.query(TABLE_TIPUS_MAQUINES, new String[]{TIPUS_MAQUINES_ID,TIPUS_MAQUINES_NOM},
                TIPUS_MAQUINES_ID+"=?", new String[]{String.valueOf(id)},
                null, null, TIPUS_MAQUINES_ID);
    }

    public long addTipusMaquina(String tipus_maquina_nom){
        ContentValues val = new ContentValues();
        val.put(TIPUS_MAQUINES_NOM,tipus_maquina_nom);
        return dbW.insert(TABLE_TIPUS_MAQUINES,null,val);
    }

    public void updateTipusMaquina(long id, String tipus_maquina_nom){
        ContentValues val = new ContentValues();
        val.put(TIPUS_MAQUINES_NOM,tipus_maquina_nom);
        dbW.update(TABLE_TIPUS_MAQUINES,val,TIPUS_MAQUINES_ID+"=?",new String[]{String.valueOf(id)});
    }

    public boolean deleteTipusMaquina(long id) {
        //COMPROVACIO PREVIA (no es pot eliminar si hi ha màquines DEL TIPO)
        if(comprobacioDeleteTipusMaquina(id)){
        dbW.delete(TABLE_TIPUS_MAQUINES,TIPUS_MAQUINES_ID+"=?",new String[]{String.valueOf(id)});
            return true;
        }
        else
            return false;
    }



    //***********************
    // CRUD ZONES
    //***********************

    public Cursor zones() {
        return dbR.query(TABLE_ZONES, new String[]{ZONES_ID,ZONES_NOM},
                null, null,
                null, null, ZONES_ID);
    }

    public Cursor zona(long id) {
        return dbR.query(TABLE_ZONES, new String[]{ZONES_ID,ZONES_NOM},
                ZONES_ID+"=?", new String[]{String.valueOf(id)},
                null, null, ZONES_ID);
    }

    public long addzones(String zona_nom){
        ContentValues val = new ContentValues();
        val.put(ZONES_NOM,zona_nom);
        return dbW.insert(TABLE_ZONES,null,val);
    }

    public void updatezones(long id, String zones_nom){
        ContentValues val = new ContentValues();
        val.put(ZONES_NOM,zones_nom);
        dbW.update(TABLE_ZONES,val,ZONES_ID+"=?",new String[]{String.valueOf(id)});
    }

    public boolean deletezones(long id) {
        //COMPROVACIO PREVIA (no es pot eliminar si hi ha màquines en la zona)
        if(comprobacioDeleteZones(id)){
            dbW.delete(TABLE_ZONES,ZONES_ID+"=?",new String[]{String.valueOf(id)});
            return true;
        }
        else
            return false;
    }

    //***********************
    // COMPROVACIONS
    //***********************

    public boolean comprobacioDeleteZones(long id) {
        Cursor cursor= dbR.rawQuery("SELECT COUNT (*) FROM " + TABLE_MAQUINA + " WHERE " + MAQUINA_ZONA_ID + "=?",
                new String[] { String.valueOf(id) });
        return  cursor.getCount()<=0;
    }

    public boolean comprobacioDeleteTipusMaquina(long id) {
        Cursor cursor= dbR.rawQuery("SELECT COUNT (*) FROM " + TABLE_MAQUINA + " WHERE " + MAQUINA_TIPUS_ID + "=?",
                new String[] { String.valueOf(id) });
        return cursor.getCount()<=0;
    }
}
