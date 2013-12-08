package cabrerizo.luis.tarea3.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import cabrerizo.luis.tarea3.R;
import cabrerizo.luis.tarea3.data.InstagramPicture;
import cabrerizo.luis.tarea3.data.ListViewAdapter;

public class ComunidadFragment extends Fragment{
	
	ListViewAdapter adapter;
	ArrayList<InstagramPicture> imagesArray;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_comunidad, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ListView lista = (ListView)getActivity().findViewById(R.id.listaImagenes);
		
		
		imagesArray = new ArrayList<InstagramPicture>();
		adapter = new ListViewAdapter(getActivity(), imagesArray, R.id.progressBar, R.id.listaImagenes);
		
		lista.setAdapter(adapter); 

		ImageButton tomaFoto = (ImageButton)getActivity().findViewById(R.id.btnCamara);
		
		tomaFoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new FotoDialogFragment().show(getFragmentManager(), "");	
			}
		});
	}
}	


