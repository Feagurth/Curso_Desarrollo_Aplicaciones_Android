package cabrerizo.luis.tarea4.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.cabrerizo.luis.tarea4.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaFragment extends SupportMapFragment {

	private GoogleMap map;
	private Bundle savedInstance;
	public LatLng lastPosition = new LatLng(0, 0);

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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.mapa, menu);
	}

	public void updateLocation(LocationClient location) {
		if (location.isConnected()) {
			lastPosition = new LatLng(location.getLastLocation().getLatitude(),
					location.getLastLocation().getLongitude());

			map.moveCamera(CameraUpdateFactory.newLatLng(lastPosition));
		}
	}

	public void centerMap(LocationClient location) {
		if (location.isConnected() && location.getLastLocation() != null) {
			lastPosition = new LatLng(location.getLastLocation().getLatitude(),
					location.getLastLocation().getLongitude());

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 10));
		}

	}

	public void changeMapType(int mapType) {
		map.setMapType(mapType);
	}

	private void setupMap() {
		if (map == null) {
			map = getMap();
			if (map != null) {
				if (savedInstance == null) {
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							lastPosition, 10));
				}
				map.setMyLocationEnabled(true);
				map.getUiSettings().setZoomControlsEnabled(false);
				map.getUiSettings().setMyLocationButtonEnabled(false);
			}
		}

	}
}
