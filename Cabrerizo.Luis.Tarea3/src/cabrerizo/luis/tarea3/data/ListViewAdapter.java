package cabrerizo.luis.tarea3.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cabrerizo.luis.tarea3.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	
	private static final int PIC_HEIGHT = 600;
	private static final int PIC_WIDTH = 300;
	

	private ArrayList<Bitmap> arrayPics = new ArrayList<Bitmap>();
	private ArrayList<String> arrayFecha = new ArrayList<String>();
	
	private Resources resources;
	private LayoutInflater inflater;


	public void addImage(Bitmap imagen){
		arrayPics.add(Bitmap.createScaledBitmap(imagen, PIC_WIDTH, PIC_HEIGHT, false));
		arrayFecha.add(new SimpleDateFormat("dd/MM/yyyy HH:mm", 
						Locale.getDefault()).format(Calendar.getInstance().getTime()));
		
		this.notifyDataSetChanged();
		
	}
	
	public ListViewAdapter(Context context) {

		this.resources = context.getResources();
		this.inflater = LayoutInflater.from(context);
		
		arrayPics.add(decodeSampledBitmapFromResource(resources, R.drawable.viewpager_imagen1, PIC_WIDTH, PIC_HEIGHT));
		arrayPics.add(decodeSampledBitmapFromResource(resources, R.drawable.viewpager_imagen2, PIC_WIDTH, PIC_HEIGHT));
		arrayPics.add(decodeSampledBitmapFromResource(resources, R.drawable.viewpager_imagen3, PIC_WIDTH, PIC_HEIGHT));
		arrayPics.add(decodeSampledBitmapFromResource(resources, R.drawable.viewpager_imagen4, PIC_WIDTH, PIC_HEIGHT));
		arrayPics.add(decodeSampledBitmapFromResource(resources, R.drawable.viewpager_imagen5, PIC_WIDTH, PIC_HEIGHT));
		
		arrayFecha.add("22/12/2013 22:22");
		arrayFecha.add("23/12/2013 23:23");
		arrayFecha.add("24/12/2013 24:24");
		arrayFecha.add("25/12/2013 25:25");
		arrayFecha.add("26/12/2013 26:26");

	};


	@Override
	public int getCount() {
		return arrayPics.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;

		BitmapFactory.decodeResource(res, resId, options);
		
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(res, resId, options);
	}	

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}        	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null)
		{
			convertView=inflater.inflate(R.layout.listview_image, null);

			holder = new ViewHolder();

			holder.txt = (TextView)convertView.findViewById(R.id.listTexto);
			holder.img = (ImageView)convertView.findViewById(R.id.listImagen);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();

		}

		holder.txt.setText(arrayFecha.get(position));
		holder.img.setImageBitmap(arrayPics.get(position));

		return convertView;
	}

	static class ViewHolder {
		public ImageView img;
		public TextView txt;
	}


}
