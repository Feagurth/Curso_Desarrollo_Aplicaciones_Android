package cabrerizo.luis.tarea3.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import cabrerizo.luis.tarea2.R;
import cabrerizo.luis.tarea3.data.Photo;

public class FotografiaActivity extends FragmentActivity{
	private static final String FILENAME = "tmpimg.jpg";

	ImageView imagen;
	TextView texto;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotografia);
		
		final Photo foto = (Photo) getIntent().getExtras().getSerializable("foto");
		
		imagen = (ImageView)findViewById(R.id.imagen);
		texto = (TextView)findViewById(R.id.textoDescriptivo);
		
		APITask tarea = new APITask();
		AsyncTask<String,Void,Bitmap> fotiqui = tarea.execute(foto.getUrl());
		
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
	
	class APITask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
		    try {
	            URL url = new URL(params[0]);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            
	            connection.setDoInput(true);
	            connection.connect();
	            
	            InputStream input = connection.getInputStream();
	            Bitmap myBitmap = BitmapFactory.decodeStream(input);

	            return myBitmap;
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotografia, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
			File storageDirectory = null;

			Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
			
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
			
	
			if(dataFile.exists())
			{
				dataFile.delete();
				
			}
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
			intent.putExtra("sms_body", (String) getText(R.string.msg_share_pic) + texto.getText());
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(dataFile));
			intent.setType("image/*");
			startActivity(Intent.createChooser(intent, getText(R.string.action_share)));			
			
			return true;
	}	

}
