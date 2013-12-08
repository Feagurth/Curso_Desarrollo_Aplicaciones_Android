package cabrerizo.luis.tarea3.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cabrerizo.luis.tarea3.R;
import cabrerizo.luis.tarea3.global.BitmapLRUCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ListViewAdapter extends BaseAdapter {

	private static final int PIC_HEIGHT = 600;
	private static final int PIC_WIDTH = 300;
	
	private ImageLoader imageLoader;


	private ArrayList<InstagramPicture> dataArray = new ArrayList<InstagramPicture>();
	
	//private Resources resources;
	private LayoutInflater inflater;

	static RequestQueue requestQueue;
	static ProgressBar barra;
	static ListView lista;

	public void addImage(Bitmap imagen) {
		/*		
		InstagramPicture pic = new InstagramPicture();
		

		pic.setFoto(Bitmap.createScaledBitmap(imagen, PIC_WIDTH,
				PIC_HEIGHT, false));
		
		pic.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
						.format(Calendar.getInstance().getTime()));
		
		dataArray.add(pic);
		
		this.notifyDataSetChanged();
*/		

	}

	public ListViewAdapter(Activity activity, ArrayList<InstagramPicture> dataArray, int idBarraProgreso,
			int idListView) {

		requestQueue = Volley.newRequestQueue(activity.getApplicationContext());

		this.dataArray = dataArray;
		this.inflater = LayoutInflater.from(activity.getApplicationContext());
		this.imageLoader = new ImageLoader(requestQueue, new BitmapLRUCache());

		barra = (ProgressBar) activity.findViewById(idBarraProgreso);
		lista = (ListView) activity.findViewById(idListView);
		
		ApiCall();
		
	};

	public void ApiCall() {

		if (dataArray.isEmpty()) {
			String url = HelperInstagram.getRecentMediaUrl("shopping_mall");

			Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					JSONArray data;
					try {
						data = response.getJSONArray("data");

						for (int i = 0; i < data.length(); i++) {
							JSONObject element = data.getJSONObject(i);

							String type = element.getString("type");

							if (type.equals("image")) {
								JSONObject caption = element
										.getJSONObject("caption");
								JSONObject images = element
										.getJSONObject("images");
								JSONObject standardResolution = images
										.getJSONObject("standard_resolution");

								int createdTime = Integer.parseInt(caption
										.getString("created_time"));
								String urlPic = standardResolution
										.getString("url");

								String fecha = new SimpleDateFormat(
										"dd/MM/yyyy HH:mm", Locale.getDefault())
										.format(new java.util.Date(
												(long) createdTime * 1000));
								
								InstagramPicture img = new InstagramPicture();
								
								img.setFecha(fecha);
								img.setFoto(urlPic);
								
								dataArray.add(img);
								
								
								notifyDataSetChanged();

							}
						}

					} catch (JSONException e) {
						Log.e("ERROR", Log.getStackTraceString(e));
					}

					lista.setVisibility(View.VISIBLE);
					barra.setVisibility(View.GONE);
				}
			};

			JsonObjectRequest request = new JsonObjectRequest(
					Request.Method.GET, url, null, listener, null);

			requestQueue.add(request);
		}

	}

	@Override
	public int getCount() {
		return dataArray.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		InstagramPicture current = dataArray.get(position);
		 
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_image, null);

			holder = new ViewHolder();

			holder.txt = (TextView) convertView.findViewById(R.id.listTexto);
			holder.img = (NetworkImageView)convertView.findViewById(R.id.listImagen);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.txt.setText(current.getFecha());
		holder.img.setImageUrl(current.getFoto(), imageLoader);

		return convertView;
	}

	static class ViewHolder {
		public NetworkImageView img;
		public TextView txt;
	}
}
