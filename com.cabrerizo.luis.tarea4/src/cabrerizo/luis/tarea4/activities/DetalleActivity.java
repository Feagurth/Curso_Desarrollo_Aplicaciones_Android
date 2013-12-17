package cabrerizo.luis.tarea4.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cabrerizo.luis.tarea4.data.Store;
import cabrerizo.luis.tarea4.global.Utiles;

import com.cabrerizo.luis.tarea4.R;

public class DetalleActivity extends FragmentActivity {
	int id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);

		id = getIntent().getExtras().getInt("id");
			
		final Store store = Utiles.locateStore(getApplicationContext(), id);

		final TextView nombre = (TextView) findViewById(R.id.Nombre);
		final TextView direccion = (TextView) findViewById(R.id.Direccion);
		final TextView telefono = (TextView) findViewById(R.id.Telefono);
		final TextView horarios = (TextView) findViewById(R.id.Horarios);
		final TextView website = (TextView) findViewById(R.id.Website);
		final TextView eMail = (TextView) findViewById(R.id.EMail);

		nombre.setText(store.getNombre());
		direccion.setText(store.getDireccion());
		telefono.setText(store.getTelefono());
		horarios.setText(store.getHorarios());
		website.setText(store.getWebsite());
		eMail.setText(store.getEmail());

		Linkify.addLinks(direccion, Linkify.MAP_ADDRESSES);
		Linkify.addLinks(telefono, Linkify.PHONE_NUMBERS);
		Linkify.addLinks(website, Linkify.WEB_URLS);
		Linkify.addLinks(eMail, Linkify.EMAIL_ADDRESSES);

		Button llamada = (Button) findViewById(R.id.botonLlamada);
		Button imagen = (Button) findViewById(R.id.botonImagen);

		OnClickListener botonImagen = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						FotografiaActivity.class);

				intent.putExtra("id", id);

				startActivity(intent);
			}
		};

		OnClickListener botonLlamada = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"
						+ telefono.getText().toString().trim()));
				startActivity(intent);
			}
		};

		llamada.setOnClickListener(botonLlamada);
		imagen.setOnClickListener(botonImagen);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
