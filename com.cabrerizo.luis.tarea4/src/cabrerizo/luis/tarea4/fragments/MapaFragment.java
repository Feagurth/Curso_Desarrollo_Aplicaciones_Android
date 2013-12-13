package cabrerizo.luis.tarea4.fragments;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaFragment extends SupportMapFragment {
	private GoogleMap map;
	private Bundle savedInstance;
	public static final LatLng GUATEMALA = new LatLng(14.62, -90.56);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstance = savedInstanceState;
	}

	@Override
	public void onResume() {
		super.onResume();
		setupMap();
	}

	private void setupMap() {
		if(map == null)
		{
			map = getMap();
			if(map != null)
			{
				if(savedInstance == null)
				{
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(GUATEMALA, 10));
				}
				map.getUiSettings().setZoomControlsEnabled(false);				
				
			}
		}
		
	}
}
