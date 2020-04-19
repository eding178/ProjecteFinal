package com.example.projectefinal.ui.maquines;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectefinal.DataSource;
import com.example.projectefinal.R;
import com.example.projectefinal.myDialogs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class maquinesAddEdit extends AppCompatActivity {
    DataSource dataSource;
    private long id;
    private Cursor zonesCursor;
    private Cursor tipusMaquinesCursor;
    private String zonaEscullida;
    private String tipusMaquinaEscullit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquines_add_edit);
        dataSource = new DataSource(this);

//CONSULTO QUINES ZONES I TIPUS DE MAQUINA HI HA
        zonesCursor = dataSource.zones();
        tipusMaquinesCursor = dataSource.tipusMaquines();

//HO PASSO A ARRAYLIST EL CURSOR DE ZONES
        ArrayList<String> arrayZones = new ArrayList<String>();
        while(zonesCursor.moveToNext()) {
            arrayZones.add(zonesCursor.getString(zonesCursor.getColumnIndex(dataSource.ZONES_NOM))); //add the item
        }
        System.out.println(arrayZones.toString());

//HO PASSO A ARRAYLIST EL CURSOR DE TIPUSMAQUINES
        ArrayList<String> arrayTipusMaquines = new ArrayList<String>();
        while(tipusMaquinesCursor.moveToNext()) {
            arrayTipusMaquines.add(tipusMaquinesCursor.getString(tipusMaquinesCursor.getColumnIndex(dataSource.TIPUS_MAQUINES_NOM))); //add the item
        }
        System.out.println(arrayTipusMaquines.toString());

        Spinner desplegableTipusMaquina = findViewById(R.id.tipusMaquina);
        ArrayAdapter<CharSequence> adapterTipusMaquina = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayTipusMaquines);
        desplegableTipusMaquina.setAdapter(adapterTipusMaquina);
        desplegableTipusMaquina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipusMaquinaEscullit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner desplegableZones = findViewById(R.id.zona);
        ArrayAdapter<CharSequence> adapterZones = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayTipusMaquines);
        desplegableZones.setAdapter(adapterZones);
        desplegableZones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zonaEscullida = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Boton ok
        ImageView btnOk = (ImageView) findViewById(R.id.btnSave);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aceptarCambios();
            }
        });

        // Boton cancelar
        ImageView  btnCancel = (ImageView) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelarCambios();
            }
        });

        // Busquem el id que estem modificant
        // si el id es -1 vol dir que s'està creant
        id = this.getIntent().getExtras().getLong("id");

        if (id != -1) {
            // Si estem modificant carreguem les dades en pantalla
            cargarDatos();
        }
    }
    private void cargarDatos() {

        // Demanem un cursor que retorna un sol registre amb les dades de la tasca
        // Això es podria fer amb un classe pero...
        Cursor datos = dataSource.maquina(id);
        datos.moveToFirst();

        // Carreguem les dades en la interfície
        TextView tv;

        tv = (TextView) findViewById(R.id.nom);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_NOM_CLIENT)));
        tv.setEnabled(false);

        tv = (TextView) findViewById(R.id.adreca);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_ADRECA)));

        tv = (TextView) findViewById(R.id.codiPostal);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_CODI_POSTAL)));

        tv = (TextView) findViewById(R.id.poblacio);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_POBLACIO)));

        tv = (TextView) findViewById(R.id.telefon);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_TELEFON)));

        tv = (TextView) findViewById(R.id.email);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_EMAIL)));

        tv = (TextView) findViewById(R.id.numeroSerie);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_NUMERO_SERIE)));

        tv = (TextView) findViewById(R.id.dataRevisio);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_ULTIMA_REVISIO)));
/*

        tv = (TextView) findViewById(R.id.tipusMaquina);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_TIPUS_ID)));

        tv = (TextView) findViewById(R.id.zona);
        tv.setText(datos.getString(datos.getColumnIndex(DataSource.MAQUINA_ZONA_ID)));
*/
    }
    private void aceptarCambios() {
        // Validem les dades que cal validar

        TextView tv;
        tv = (TextView) findViewById(R.id.nom);
        String nom;
        try {
            nom = (tv.getText().toString());
            if(nom==null){
                myDialogs.showToast(this,"El nom es obligatori");
                return;
            }
            else if(null==""){
                myDialogs.showToast(this,"El nom es obligatori");
                return;
            }
        }
        catch (Exception e) {
            myDialogs.showToast(this,"Hi ha hagut algun error");
            return;
        }

        tv = (TextView) findViewById(R.id.numeroSerie);
        String num;
        try {
            num = tv.getText().toString();
            if(num.length()<=0){
                myDialogs.showToast(this,"El numero de serie no es valid");
                return;
            }
        }
        catch (Exception e) {
            myDialogs.showToast(this,"El numero de serie ha de ser un valor numeric");
            return;
        }
/*
        tv = (TextView) findViewById(R.id.tipusMaquina);
        String tipusMaquina = tv.getText().toString();
        if(tipusMaquina.length()==0){
            myDialogs.showToast(this,"El tipus maquina es obligatoria");
            return;
        }


        tv = (TextView) findViewById(R.id.zona);
        String zona = tv.getText().toString();
        if(zona.length()==0){
            myDialogs.showToast(this,"La zona es obligatoria");
            return;
        }
*/
        String tipusMaquina="T1";
        String zona="Z1";


        // guardem les altres dades que no cal validar
        tv = (TextView) findViewById(R.id.adreca);
        String adreca = tv.getText().toString();

        tv = (TextView) findViewById(R.id.codiPostal);
        String codiPostal = tv.getText().toString();

        tv = (TextView) findViewById(R.id.poblacio);
        String poblacio = tv.getText().toString();

        tv = (TextView) findViewById(R.id.telefon);
        String tel = tv.getText().toString();

        tv = (TextView) findViewById(R.id.email);
        String email = tv.getText().toString();

        tv = (TextView) findViewById(R.id.dataRevisio);
        String dataRevisio = tv.getText().toString();
        if(id==-1)
            dataSource.addMaquina(nom,adreca,codiPostal,poblacio,tel,email,num,dataRevisio,tipusMaquina,zona);
        else
            dataSource.updateMaquina(id,nom,adreca,codiPostal,poblacio,tel,email,num,dataRevisio,tipusMaquina,zona);

        Intent mIntent = new Intent();
        mIntent.putExtra("id", id);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    private void cancelarCambios() {
        Intent mIntent = new Intent();
        mIntent.putExtra("id", id);
        setResult(RESULT_CANCELED, mIntent);
        finish();
    }
}
