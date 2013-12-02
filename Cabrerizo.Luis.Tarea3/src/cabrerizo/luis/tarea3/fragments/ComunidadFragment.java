package cabrerizo.luis.tarea3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cabrerizo.luis.tarea3.R;
import cabrerizo.luis.tarea3.data.ListViewAdapter;

public class ComunidadFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_comunidad, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ListView lista = (ListView)getActivity().findViewById(R.id.listaImagenes);
		lista.setAdapter(new ListViewAdapter(getActivity().getApplicationContext()));
		
	}

}