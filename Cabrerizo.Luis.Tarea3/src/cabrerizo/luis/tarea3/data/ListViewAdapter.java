package cabrerizo.luis.tarea3.data;

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

	int[] arrayImagenes = new int[]{
			R.drawable.viewpager_imagen1,
			R.drawable.viewpager_imagen2,
			R.drawable.viewpager_imagen3,
			R.drawable.viewpager_imagen4,
			R.drawable.viewpager_imagen5};
	
	String[] arrayTextos = new String[]{"Texto1", "Texto2", "Texto3", "Texto4", "Texto5"};
	
	private Resources resources;
	private LayoutInflater inflater;
	
	public ListViewAdapter(Context context) {
		
		this.resources = context.getResources();
		this.inflater = LayoutInflater.from(context);

	};
	
	
	@Override
	public int getCount() {
		return arrayImagenes.length;
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
			
		holder.txt.setText(arrayTextos[position].toString());
		holder.img.setImageBitmap(decodeSampledBitmapFromResource(resources, arrayImagenes[position], 400, 200));
			
		return convertView;
	}
	
    static class ViewHolder {
        public ImageView img;
        public TextView txt;
    }
	

}
