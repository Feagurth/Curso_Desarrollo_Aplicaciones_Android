package cabrerizo.luis.tarea1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class FotografiaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);
		
		int foto = getIntent().getIntExtra("foto", 0);
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

}
