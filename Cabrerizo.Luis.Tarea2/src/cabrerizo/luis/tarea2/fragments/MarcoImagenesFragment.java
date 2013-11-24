package cabrerizo.luis.tarea2.fragments;

import cabrerizo.luis.tarea2.R;
import cabrerizo.luis.tarea2.data.ImagenesFragmentAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MarcoImagenesFragment extends Fragment {

	ViewPager viewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista = inflater.inflate(R.layout.fragment_marco_imagenes, container);
		
		viewPager = (ViewPager)vista.findViewById(R.id.viewPager);
		
		return vista;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImagenesFragmentAdapter adapter = new ImagenesFragmentAdapter(getChildFragmentManager());
		viewPager.setAdapter(adapter);
		
	}

}
