package cabrerizo.luis.tarea4.fragments;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import cabrerizo.luis.tarea4.R;
import cabrerizo.luis.tarea4.data.InstagramPicture;
import cabrerizo.luis.tarea4.data.ListViewAdapter;

public class ComunidadFragment extends Fragment implements OnRefreshListener {

	ListViewAdapter adapter;
	ArrayList<InstagramPicture> imagesArray;
	ListView lista;
	private PullToRefreshLayout mPullToRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_comunidad, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		lista = (ListView) getActivity().findViewById(R.id.listaImagenes);

		  mPullToRefreshLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.pull);
		    
		  ActionBarPullToRefresh.from(getActivity())
		  			.allChildrenArePullable()
		            .listener(this)
		            .setup(mPullToRefreshLayout);		
		
		imagesArray = new ArrayList<InstagramPicture>();
		adapter = new ListViewAdapter(getActivity(), imagesArray,
				R.id.progressBar, R.id.listaImagenes, mPullToRefreshLayout);

		lista.setAdapter(adapter);

		final ImageButton tomaFoto = (ImageButton) getActivity().findViewById(
				R.id.btnCamara);
				
		tomaFoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new FotoDialogFragment().show(getFragmentManager(), "");
			}
		});
	}

	@Override
	public void onRefreshStarted(View view) {
		imagesArray = new ArrayList<InstagramPicture>();
		adapter = new ListViewAdapter(getActivity(), imagesArray,
				R.id.progressBar, R.id.listaImagenes, mPullToRefreshLayout);

		lista.setAdapter(adapter);	
		}


}
