package cabrerizo.luis.tarea2.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import cabrerizo.luis.tarea2.R;

public class FotografiaActivity extends FragmentActivity{
	int foto = -1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);
		
		foto = getIntent().getIntExtra("foto", 0);
		ImageView imagen = (ImageView)findViewById(R.id.imagen);
		TextView texto = (TextView)findViewById(R.id.textoDescriptivo);
		
		switch (foto) {
		case 0:
			imagen.setImageResource(R.drawable.tiendadezapatos);
			texto.setText(R.string.descripcion_zapatos);
			break;
		case 1:
			imagen.setImageResource(R.drawable.tiendadepantalones);
			texto.setText(R.string.descripcion_pantalones);
			break;
		case 2:
			imagen.setImageResource(R.drawable.tiendadecamisas);
			texto.setText(R.string.descripcion_camisas);
			break;
		case 3:
			imagen.setImageResource(R.drawable.tiendadedeportes);
			texto.setText(R.string.descripcion_deportes);
			break;
		case 4:
			imagen.setImageResource(R.drawable.tiendadecaramelos);
			texto.setText(R.string.descripcion_caramelos);
			break;
		case 5:
			imagen.setImageResource(R.drawable.tiendadevideojuegos);
			texto.setText(R.string.descripcion_videojuegos);
			break;
		case 6:
			imagen.setImageResource(R.drawable.tiendadeordenadores);
			texto.setText(R.string.descripcion_ordenadores);
			break;
		default:
			imagen.setImageResource(R.drawable.nombre);
			texto.setText(R.string.descripcion_nombre);
			break;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotografia, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{
		case R.id.action_share:

			String cadena = "";
			
			switch (foto) {
			case 0:
				cadena = "tiendadezapatos";
				break;
			case 1:
				cadena = "tiendadepantalones";
				break;
			case 2:
				cadena = "tiendadecamisas";
				break;
			case 3:
				cadena = "tiendadedeportes";
				break;
			case 4:
				cadena = "tiendadecaramelos";
				break;
			case 5:
				cadena = "tiendadevideojuegos";
				break;
			case 6:
				cadena = "tiendadeordenadores";
				break;				
			default:
				break;
			}
			
		    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + cadena);
		    
		    Intent intent = new Intent(Intent.ACTION_SEND);
		    intent.setType("image/jpg");
		    intent.putExtra(Intent.EXTRA_STREAM, uri);
		    
		    startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	

}
