package cabrerizo.luis.tarea4.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cabrerizo.luis.tarea4.App;
import cabrerizo.luis.tarea4.activities.DetalleActivity;
import cabrerizo.luis.tarea4.data.Store;
import cabrerizo.luis.tarea4.data.UrlToBitmapTask;
import cabrerizo.luis.tarea4.global.Utiles;

import com.cabrerizo.luis.tarea4.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment implements
		InfoWindowAdapter, OnInfoWindowClickListener {

	private GoogleMap map;
	private Bundle savedInstance;
	public LatLng lastPosition = new LatLng(0, 0);
	private ArrayList<Store> storeArray;
	private HashMap<Marker, String> markers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstance = savedInstanceState;

		storeArray = ((App) getActivity().getApplicationContext())
				.getStoreArray();
		markers = ((App) getActivity().getApplicationContext()).getMarkers();
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
		}
	}

	public void centerMap(LocationClient location) {
		if (location.isConnected() && location.getLastLocation() != null) {
			lastPosition = new LatLng(location.getLastLocation().getLatitude(),
					location.getLastLocation().getLongitude());

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 15));
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
				map.getUiSettings().setMyLocationButtonEnabled(true);
				map.setInfoWindowAdapter(this);
				map.setOnInfoWindowClickListener(this);
				populateMarkers();
			}
		}

	}

	private void populateMarkers() {

		if (map != null) {
			if (!storeArray.isEmpty() && storeArray != null) {

				for (Store tienda : storeArray) {
					MarkerOptions options = new MarkerOptions();

					options.position(new LatLng(
							tienda.getUbicacionGeografica()[0], tienda
									.getUbicacionGeografica()[1]));

					options.title(tienda.getNombre());
					options.snippet(tienda.getDireccion());
					options.draggable(false);

					options.icon(BitmapDescriptorFactory.defaultMarker(Utiles
							.parseIconoMapaTipoTienda(tienda.getTipoTienda())));

					Marker marker = map.addMarker(options);
					markers.put(marker, String.valueOf(tienda.getId()));
				}
			}

			((App) getActivity().getApplicationContext()).setMarkers(markers);
		}
	}


	@Override
	public View getInfoContents(Marker marker) {
		View window = getActivity().getLayoutInflater().inflate(
				R.layout.map_info_window, null);

		TextView titulo = (TextView) window.findViewById(R.id.infoWindowTitulo);
		TextView descripcion = (TextView) window
				.findViewById(R.id.infoWindowDescripcion);
		ImageView imagen = (ImageView) window
				.findViewById(R.id.infoWindowImagen);


		UrlToBitmapTask tarea = new UrlToBitmapTask();
		AsyncTask<String, Void, Bitmap> fotiqui = tarea.execute(
				Utiles.locateStore(getActivity().getApplicationContext(), marker)
				.getFoto().getUrl());

		Bitmap tmp = null;

		try {
			tmp = Bitmap.createScaledBitmap(fotiqui.get(), 150, 150, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		imagen.setImageBitmap(tmp);

		titulo.setText(marker.getTitle());
		descripcion.setText(marker.getSnippet());

		return window;
	}
	

		
	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent intent = new Intent(getActivity(), DetalleActivity.class);

		int id = Utiles.locateStore(getActivity().getApplicationContext(), marker).getId();
				
		intent.putExtra("id", id);

		startActivity(intent);

	}
}
