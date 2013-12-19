package cabrerizo.luis.tarea4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import cabrerizo.luis.tarea4.App;
import cabrerizo.luis.tarea4.data.Data;
import cabrerizo.luis.tarea4.data.models.Store;

import com.android.volley.toolbox.NetworkImageView;
import com.cabrerizo.luis.tarea4.R;

public class DetalleActivity extends FragmentActivity {
	Store store;
	TextView favoritos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);

		store = Data.locateStore(getApplicationContext(), getIntent().getExtras().getInt("id"));

		TextView nombre = (TextView) findViewById(R.id.Nombre);
		TextView direccion = (TextView) findViewById(R.id.Direccion);
		TextView telefono = (TextView) findViewById(R.id.Telefono);
		TextView horarios = (TextView) findViewById(R.id.Horarios);
		TextView website = (TextView) findViewById(R.id.Website);
		TextView eMail = (TextView) findViewById(R.id.EMail);
		NetworkImageView fotoDetalle = (NetworkImageView) findViewById(R.id.fotoDetalle);
		favoritos = (TextView) findViewById(R.id.textoFavoritos);

		nombre.setText(store.getNombre());
		direccion.setText(store.getDireccion());
		telefono.setText(store.getTelefono());
		horarios.setText(store.getHorarios());
		website.setText(store.getWebsite());
		eMail.setText(store.getEmail());
		fotoDetalle.setImageUrl(store.getFoto().getUrl(),
				((App) getApplicationContext()).getImageLoader());
		favoritos.setText(getString(R.string.Favoritos)
				+ String.valueOf(store.getNumeroFavoritos()));
		
		Linkify.addLinks(direccion, Linkify.MAP_ADDRESSES);
		Linkify.addLinks(telefono, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(website, Linkify.WEB_URLS);
		Linkify.addLinks(eMail, Linkify.EMAIL_ADDRESSES);
		
		OnClickListener botonImagen = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						FotografiaActivity.class);

				intent.putExtra("id", store.getId());

				startActivity(intent);
			}
		};

		fotoDetalle.setOnClickListener(botonImagen);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		if (store.getEsFavorito() == 0) {
			menu.getItem(0).setIcon(R.drawable.ic_action_not_important);
		} else {
			menu.getItem(0).setIcon(R.drawable.ic_action_important);
		}

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detalle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_share:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			String mensaje = getString(R.string.msg_share_text) + "\r\n";

			mensaje = mensaje
					+ ((TextView) findViewById(R.id.Nombre)).getText()
							.toString() + "\r\n";
			mensaje = mensaje
					+ ((TextView) findViewById(R.id.Direccion)).getText()
							.toString() + "\r\n";
			mensaje = mensaje
					+ ((TextView) findViewById(R.id.Telefono)).getText()
							.toString() + "\r\n";
			mensaje = mensaje
					+ ((TextView) findViewById(R.id.Horarios)).getText()
							.toString() + "\r\n";
			mensaje = mensaje
					+ ((TextView) findViewById(R.id.Website)).getText()
							.toString() + "\r\n";
			mensaje = mensaje
					+ ((TextView) findViewById(R.id.EMail)).getText()
							.toString() + "\r\n";

			intent.putExtra(Intent.EXTRA_TEXT, mensaje);
			intent.setType("text/plain");
			startActivity(Intent.createChooser(intent,
					getString(R.string.action_share)));

			return true;
		case R.id.action_star:
			if (store.getEsFavorito() == 0) {
				store.setEsFavorito(1);
				store.setNumeroFavoritos(store.getNumeroFavoritos() + 1);
			} else {
				store.setNumeroFavoritos(store.getNumeroFavoritos() - 1);
				store.setEsFavorito(0);
			}

			
			Data.updateStore(getApplicationContext(), store);
			((App)getApplicationContext()).getDb().updateStore(store);
			
			favoritos.setText(getString(R.string.Favoritos)
					+ String.valueOf(store.getNumeroFavoritos()));

			
			supportInvalidateOptionsMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}