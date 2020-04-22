package com.example.projectefinal.ui.maquines;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import static android.app.Activity.RESULT_OK;
import com.example.projectefinal.DataSource;
import com.example.projectefinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MaquinesFragment extends Fragment {
    private static String[] from = new String[]{DataSource.MAQUINA_NOM_CLIENT,DataSource.MAQUINA_ADRECA,DataSource.MAQUINA_TELEFON,DataSource.MAQUINA_EMAIL,DataSource.MAQUINA_NUMERO_SERIE,DataSource.MAQUINA_ULTIMA_REVISIO,DataSource.MAQUINA_TIPUS_ID,DataSource.MAQUINA_ZONA_ID};
    private static int[] to = new int[]{R.id.nom,R.id.adreca,R.id.telefon,R.id.email,R.id.numeroSerie,R.id.dataRevisio,R.id.tipusMaquinaSpin,R.id.zonaSpin};
    private MaquinesViewModel homeViewModel;
    Adapter adapter;
    View root;
    private DataSource dataSource;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_maquines, container, false);
        dataSource = new DataSource(getActivity());
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMaquina();
            }
        });

        adapter = new Adapter(getActivity(), R.layout.un_maquines, consultaShowMaquines(), from, to, 1, this);

        if(consultaShowMaquines().getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hi ha ningun registre de tipus de maquinas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id.listCustom);
        lv.setAdapter(adapter);
        return root;
    }
    public void editMaquina(long id) {
        Intent i = new Intent(MaquinesFragment.this.getActivity(), maquinesAddEdit.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        i.putExtras(bundle);
        startActivityForResult(i,1);
    }

    public void addMaquina() {
        Bundle bundle = new Bundle();
        bundle.putLong("id",-1);
        Intent i = new Intent(MaquinesFragment.this.getActivity(), maquinesAddEdit.class );
        i.putExtras(bundle);
        startActivityForResult(i,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //refresquem despres de afegir o actualitzar
            refreshTasks();
        }
    }

    public void consultaDeleteMaquina(long id) {
        dataSource.deleteMaquina(id);
        refreshTasks();
    }

    public Cursor consultaShowMaquines() {
        Cursor cursor = dataSource.maquines();
        //cursor.moveToFirst();
        return cursor;
    }

    public void refreshTasks() {
        Cursor cursorTasks = consultaShowMaquines();
        // Notifiquem al adapter que les dades han canviat i que refresqui
        adapter.changeCursor(cursorTasks);
        adapter.notifyDataSetChanged();
    }

}
//****************************
//Adapter
//****************************
class Adapter extends SimpleCursorAdapter {
    private MaquinesFragment fragment;
    private Context context;

    public Adapter (Context context, int layout, Cursor c, String[] from, int[] to, int flags, MaquinesFragment fragment) {
        super(context, layout, c, from, to, flags);
        this.context = context;
        this.fragment = fragment;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = super.getView (position, convertView, parent);

        //boto Borrar
        ImageView deleteBtn = view.findViewById(R.id.btnDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Busquem la linia a eliminar
                View row = (View) v.getParent().getParent();
                // Busquem el listView per poder treure el numero de la fila
                ListView lv = (ListView) row.getParent().getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);
                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);
                fragment.consultaDeleteMaquina(linia.getLong(linia.getColumnIndexOrThrow(DataSource.MAQUINA_ID)));
            }
        });

        //boto Editar
        ImageView EditBtn = view.findViewById(R.id.btnEdit);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Busquem la linia a eliminar
                View row = (View) v.getParent().getParent();
                // Busquem el listView per poder treure el numero de la fila
                ListView lv = (ListView) row.getParent().getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);
                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);
                fragment.editMaquina(linia.getLong(linia.getColumnIndexOrThrow(DataSource.MAQUINA_ID)));
            }
        });

        //boto trucar
        ImageView btnMail = view.findViewById(R.id.btnMail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Busquem la linia a eliminar
                View row = (View) v.getParent().getParent();
                // Busquem el listView per poder treure el numero de la fila
                ListView lv = (ListView) row.getParent().getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);
                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);
                int numeroSerie = (linia.getInt(linia.getColumnIndexOrThrow(DataSource.MAQUINA_NUMERO_SERIE)));
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Propera revisió màquina nº "+numeroSerie);
                //intent.putExtra(Intent.EXTRA_TEXT, "Enviant un Mail desde la app");
                //intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"eding178@gmail.com"});
                fragment.startActivity(intent);
            }
        });

        //boto trucar
        ImageView btnCall = view.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Busquem la linia a eliminar
                View row = (View) v.getParent().getParent();
                // Busquem el listView per poder treure el numero de la fila
                ListView lv = (ListView) row.getParent().getParent();
                // Busco quina posicio ocupa la Row dins de la ListView
                int position = lv.getPositionForView(row);
                // Carrego la linia del cursor de la posició.
                Cursor linia = (Cursor) getItem(position);
                int telefon = (linia.getInt(linia.getColumnIndexOrThrow(DataSource.MAQUINA_TELEFON)));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefon));
                fragment.startActivity(intent);
            }
        });
        return view;
    }
}