package cabrerizo.luis.tarea2.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.cabrerizo.luis.tarea2.R;

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
		
		int ImageId = 0;
		
		switch (item.getItemId()) 
		{
		case R.id.action_share:

			String[] nombreTiendas;
			
			nombreTiendas = getResources().getStringArray(R.array.ListaTiendas);
			
			switch (foto) {
			case 0:
				ImageId = R.drawable.tiendadezapatos;
				break;
			case 1:
				ImageId = R.drawable.tiendadepantalones;
				break;
			case 2:
				ImageId = R.drawable.tiendadecamisas;
				break;
			case 3:
				ImageId = R.drawable.tiendadedeportes;
				break;
			case 4:
				ImageId = R.drawable.tiendadecaramelos;
				break;
			case 5:
				ImageId = R.drawable.tiendadevideojuegos;
				break;
			case 6:
				ImageId = R.drawable.tiendadeordenadores;
				break;				
			default:
				break;
			}
			
			File storageDirectory = null;
			final String FILENAME = ImageId + ".jpg";
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ImageId);
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			    storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			} 
			else 
			{
			    storageDirectory = Environment.getDataDirectory();
			}
			
			if(!storageDirectory.exists()) 
			{
			    storageDirectory.mkdirs();
			}
			
			File dataFile = new File(storageDirectory, FILENAME);
			
			if(!dataFile.exists()) 
			{
			     ByteArrayOutputStream stream = new ByteArrayOutputStream();
			     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			     byte[] bitmapdata = stream.toByteArray();
			     try {
			         FileOutputStream fos = new FileOutputStream(dataFile);
			         fos.write(bitmapdata);
			         fos.close();
			     } catch (FileNotFoundException e) 
			     {
			         e.printStackTrace();
			     } catch (IOException e) 
			     {
			         e.printStackTrace();
			     }
			}
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra("sms_body", "Te envio una foto de : " + nombreTiendas[foto]);
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(dataFile));
			intent.setType("image/*");
			startActivity(Intent.createChooser(intent, getText(R.string.action_share)));			
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	

}
