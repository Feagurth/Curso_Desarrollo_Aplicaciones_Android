package cabrerizo.luis.tarea3.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cabrerizo.luis.tarea3.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ListViewAdapter extends BaseAdapter {

	private static final int PIC_HEIGHT = 600;
	private static final int PIC_WIDTH = 300;

	private static ArrayList<Bitmap> arrayPics = new ArrayList<Bitmap>();
	private static ArrayList<String> arrayFecha = new ArrayList<String>();

	//private Resources resources;
	private LayoutInflater inflater;

	static RequestQueue requestQueue;
	static ProgressBar barra;
	static ListView lista;

	public void addImage(Bitmap imagen) {
		arrayPics
				.add(0, Bitmap.createScaledBitmap(imagen, PIC_WIDTH,
						PIC_HEIGHT, false));
		arrayFecha.add(0,
				new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
						.format(Calendar.getInstance().getTime()));

		this.notifyDataSetChanged();

	}

	public ListViewAdapter(Activity activity, int idBarraProgreso,
			int idListView) {

		requestQueue = Volley.newRequestQueue(activity.getApplicationContext());

		//this.resources = activity.getApplicationContext().getResources();
		this.inflater = LayoutInflater.from(activity.getApplicationContext());

		barra = (ProgressBar) activity.findViewById(idBarraProgreso);
		lista = (ListView) activity.findViewById(idListView);
		
		ApiCall();

		/*
		 arrayPics.add(0, decodeSampledBitmapFromResource(resources,
		 R.drawable.viewpager_imagen1, PIC_WIDTH, PIC_HEIGHT));
		 arrayPics.add(0, decodeSampledBitmapFromResource(resources,
		 R.drawable.viewpager_imagen2, PIC_WIDTH, PIC_HEIGHT));
		 arrayPics.add(0, decodeSampledBitmapFromResource(resources,
		 R.drawable.viewpager_imagen3, PIC_WIDTH, PIC_HEIGHT));
		 arrayPics.add(0, decodeSampledBitmapFromResource(resources,
		 R.drawable.viewpager_imagen4, PIC_WIDTH, PIC_HEIGHT));
		 arrayPics.add(0, decodeSampledBitmapFromResource(resources,
		 R.drawable.viewpager_imagen5, PIC_WIDTH, PIC_HEIGHT));
		 
		 arrayFecha.add(0, "22/12/2013 22:22"); arrayFecha.add(0,
		 "23/12/2013 23:23"); arrayFecha.add(0, "24/12/2013 24:24");
		 arrayFecha.add(0, "25/12/2013 25:25"); arrayFecha.add(0,
		 "26/12/2013 26:26");
		 */
		 

	};

	public static void ApiCall() {

		if (arrayPics.isEmpty()) {
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

								APITask tarea = new APITask();
								AsyncTask<String, Void, Bitmap> imagen = tarea
										.execute(urlPic);

								try {
									arrayPics.add(0, Bitmap.createScaledBitmap(
											imagen.get(), PIC_WIDTH,
											PIC_HEIGHT, false));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String fecha = new SimpleDateFormat(
										"dd/MM/yyyy HH:mm", Locale.getDefault())
										.format(new java.util.Date(
												(long) createdTime * 1000));

								arrayFecha.add(0, fecha);

								this.notifyDataSetChanged();

							}
						}

					} catch (JSONException e) {
						Log.e("ERROR", Log.getStackTraceString(e));
					}

					lista.setVisibility(View.VISIBLE);
					barra.setVisibility(View.GONE);

				}

				private void notifyDataSetChanged() {
					// TODO Auto-generated method stub

				}
			};

			JsonObjectRequest request = new JsonObjectRequest(
					Request.Method.GET, url, null, listener, null);

			lista.setVisibility(View.GONE);
			barra.setVisibility(View.VISIBLE);

			requestQueue.add(request);
		}

	}

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

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_image, null);

			holder = new ViewHolder();

			holder.txt = (TextView) convertView.findViewById(R.id.listTexto);
			holder.img = (ImageView) convertView.findViewById(R.id.listImagen);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

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
