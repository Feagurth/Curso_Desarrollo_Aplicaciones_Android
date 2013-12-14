package cabrerizo.luis.tarea4.fragments;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cabrerizo.luis.tarea4.activities.MainActivity;

import com.cabrerizo.luis.tarea4.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class TiendasContentFragment extends Fragment implements TabListener,
		OnConnectionFailedListener, ConnectionCallbacks, LocationListener {

	public final static int MILLISECONDS_PER_SECOND = 1000;
	public final static long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 5;
	public final static long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 1;

	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	private LocationClient locationClient;
	private LocationRequest locationRequest;
	
	public int currentTab;

	Location currentLocation;

	Fragment[] fragments = new Fragment[] { new ListadoFragment(),
			new MapaFragment() };

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		locationClient = new LocationClient(getActivity(), this, this);
		locationRequest = LocationRequest.create();
		locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		locationRequest
				.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		ActionBar actionbar = ((MainActivity) getActivity())
				.getSupportActionBar();

		actionbar.addTab(actionbar.newTab()
				.setText(R.string.title_fragment_Listado).setTabListener(this));

		actionbar.addTab(actionbar.newTab()
				.setText(R.string.title_fragment_Mapa).setTabListener(this));

		FragmentManager manager = getActivity().getSupportFragmentManager();
		manager.beginTransaction().add(R.id.MainContent, fragments[0])
				.add(R.id.MainContent, fragments[1]).commit();

	}

	@Override
	public void onStart() {
		super.onStart();
		locationClient.connect();		
		((MapaFragment) fragments[1]).centerMap(locationClient);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment esconder = null;
		Fragment mostrar = null;

		switch (tab.getPosition()) {
		case 0:
			esconder = fragments[1];
			mostrar = fragments[0];
			currentTab = 0;
			this.onStop();
			break;
		case 1:
			esconder = fragments[0];
			mostrar = fragments[1];
			currentTab = 1;
			this.onStart();
		default:
			break;
		}

		if (servicesConnected(mostrar)) {
			ft.hide(esconder).show(mostrar);
		} else {
			if (mostrar == fragments[1]) {
				ft.hide(esconder).hide(mostrar);
			} else {
				ft.hide(esconder).show(mostrar);
			}
		}

	}

	private boolean servicesConnected(Fragment framentVisible) {
		int code = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());

		if (code == ConnectionResult.SUCCESS) {
			return true;

		} else {
			if (framentVisible == fragments[1]) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(code,
						getActivity(), 0);

				if (dialog != null) {
					ErrorDialogFragment fragment = new ErrorDialogFragment();
					fragment.setDialog(dialog);
					fragment.show(getChildFragmentManager(), "error");
				}
			}
		}
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tiendas_content, container,
				false);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(getActivity(),
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (IntentSender.SendIntentException e) {
				Log.e("ERROR", Log.getStackTraceString(e));

			}
		} else {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
					connectionResult.getErrorCode(), getActivity(),
					CONNECTION_FAILURE_RESOLUTION_REQUEST);

			if (dialog != null) {
				ErrorDialogFragment fragment = new ErrorDialogFragment();
				fragment.setDialog(dialog);
				fragment.show(getChildFragmentManager(), "error");
			}
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		if (locationClient.isConnected()) {
			((MapaFragment) fragments[1]).updateLocation(locationClient);
			locationClient.requestLocationUpdates(locationRequest, this);
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		if (locationClient.isConnected()) {
			((MapaFragment) fragments[1]).updateLocation(locationClient);
		}
	}
}
