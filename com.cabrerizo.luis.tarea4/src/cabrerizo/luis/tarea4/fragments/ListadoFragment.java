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
import android.widget.TextView;
import cabrerizo.luis.tarea4.App;
import cabrerizo.luis.tarea4.activities.DetalleActivity;
import cabrerizo.luis.tarea4.data.Store;
import cabrerizo.luis.tarea4.global.Utiles;

import com.cabrerizo.luis.tarea4.R;

public class ListadoFragment extends Fragment implements OnItemClickListener {
	ListView lista;
	ArrayList<Store> storeArray;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		storeArray = ((App)getActivity().getApplicationContext()).getStoreArray();
		

		String[] listaTiendas = new String[storeArray.size()];

		for (int i = 0; i < storeArray.size(); i++) {
			listaTiendas[i] = storeArray.get(i).getNombre();
		}

		lista.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_2, android.R.id.text1, listaTiendas)
		{
			@Override
			 public View getView(int position, View convertView, ViewGroup parent) {
			      View view = super.getView(position, convertView, parent);
			      TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			      TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			      text1.setText(storeArray.get(position).getNombre());
			      text2.setText(Utiles.parseTipoTienda(getContext(), storeArray.get(position).getTipoTienda()));

			      return view;
			    }
		});

		lista.setOnItemClickListener(this);
		registerForContextMenu(lista);

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vista = inflater.inflate(R.layout.fragment_listado, container,
				false);

		lista = (ListView) vista.findViewById(R.id.listaTiendas);

		return vista;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(getActivity(), DetalleActivity.class);

		int id = ((App)getActivity().getApplicationContext()).getStoreArray().get(arg2).getId();
				
		intent.putExtra("id", id);

		startActivity(intent);
	}

}
