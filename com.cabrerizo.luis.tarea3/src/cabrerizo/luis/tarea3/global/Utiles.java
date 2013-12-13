package cabrerizo.luis.tarea3.global;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class Utiles {

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
	public static File saveFile(Bitmap bitmap, String nombre){
		
		File storageDirectory = null;
		final String FILENAME = "tmpimg.jpg";
		final int PIC_HEIGHT = 600;
		final int PIC_WIDTH = 300;
		
		
		if(nombre.isEmpty())
		{
			nombre = FILENAME;
		}
		

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
		
		File dataFile = new File(storageDirectory, nombre);
		

		if(dataFile.exists())
		{
			dataFile.delete();
			
		}
		if(!dataFile.exists())
		{
		     ByteArrayOutputStream stream = new ByteArrayOutputStream();
		     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		     
		     Bitmap tmp;
		     
		     tmp = Bitmap.createScaledBitmap(bitmap, PIC_WIDTH, PIC_HEIGHT, false);
		     
		     bitmap = tmp;
		     
		     
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
		
		return dataFile;
		
	}
	
}
