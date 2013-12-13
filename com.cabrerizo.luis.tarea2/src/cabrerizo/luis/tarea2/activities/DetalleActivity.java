package cabrerizo.luis.tarea2.activities;

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
import com.cabrerizo.luis.tarea2.R;

public class DetalleActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		
		final int valor = getIntent().getIntExtra("valor", 0);
		
		final TextView nombre = (TextView)findViewById(R.id.Nombre);
		final TextView direccion = (TextView)findViewById(R.id.Direccion);
		final TextView telefono = (TextView)findViewById(R.id.Telefono);
		final TextView horarios = (TextView)findViewById(R.id.Horarios);
		final TextView website = (TextView)findViewById(R.id.Website);
		final TextView eMail = (TextView)findViewById(R.id.EMail);
		
		switch (valor) {
		case 0:
			nombre.setText("Tienda de Zapatos");
			direccion.setText("436 Mayfield Ave, Stanford, CA");
			telefono.setText("(415) 555-1212");
			horarios.setText("Lunes a Sabado : 9:00 - 16:00");
			website.setText("www.zapateria.com");
			eMail.setText("info@zapatos.com");
			break;
		case 1:
			nombre.setText("Tienda de Pantalones");
			direccion.setText("34 Maint St New York, NY");
			telefono.setText("(456) 555-1212");
			horarios.setText("Lunes a Viernes : 10:00 - 19:00\nSabado : 10:00 - 14:00");
			eMail.setText("info@pantalones.com");
			website.setText("www.pantalones.com");
			break;
		case 2:
			nombre.setText("Tienda de Camisas");
			direccion.setText("42 Maint St Los Angeles, LA");
			telefono.setText("(415) 555-2222");
			horarios.setText("Lunes a Viernes : 09:30 - 20:00\nSabado : 10:30 - 14:30");
			website.setText("www.camisas.com");
			eMail.setText("info@camisas.com");
			break;
		case 3:
			nombre.setText("Tienda de Deporte");
			direccion.setText("47 Maint St Illinois, IL");
			telefono.setText("(415) 555-1232");
			horarios.setText("Lunes a Viernes : 10:00 - 20:00\nSabado : 10:00 - 18:30");
			website.setText("www.deportes.com");
			eMail.setText("info@deportes.com");
			break;
		case 4:
			nombre.setText("Tienda de Caramelos");
			direccion.setText("31 Maint St New York, NY");
			telefono.setText("(415) 555-1111");
			horarios.setText("Lunes a Viernes : 09:00 - 15:00\nSabado : 16:30 - 19:30");
			website.setText("www.caramelos.com");
			eMail.setText("info@caramelos.com");
			break;
		case 5:
			nombre.setText("Tienda de Videojuegos");
			direccion.setText("34 Saints St New York, NY");
			telefono.setText("(111) 111-1111");
			horarios.setText("Lunes a Viernes : 10:00 - 22:00");
			website.setText("www.videojuegos.com");
			eMail.setText("info@videojuegos.com");
			break;
		case 6:
			nombre.setText("Tienda de Ordenadores");
			direccion.setText("23 Jordan Av New York, NY");
			telefono.setText("(222) 222-2222");
			horarios.setText("Lunes a Viernes : 10:00 - 20:30\nSabado: 10:30 - 14:30");
			website.setText("www.ordenadores.com");
			eMail.setText("info@ordenadores.com");
			break;				
		default:
			break;
		}
		
		Linkify.addLinks(direccion, Linkify.ALL);
		Linkify.addLinks(telefono, Linkify.ALL);
		Linkify.addLinks(website, Linkify.ALL);
		Linkify.addLinks(eMail, Linkify.ALL);
		
		Button llamada = (Button)findViewById(R.id.botonLlamada);
		Button imagen = (Button)findViewById(R.id.botonImagen);
		
		
		OnClickListener botonImagen = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),FotografiaActivity.class);
				intent.putExtra("foto", valor);
				startActivity(intent);
			}
		};
		
		OnClickListener botonLlamada = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + telefono.getText().toString().trim()));
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
		switch (item.getItemId()) 
		{
		case R.id.action_share:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			String mensaje = getString(R.string.msg_share_text) + "\r\n";
			
			mensaje = mensaje + ((TextView)findViewById(R.id.Nombre)).getText().toString() + "\r\n";
			mensaje = mensaje + ((TextView)findViewById(R.id.Direccion)).getText().toString() + "\r\n";
			mensaje = mensaje + ((TextView)findViewById(R.id.Telefono)).getText().toString() + "\r\n";
			mensaje = mensaje + ((TextView)findViewById(R.id.Horarios)).getText().toString() + "\r\n";
			mensaje = mensaje + ((TextView)findViewById(R.id.Website)).getText().toString() + "\r\n";
			mensaje = mensaje + ((TextView)findViewById(R.id.EMail)).getText().toString() + "\r\n";
			
			intent.putExtra(Intent.EXTRA_TEXT, mensaje);
			intent.setType("text/plain");
			startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

}
