package cabrerizo.luis.tarea4.activities;

import java.io.File;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.cabrerizo.luis.tarea4.R;
import cabrerizo.luis.tarea4.data.Photo;
import cabrerizo.luis.tarea4.data.UrlToBitmapTask;
import cabrerizo.luis.tarea4.global.Utiles;

public class FotografiaActivity extends FragmentActivity {
	private static final String FILENAME = "tmpimg.jpg";

	ImageView imagen;
	TextView texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);

		final Photo foto = (Photo) getIntent().getExtras().getSerializable(
				"photo");

		imagen = (ImageView) findViewById(R.id.imagen);
		texto = (TextView) findViewById(R.id.textoDescriptivo);

		UrlToBitmapTask tarea = new UrlToBitmapTask();
		AsyncTask<String, Void, Bitmap> fotiqui = tarea.execute(foto.getUrl());

		try {
			imagen.setImageBitmap(fotiqui.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		texto.setText(foto.getDescripcion());
		
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
