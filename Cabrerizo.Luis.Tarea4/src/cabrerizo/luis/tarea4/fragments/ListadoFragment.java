package cabrerizo.luis.tarea4.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cabrerizo.luis.tarea3.R;
import cabrerizo.luis.tarea4.activities.DetalleActivity;
import cabrerizo.luis.tarea4.data.Data;
import cabrerizo.luis.tarea4.data.Store;

public class ListadoFragment extends Fragment implements OnItemClickListener {
	ListView lista;
	ArrayList<Store> storeArray = new ArrayList<Store>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		storeArray = Data.ParseStore("data", getActivity());
			
		String[] listaTiendas = new String[storeArray.size()];
		
		for (int i = 0; i < storeArray.size(); i++) {
			listaTiendas[i] = storeArray.get(i).getNombre();
		}
		
		lista.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listaTiendas));
        lista.setOnItemClickListener(this);
        registerForContextMenu(lista);
        
	}        

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vista = inflater.inflate(R.layout.fragment_listado, container, false);
			
	    lista = (ListView)vista.findViewById(R.id.listaTiendas);
	    		
		return vista;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 	Intent intent = new Intent(getActivity(), DetalleActivity.class);
	 	
		 	intent.putExtra("store", storeArray.get(arg2));
		 	
	        startActivity(intent);
	}

	
}
