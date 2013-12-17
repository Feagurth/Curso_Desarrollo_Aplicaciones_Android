package cabrerizo.luis.tarea4.activities;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import cabrerizo.luis.tarea4.App;
import cabrerizo.luis.tarea4.data.Photo;
import cabrerizo.luis.tarea4.global.Utiles;

import com.android.volley.toolbox.NetworkImageView;
import com.cabrerizo.luis.tarea4.R;

public class FotografiaActivity extends FragmentActivity {
	private static final String FILENAME = "tmpimg.jpg";

	NetworkImageView imagen;
	TextView texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);

		int id = getIntent().getExtras().getInt("id");
		
		final Photo foto = Utiles.locateStore(getApplicationContext(), id).getFoto();

		texto = (TextView) findViewById(R.id.textoDescriptivo);
		imagen = (NetworkImageView) findViewById(R.id.imagen);
		TextView favoritos = (TextView)findViewById(R.id.textoFavoritos);
		
		imagen.setImageUrl(foto.getUrl(), ((App)getApplicationContext()).getImageLoader());
		texto.setText(foto.getDescripcion());
		favoritos.setText(getString(R.string.Favoritos) + String.valueOf(foto.getNumeroFavoritos()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotografia, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();

		File fichero = Utiles.saveFile(bitmap, FILENAME);

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra("sms_body", (String) getText(R.string.msg_share_pic)
				+ texto.getText());
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fichero));
		intent.setType("image/*");
		startActivity(Intent.createChooser(intent,
				getText(R.string.action_share)));

		return true;
	}

}
