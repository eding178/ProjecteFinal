package com.example.projectefinal.ui.zones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.projectefinal.DataSource;
import com.example.projectefinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ZonesFragment extends Fragment {
    private DataSource dataSource;
    private static String[] from = new String[]{DataSource.ZONES_NOM};
    private static int[] to = new int[]{R.id.zonaSpin};
    com.example.projectefinal.ui.zones.Adapter adapter;
    View root;
    private ZonesViewModel zonesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_zones, container, false);
        dataSource = new DataSource(getActivity());
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addZonesDialog();
            }
        });

        adapter = new com.example.projectefinal.ui.zones.Adapter(getActivity(), R.layout.un_zones, consultaShowZones(), from, to, 1, this);

        if(consultaShowZones().getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hi ha ningun registre de tipus de maquinas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id.listCustom);
        lv.setAdapter(adapter);

        return root;
    }


    private String addZonesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nom del tipus de maquina");

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                consultaAddZones(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        return input.getText().toString();
    }

    public void updateZonesDialog(final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nom nou del tipus de maquina");

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                consultaUpdateZones(id,input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void consultaAddZones(String nomZona) {
        dataSource.addzones(nomZona);
        refreshTasks();
    }

    public void consultaDeleteTipusMaquines(long id) {
        dataSource.deletezones(id);
        refreshTasks();
    }

    public Cursor consultaShowZones() {
        Cursor cursor = dataSource.zones();
        //cursor.moveToFirst();
        return cursor;
    }

    public void consultaUpdateZones(long id,String nom) {
        dataSource.updatezones(id, nom);
        refreshTasks();
    }

    public void refreshTasks() {
        Cursor cursorTasks = consultaShowZones();
        // Notifiquem al adapter que les dades han canviat i que refresqui
        adapter.changeCursor(cursorTasks);
        adapter.notifyDataSetChanged();
    }
}

//****************************
//Adapter
//****************************
class Adapter extends SimpleCursorAdapter {
    private com.example.projectefinal.ui.zones.ZonesFragment fragment;
    private Context context;

    public Adapter (Context context, int layout, Cursor c, String[] from, int[] to, int flags, com.example.projectefinal.ui.zones.ZonesFragment fragment) {
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
                fragment.consultaDeleteTipusMaquines(linia.getLong(linia.getColumnIndexOrThrow(DataSource.TIPUS_MAQUINES_ID)));
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
                fragment.updateZonesDialog(linia.getLong(linia.getColumnIndexOrThrow(DataSource.TIPUS_MAQUINES_ID)));
            }
        });
        return view;
    }
}



