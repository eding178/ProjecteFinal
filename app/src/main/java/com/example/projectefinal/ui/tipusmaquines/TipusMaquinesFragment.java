package com.example.projectefinal.ui.tipusmaquines;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class TipusMaquinesFragment extends Fragment {
    private DataSource dataSource;
    private static String[] from = new String[]{DataSource.TIPUS_MAQUINES_NOM};
    private static int[] to = new int[]{R.id.tipusMaquina};
    Adapter adapter;
    View root;
    private TipusMaquinesViewModel tipusmaquinesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_tipusmaquines, container, false);
        dataSource = new DataSource(getActivity());
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTipusMaquinesDialog();
            }
        });

        adapter = new Adapter(getActivity(), R.layout.un_tipusmaquines, consultaShowTipusMaquines(), from, to, 1, this);

        if(consultaShowTipusMaquines().getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hi ha ningun registre de tipus de maquinas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id.listCustom);
        lv.setAdapter(adapter);

        return root;
    }

    private String addTipusMaquinesDialog() {
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

                consultaAddTipusMaquines(input.getText().toString());
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

    public void updateTipusMaquinesDialog(final long id) {
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
                consultaUpdateTipusMaquines(id,input.getText().toString());
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

    public void consultaAddTipusMaquines(String nomTipusMaquines) {
        dataSource.addTipusMaquina(nomTipusMaquines);
        refreshTasks();
    }

    public void consultaDeleteTipusMaquines(long id) {
        boolean doIt =dataSource.deleteTipusMaquina(id);
        if(doIt)
        refreshTasks();
        else {
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No s'ha pogut eliminar per que hi han maquines d'aquest tipo.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }
    }

    public Cursor consultaShowTipusMaquines() {
        Cursor cursor = dataSource.tipusMaquines();
        //cursor.moveToFirst();
        return cursor;
    }

    public void consultaUpdateTipusMaquines(long id,String nom) {
        dataSource.updateTipusMaquina(id, nom);
        refreshTasks();
    }

    public void refreshTasks() {
        Cursor cursorTasks = consultaShowTipusMaquines();
        // Notifiquem al adapter que les dades han canviat i que refresqui
        adapter.changeCursor(cursorTasks);
        adapter.notifyDataSetChanged();
    }
}

//****************************
//Adapter
//****************************
class Adapter extends SimpleCursorAdapter {
    private TipusMaquinesFragment fragment;
    private Context context;

    public Adapter (Context context, int layout, Cursor c, String[] from, int[] to, int flags, TipusMaquinesFragment fragment) {
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
                fragment.updateTipusMaquinesDialog(linia.getLong(linia.getColumnIndexOrThrow(DataSource.TIPUS_MAQUINES_ID)));
            }
        });
        return view;
    }
}


