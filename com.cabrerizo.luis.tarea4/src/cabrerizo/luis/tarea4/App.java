package cabrerizo.luis.tarea4;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import cabrerizo.luis.tarea4.data.DBAdapter;
import cabrerizo.luis.tarea4.data.Data;
import cabrerizo.luis.tarea4.data.models.Store;
import cabrerizo.luis.tarea4.global.BitmapLRUCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.Marker;

public class App extends Application {

	private RequestQueue requestQueue;
	private ArrayList<Store> storeArray;
	private HashMap<Marker, String> markers;
	private ImageLoader imageLoader;
	private DBAdapter db;

	@Override
	public void onCreate() {
		super.onCreate();

		db = new DBAdapter(this);

		storeArray = new ArrayList<Store>();
		markers = new HashMap<Marker, String>();

		requestQueue = Volley.newRequestQueue(this);
		requestQueue.start();

		if (!db.hasValues("STORE")) {
			storeArray = Data.JsonToDatabase("data.json", this, db);
		} else {
			storeArray = Data.ParseFromDatabase(db);
		}

		imageLoader = new ImageLoader(requestQueue, new BitmapLRUCache());

	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public ArrayList<Store> getStoreArray() {
		return storeArray;
	}

	public void setStoreArray(ArrayList<Store> storeArray) {
		this.storeArray = storeArray;
	}

	public HashMap<Marker, String> getMarkers() {
		return markers;
	}

	public void setMarkers(HashMap<Marker, String> markers) {
		this.markers = markers;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	public DBAdapter getDb() {
		return db;
	}

	public void setDb(DBAdapter db) {
		this.db = db;
	}

}
