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
import cabrerizo.luis.tarea4.data.Data;
import cabrerizo.luis.tarea4.data.models.Photo;
import cabrerizo.luis.tarea4.global.Utiles;

import com.android.volley.toolbox.NetworkImageView;
import com.cabrerizo.luis.tarea4.R;

public class FotografiaActivity extends FragmentActivity {
	private static final String FILENAME = "tmpimg.jpg";

	NetworkImageView imagen;
	TextView texto;
	int IdStore;
	Photo foto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);

		IdStore = getIntent().getExtras().getInt("id");

		foto = Data.locateStore(getApplicationContext(), IdStore)
				.getFoto();

		texto = (TextView) findViewById(R.id.textoDescriptivo);
		imagen = (NetworkImageView) findViewById(R.id.imagen);
		TextView favoritos = (TextView) findViewById(R.id.textoFavoritos);

		imagen.setImageUrl(foto.getUrl(),
				((App) getApplicationContext()).getImageLoader());
		texto.setText(foto.getDescripcion());
		favoritos.setText(getString(R.string.Favoritos)
				+ String.valueOf(foto.getNumeroFavoritos()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotografia, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_share: {

			Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();

			File fichero = Utiles.saveFile(bitmap, FILENAME);

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.putExtra("sms_body",
					(String) getText(R.string.msg_share_pic) + texto.getText());
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fichero));
			intent.setType("image/*");
			startActivity(Intent.createChooser(intent,
					getText(R.string.action_share)));

			return true;
		}
		case R.id.action_star:
			if (foto.getEsFavorito() == 0) {
				foto.setEsFavorito(1);
			} else {
				foto.setEsFavorito(0);
			}
			
			Data.updatePhoto(getApplicationContext(), foto, IdStore);			
			((App)getApplicationContext()).getDb().updatePhoto(foto, IdStore);
			supportInvalidateOptionsMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		if (foto.getEsFavorito() == 0) {
			menu.getItem(0).setIcon(R.drawable.ic_action_not_important);
		} else {
			menu.getItem(0).setIcon(R.drawable.ic_action_important);
		}

		return super.onPrepareOptionsMenu(menu);
	}

}
